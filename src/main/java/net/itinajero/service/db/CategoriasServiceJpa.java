package net.itinajero.service.db;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import net.itinajero.model.Categoria;
import net.itinajero.repository.CategoriasRepository;
import net.itinajero.service.ICategoriasService;

@Service
@Primary
public class CategoriasServiceJpa implements ICategoriasService {

	@Autowired
	private CategoriasRepository categoriasRepo;
		
	public void guardar(Categoria categoria) {
		categoriasRepo.save(categoria);
	}
	
	public List<Categoria> buscarTodas() {
		return categoriasRepo.findAll();
	}
	
	public Categoria buscarPorId(Integer idCategoria) {
		Optional<Categoria> optional = categoriasRepo.findById(idCategoria);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public void eliminar(Integer idCategoria) {
		categoriasRepo.deleteById(idCategoria);		
	}
	
	public List<Categoria> buscarByExample(Example<Categoria> example) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Page<Categoria> buscarTodas(Pageable page) {
		return categoriasRepo.findAll(page);
	}
		
}
