package com.ideia.projetoideia.model;

import java.io.File;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;

@Entity
@Data
@Table(name = "tb_competicao")
public class Competicao {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(nullable = false, name = "nome_competicao")
	private String nomeCompeticao;

	@Column(nullable = false, name = "qntd_maxima_membros_por_equipe")
	@Max(value = 30)
	@Min(value = 1)
	private Integer qntdMaximaMembrosPorEquipe;
	
	@Column(nullable = false, name = "qntd_minima_membros_por_equipe")
	@Max(value = 29)
	@Min(value = 1)
	private Integer qntdMinimaMembrosPorEquipe;

	@Column(nullable = false, name = "tempo_maximo_video")
	@Min(value = 30)
	@Max(value = 1200)
	private Float tempoMaximoVideoEmSeg;

	@Column(nullable = false, name = "arquivo_regulamento_competicao")
	private File arquivoRegulamentoCompeticao;

	@Column(name = "dominio_competicao")
	private String dominioCompeticao = "";

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "organizador_fk")
	private Usuario organizador;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "etapa_fk")
	private Etapa etapa;

//	@OneToMany(mappedBy = "id")
//	private List<Equipe> equipesCadatradas;

}
