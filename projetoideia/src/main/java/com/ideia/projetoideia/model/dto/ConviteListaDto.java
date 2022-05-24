package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.Convite;
import com.ideia.projetoideia.model.enums.StatusConvite;
import com.ideia.projetoideia.model.enums.TipoConvite;

import lombok.Data;

@Data
public class ConviteListaDto {

	private Integer id;
	
	private TipoConvite tipoConvite;
	
	private StatusConvite statusConvite;
	
	private Integer idCompeticao;
	
	private String nomeCompeticao;

	public ConviteListaDto(Convite convite) {
		super();
		this.id = convite.getId();
		this.tipoConvite = convite.getTipoConvite();
		this.statusConvite = convite.getStatusConvite();
		this.idCompeticao = convite.getCompeticao().getId();
		this.nomeCompeticao = convite.getCompeticao().getNomeCompeticao();
	}
	
	
}
