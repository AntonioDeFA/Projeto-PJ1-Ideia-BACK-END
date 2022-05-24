package com.ideia.projetoideia.model.dto;

import lombok.Data;

@Data
public class ConvitesquantidadeDto {
	
	private Integer quantidade;

	public ConvitesquantidadeDto(Integer quantidade) {
		super();
		this.quantidade = quantidade;
	}
	
	public void agregar() {
		quantidade++;
	}
}
