package com.ideia.projetoideia.model.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class NotasEquipeDto {
	
	private Integer notaAtribuidaAdaptabilidade;
	
	private Integer notaMaximaAdaptabilidade;
	
	private Integer notaAtribuidaInovacao;
	
	private Integer notaMaximaInovacao;
	
	private Integer notaAtribuidaUtilidade;
	
	private Integer notaMaximaUtilidade;
	
	private Integer notaAtribuidaSustentabilidade;
	
	private Integer notaMaximaSustentabilidade;
	
	
	private List<NotaQuestaoDto> listaAdaptabilidade = new ArrayList<NotaQuestaoDto>();
	
	private List<NotaQuestaoDto> listaInovacao = new ArrayList<NotaQuestaoDto>();
	
	private List<NotaQuestaoDto> listaUtilidade = new ArrayList<NotaQuestaoDto>();
	
	private List<NotaQuestaoDto> listaSustentabilidade = new ArrayList<NotaQuestaoDto>();
}
