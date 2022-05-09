package com.ideia.projetoideia.model.enums;

public enum TipoMaterialEstudo {

	LINK("LINK"), VIDEO("VIDEO"), PDF("PDF");

	private String value;

	TipoMaterialEstudo(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
