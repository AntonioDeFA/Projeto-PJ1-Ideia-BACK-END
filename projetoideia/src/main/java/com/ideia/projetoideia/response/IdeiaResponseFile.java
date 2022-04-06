package com.ideia.projetoideia.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class IdeiaResponseFile {

	private String mensagem;
	private Integer status;
	private List<String> motivosErros = new ArrayList<>();
	
	public IdeiaResponseFile(String mensagem,HttpStatus status) {
		this.mensagem = mensagem;
		this.status = status.value();
		
	}
	
	public IdeiaResponseFile(String mensagem,String motivo,HttpStatus status) {
		this.mensagem = mensagem;
		this.motivosErros.add(motivo);
		this.status = status.value();
	}
	
	public IdeiaResponseFile(String mensagem,List<FieldError>motivos,HttpStatus status) {
		this.mensagem = mensagem;
		this.status = status.value();
		for (FieldError fieldError : motivos) {
			motivosErros.add(fieldError.getDefaultMessage());
		}
	}
	
	

}
