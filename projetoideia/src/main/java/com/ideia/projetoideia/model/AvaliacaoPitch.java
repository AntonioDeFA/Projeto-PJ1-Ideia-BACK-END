package com.ideia.projetoideia.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "tb_avaliacao_pitch")
@Data
public class AvaliacaoPitch {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(nullable = false, name = "data_avaliacao")
	private LocalDate dataAvaliacao;

	@Column(nullable = false, name = "nota_atribuida")
	@NotNull(message = "Você deve escrever qual uma nota")
	private Integer notaAtribuida;

	@Column(nullable = false)
	@NotNull(message = "Você deve escrever qual uma observação")
	private String observacao;

	@OneToOne
	@JoinColumn(name = "pitch_fk")
	private Pitch pitch;

	@ManyToOne
	@JoinColumn(name = "questao_avaliativa_fk")
	private QuestaoAvaliativa questaoAvaliativa;

	@OneToOne
	@JoinColumn(name = "avaliador_fk")
	private Usuario avaliador;

	public AvaliacaoPitch(LocalDate dataAvaliacao, Integer notaAtribuida, String observacao, Pitch pitch,
			QuestaoAvaliativa questaoAvaliativa, Usuario avaliador) {
		super();
		this.dataAvaliacao = dataAvaliacao;
		this.notaAtribuida = notaAtribuida;
		this.observacao = observacao;
		this.pitch = pitch;
		this.questaoAvaliativa = questaoAvaliativa;
		this.avaliador = avaliador;
	}

}
