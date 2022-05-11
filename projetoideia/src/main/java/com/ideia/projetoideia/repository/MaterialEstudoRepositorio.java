package com.ideia.projetoideia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideia.projetoideia.model.Etapa;
import com.ideia.projetoideia.model.MaterialEstudo;

public interface MaterialEstudoRepositorio extends JpaRepository<MaterialEstudo, Integer> {
	
	public List<MaterialEstudo> findByEtapa(Etapa etapa);
		
}
