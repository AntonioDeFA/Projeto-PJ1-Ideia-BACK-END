package com.ideia.projetoideia.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ideia.projetoideia.model.enums.EtapaArtefatoPitch;

import lombok.Data;

@Entity
@Table(name = "tb_lean_canvas")
@Data
public class LeanCanvas {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
)
	private String problema;
	
	
	@Column(nullable = false)
	@Size(min = 3)
	private String solucao;

	@Column(name = "metricas_chave" )
	private String metricasChave;

	@Column(name = "proposta_valor")
	private String propostaValor;

	@Column(name = "vantagem_competitiva")
	private String vantagemCompetitiva;
	

	private String canais;

	@Column(name = "segmentos_de_clientes")
	private String segmentosDeClientes;

	@Column(name = "estrutura_de_custo")
	private String estruturaDeCusto;

	@Column(name = "fontes_de_receita")
	private String fontesDeReceita;

	@Enumerated(EnumType.STRING)
	private EtapaArtefatoPitch etapaSolucaoCanvas;

	@ManyToOne
	@JoinColumn(name = "equipe_fk")
	private Equipe equipe;
	
	@OneToMany(mappedBy = "leanCanvas", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<FeedbackAvaliativo> feedbackAvaliativos = new ArrayList<>();
	
	
	public LeanCanvas(LeanCanvas canvas) {
		
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
	
	public LeanCanvas() {}
	

}
