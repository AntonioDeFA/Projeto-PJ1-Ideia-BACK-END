package com.ideia.projetoideia.model.enums;

public enum EtapaArtefatoPitch {

	EM_ELABORACAO("EM_ELABORACAO"), EM_CONSULTORIA("EM_CONSULTORIA"), EM_AVALIACAO("EM_AVALIACAO"),
	APROVADO("APROVADO");

	private String value;

	EtapaArtefatoPitch(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
