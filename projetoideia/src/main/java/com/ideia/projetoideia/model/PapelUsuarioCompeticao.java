package com.ideia.projetoideia.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "tb_papel_usuario_competicao")
@Data
public class PapelUsuarioCompeticao {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "usuario_fk")
	@JsonIgnore
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "competicao_fk")
	@JsonIgnore
	private Competicao competicaoCadastrada;

	@Enumerated(EnumType.STRING)
	private TipoPapelUsuario tipoPapelUsuario;

}
