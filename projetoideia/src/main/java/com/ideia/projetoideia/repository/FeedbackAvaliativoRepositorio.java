package com.ideia.projetoideia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideia.projetoideia.model.FeedbackAvaliativo;
import com.ideia.projetoideia.model.LeanCanvas;

public interface FeedbackAvaliativoRepositorio extends JpaRepository<FeedbackAvaliativo, Integer> {
	
	public List<FeedbackAvaliativo> findByLeanCanvas(LeanCanvas leanCanvas);
}
