package com.ideia.projetoideia.model.dto;

import lombok.Data;

@Data
public class EquipeNotaDto {
	
	private String nome;
	
	private Float notaAtribuida;
	
	private Float notaMaximaCompeticao;
	
	private Integer id;

	public EquipeNotaDto(String nome, Float notaAtribuida, Float notaMaximaCompeticao, Integer id) {
		super();
		this.nome = nome;
		this.notaAtribuida = notaAtribuida;
		this.notaMaximaCompeticao = notaMaximaCompeticao;
		this.id = id;
	}

}
