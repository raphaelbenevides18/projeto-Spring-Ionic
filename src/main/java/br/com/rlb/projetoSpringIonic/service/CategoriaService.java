package br.com.rlb.projetoSpringIonic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.rlb.projetoSpringIonic.entity.Categoria;
import br.com.rlb.projetoSpringIonic.repository.CategoriaRepository;
import br.com.rlb.projetoSpringIonic.service.exception.DataIntegrityException;
import br.com.rlb.projetoSpringIonic.service.exception.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository dao;
	
	public Categoria findById(Integer id) {
		Optional<Categoria> obj = dao.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		}
	
	public List<Categoria> findAll(){
		return dao.findAll();
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return dao.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		findById(obj.getId());
		return dao.save(obj);
	}
	
	public void delete(Integer id) {
		findById(id);
		try {
			dao.deleteById(id);
		} catch (DataIntegrityViolationException  e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria com produtos...");
			
		}
	}

}
