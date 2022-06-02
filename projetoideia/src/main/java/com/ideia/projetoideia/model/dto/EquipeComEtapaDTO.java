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

	private Integer idCompeticaoCadastrada;
	
	private String etapaVigenteStr;
	
	private List<UsuarioDto> usuarios = new ArrayList<>();

	public EquipeComEtapaDTO(Equipe equipe, String etapaVigenteStr,List<UsuarioMembroComum> usuarios) {
		this.id = equipe.getId();
		this.nomeEquipe = equipe.getNomeEquipe();
		this.token = equipe.getToken();
		this.dataInscricao = equipe.getDataInscricao();
		this.idCompeticaoCadastrada = equipe.getCompeticaoCadastrada().getId();
		this.etapaVigenteStr = etapaVigenteStr;
		
		UsuarioDto u  = new UsuarioDto();
		u.setEmail(equipe.getLider().getEmail());
		u.setNomeUsuario(equipe.getLider().getNomeUsuario());
		u.setId(equipe.getLider().getId());
		this.usuarios.add(u);
		
		for (UsuarioMembroComum usuarioMembroComum : usuarios) {
			UsuarioDto user = new UsuarioDto();
			user.setEmail(usuarioMembroComum.getEmail());
			user.setNomeUsuario(usuarioMembroComum.getNome());
			user.setId(usuarioMembroComum.getId());
			this.usuarios.add(user);
		}
		
		
	}

}
