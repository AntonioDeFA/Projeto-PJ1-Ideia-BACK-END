package com.ideia.projetoideia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ideia.projetoideia.model.PapelUsuarioCompeticao;

public interface PapelUsuarioCompeticaoRepositorio extends JpaRepository<PapelUsuarioCompeticao, Integer> {

	@Query(value = "SELECT * FROM db_ideia.tb_papel_usuario_competicao AS papel WHERE papel.tipo_papel_usuario = 'ORGANIZADOR' AND papel.competicao_fk = ?1", nativeQuery = true)
	public List<PapelUsuarioCompeticao> findByCompeticao(Integer competicao);
}
