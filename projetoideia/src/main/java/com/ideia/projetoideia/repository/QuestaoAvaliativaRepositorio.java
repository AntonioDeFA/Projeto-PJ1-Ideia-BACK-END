package com.ideia.projetoideia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.QuestaoAvaliativa;

public interface QuestaoAvaliativaRepositorio extends JpaRepository<QuestaoAvaliativa, Integer>{
	
	
	public List<QuestaoAvaliativa> findByCompeticaoCadastrada(Competicao competicaoCadastrada);

}
