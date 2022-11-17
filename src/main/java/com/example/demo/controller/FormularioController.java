package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.interfaceService.IaprendizService;
import com.example.demo.interfaceService.IformularioService;
import com.example.demo.interfaceService.IusuarioService;
import com.example.demo.model.Aprendiz;
import com.example.demo.model.Formulario;
import com.example.demo.model.Usuario;


@Controller
@RequestMapping("/formulario")
public class FormularioController {
	
	@Autowired
	private IformularioService iformularioService;
	
	@Autowired
	private IaprendizService iaprendizService;
	
	@Autowired
	private IusuarioService service;
	
	private final Logger log= LoggerFactory.getLogger(Admincontroller.class);
	
	
	@GetMapping("/listar")
	public String listarFormulario(Model model, HttpSession session, Usuario usuario) {
		log.info("sesion del usuario: {}", session.getAttribute("idusuario"));
		List<Usuario>usuarios=service.listar();
		model.addAttribute("usuarios", usuarios);
		
		List<Aprendiz>lstaprendiz=iaprendizService.listar();
		model.addAttribute("lstaprendiz", lstaprendiz);
		List<Formulario>formularios=iformularioService.listar();
		model.addAttribute("formularios", formularios);
		return "formulario/VistaFormulario";
	}
	
	@GetMapping("/salida")
	public String salida(Model model, HttpSession session, Usuario usuario) {
		log.info("sesion del usuario: {}", session.getAttribute("idusuario"));
		List<Usuario>usuarios=service.listar();
		model.addAttribute("usuarios", usuarios);
		
		List<Aprendiz>lstaprendiz=iaprendizService.listar();
		model.addAttribute("lstaprendiz", lstaprendiz);
		List<Formulario>formularios=iformularioService.listar();
		model.addAttribute("formularios", formularios);
		return "formulario/salida";
	}
	
	@GetMapping("/new")
	public String nuevoFormulario(Model model, Formulario formulario) {
		List<Aprendiz>lstaprendiz=iaprendizService.listar();
		model.addAttribute("lstaprendiz", lstaprendiz);
		model.addAttribute("formulario", new Formulario());
		return "formulario/form";
		
	}
	
	@GetMapping("/success")
	public String nuevoFormularios(Model model, Formulario formulario) {
		List<Aprendiz>lstaprendiz=iaprendizService.listar();
		model.addAttribute("lstaprendiz", lstaprendiz);
		model.addAttribute("formulario", new Formulario());
		return "formulario/form_success";
		
	}
	
	@GetMapping("/good")
	public String nuevoFormularioValidado(Model model, Formulario formulario) {
		List<Aprendiz>lstaprendiz=iaprendizService.listar();
		model.addAttribute("lstaprendiz", lstaprendiz);
		model.addAttribute("formulario", new Formulario());
		return "formulario/form_good";
		
	}
	
	@PostMapping("/save")
	public String saveFormulario(Formulario f) {
		iformularioService.save(f);
		return "redirect:/formulario/success";
	}
	
	@GetMapping("/edit/{id}")
	public String editFormulario(@PathVariable int id, Model model) {
		Optional<Formulario>formulario=iformularioService.listarId(id);
		model.addAttribute("formulario", formulario);
		return "editarFormulario";
		
	}
}
