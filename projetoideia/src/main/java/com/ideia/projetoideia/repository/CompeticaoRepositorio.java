package com.ideia.projetoideia.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Usuario;

public interface CompeticaoRepositorio extends JpaRepository<Competicao, Integer> {

	@Query(value = "Select * from db_ideia.tb_competicao as comp join db_ideia.tb_etapa as etapa on etapa.competicao_fk=comp.id where"
			+ " etapa.tipo_etapa = 'INSCRICAO' ", nativeQuery = true)
	public Page<Competicao> findByInscricao(PageRequest pageRequest);

	@Query(value = "select comp.* From db_ideia.tb_competicao as comp join db_ideia.tb_equipe as lider_equipe on  comp.id =  lider_equipe.competicao_fk"
			+ " where (comp.organizador_fk = 1?) or (lider_equipe.lider_fk = 1?);", nativeQuery = true)
	public Page<Competicao> findByUsuario(Integer idUsuario, PageRequest pageRequest);

	public List<Competicao> findByOrganiador(Usuario organiador);

}
