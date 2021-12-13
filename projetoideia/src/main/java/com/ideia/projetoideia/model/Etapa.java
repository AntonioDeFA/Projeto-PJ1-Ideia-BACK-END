package com.ideia.projetoideia.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tb_etapa")
@Data
public class Etapa {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(nullable = false, name = "data_inicio")
	private LocalDate dataInicio;
	@Column(nullable = false, name = "data_termino")
	private LocalDate dataTermino;

	@Enumerated(EnumType.STRING)
	private TipoEtapa tipoEtapa;
	
	@OneToMany(cascade = CascadeType.MERGE , mappedBy = "etapa")
	private List<Competicao> competicoes = new ArrayList<>();

	public boolean isVigente(){
		LocalDate dataAtual = LocalDate.now();

		return (dataAtual.isAfter(dataInicio) || dataAtual.isEqual(dataInicio) 
				&& 
				dataAtual.isBefore(dataTermino)|| dataAtual.isEqual(dataTermino));

	}

}
