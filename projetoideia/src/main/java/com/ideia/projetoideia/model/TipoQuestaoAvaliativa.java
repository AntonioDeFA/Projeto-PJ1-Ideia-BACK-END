package com.ideia.projetoideia.model;

public enum TipoQuestaoAvaliativa {
	
	ADAPTABILIDADE("ADAPTABILIDADE"), INOVACAO("INOVACAO"), UTILIDADE("UTILIDADE"),
	SUSTENTABILIDADE("SUSTENTABILIDADE");

	private String value;

	TipoQuestaoAvaliativa(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
