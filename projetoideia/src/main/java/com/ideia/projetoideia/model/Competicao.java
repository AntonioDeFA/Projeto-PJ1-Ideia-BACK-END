package com.ideia.projetoideia.model;

import java.io.File;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="tb_competicao")
public class Competicao {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(nullable = false, name = "nome_competicao")
	private String nomeCompeticao;
	
	@Column(nullable = false, name = "qntd_maxima_membros_por_equipe")
	private Integer qntdMaximaMembrosPorEquipe;
	
	@Column(nullable = false, name = "qntd_minima_membros_por_equipe")
	private Integer qntdMinimaMembrosPorEquipe;
	
	@Column(nullable = false, name = "tempo_maximo_video")
	private Float tempoMaximoVideo;
	
	@Column(nullable = false, name = "arquivo_regulamento_competicao")
	private File arquivoRegulamentoCompeticao;
	
	@Column(name = "dominio_competicao")
	private String dominioCompeticao="";
	
	@OneToOne
	private Etapa etapa;
	
	
}
