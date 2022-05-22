package com.ideia.projetoideia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.Pitch;

public interface PitchRepositorio  extends JpaRepository<Pitch, Integer> {

	public List<Pitch> findByEquipe(Equipe equipe);
}
