package com.ideia.projetoideia.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.UsuarioMembroComum;

import lombok.Data;

@Data
public class EquipeComEtapaDTO {

	private Integer id;

	private String nomeEquipe;

	private String token;

	private LocalDate dataInscricao;

	private Integer idLider;

	private Integer idCompeticaoCadastrada;
	
	private String etapaVigenteStr;
	
	private List<UsuarioMembroComum> usuarios = new ArrayList<>();

	public EquipeComEtapaDTO(Equipe equipe, String etapaVigenteStr,List<UsuarioMembroComum> usuarios) {
		this.id = equipe.getId();
		this.nomeEquipe = equipe.getNomeEquipe();
		this.token = equipe.getToken();
		this.dataInscricao = equipe.getDataInscricao();
		this.idLider = equipe.getLider().getId();
		this.idCompeticaoCadastrada = equipe.getCompeticaoCadastrada().getId();
		this.usuarios = usuarios;
		this.etapaVigenteStr = etapaVigenteStr;
	}

}
