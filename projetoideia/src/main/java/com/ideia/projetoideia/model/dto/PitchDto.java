package com.ideia.projetoideia.model.dto;

import javax.persistence.Lob;

import lombok.Data;

@Data
public class PitchDto {
	
	@Lob
	private String arquivoPitchDeck;
	
	private String tipo;
	
	private String titulo;
	
	private String descricao;
}
