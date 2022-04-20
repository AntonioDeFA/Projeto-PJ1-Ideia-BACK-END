package com.ideia.projetoideia.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginTokenDto {

	@NotBlank
	private String token;
}
