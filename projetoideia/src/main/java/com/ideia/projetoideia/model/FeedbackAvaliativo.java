package com.ideia.projetoideia.model;

import java.time.LocalDateTime;

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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ideia.projetoideia.model.enums.TipoFeedback;

import lombok.Data;

@Entity
@Table(name = "tb_feedback_avaliativo")
@Data
public class FeedbackAvaliativo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private LocalDateTime dataCriacao;
	
	@Enumerated(EnumType.STRING)
	private TipoFeedback tipoFeedback;
	
	@Column(nullable = false)
	@NotNull(message = "Você deve escrever qual alguma sugestão")
	@Size(min = 5,message = "Deve ter no mínimo 5 caracteres")
	private String sugestao;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "pitch_fk")
	private Pitch pitch;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "consultor_fk")
	private Usuario consultor;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "lean_canvas_fk")
	private LeanCanvas leanCanvas;
}
