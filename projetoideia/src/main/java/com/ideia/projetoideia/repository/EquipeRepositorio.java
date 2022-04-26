package com.ideia.projetoideia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.Usuario;

public interface EquipeRepositorio extends JpaRepository<Equipe, Integer> {

	@Query(value = "Select * from db_ideia.tb_equipe as eq where eq.competicao_fk = ?1", nativeQuery = true)
	public Page<Equipe> findByCompeticao(Integer competicaoId, PageRequest pageRequest);

	@Modifying
	@Query(value = "DELETE * from db_ideia.tb_equipe as eq  where eq.competicao_fk = ?1", nativeQuery = true)
	public void deleteByCompeticao(Integer competicaoId);
	
	@Query(value = "select * from tb_equipe where token = :token", nativeQuery = true)
	public Optional<Equipe> consultarEquipePorToken(@Param("token") String token);
	
	@Query(value = "select count(*) from tb_equipe tbe join tb_competicao tbc on tbc.id = tbe.competicao_fk where tbe.lider_fk = :id_usuario and tbc.id = :id_competicao and tbc.organizador_fk != :id_usuario", nativeQuery = true)
	public int validarUsuarioLiderEOrganizador(@Param("id_usuario") int idUsuario, @Param("id_competicao") int idCompeticao);

	@Query(value = "select count(*) from tb_usuario_membro_comum tbumc join tb_equipe tbe on tbumc.equipe_fk = tbe.id where tbumc.email in(:lista) and tbe.competicao_fk = :id_competicao", nativeQuery = true)
	public int validarMembrosDeUmaEquipeEmUmaCompeticao(@Param("lista") List<String> emails, @Param("id_competicao") int idCompeticao);
	
	public List<Equipe> findByLider(Usuario lider);
	
}
