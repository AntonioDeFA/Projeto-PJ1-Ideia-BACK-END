package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.QuestaoAvaliativa;
import com.ideia.projetoideia.model.enums.TipoQuestaoAvaliativa;

import lombok.Data;

@Data
public class QuestoesAvaliativasDto {

	private Integer id;

	private Integer notaMax;

	private String questao;

	private Integer enumeracao;

	private TipoQuestaoAvaliativa tipoQuestaoAvaliativa;

	public QuestoesAvaliativasDto(QuestaoAvaliativa questaoAvaliativa) {
		this.id = questaoAvaliativa.getId();
		this.notaMax = questaoAvaliativa.getNotaMax();
		this.questao = questaoAvaliativa.getQuestao();
		this.tipoQuestaoAvaliativa = questaoAvaliativa.getTipoQuestaoAvaliativa();
		this.enumeracao = questaoAvaliativa.getEnumeracao();
	}

}
