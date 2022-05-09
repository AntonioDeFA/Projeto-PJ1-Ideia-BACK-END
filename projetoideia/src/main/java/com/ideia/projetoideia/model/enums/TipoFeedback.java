package com.ideia.projetoideia.model.enums;

public enum TipoFeedback {
	
	POTENCIALIDADE("POTENCIALIDADE"), FRAGILIDADE("FRAGILIDADE");
	
	private String value;

	TipoFeedback(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
