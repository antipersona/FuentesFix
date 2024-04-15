// package es.ucm.fdi.iw;

// import java.io.IOException;
// import java.util.Collection;

// import javax.persistence.EntityManager;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import javax.servlet.http.HttpSession;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpHeaders;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
// import org.springframework.stereotype.Component;

// import es.ucm.fdi.iw.model.User;
// import es.ucm.fdi.iw.model.User.Role;

// @Component
// @Transactional
// public class RegisterSuccessHandler implements AuthenticationSuccessHandler {
    
//     @Autowired 
//     private HttpSession session;
    
//     @Autowired
//     private EntityManager entityManager;   

//     private static Logger log = LogManager.getLogger(RegisterSuccessHandler.class);

//     @Override
// 	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

//         addSameSiteCookieAttribute(response);
//     }
// }