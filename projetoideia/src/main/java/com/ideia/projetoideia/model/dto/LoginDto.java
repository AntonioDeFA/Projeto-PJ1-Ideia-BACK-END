package com.ideia.projetoideia.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginDto {
	
	@NotBlank
	public String login;

	@NotBlank
	public String senha;

}
