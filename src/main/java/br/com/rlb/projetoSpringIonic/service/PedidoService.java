package br.com.rlb.projetoSpringIonic.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rlb.projetoSpringIonic.entity.EstadoPagamento;
import br.com.rlb.projetoSpringIonic.entity.ItemPedido;
import br.com.rlb.projetoSpringIonic.entity.PagamentoBoleto;
import br.com.rlb.projetoSpringIonic.entity.Pedido;
import br.com.rlb.projetoSpringIonic.repository.ItemPedidoRepository;
import br.com.rlb.projetoSpringIonic.repository.PagamentoRepository;
import br.com.rlb.projetoSpringIonic.repository.PedidoRepository;
import br.com.rlb.projetoSpringIonic.service.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository dao;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido findById(Integer id) {
		Optional<Pedido> obj = dao.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		}
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setDataPedido(new Date());
		obj.setCliente(clienteService.findById(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoBoleto) {
			PagamentoBoleto pagto = (PagamentoBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getDataPedido());
		}
		obj = dao.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.findById(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
	
}
