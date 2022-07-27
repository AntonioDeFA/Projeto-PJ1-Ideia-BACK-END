package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.Equipe;

import lombok.Data;

@Data
public class EquipeAvaliacaoDto {

	private String nomeEquipe;
	private Integer idEquipe;

	public EquipeAvaliacaoDto(Equipe equipe) {
		super();
		this.nomeEquipe = equipe.getNomeEquipe();
		this.idEquipe = equipe.getId();
	}

}
