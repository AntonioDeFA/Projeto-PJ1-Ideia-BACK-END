package com.ideia.projetoideia.model.dto;

import java.util.List;

import com.ideia.projetoideia.model.FeedbackAvaliativo;

import lombok.Data;

@Data
public class FeedbacksAvaliativosDto {

	private String parceriasPrincipais;
	
	private String atividadesPrincipais;
	
	private String recursosPrincipais;
	
	private String propostaDeValor;
	
	private String relacionamentoComClientes;
	
	private String canais;
	
	private String segmentosClientes;
	
	private String estruturaDeCustos;
	
	private String fonteDeReceita;
	
	private List<FeedbackAvaliativo> feedbacksAvaliativos;

	public FeedbacksAvaliativosDto(String parceriasPrincipais, String atividadesPrincipais, String recursosPrincipais,
			String propostaDeValor, String relacionamentoComClientes, String canais, String segmentosClientes,
			String estruturaDeCustos, String fonteDeReceita, List<FeedbackAvaliativo> feedbacksAvaliativos) {
		super();
		this.parceriasPrincipais = parceriasPrincipais;
		this.atividadesPrincipais = atividadesPrincipais;
		this.recursosPrincipais = recursosPrincipais;
		this.propostaDeValor = propostaDeValor;
		this.relacionamentoComClientes = relacionamentoComClientes;
		this.canais = canais;
		this.segmentosClientes = segmentosClientes;
		this.estruturaDeCustos = estruturaDeCustos;
		this.fonteDeReceita = fonteDeReceita;
		this.feedbacksAvaliativos = feedbacksAvaliativos;
	}
	
	
}
