package com.ideia.projetoideia.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tb_avaliacao_pitch")
@Data
public class AvaliacaoPitch {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(nullable = false, name = "data_avaliacao")
	private Date dataAvaliacao;
	
	@Column(nullable = false, name = "nota_atribuida")
	private Float notaAtribuida;
	
	@Column(nullable = false)
	private String observacao;
	
	@OneToOne
	@JoinColumn(name = "pitch_fk")
	private Pitch pitch;
	
	@OneToOne
	@JoinColumn(name = "questao_avaliativa_fk")
	private QuestaoAvaliativa questaoAvaliativa;
	
	@OneToOne
	@JoinColumn(name = "avaliador_fk")
	private Usuario avaliador;
	
}
