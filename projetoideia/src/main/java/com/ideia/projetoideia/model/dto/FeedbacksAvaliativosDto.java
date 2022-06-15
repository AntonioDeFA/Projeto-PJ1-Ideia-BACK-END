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
}
