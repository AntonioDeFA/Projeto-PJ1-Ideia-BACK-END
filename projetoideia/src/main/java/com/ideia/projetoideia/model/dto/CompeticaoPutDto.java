package com.ideia.projetoideia.model.dto;

import java.util.List;

import com.ideia.projetoideia.model.Etapa;

import lombok.Data;

@Data
public class CompeticaoPutDto {
	
	private String nomeCompeticao;
	
	private Integer qntdMaximaMembrosPorEquipe;
	
	private Integer qntdMinimaMembrosPorEquipe;

	private Float tempoMaximoVideoEmSeg;
	
	private String dominioCompeticao = "";

	private String arquivoRegulamentoCompeticao;
	
	private List<Etapa> etapas;
	

}
