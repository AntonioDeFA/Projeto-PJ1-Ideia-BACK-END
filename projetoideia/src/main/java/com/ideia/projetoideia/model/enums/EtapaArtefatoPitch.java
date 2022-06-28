package com.ideia.projetoideia.model.enums;

public enum EtapaArtefatoPitch {

	EM_ELABORACAO("EM_ELABORACAO"), EM_CONSULTORIA("EM_CONSULTORIA"), EM_AVALIACAO("EM_AVALIACAO"),
	AVALIADO_CONSULTOR("AVALIADO_CONSULTOR"),AVALIADO_AVALIADOR("AVALIADO_AVALIADOR");

	private String value;

	EtapaArtefatoPitch(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
