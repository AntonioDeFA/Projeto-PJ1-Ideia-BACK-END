package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.Usuario;

import lombok.Data;

@Data
public class UsuarioNaoRelacionadoDTO {
	private Integer id;
	private String nome;
	private String email;

	public UsuarioNaoRelacionadoDTO() {
	}

	public UsuarioNaoRelacionadoDTO(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNomeUsuario();
		this.email = usuario.getEmail();
	}

}
