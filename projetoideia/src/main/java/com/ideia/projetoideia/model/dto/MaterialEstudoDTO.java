package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.MaterialEstudo;
import com.ideia.projetoideia.model.enums.TipoMaterialEstudo;

import lombok.Data;

@Data
public class MaterialEstudoDTO {

	private Integer id;
	
	private TipoMaterialEstudo tipoMaterialEstudo;
	
	private String link;
	
	private String arquivoEstudo;
	
	private String nome;

	public MaterialEstudoDTO(MaterialEstudo materialEstudo) {
		super();
		this.id = materialEstudo.getId();
		this.tipoMaterialEstudo = materialEstudo.getTipoMaterialEstudo();
		this.link = materialEstudo.getLink();
		this.arquivoEstudo = materialEstudo.getArquivoEstudo();
		this.nome = materialEstudo.getNomeMaterial();
	}
	
}
