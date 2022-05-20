package com.ideia.projetoideia.model.dto;

import lombok.Data;

@Data
public class EquipeNomeDto {

	private Integer id;
	
	private String nome;

	public EquipeNomeDto(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
}
