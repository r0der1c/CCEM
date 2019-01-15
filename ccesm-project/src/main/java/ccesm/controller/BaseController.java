package ccesm.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ccesm.util.WebUtility;

@Controller
@RequestMapping("/")
public class BaseController extends WebUtility implements Serializable{
	private static final long serialVersionUID = -7453627320579188553L;

	@RequestMapping("/login")
	public String login(HttpServletRequest request, ModelMap model){
		return "login";
	}

	@RequestMapping("/home")
	public String home(HttpServletRequest request, ModelMap model){
		return "home";
	}

}
