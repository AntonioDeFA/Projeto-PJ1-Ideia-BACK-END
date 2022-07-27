package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.QuestaoAvaliativa;

import lombok.Data;

@Data
public class QuestaoAvaliativaDto {
	
	private Integer id;
	
	private String questaoAvaliativa;
	
	private String tipoQuestaoAvaliativa;
	
	private Integer pontuacaoMaxima;
	
	
	public QuestaoAvaliativaDto (QuestaoAvaliativa questaoAvaliativa ) {
		this.id = questaoAvaliativa.getId();
		this.questaoAvaliativa = questaoAvaliativa.getQuestao();
		this.tipoQuestaoAvaliativa = questaoAvaliativa.getTipoQuestaoAvaliativa().getValue();
		this.pontuacaoMaxima = questaoAvaliativa.getNotaMax();
	}
	

}
