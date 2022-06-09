package com.ideia.projetoideia.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ideia.projetoideia.model.MaterialEstudo;
import com.ideia.projetoideia.model.enums.TipoMaterialEstudo;

import lombok.Data;

@Data
public class MaterialEstudoEnvioDto {
	
	private Integer id;
	private TipoMaterialEstudo tipoMaterialEstudo;
	
	@JsonInclude(value = Include.NON_NULL)
	private String link;
	
	@JsonInclude(value = Include.NON_NULL)
	private String arquivoEstudo;
	
	private Boolean isConcluido;
	
	private Float estimativaTempoConsumoMinutos;
	
	private String dicaEstudoMaterial;
	
	private String nomeMaterial;
	
	public MaterialEstudoEnvioDto(MaterialEstudo materialEstudo , Boolean isConcluido) {
		this.id = materialEstudo.getId();
		this.tipoMaterialEstudo = materialEstudo.getTipoMaterialEstudo();
		this.link = materialEstudo.getLink();
		this.arquivoEstudo = materialEstudo.getArquivoEstudo();	
		this.isConcluido = isConcluido;
		this.estimativaTempoConsumoMinutos = materialEstudo.getEstimativaTempoConsumoMinutos();
		this.dicaEstudoMaterial = materialEstudo.getDicaEstudoMaterial();
		this.nomeMaterial = materialEstudo.getNomeMaterial();
	}
	

}
