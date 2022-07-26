package com.ideia.projetoideia.model.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ideia.projetoideia.model.FeedbackAvaliativo;
import com.ideia.projetoideia.model.Pitch;

import lombok.Data;

@Data
public class VersaoPitchDeckDto {
	
	private LocalDateTime data;
	private String arquivoPitchDeck;
	private String tipo;
	private List<FeedbackSugestaoDto> feedbacksAvaliativos;
	
	public VersaoPitchDeckDto(Pitch pitch, List<FeedbackAvaliativo> feedbacksAvaliativos) {
		
		this.feedbacksAvaliativos = new ArrayList<FeedbackSugestaoDto>();
		this.data = pitch.getDataCriacao();
		
		String tipo = "ARQUIVO";
		String arquivo = pitch.getPitchDeck();
		
		if(pitch.getPitchDeck() == null) {
			tipo = "VIDEO";
			arquivo = pitch.getVideo();
		}
		
		this.arquivoPitchDeck = arquivo;
		this.tipo = tipo;
		
		for(FeedbackAvaliativo feedback: feedbacksAvaliativos) {
			this.feedbacksAvaliativos.add(new FeedbackSugestaoDto( feedback.getTipoFeedback(), feedback.getSugestao()));
		}
	}

}
