package com.ideia.projetoideia.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ideia.projetoideia.model.dto.UsuarioNaoRelacionadoDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdeiaResponseFile {

	private String mensagem;

	private Integer status;

	private List<String> motivosErros = new ArrayList<>();

	@JsonInclude(value = Include.NON_NULL)
	private Integer idCompeticao;

	@JsonInclude(value = Include.NON_NULL)
	private List<UsuarioNaoRelacionadoDTO> usuarios;

	public IdeiaResponseFile(String mensagem, HttpStatus status) {
		this.mensagem = mensagem;
		this.status = status.value();
	}

	public IdeiaResponseFile(String mensagem, String motivo, HttpStatus status) {
		this.mensagem = mensagem;
		this.motivosErros.add(motivo);
		this.status = status.value();
	}

	public IdeiaResponseFile(String mensagem, List<FieldError> motivos, HttpStatus status) {
		this.mensagem = mensagem;
		this.status = status.value();
		for (FieldError fieldError : motivos) {
			motivosErros.add(fieldError.getDefaultMessage());
		}
	}

	public IdeiaResponseFile(String mensagem, HttpStatus status, Integer idCompeticao) {
		this.mensagem = mensagem;
		this.status = status.value();
		this.idCompeticao = idCompeticao;
	}
	
	public IdeiaResponseFile(String mensagem, HttpStatus status, List<UsuarioNaoRelacionadoDTO> usuarios) {
		this.mensagem = mensagem;
		this.status = status.value();
		this.usuarios = usuarios;
	}
}
