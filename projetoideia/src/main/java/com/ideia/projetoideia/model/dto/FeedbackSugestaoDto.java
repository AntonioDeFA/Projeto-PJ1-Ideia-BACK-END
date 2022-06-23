package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.enums.TipoFeedback;

import lombok.Data;

@Data
public class FeedbackSugestaoDto {

	private TipoFeedback tipoFeedback;

	private String sugestao;

	public FeedbackSugestaoDto(TipoFeedback tipoFeedback, String sugestao) {
		super();
		this.tipoFeedback = tipoFeedback;
		this.sugestao = sugestao;
	}
}
