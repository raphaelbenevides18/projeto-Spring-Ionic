package br.com.rlb.projetoSpringIonic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.rlb.projetoSpringIonic.entity.Categoria;
import br.com.rlb.projetoSpringIonic.entity.Produto;
import br.com.rlb.projetoSpringIonic.repository.CategoriaRepository;
import br.com.rlb.projetoSpringIonic.repository.ProdutoRepository;
import br.com.rlb.projetoSpringIonic.service.exception.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository dao;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto findById(Integer id) {
		Optional<Produto> obj = dao.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return dao.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);	
	}
}
	
