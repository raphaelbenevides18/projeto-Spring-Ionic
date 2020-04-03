package br.com.rlb.projetoSpringIonic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rlb.projetoSpringIonic.entity.Categoria;
import br.com.rlb.projetoSpringIonic.repository.CategoriaRepository;
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

}