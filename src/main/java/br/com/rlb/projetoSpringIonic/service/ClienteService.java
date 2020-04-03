package br.com.rlb.projetoSpringIonic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rlb.projetoSpringIonic.entity.Cliente;
import br.com.rlb.projetoSpringIonic.repository.ClienteRepository;
import br.com.rlb.projetoSpringIonic.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository dao;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = dao.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		}

}
