package com.ideia.projetoideia.model;

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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ideia.projetoideia.model.enums.TipoQuestaoAvaliativa;

import lombok.Data;

@Entity
@Table(name = "tb_questao_avaliativa")
@Data
public class QuestaoAvaliativa {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(nullable = false, name = "nota_maxima")
	@NotNull(message = "Você deve colocar qual a nota máxima")
	@Min(value = 5, message = "A nota mínima é 5")
	private Integer notaMax;
	
	@Column(nullable = false)
	@NotNull(message = "Você deve escrever qual a questão")
	private String questao;
	
	@Column(nullable = false)
	private Integer enumeracao;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "O tipo questao avaliativa não pode ser nulo")
	private TipoQuestaoAvaliativa tipoQuestaoAvaliativa;
	
	@ManyToOne
	@JoinColumn(name = "competicao_fk")
	private Competicao competicaoCadastrada;
	
	@OneToMany(mappedBy = "questaoAvaliativa", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<AvaliacaoPitch> avaliacaoPitch = new ArrayList<>();
	
}
