package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.enums.EtapaArtefatoPitch;

import lombok.Data;

@Data
public class AtualizarArtefatoDto {

	private Integer idArtefato;
	
	private String tipoArtefato;
	
	private EtapaArtefatoPitch novaEtapa;
	
}
