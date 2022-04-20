package com.ideia.projetoideia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ideia.projetoideia.model.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
	
	public Optional<Usuario> findByEmail(String email);

}
