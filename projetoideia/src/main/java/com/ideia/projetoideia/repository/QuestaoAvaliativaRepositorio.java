package com.ideia.projetoideia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.QuestaoAvaliativa;

public interface QuestaoAvaliativaRepositorio extends JpaRepository<QuestaoAvaliativa, Integer>{
	
	
	public List<QuestaoAvaliativa> findByCompeticaoCadastrada(Competicao competicaoCadastrada);
	
	Optional<QuestaoAvaliativa> findByQuestao(String questao);

}
