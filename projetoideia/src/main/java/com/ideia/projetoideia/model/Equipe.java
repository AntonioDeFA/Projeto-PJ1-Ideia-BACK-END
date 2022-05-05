package com.ideia.projetoideia.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "tb_equipe")
@Data
public class Equipe {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(nullable = false, name = "nome_equipe")
	@Size(min=3,max=16, message = "O nome do equipe deve ter entre 3 e 50 caracteres.")
	private String nomeEquipe;

	@Column(nullable = false)
	private String token;

	@Column(nullable = false)
	private LocalDate dataInscricao;

	@OneToOne
	@JoinColumn(name = "lider_fk")
	private Usuario lider;
	
	@OneToOne
	@JoinColumn(name = "avaliador_fk")
	private Usuario avaliador;
	
	@ManyToOne
	@JoinColumn(name = "competicao_cadastrada_fk")
	private Competicao competicaoCadastrada;

	@OneToMany(mappedBy = "equipe", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Pitch> pitchDaEquipe = new ArrayList<>();
	
	@OneToMany(mappedBy = "equipe", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<LeanCanvas> canvasDaEquipe = new ArrayList<>();
	
	@OneToMany(mappedBy = "equipe", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<UsuarioMembroComum> usuarios = new ArrayList<>();

}
