package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.Usuario;

import lombok.Data;

@Data
public class EquipeNomeDto {

	private Integer id;
	
	private String nome;
	
	private String consultor;

	public EquipeNomeDto(Integer id, String nome, Usuario consultor) {
		this.id = id;
		this.nome = nome;
		
		if (consultor != null) {
			this.consultor = consultor.getNomeUsuario();
		} else {
			this.consultor = null;
		}
		
	}
	
}
