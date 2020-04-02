package br.com.rlb.projetoSpringIonic;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.rlb.projetoSpringIonic.entity.Categoria;
import br.com.rlb.projetoSpringIonic.entity.Produto;
import br.com.rlb.projetoSpringIonic.repository.CategoriaRepository;

@SpringBootApplication
public class projetoSpringIonicApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public static void main(String[] args) {
		SpringApplication.run(projetoSpringIonicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null,"Informatica");
		Categoria cat2 = new Categoria(null,"Escritorio");
		
		//Produto p1 = new Produto(null,"computador",2000.);
		//Produto p2 = new Produto(null,"imoressora",800.);
		//Produto p3 = new Produto(null,"mouser",80.);
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		
		
	}
	
	

}
