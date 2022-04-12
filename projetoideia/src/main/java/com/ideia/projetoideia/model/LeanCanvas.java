package com.ideia.projetoideia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tb_lean_canvas")
@Data
public class LeanCanvas {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	private String problema;

	private String solucao;

	@Column(name = "metricas_chave")
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

	private EtapaArtefatoPitch etapaSolucaoCanvas;

	@ManyToOne
	@JoinColumn(name = "equipe_fk")
	private Equipe equipe;

}
