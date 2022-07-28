package com.ideia.projetoideia.model.dto;

import java.time.LocalDateTime;

import com.ideia.projetoideia.model.enums.TipoFeedback;

import lombok.Data;

@Data
public class FeedbackSugestaoDto {

	private TipoFeedback tipoFeedback;

	private String sugestao;

	private LocalDateTime dataCriacao;
	
	private Integer idFeedback;

	public FeedbackSugestaoDto(TipoFeedback tipoFeedback, String sugestao, LocalDateTime dataCriacao, Integer idFeedback) {
		super();
		this.tipoFeedback = tipoFeedback;
		this.sugestao = sugestao;
		this.dataCriacao = dataCriacao;
		this.idFeedback = idFeedback;
	}

	public FeedbackSugestaoDto(TipoFeedback tipoFeedback, String sugestao) {
		super();
		this.tipoFeedback = tipoFeedback;
		this.sugestao = sugestao;
	}
}
