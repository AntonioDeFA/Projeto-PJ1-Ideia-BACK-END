package com.ideia.projetoideia.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.ideia.projetoideia.model.Pitch;

import lombok.Data;

@Data
public class FeedbacksAvaliativosPitchDto {
	
	
	private List<VersaoPitchDeckDto> listaVersoesPitch;
	
	public FeedbacksAvaliativosPitchDto (List<Pitch> listaVersoesPitch) {
		
		this.listaVersoesPitch = new ArrayList<VersaoPitchDeckDto>();
		for(Pitch pitch: listaVersoesPitch) {
			
			this.listaVersoesPitch.add(new VersaoPitchDeckDto(pitch, pitch.getFeedbackAvaliativos()));
		}
	}
}
