package com.ideia.projetoideia.model;

public enum StatusConvite {
	
	ENVIADO("ENVIADO"),RECUSADO("RECUSADO"),ACEITO("ACEITO");

	private String value;

	StatusConvite(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}

}
