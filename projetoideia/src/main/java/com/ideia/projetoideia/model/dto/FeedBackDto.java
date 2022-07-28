package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.enums.TipoFeedback;

import lombok.Data;

@Data
public class FeedBackDto {

	private TipoFeedback tipoFeedback;

	private String sugestao;

	private String tipoArtefato;

	private Integer idArtefato;

}
