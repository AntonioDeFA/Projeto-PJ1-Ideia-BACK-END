package com.ideia.projetoideia.model.dto;

import lombok.Data;

@Data
public class PitchArquivoPitchDeckDto {
	
	private String arquivoPitchDeck;

	public PitchArquivoPitchDeckDto(String arquivoPitchDeck) {
		super();
		this.arquivoPitchDeck = arquivoPitchDeck;
	}

}
