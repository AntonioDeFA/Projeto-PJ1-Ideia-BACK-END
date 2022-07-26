package com.ideia.projetoideia.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ideia.projetoideia.model.LeanCanvas;

import lombok.Data;

@Data
public class FeedbacksAvaliativosDto {
	
	private String problema;

	private String solucao;
	
	private String vantagemCompetitiva;

	private String metricasChave;

	private String propostaDeValor;
		
	private String canais;
	
	private String segmentosClientes;
	
	private String estruturaDeCustos;
	
	private String fonteDeReceita;
	
	private LocalDateTime dataHoraUltimoFeedbackInformado;
	
	private List<FeedbackSugestaoDto> feedbacksAvaliativos;

	public FeedbacksAvaliativosDto(LeanCanvas leanCanvas, List<FeedbackSugestaoDto> feedbacksAvaliativos) {
		this.problema = leanCanvas.getProblema();
		this.solucao = leanCanvas.getSolucao();
		this.vantagemCompetitiva = leanCanvas.getVantagemCompetitiva();
		this.metricasChave = leanCanvas.getMetricasChave();
		this.propostaDeValor = leanCanvas.getPropostaValor();
		this.canais = leanCanvas.getCanais();
		this.segmentosClientes = leanCanvas.getSegmentosDeClientes();
		this.estruturaDeCustos = leanCanvas.getEstruturaDeCusto();
		this.fonteDeReceita = leanCanvas.getFontesDeReceita();
		this.feedbacksAvaliativos = feedbacksAvaliativos;
	}

	public FeedbacksAvaliativosDto(LeanCanvas leanCanvas, List<FeedbackSugestaoDto> feedbacksAvaliativos, LocalDateTime dataHoraUltimoFeedbackInformado) {
		this.problema = leanCanvas.getProblema();
		this.solucao = leanCanvas.getSolucao();
		this.vantagemCompetitiva = leanCanvas.getVantagemCompetitiva();
		this.metricasChave = leanCanvas.getMetricasChave();
		this.propostaDeValor = leanCanvas.getPropostaValor();
		this.canais = leanCanvas.getCanais();
		this.segmentosClientes = leanCanvas.getSegmentosDeClientes();
		this.estruturaDeCustos = leanCanvas.getEstruturaDeCusto();
		this.fonteDeReceita = leanCanvas.getFontesDeReceita();
		this.feedbacksAvaliativos = feedbacksAvaliativos;
		this.dataHoraUltimoFeedbackInformado = dataHoraUltimoFeedbackInformado;
	}

	
}
