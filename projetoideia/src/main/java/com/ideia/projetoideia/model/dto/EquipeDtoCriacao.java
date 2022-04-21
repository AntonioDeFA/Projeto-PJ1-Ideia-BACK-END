package com.ideia.projetoideia.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class EquipeDtoCriacao {
	String nomeEquipe;
	Integer idCompeticao;
	List<UsuarioDto> usuarios;
}
