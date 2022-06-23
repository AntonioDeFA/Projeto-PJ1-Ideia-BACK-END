package com.ideia.projetoideia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ideia.projetoideia.model.FeedbackAvaliativo;
import com.ideia.projetoideia.model.dto.FeedbackSugestaoDto;

public interface FeedbackAvaliativoRepositorio extends JpaRepository<FeedbackAvaliativo, Integer> {
	
	@Query(value = "SELECT feed.tipoFeedback, feed.sugestao from db_ideia.tb_feedback_avaliativo as feed where feed.lean_canvas_fk = ?1", nativeQuery = true)
	public List<FeedbackSugestaoDto> findByLeanCanvas(Integer leanCanvas);
	
	
}
