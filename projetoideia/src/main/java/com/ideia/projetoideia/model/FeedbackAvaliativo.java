package com.ideia.projetoideia.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tb_feedback_avaliativo")
@Data
public class FeedbackAvaliativo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(nullable = false)
	private Date dataCriaca;
	
	@Enumerated(EnumType.STRING)
	private TipoFeedback tipoFeedback;
	
	@Column(nullable = false)
	private String sugestao;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "pitch_fk")
	private Pitch pitch;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "consultor_fk")
	private Usuario consultor;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "lean_canvas_fk")
	private LeanCanvas leanCanvas;
}
