package com.ideia.projetoideia.model.dto;

import java.io.File;
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
	
	private File arquivoRegulamentoCompeticao;
	
	private Float tempoMaximoVideoEmSeg;
	
	private List<MaterialEstudo> materiaisDeEstudo;
	
	private boolean isElaboracao;
	
	
}
