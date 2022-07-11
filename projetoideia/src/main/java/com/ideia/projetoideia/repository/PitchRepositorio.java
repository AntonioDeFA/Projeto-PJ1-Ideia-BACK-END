package com.ideia.projetoideia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.Pitch;

public interface PitchRepositorio  extends JpaRepository<Pitch, Integer> {

	public List<Pitch> findByEquipe(Equipe equipe);
	
	@Query(value = "SELECT * FROM db_ideia.tb_pitch as c where c.equipe_fk = ?1 and c.etapa_avaliacao_video = ?2"
			, nativeQuery = true)
	public Pitch findByIdEquipeEEtapaList(Integer idEquipe , String etapaAvaliacaoVideo);
	
}
