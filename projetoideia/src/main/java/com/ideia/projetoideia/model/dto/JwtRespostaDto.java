package com.ideia.projetoideia.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class JwtRespostaDto {
	private String token;
	private String type = "Bearer";
	private Integer id;
	private String login;
	private String email;
	private String nome;
	private List<String> roles;
	
	public JwtRespostaDto(String token, Integer id, String nome, String username, String email, List<String> roles) {
		super();
		this.token = token;
		this.id = id;
		this.nome = nome;
		this.login = username;
		this.email = email;
		this.roles = roles;
	}

}
