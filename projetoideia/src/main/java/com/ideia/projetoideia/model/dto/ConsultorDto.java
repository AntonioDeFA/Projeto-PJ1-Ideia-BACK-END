package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.enums.StatusConvite;

import lombok.Data;

@Data
public class ConsultorDto {
	
	private String emaiConsultor;
	
	private Integer idUsuario;
	
	private StatusConvite statusConvite;
	
	public ConsultorDto(Usuario usuario , StatusConvite statusConvite) {
		
		this.emaiConsultor = usuario.getEmail();
		this.idUsuario = usuario.getId();
		this.statusConvite = statusConvite;
		
		
	}
}
