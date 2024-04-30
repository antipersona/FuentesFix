package es.ucm.fdi.iw.controller;

import es.ucm.fdi.iw.LocalData;
import es.ucm.fdi.iw.model.Message;
import es.ucm.fdi.iw.model.Transferable;
import es.ucm.fdi.iw.model.User;
import es.ucm.fdi.iw.model.User.Role;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.handler.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;


import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.*;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@Controller 
public class chatController{

    @Autowired
    private EntityManager entityManager;

    @Autowired
	private SimpMessagingTemplate messagingTemplate;

	@ModelAttribute
    private void isLogged(Model model, HttpSession session) {
        User user = (User) session.getAttribute("u");
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("logged", true);
        } else {
            model.addAttribute("logged", false);
        }
    }

    @GetMapping(path = "/chat")
	public String printAllMessages( Model model, HttpSession session) {
		System.out.println("chat page");
        List<Message> allMessages = entityManager.createQuery(
            "SELECT m FROM Message m ORDER BY m.dateSent DESC", Message.class)
            .getResultList();
		
		List<Message.Transfer> transferMessages = allMessages.stream()
             .map(Transferable::toTransfer)
             .collect(Collectors.toList());
		model.addAttribute("messages", transferMessages);

		return  "chat";//allMessages.stream().map(Transferable::toTransfer).collect(Collectors.toList());
    }
    

//@ModelAttribute
//@Transactional
    @GetMapping(path = "/getmsg")//, produces = "application/json")
	@ResponseBody
	public List<Message.Transfer> printAllMessages( HttpSession session) {//ResponseEntity<String>
		System.out.println("chat page");
        List<Message> allMessages = entityManager.createQuery(
            "SELECT m FROM Message m ORDER BY m.dateSent DESC", Message.class)
            .getResultList();
		return  allMessages.stream().map(Transferable::toTransfer).collect(Collectors.toList());
    }
    
    /**
     * Posts a message to a user.
     * @param id of target user (source user is from ID)
     * @param o JSON-ized message, similar to {"message": "text goes here"}
     * @throws JsonProcessingException
     */

	//does not work, wrong type of data received (at least :(
    @PostMapping(path = "/chat", produces = "application/json")//before : /{id}/msg
	@ResponseBody
	@Transactional   //before : @PathVariable long id,
	public Message.Transfer postMsg( @RequestBody JsonNode o, Model model, HttpSession session) 
		throws JsonProcessingException {
		
		String text = o.get("message").asText();
        User sender = (User) session.getAttribute("u");
		
		// construye mensaje, lo guarda en BD
		Message m = new Message();
		//m.setRecipient(u);
		m.setSender(sender);
		m.setDateSent(LocalDateTime.now());
		m.setText(text);
		entityManager.persist(m);
		entityManager.flush(); // to get Id before commit
		
		ObjectMapper mapper = new ObjectMapper();
		
		// persiste objeto a json usando Jackson
		String json = mapper.writeValueAsString(m.toTransfer());

		//log.info("Sending a message to {} with contents '{}'", userId, json);

        messagingTemplate.convertAndSend("/topic/chat", json);
		//messagingTemplate.convertAndSend("/user/"+u.getUsername()+"/queue/updates", json);
		return m.toTransfer();//"{\"result\": \"message sent.\"}";
	}
}