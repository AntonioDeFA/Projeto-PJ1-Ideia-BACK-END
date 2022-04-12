package com.ideia.projetoideia.model;

public enum TipoPapelUsuario {

	ORGANIZADOR("ORGANIZADOR"), COMPETIDOR("COMPETIDOR"), CONSULTOR("CONSULTOR"), AVALIADOR("AVALIADOR");

	private String value;

	TipoPapelUsuario(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
