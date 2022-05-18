package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.enums.TipoConvite;

import lombok.Data;

@Data
public class ConviteRespostaDto {
	
	 private Integer idCompeticao;
	 
     private boolean aceito;
     
     private TipoConvite tipoConvite;
}
