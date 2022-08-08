package com.ideia.projetoideia.model.dto;

import javax.persistence.Lob;

import com.ideia.projetoideia.model.Pitch;

import lombok.Data;

@Data
public class PitchDto {
	
	@Lob
	private String arquivoPitchDeck;
	
	private String tipo;
	
	private String titulo;
	
	private String descricao;
	
	
	public PitchDto() {}
	
	public PitchDto(Pitch pitch) {
		
		this.tipo = "ARQUIVO";
		this.arquivoPitchDeck = pitch.getPitchDeck();
		
		if(pitch.getPitchDeck() == null) {
			this.tipo = "VIDEO";
			this.arquivoPitchDeck = pitch.getVideo();
		}
		
		this.titulo = pitch.getTitulo();
		this.descricao = pitch.getDescricao();
	}
}
