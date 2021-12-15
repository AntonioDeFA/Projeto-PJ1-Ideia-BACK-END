package com.ideia.projetoideia.model;

import java.time.LocalDate;

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
@Data
@Table(name = "tb_equipe")
public class Equipe {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	@Column(nullable = false, name = "nome_equipe")
	private String nomeEquipe;
	@Column(nullable = false)
	private String token;
	@Column(nullable = false)
	private LocalDate dataInscricao;
	@OneToOne
	@JoinColumn(name = "lider_fk")
	private Usuario lider;

}
