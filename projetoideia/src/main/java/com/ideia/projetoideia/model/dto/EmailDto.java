package com.ideia.projetoideia.model.dto;

import lombok.Data;

@Data
public class EmailDto {

	String email;

	public EmailDto(String email) {
		this.email = email;
	}

}
