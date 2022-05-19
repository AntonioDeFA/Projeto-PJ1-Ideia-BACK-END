package com.ideia.projetoideia.model.dto;

import java.util.List;

import com.ideia.projetoideia.model.Etapa;
import com.ideia.projetoideia.model.MaterialEstudo;
import com.ideia.projetoideia.model.QuestaoAvaliativa;

import lombok.Data;

@Data
public class CompeticaoPatchDto {
	
	private String nomeCompeticao;
	
	private List<Etapa> etapas;
	
	private List<QuestaoAvaliativa> questoesAvaliativas;
	
	private String arquivoRegulamentoCompeticao;
	
	private Float tempoMaximoVideoEmSeg;
	
	private List<MaterialEstudo> materiaisDeEstudo;
	
	private boolean isElaboracao;
	
	
}
