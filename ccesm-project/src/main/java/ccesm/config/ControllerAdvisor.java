package ccesm.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {
     @ExceptionHandler(Exception.class)
     public String handle(HttpServletRequest req, Exception e) {
    	 return "error";
	 }

}