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

import lombok.Data;

@Entity
@Table(name = "tb_equipe")
@Data
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

	@ManyToOne
	@JoinColumn(name = "competicao_fk")
	private Competicao competicaoCadastrada;

	@OneToMany(mappedBy = "equipe", cascade = CascadeType.REMOVE)
	private List<LeanCanvas> canvasDaEquipe = new ArrayList<>();
	
	@OneToMany(mappedBy = "equipe", cascade = CascadeType.REMOVE)
	private List<UsuarioMembroComum> usuarios = new ArrayList<>();

}
