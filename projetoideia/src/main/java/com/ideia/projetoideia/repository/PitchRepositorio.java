package com.ideia.projetoideia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.Pitch;

public interface PitchRepositorio  extends JpaRepository<Pitch, Integer> {

	public List<Pitch> findByEquipe(Equipe equipe);
	
	@Query(value = "SELECT * FROM db_ideia.tb_pitch as c where c.equipe_fk = ?1 and c.etapa_avaliacao_video = ?2"
			, nativeQuery = true)
	public Pitch findByIdEquipeEEtapaList(Integer idEquipe , String etapaAvaliacaoVideo);
	
	@Query(value = "select * from tb_pitch where equipe_fk = :id order by data desc limit 1", nativeQuery = true)
	public Optional<Pitch> findByIdEquipe(@Param ("id") Integer id);
	
	@Query(value = "select * from tb_pitch where equipe_fk = :id and etapa_avaliacao_video = 'AVALIADO_CONSULTOR' order by data desc;", nativeQuery = true)
	public List<Pitch> findByIdEquipeFeedbacksPitch(@Param ("id") Integer id);
}
