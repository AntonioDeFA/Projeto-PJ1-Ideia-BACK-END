package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.enums.TipoConvite;

import lombok.Data;

@Data
public class ConviteDto {
	
	private String emailDoUsuario;
	
	private Integer idCompeticao;
	
	private TipoConvite tipoConvite;

}
