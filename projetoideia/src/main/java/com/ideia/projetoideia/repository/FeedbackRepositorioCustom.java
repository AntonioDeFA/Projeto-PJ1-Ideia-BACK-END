package com.ideia.projetoideia.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ideia.projetoideia.model.dto.FeedbackSugestaoDto;

@Repository
public class FeedbackRepositorioCustom {
	private final EntityManager entityManager;

	public FeedbackRepositorioCustom(EntityManager en) {
		entityManager = en;
	}

	public List<FeedbackSugestaoDto> getByLeanCanvas(Integer id) {

		StringBuffer jpql = new StringBuffer().append(
				"SELECT new com.ideia.projetoideia.model.dto.FeedbackSugestaoDto(feed.tipoFeedback, feed.sugestao) FROM FeedbackAvaliativo AS feed WHERE feed.leanCanvas.id =:id");
		TypedQuery<FeedbackSugestaoDto> query = entityManager.createQuery(jpql.toString(), FeedbackSugestaoDto.class);
		query.setParameter("id", id);

		List<FeedbackSugestaoDto> feedbacks = query.getResultList();

		return feedbacks;
	}
}
