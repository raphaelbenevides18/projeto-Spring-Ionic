package br.com.rlb.projetoSpringIonic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rlb.projetoSpringIonic.dto.ClienteDTO;
import br.com.rlb.projetoSpringIonic.dto.ClienteNewDTO;
import br.com.rlb.projetoSpringIonic.entity.Cidade;
import br.com.rlb.projetoSpringIonic.entity.Cliente;
import br.com.rlb.projetoSpringIonic.entity.Endereco;
import br.com.rlb.projetoSpringIonic.entity.TipoCliente;
import br.com.rlb.projetoSpringIonic.repository.ClienteRepository;
import br.com.rlb.projetoSpringIonic.repository.EnderecoRepository;
import br.com.rlb.projetoSpringIonic.service.exception.DataIntegrityException;
import br.com.rlb.projetoSpringIonic.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository dao;
	
	//@Autowired
	//private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = dao.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		}
	
	public List<Cliente> findAll(){
		return dao.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return dao.findAll(pageRequest);
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = dao.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	
	public Cliente update(Cliente obj) {
		Cliente newObj = findById(obj.getId());
		updateData(newObj, obj);
		return dao.save(newObj);
	}
	
	public void delete(Integer id) {
		findById(id);
		try {
			dao.deleteById(id);
		} catch (DataIntegrityViolationException  e) {
			throw new DataIntegrityException("Não é possivel excluir uma cliente com pedidos...");
			
		}
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(),objDto.getEmail(), null,null) ;
	}	
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(),objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		//Cidade cid = cidadeRepository.findOne(objDto.getCidadeId());
		Cidade cid = new Cidade(objDto.getCidadeId(),null,null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
