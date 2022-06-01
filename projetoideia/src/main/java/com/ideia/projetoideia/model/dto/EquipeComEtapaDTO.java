package com.ideia.projetoideia.model.dto;

import java.time.LocalDate;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.Usuario;

import lombok.Data;

@Data
public class EquipeComEtapaDTO {

	private Integer id;

	private String nomeEquipe;

	private String token;

	private LocalDate dataInscricao;

	private Usuario lider;

	private Usuario consultor;

	private Competicao competicaoCadastrada;
	
	private String etapaVigenteStr;

	public EquipeComEtapaDTO(Equipe equipe, String etapaVigenteStr) {
		this.id = equipe.getId();
		this.nomeEquipe = equipe.getNomeEquipe();
		this.token = equipe.getToken();
		this.dataInscricao = equipe.getDataInscricao();
		this.lider = equipe.getLider();
		this.consultor = equipe.getConsultor();
		this.competicaoCadastrada = equipe.getCompeticaoCadastrada();
		this.etapaVigenteStr = etapaVigenteStr;
	}

}
