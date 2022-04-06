package com.ideia.projetoideia.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class IdeiaResponseFile {

	private String mensagem;
	private String motivoErro;
	private Integer status;
	
	public IdeiaResponseFile(String mensagem,HttpStatus status) {
		this.mensagem = mensagem;
		this.status = status.value();
		
	}
	
	public IdeiaResponseFile(String mensagem,String motivo,HttpStatus status) {
		this.mensagem = mensagem;
		this.motivoErro = motivo;
		this.status = status.value();
	}

}
