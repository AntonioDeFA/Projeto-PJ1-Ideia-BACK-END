package com.ideia.projetoideia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideia.projetoideia.model.AvaliacaoPitch;
import com.ideia.projetoideia.model.Pitch;

public interface AvaliacaoPitchRpositorio extends JpaRepository<AvaliacaoPitch, Integer> {
	
	public List<AvaliacaoPitch> findByPitch(Pitch pitch);

}
