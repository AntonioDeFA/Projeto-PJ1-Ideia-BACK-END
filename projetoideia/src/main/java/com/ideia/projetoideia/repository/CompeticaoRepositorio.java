package com.ideia.projetoideia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Usuario;

public interface CompeticaoRepositorio extends JpaRepository<Competicao, Integer> {

	@Query(value = "Select * from db_ideia.tb_competicao as comp join db_ideia.tb_etapa as etapa on etapa.competicao_fk=comp.id where"
			+ " etapa.tipo_etapa = 'INSCRICAO' ", nativeQuery = true)
	public Page<Competicao> findByInscricao(PageRequest pageRequest);

	@Query(value = "SELECT comp.* FROM db_ideia.tb_competicao as comp join db_ideia.tb_equipe as equipe on comp.id = equipe.competicao_fk "
			+ "where equipe.lider_fk = ?1", nativeQuery = true)
	public List<Competicao> findByEquipe(Integer usuarioId);

	public Page<Competicao> findByOrganizador(Usuario organiador, PageRequest pageRequest);

	@Query(value = "SELECT * from db_ideia.tb_competicao as comp where comp.organizador_fk = ?1", nativeQuery = true)
	public List<Competicao> findByOrganizador(Integer organiador);

	public Optional<Competicao> findById(Integer id);

	public List<Competicao> findByNomeCompeticao(String nomeCompeticao);
	
}
