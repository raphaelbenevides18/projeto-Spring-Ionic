package br.com.rlb.projetoSpringIonic.entity;

public class PagamentoCartao extends Pagamento{

	private static final long serialVersionUID = 1L;
	
	private Integer numDeParcelas;
	
	public PagamentoCartao() {
		
	}

	public PagamentoCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numDeParcelas) {
		super(id, estado, pedido);
		this.numDeParcelas = numDeParcelas;
	}

	public Integer getNumDeParcelas() {
		return numDeParcelas;
	}

	public void setNumDeParcelas(Integer numDeParcelas) {
		this.numDeParcelas = numDeParcelas;
	}
	
	

}
