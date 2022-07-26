package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.LeanCanvas;
import com.ideia.projetoideia.model.Pitch;

import lombok.Data;

@Data
public class EquipeConsultoriaDto {

	private String nomeEquipe;

	private Integer idEquipe;

	private Integer idLeanCanvas;

	private Integer idPitch;

	private Integer idCompeticao;

	public EquipeConsultoriaDto(Equipe equipe, LeanCanvas leanCanvas, Pitch pitch) {

		this.nomeEquipe = equipe.getNomeEquipe();

		this.idEquipe = equipe.getId();

		if (leanCanvas != null) {
			this.idLeanCanvas = leanCanvas.getId();
		}

		if (pitch != null) {
			this.idPitch = pitch.getId();
		}

		this.idCompeticao = equipe.getCompeticaoCadastrada().getId();

	}

}
