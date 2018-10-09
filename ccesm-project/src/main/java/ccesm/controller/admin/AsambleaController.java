package ccesm.controller.admin;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ccesm.controller.BaseController;

@Controller
@RequestMapping("/admin/asamblea")
public class AsambleaController extends BaseController{
	private static final long serialVersionUID = -7453627320579188553L;

	@RequestMapping("/")
	public String view(HttpServletRequest request, ModelMap model){
		return "classic/administrador";
	}

}
