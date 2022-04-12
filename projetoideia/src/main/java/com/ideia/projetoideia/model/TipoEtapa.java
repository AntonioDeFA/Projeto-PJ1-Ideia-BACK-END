package com.ideia.projetoideia.model;

public enum TipoEtapa {
	
	INSCRICAO("INSCRICAO"), AQUECIMENTO("AQUECIMENTO"), IMERSAO("IMERSAO"), PITCH("PITCH"), ENCERRADA("ENCERRADA");

	private String value;

	TipoEtapa(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
