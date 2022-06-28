package com.ideia.projetoideia.model.dto;

import java.time.LocalDateTime;

import com.ideia.projetoideia.model.FeedbackAvaliativo;
import com.ideia.projetoideia.model.LeanCanvas;

import lombok.Data;

@Data
public class LeanCanvasAprovadoConsultoriaDto implements Comparable<LeanCanvasAprovadoConsultoriaDto> {

	private Integer idLeanCanvas;

	private LocalDateTime dataHoraAprovacao;

	public LeanCanvasAprovadoConsultoriaDto(LeanCanvas canvas) {

		idLeanCanvas = canvas.getId();

		FeedbackAvaliativo back = canvas.getFeedbackAvaliativos().get(canvas.getFeedbackAvaliativos().size() - 1);

		dataHoraAprovacao = back.getDataCriacao();

	}

	@Override
	public int compareTo(LeanCanvasAprovadoConsultoriaDto dto) {
		if (this.getDataHoraAprovacao().isBefore(dto.getDataHoraAprovacao())) {
			return 1;
		}
		
		else if(this.getDataHoraAprovacao().isAfter(dto.dataHoraAprovacao)) {
			return -1;
		}
		
		return 0;
	}
	
}
