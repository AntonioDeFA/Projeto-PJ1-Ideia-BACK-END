package com.ideia.projetoideia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.PapelUsuarioCompeticao;
import com.ideia.projetoideia.model.Usuario;

public interface PapelUsuarioCompeticaoRepositorio extends JpaRepository<PapelUsuarioCompeticao, Integer> {
	
	public List<PapelUsuarioCompeticao> findByUsuario(Usuario usuario);
	
	public List<PapelUsuarioCompeticao> findByCompeticaoCadastrada(Competicao competicaoCadastrada);
	
	@Query(value = "SELECT * from db_ideia.tb_papel_usuario_competicao as papel where papel.usuario_fk = :id_usuario and papel.competicao_fk= :id_competicao", nativeQuery = true)
	public int findByCompeticaoCadastradaAndUsuario(@Param("id_usuario") int idUsuario, @Param("id_competicao") int idCompeticao);
	
	public Optional<PapelUsuarioCompeticao> findById(Integer id);
}