package com.ideia.projetoideia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideia.projetoideia.model.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
	
	public Optional<Usuario> findByEmail(String email);
	
	public List<Usuario> findByNomeUsuario(String nomeUsuario);

}
