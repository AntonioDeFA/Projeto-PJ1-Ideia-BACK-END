package com.ideia.projetoideia;


public enum TipoConvite {

	CONSULTOR("CONSULTOR"), AVALIADOR("AVALIADOR");

	private String value;

	TipoConvite(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
