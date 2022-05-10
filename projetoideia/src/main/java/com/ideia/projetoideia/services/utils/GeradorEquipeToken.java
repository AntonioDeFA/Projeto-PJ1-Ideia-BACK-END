package com.ideia.projetoideia.services.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.RandomStringUtils;

public class GeradorEquipeToken {

	public static String gerarTokenEquipe(String nomeEquipe) {
		
		String token = RandomStringUtils.randomAlphanumeric(30).toUpperCase() + nomeEquipe.replaceAll(" ", "").toUpperCase();
		
		try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] textBytes = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < textBytes.length; i++) {
             buffer.append(Integer.toString((textBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            token = buffer.toString();
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
		
		return token.toUpperCase();
	}
}
