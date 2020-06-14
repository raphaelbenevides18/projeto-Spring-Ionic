package br.com.rlb.projetoSpringIonic.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public Pedido findById(Integer id) {
		Optional<Pedido> obj = dao.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setDataPedido(new Date());
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
			ip.setPreco(produtoService.findById(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
	
}
