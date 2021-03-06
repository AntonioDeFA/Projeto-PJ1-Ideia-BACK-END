package com.ideia.projetoideia.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "tb_usuario")
@Data
public class Usuario implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(nullable = false, name = "nome_usuario")
	@Size(min = 3, max = 50, message = "O nome usuario deve ter entre 3 e 50 caracteres.")
	private String nomeUsuario;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String senha;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Perfil> perfis;

	@OneToMany(mappedBy = "lider", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Equipe> equipesLider = new ArrayList<>();

	@OneToMany(mappedBy = "consultor", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Equipe> equipesConsultor = new ArrayList<>();

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.MERGE)
	private List<PapelUsuarioCompeticao> papeisUsuarioCompeticao = new ArrayList<>();

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.MERGE)
	private List<Convite> convites = new ArrayList<>();

	@OneToMany(mappedBy = "avaliador", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<AvaliacaoPitch> avaliacaoPitch = new ArrayList<>();

	@OneToMany(mappedBy = "consultor", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<FeedbackAvaliativo> feedbackAvaliativos = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.perfis;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.senha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
