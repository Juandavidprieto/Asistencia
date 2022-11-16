package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.interfaces.IArea;
import com.example.demo.model.Aprendiz;
import com.example.demo.model.Area;
import com.example.demo.model.Usuario;
import com.example.demo.service.AprendizService;
import com.example.demo.service.UploadFileService;


@Controller
@RequestMapping("/aprendiz")
public class AprendizController {
	
	@Autowired
	private AprendizService aprendizService;
	
	@Autowired
	private IArea iArea;
	
	@Autowired
	private UploadFileService upload;
	
	@GetMapping("/listar")
	public String listarAprendiz(Model model) {
		List<Aprendiz>aprendices=aprendizService.listar();
		model.addAttribute("aprendices", aprendices);
		return "aprendiz/vistaAprendiz";
	}
	
	@GetMapping("/new")
	public String nuevoAprendiz(Model model) {
		List<Area>lstArea= (List<Area>)iArea.findAll();
		model.addAttribute("lstArea", lstArea);
		model.addAttribute("aprendiz", new Aprendiz());
		return "aprendiz/formulario";
		
	}
	
	@GetMapping("/nuevo")
	public String nuevoAprendices(Model model) {
		List<Area>lstArea= (List<Area>)iArea.findAll();
		model.addAttribute("lstArea", lstArea);
		model.addAttribute("aprendiz", new Aprendiz());
		return "aprendiz/formout";
		
	}
	
	@PostMapping("/save")
	public String guardarAprendiz(@Validated int id, Aprendiz a, Model model,@RequestParam("img") MultipartFile file) throws IOException {
		List<Area>lstArea= (List<Area>)iArea.findAll();
		model.addAttribute("lstArea", lstArea);
		
		//imagen
		if (a.getId()==0) { // cuando se crea un producto
			String nombreImagen= upload.saveImage(file);
			a.setImagen(nombreImagen);
		}else {
			
		}
		
		aprendizService.save(a);
		return "redirect:/aprendiz/listar";
	}
	
	@PostMapping("/guardar")
	public String guardarAprendices(@Validated int id, Aprendiz a, Model model,@RequestParam("img") MultipartFile file) throws IOException {
		List<Area>lstArea= (List<Area>)iArea.findAll();
		model.addAttribute("lstArea", lstArea);
		
		//imagen
				if (a.getId()==0) { // cuando se crea un producto
					String nombreImagen= upload.saveImage(file);
					a.setImagen(nombreImagen);
				}else {
					
				}
				
		aprendizService.save(a);
		return "redirect:/formulario/good";
	}
	
	@GetMapping("/edit/{id}")
	public String editarAprendiz(@PathVariable int id, Model model) {
		List<Area>lstArea= (List<Area>)iArea.findAll();
		model.addAttribute("lstArea", lstArea);
		Optional<Aprendiz>aprendiz=aprendizService.listarId(id);
		model.addAttribute("aprendiz", aprendiz);
		return "aprendiz/update";
		
	}
	
	@PostMapping("/update")
	public String update(Aprendiz aprendiz, @RequestParam("img") MultipartFile file ) throws IOException {
		Aprendiz u= new Aprendiz();
		u=aprendizService.listarId(aprendiz.getId()).get();
		
		if (file.isEmpty()) { // editamos el producto pero no cambiamos la imagem
			
			aprendiz.setImagen(u.getImagen());
		}else {// cuando se edita tbn la imagen			
			//eliminar cuando no sea la imagen por defecto
			if (!u.getImagen().equals("default.jpg")) {
				upload.deleteImage(u.getImagen());
			}
			String nombreImagen= upload.saveImage(file);
			aprendiz.setImagen(nombreImagen);
		}
		aprendizService.update(aprendiz);		
		return "redirect:/aprendiz/listar";
	}
	
	@RequestMapping("/estadoAprendiz")
    public String estadoAprendiz(
    @RequestParam(name = "id", defaultValue = "0") Integer id) {
        Aprendiz aux = aprendizService.listarId(id).orElse(null);
        if(aux.getEstado().equals("activo")){
            aux.setEstado("inactivo");
            aprendizService.save(aux);
        } else if(aux.getEstado().equals("inactivo")){
            aux.setEstado("activo");
            aprendizService.save(aux);
        }
         return "redirect:/aprendiz/listar";
    }
}
