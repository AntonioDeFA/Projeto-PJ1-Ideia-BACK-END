package com.ideia.projetoideia.model;

import java.io.File;
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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "tb_pitch")
@Data
public class Pitch {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(nullable = false)
	@NotNull(message = "O arquivo não pode ser nulo.")
	private File video;
	
	@Column(nullable = false)
	private String titulo;
	
	@Column(nullable = false)
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	private EtapaArtefatoPitch etapaAvaliacaoVideo;
	
	@Column(nullable = false, name = "pitch_deck")
	private File pitchDeck;
	
	@ManyToOne
	@JoinColumn(name = "equipe_fk")
	private Equipe equipe;
	
	@OneToMany(mappedBy = "pitch", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<AvaliacaoPitch> avaliacaoPitch = new ArrayList<>();
	
	@OneToMany(mappedBy = "pitch", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<FeedbackAvaliativo> feedbackAvaliativos = new ArrayList<>();

}
