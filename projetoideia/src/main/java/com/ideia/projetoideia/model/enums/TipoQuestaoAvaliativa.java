package com.ideia.projetoideia.model.enums;

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
