package com.ideia.projetoideia.model;

import java.time.LocalDateTime;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ideia.projetoideia.model.enums.EtapaArtefatoPitch;

import lombok.Data;

@Entity
@Table(name = "tb_pitch")
@Data
public class Pitch {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Lob
	private String video;

	@Column(nullable = false)
	@NotNull(message = "Você deve escrever qual um titulo")
	private String titulo;

	@Column(nullable = false)
	@NotNull(message = "Você deve escrever qual uma descrição")
	private String descricao;

	@Column(name = "data", nullable = false)
	private LocalDateTime dataCriacao;

	@Enumerated(EnumType.STRING)
	private EtapaArtefatoPitch etapaAvaliacaoVideo;

	@Lob
	@Column(name = "pitch_deck")
	private String pitchDeck;

	@ManyToOne
	@JoinColumn(name = "equipe_fk")
	private Equipe equipe;

	@OneToMany(mappedBy = "pitch", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<AvaliacaoPitch> avaliacaoPitch = new ArrayList<>();

	@OneToMany(mappedBy = "pitch", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<FeedbackAvaliativo> feedbackAvaliativos = new ArrayList<>();

	public Pitch() {
	}

	public Pitch(Pitch pitch) {
		super();
		this.video = pitch.getVideo();
		this.titulo = pitch.getTitulo();
		this.descricao = pitch.getDescricao();
		this.etapaAvaliacaoVideo = pitch.getEtapaAvaliacaoVideo();
		this.pitchDeck = pitch.getPitchDeck();
		this.equipe = pitch.getEquipe();
		this.avaliacaoPitch = pitch.getAvaliacaoPitch();
		this.feedbackAvaliativos = pitch.getFeedbackAvaliativos();
	}

}
