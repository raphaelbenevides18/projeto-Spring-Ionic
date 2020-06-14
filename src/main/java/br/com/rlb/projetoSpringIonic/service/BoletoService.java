package br.com.rlb.projetoSpringIonic.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.rlb.projetoSpringIonic.entity.PagamentoBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoBoleto pagto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}
}
