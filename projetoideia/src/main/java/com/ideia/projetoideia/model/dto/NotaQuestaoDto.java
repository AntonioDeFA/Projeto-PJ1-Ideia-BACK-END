package com.ideia.projetoideia.model.dto;

import lombok.Data;

@Data
public class NotaQuestaoDto {
	
	private String avaliador;
	
	private String questao;
	
	private String comentario;
	
	private Integer nota;
	
	private Integer notaMax;

}
