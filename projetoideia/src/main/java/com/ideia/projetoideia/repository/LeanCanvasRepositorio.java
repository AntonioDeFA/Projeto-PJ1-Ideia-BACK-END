package com.ideia.projetoideia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.LeanCanvas;

@Repository
public interface LeanCanvasRepositorio extends JpaRepository<LeanCanvas, Integer> {
	
	
	@Query(value = "SELECT * FROM db_ideia.tb_lean_canvas as c where c.equipe_fk = ?1 and c.etapa_solucao_canvas = ?2"
			, nativeQuery = true)
	public LeanCanvas findByIdEquipeEEtapa(Integer idEquipe , String EtapaSolucaCanvas);
	
	
	@Query(value = "SELECT * FROM db_ideia.tb_lean_canvas as c where c.equipe_fk = ?1 and c.etapa_solucao_canvas = ?2"
			, nativeQuery = true)
	public List<LeanCanvas> findByIdEquipeEEtapaList(Integer idEquipe , String EtapaSolucaCanvas);
	
	@Query(value = "SELECT * FROM db_ideia.tb_lean_canvas as c where c.id = ?1"
			, nativeQuery = true)
	public List<LeanCanvas> buscarPorID(Integer id);
	
	public List<LeanCanvas> findByEquipe(Equipe equipe);
	
	@Query(value = "select * from tb_lean_canvas where equipe_fk = :id and etapa_solucao_canvas = 'EM_CONSULTORIA';", nativeQuery = true)
	public List<LeanCanvas> findByEquipeLeanCanvasConsultoria(@Param ("id") Integer id);
}
