package com.ideia.projetoideia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.UsuarioMembroComum;

public interface UsuarioMembroComumRepositorio extends JpaRepository<UsuarioMembroComum, Integer> {
	
	List<UsuarioMembroComum> findByEquipe(Equipe equipe);
	
}
