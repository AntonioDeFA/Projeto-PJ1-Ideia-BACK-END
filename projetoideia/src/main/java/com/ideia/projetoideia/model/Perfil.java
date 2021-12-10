package com.ideia.projetoideia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Entity
@Data
@Table(name="tb_perfil")
public class Perfil implements GrantedAuthority {
	
	public static final String PERFIL_USUARIO= "usuario";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(nullable = false)
	private String nomePerfil;

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.nomePerfil;
	}
}
