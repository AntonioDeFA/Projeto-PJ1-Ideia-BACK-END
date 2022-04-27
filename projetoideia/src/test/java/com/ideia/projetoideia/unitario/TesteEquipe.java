package com.ideia.projetoideia.unitario;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ideia.projetoideia.ProjetoideiaApplication;
import com.ideia.projetoideia.repository.EquipeRepositorio;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration
@SpringBootTest(classes = ProjetoideiaApplication.class)
public class TesteEquipe {

	@Autowired
	private EquipeRepositorio equipeRepositorio;
	
	public void criarEquipe() {
		
	}
	
	public void atualizarEquipe() {
		
	}
	
	public void deletarEquipe() {
		
	}
	
}
