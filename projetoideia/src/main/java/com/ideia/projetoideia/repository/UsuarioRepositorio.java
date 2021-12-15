package com.ideia.projetoideia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideia.projetoideia.model.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
	public Usuario findByEmail(String email);
}
