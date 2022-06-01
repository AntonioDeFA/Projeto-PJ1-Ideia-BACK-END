package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.LeanCanvas;
import com.ideia.projetoideia.model.enums.EtapaArtefatoPitch;

import lombok.Data;

@Data
public class LeanCanvasDto {
	
	private Integer id;
	
	private String problema;
	
	private String solucao;

	private String metricasChave;
	
	private String propostaValor;
	
	private String vantagemCompetitiva;
	
	private String canais;
	
	private String segmentosDeClientes;
	
	private String estruturaDeCusto;
	
	private String fontesDeReceita;
	
	private EtapaArtefatoPitch etapaSolucaoCanvas;
	
	
	
	public LeanCanvasDto(LeanCanvas canvas) {
		this.id = canvas.getId();
		this.problema = canvas.getProblema();
		this.solucao = canvas.getSolucao();
		this.metricasChave = canvas.getMetricasChave();
		this.propostaValor = canvas.getPropostaValor();
		this.vantagemCompetitiva =canvas.getVantagemCompetitiva();
		this.canais = canvas.getCanais();
		this.segmentosDeClientes = canvas.getSegmentosDeClientes();
		this.estruturaDeCusto = canvas.getEstruturaDeCusto();
		this.fontesDeReceita = canvas.getFontesDeReceita();
		this.etapaSolucaoCanvas = canvas.getEtapaSolucaoCanvas();
		
	}
}
