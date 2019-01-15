package ccesm.controller.admin;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ccesm.controller.BaseController;
import ccesm.dao.admin.AsambleaDao;

@Controller
@RequestMapping("/admin/asamblea")
public class AsambleaController extends BaseController{
	private static final long serialVersionUID = -7453627320579188553L;
	@Resource private AsambleaDao asambleaDao;

	@RequestMapping("/")
	public String view(HttpServletRequest request, ModelMap model){
		HashMap<String, Object> map = new HashMap<>();
		map.put("idgrp", 1L);
		model.put("asambleas", toJson(asambleaDao.list(getQueryByName(AsambleaDao.GET_ASAMBLEAS_GRUPO), map)));
		return "classic/administrador/asamblea";
	}

	@RequestMapping("/getServiciosAsamblea")
	public void getServiciosAsamblea(HttpServletResponse response, @RequestParam Long idasm){
		HashMap<String, Object> map = new HashMap<>();
		map.put("idgrp", 1L);
		map.put("idasm", idasm);
		map.put("list", asambleaDao.list(getQueryByName(AsambleaDao.GET_SERVICIOS_ASAMBLEA), map));
		serialize(map, response);
	}

}
