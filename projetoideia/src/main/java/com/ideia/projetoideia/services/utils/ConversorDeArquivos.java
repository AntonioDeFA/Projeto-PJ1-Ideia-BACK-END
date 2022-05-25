package com.ideia.projetoideia.services.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import com.ideia.projetoideia.model.MaterialEstudo;
import com.ideia.projetoideia.model.enums.TipoMaterialEstudo;

public class ConversorDeArquivos {

	public static String converterStringParaArquivo(String file, Integer idCompeticao) throws Exception{
		
		try {
			
			File diretorio = new File("regulamentos");
			
			if(!diretorio.exists()) {
				diretorio.mkdirs();
			}
			
			File arquivo = new File ("regulamentos/regulamento"+idCompeticao+".pdf");
			
			if(arquivo.exists()) {
				arquivo.delete();
				arquivo = new File ("regulamentos/regulamento"+idCompeticao+".pdf");
			}
			
			FileOutputStream fos = new FileOutputStream (arquivo);
						
			byte[] array = Base64.getDecoder().decode(file);
			
			fos.write (array);
			fos.close(); 
			
		    
			return arquivo.getAbsolutePath();
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception("Erro ao gerar e salvar arquivo");
		}
	    	    
	}
	
	public static void converterStringParaArquivo(List<MaterialEstudo> materiais, Integer idCompeticao) throws Exception{
		
		try {
			
			File diretorio = new File("materiais");
			
			if(!diretorio.exists()) {
				diretorio.mkdirs();
			}
			
			File diretorioCompeticao = new File("materiais/competicao_"+idCompeticao);
			
			if(!diretorioCompeticao.exists()) {
				diretorioCompeticao.mkdirs();
			}else {
				File[] files = diretorioCompeticao.listFiles();
				for (File fileDelete : files) {
					fileDelete.delete();
				}
			}
			
			for(MaterialEstudo material: materiais) {
				
				if(material.getTipoMaterialEstudo() != TipoMaterialEstudo.LINK) {
					
					byte[] array = Base64.getDecoder().decode(material.getArquivoEstudo());
					
					if(material.getTipoMaterialEstudo() == TipoMaterialEstudo.PDF) {
						
						File arquivo = new File ("materiais/competicao_"+idCompeticao+"/material"+RandomStringUtils.randomAlphanumeric(10).toUpperCase()+".pdf");
						FileOutputStream fos = new FileOutputStream (arquivo);
						
						fos.write (array);
						fos.close(); 
						
					}else {
						
						File arquivo = new File ("materiais/competicao_"+idCompeticao+"/material"+RandomStringUtils.randomAlphanumeric(10).toUpperCase()+".pdf");
						FileOutputStream fos = new FileOutputStream (arquivo);
						
						fos.write (array);
						fos.close(); 
					}
					
					
				}
				
				
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new Exception("Erro ao gerar e salvar arquivo");
		}
	    	    
	}
}
