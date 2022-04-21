package com.ideia.projetoideia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "tb_usuario_membro_comum")
public class UsuarioMembroComum {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "equipe_fk")
	private Equipe equipe;
	
}
