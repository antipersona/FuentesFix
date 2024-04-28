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
    
    /**
     * Returns JSON with all received messages
     */
    // @GetMapping(path = "received", produces = "application/json")//change the path to chat, maybe change the get to @messsageMapping
	// @Transactional // para no recibir resultados inconsistentes
	// @ResponseBody  // para indicar que no devuelve vista, sino un objeto (jsonizado)
	// public List<Message.Transfer> retrieveMessages(HttpSession session) {
	// 	long userId = ((User)session.getAttribute("u")).getId();		
	// 	User u = entityManager.find(User.class, userId);
	// 	log.info("Generating message list for user {} ({} messages)", 
	// 			u.getUsername(), u.getReceived().size());
	// 	return  u.getReceived().stream().map(Transferable::toTransfer).collect(Collectors.toList());
	// }	
    

    // /**
    //  * Returns JSON with count of unread messages 
    //  */
	// @GetMapping(path = "unread", produces = "application/json")
	// @ResponseBody
	// public String checkUnread(HttpSession session) {
	// 	long userId = ((User)session.getAttribute("u")).getId();		
	// 	long unread = entityManager.createNamedQuery("Message.countUnread", Long.class)
	// 		.setParameter("userId", userId)
	// 		.getSingleResult();
	// 	session.setAttribute("unread", unread);
	// 	return "{\"unread\": " + unread + "}";
    // }


    @GetMapping(path = "/chat", produces = "application/json")
	@ResponseBody
	public List<Message.Transfer> printAllMessages(HttpSession session) {
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
    // @PostMapping("/chat")//before : /{id}/msg
	// @ResponseBody
	// @Transactional   //before : @PathVariable long id,
	// public String postMsg( @RequestBody JsonNode o, Model model, HttpSession session) 
	// 	throws JsonProcessingException {
		
	// 	String text = o.get("message").asText();
    //     long userId = ((User) session.getAttribute("u")).getId();
    //     User sender = entityManager.find(User.class, userId);
		
		
	// 	model.addAttribute("user", sender);
		
	// 	// construye mensaje, lo guarda en BD
	// 	Message m = new Message();
	// 	//m.setRecipient(u);
	// 	m.setSender(sender);
	// 	m.setDateSent(LocalDateTime.now());
	// 	m.setText(text);
	// 	entityManager.persist(m);
	// 	entityManager.flush(); // to get Id before commit
		
	// 	ObjectMapper mapper = new ObjectMapper();
		
	// 	// persiste objeto a json usando Jackson
	// 	String json = mapper.writeValueAsString(m.toTransfer());

	// 	//log.info("Sending a message to {} with contents '{}'", userId, json);

    //     messagingTemplate.convertAndSend("/topic/chat", json);
	// 	//messagingTemplate.convertAndSend("/user/"+u.getUsername()+"/queue/updates", json);
	// 	return "{\"result\": \"message sent.\"}";
	// }
}