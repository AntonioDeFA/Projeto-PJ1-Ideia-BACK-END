package com.ideia.projetoideia.controller;

import com.ideia.projetoideia.model.Competicao;

import lombok.Data;

@Data
public class CompeticaoPitchImersaoDto {

	private Integer idCompeticao;

	private String nomeCompeticao;

	public CompeticaoPitchImersaoDto(Competicao competicao) {
		super();
		this.idCompeticao = competicao.getId();
		this.nomeCompeticao = competicao.getNomeCompeticao();
	}

}
