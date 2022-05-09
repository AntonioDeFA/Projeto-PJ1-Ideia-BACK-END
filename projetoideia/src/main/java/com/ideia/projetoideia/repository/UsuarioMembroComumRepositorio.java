package com.ideia.projetoideia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideia.projetoideia.model.UsuarioMembroComum;

public interface UsuarioMembroComumRepositorio extends JpaRepository<UsuarioMembroComum, Integer> {
	
}
