package com.ideia.projetoideia.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ideia.projetoideia.model.Equipe;

public interface EquipeRepositorio extends JpaRepository<Equipe, Integer> {

	@Query(value = "Select * from db_ideia.tb_equipe as eq where eq.competicao_fk = ?1", nativeQuery = true)
	public Page<Equipe> findByCompeticao(Integer competicaoId, PageRequest pageRequest);

	@Modifying
	@Query(value = "DELETE * from db_ideia.tb_equipe as eq  where eq.competicao_fk = ?1", nativeQuery = true)
	public void deleteByCompeticao(Integer competicaoId);
	
	@Query(value = "select * from tb_equipe where token = :token", nativeQuery = true)
	public Optional<Equipe> consultarEquipePorToken(@Param("token") String token);

}
