package com.ideia.projetoideia.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ideia.projetoideia.model.Competicao;

public interface CompeticaoRepositorio extends JpaRepository<Competicao, Integer> {
	
	@Query(value=
			"Select * from db_ideia.tb_competicao as comp join db_ideia.tb_etapa as etapa on etapa.id = comp.etapa_fk"
			+ " where etapa.tipo_etapa = INSCRICAO "
			,nativeQuery = true)
	public Page<Competicao>findByInscricao(PageRequest pageRequest);
}
