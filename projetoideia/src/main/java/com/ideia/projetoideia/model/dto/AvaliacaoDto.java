package com.ideia.projetoideia.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvaliacaoDto {
	
	private Integer notaAtribuida;
	private Integer idQuestao;
	private String observacao;
	
	public AvaliacaoDto(Integer notaAtribuida, Integer idQuestao, String observacao) {
		this.notaAtribuida = notaAtribuida;
		this.idQuestao = idQuestao;
		this.observacao = observacao;
	}
}
