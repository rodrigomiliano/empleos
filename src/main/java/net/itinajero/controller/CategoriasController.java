package net.itinajero.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import net.itinajero.model.Categoria;
import net.itinajero.model.Vacante;
import net.itinajero.service.ICategoriasService;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasController {

	@Autowired
	//@Qualifier("categoriasServiceJpa")
	private ICategoriasService serviceCategorias;		
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Categoria> lista = serviceCategorias.buscarTodas();
		model.addAttribute("categorias", lista);
		return "categorias/listCategorias";
	}		
	
	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
	   Page<Categoria> lista = serviceCategorias.buscarTodas(page);
	   model.addAttribute("categorias", lista);
	   return "categorias/listCategorias";
	}
		
	@GetMapping("/create")
	public String crear(Categoria categoria) {
	return "categorias/formCategoria";
	}
			
	@PostMapping("/save")
	public String guardar(Categoria categoria,BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			for (ObjectError error: result.getAllErrors()){
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
				}
			return "categorias/formCategoria";
			}	
		
		// Guardamos el objeto categoria en la bd
		serviceCategorias.guardar(categoria);
		attributes.addFlashAttribute("msg", "Los datos de la categoría fueron guardados!");
		System.out.println("Categoria: " + categoria);		
		return "redirect:/categorias/index";
	}	
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idCategoria, Model model) {		
		Categoria categoria = serviceCategorias.buscarPorId(idCategoria);			
		model.addAttribute("categoria", categoria);
		return "categorias/formCategoria";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idCategoria, RedirectAttributes attributes) {		
		// Eliminamos la categoria.
		serviceCategorias.eliminar(idCategoria);			
		attributes.addFlashAttribute("msg", "La categoría fue eliminada!.");
		return "redirect:/categorias/index";
	}
}
