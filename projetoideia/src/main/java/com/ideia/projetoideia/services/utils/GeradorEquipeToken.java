package com.ideia.projetoideia.services.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class GeradorEquipeToken {

	public static String gerarTokenEquipe(String nomeEquipe) {
		
		return RandomStringUtils.randomAlphanumeric(20).toUpperCase() + nomeEquipe.replaceAll(" ", "").toUpperCase();
	}
}
