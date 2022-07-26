package com.ideia.projetoideia.model.dto;

import java.time.LocalDateTime;

import com.ideia.projetoideia.model.enums.TipoFeedback;

import lombok.Data;

@Data
public class FeedbackSugestaoDto {

	private TipoFeedback tipoFeedback;

	private String sugestao;

	private LocalDateTime dataCriacao;

	public FeedbackSugestaoDto(TipoFeedback tipoFeedback, String sugestao, LocalDateTime dataCriacao) {
		super();
		this.tipoFeedback = tipoFeedback;
		this.sugestao = sugestao;
		this.dataCriacao = dataCriacao;
	}

	public FeedbackSugestaoDto(TipoFeedback tipoFeedback, String sugestao) {
		super();
		this.tipoFeedback = tipoFeedback;
		this.sugestao = sugestao;
	}
}
