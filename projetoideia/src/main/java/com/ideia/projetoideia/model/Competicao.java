package com.ideia.projetoideia.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "tb_competicao")
@Data
public class Competicao {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(nullable = false, name = "nome_competicao")
	@Size(min = 3, max = 16, message = "O nome competição deve ter entre 3 e 16 caracteres.")
	private String nomeCompeticao;

	@Column(nullable = false, name = "qntd_maxima_membros_por_equipe")
	@Max(value = 30, message = "Quantidade de membros por equipe não pode ser maior que 30.")
	@Min(value = 1, message = "Quantiade de membros por equipe não pode ser menos que 1.")
	private Integer qntdMaximaMembrosPorEquipe;

	@Column(nullable = false, name = "qntd_minima_membros_por_equipe")
	@Max(value = 29, message = "Quantidade de membros por equipe não pode ser maior que 30.")
	@Min(value = 1, message = "Quantiade de membros por equipe não pode ser menos que 1.")
	private Integer qntdMinimaMembrosPorEquipe;

	@Column(nullable = false, name = "tempo_maximo_video")
	@Min(value = 30, message = "Tempo minimo de vídeo não pode ser menor que 30 segundos.")
	@Max(value = 1800, message = "Tempo máximo de vídeo não pode ser maior que 30 minutos.")
	private Float tempoMaximoVideoEmSeg;

	@Column(nullable = false, name = "arquivo_regulamento_competicao")
	@NotNull(message = "O arquivo não pode ser nulo.")
	private File arquivoRegulamentoCompeticao;

	@Column(name = "dominio_competicao")
	private String dominioCompeticao = "";

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "organizador_fk")
	private Usuario organizador;

	@OneToMany(mappedBy = "competicao", cascade = CascadeType.REMOVE)
	private List<Etapa> etapas = new ArrayList<>();

	@OneToMany(mappedBy = "competicaoCadastrada", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Equipe> equipesCadastradas = new ArrayList<>();

	@OneToMany(mappedBy = "competicaoCadastrada", cascade = CascadeType.REMOVE)
	private List<PapelUsuarioCompeticao> papeisUsuarioCompeticao = new ArrayList<>();

	@OneToMany(mappedBy = "competicaoCadastrada", cascade = CascadeType.REMOVE)
	private List<QuestaoAvaliativa> questoesAvaliativas = new ArrayList<>();

}
