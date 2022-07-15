package com.ideia.projetoideia.services.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;


public class Validador {

	public static void validarDuracaoVideoPitch (String video) throws Exception{
		
		try {
			
			File arquivo = new File("video.mp4");
			
			FileOutputStream fos = new FileOutputStream (arquivo);
			
			byte[] array = Base64.getDecoder().decode(video);
			
			fos.write (array);
			fos.close(); 
			
			//TODO - Aqui deve ser feita a validação de duração do vídeo
			

			
		}catch (Exception e) {
			System.out.println("DEU ERRO");
		}
		
		
		
	
	}
}
