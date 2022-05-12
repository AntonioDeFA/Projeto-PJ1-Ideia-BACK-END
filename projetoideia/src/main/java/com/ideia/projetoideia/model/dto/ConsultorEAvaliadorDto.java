package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.enums.StatusConvite;

import lombok.Data;

@Data
public class ConsultorEAvaliadorDto {
	
	private String email;
	
	private Integer idUsuario;
	
	private StatusConvite statusConvite;
	
	public ConsultorEAvaliadorDto(Usuario usuario , StatusConvite statusConvite) {
		
		this.email = usuario.getEmail();
		this.idUsuario = usuario.getId();
		this.statusConvite = statusConvite;
		
		
	}
}
