package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.MaterialEstudo;
import com.ideia.projetoideia.model.enums.TipoMaterialEstudo;

import lombok.Data;

@Data
public class MaterialEstudoDTO {

	private Integer id;
	
	private TipoMaterialEstudo tipoMaterialEstudo;
	
	private String link;
	
	private byte[] arquivoEstudo;

	public MaterialEstudoDTO(MaterialEstudo materialEstudo) {
		super();
		this.id = materialEstudo.getId();
		this.tipoMaterialEstudo = materialEstudo.getTipoMaterialEstudo();
		this.link = materialEstudo.getLink();
		this.arquivoEstudo = materialEstudo.getArquivoEstudo();
	}
	
}
