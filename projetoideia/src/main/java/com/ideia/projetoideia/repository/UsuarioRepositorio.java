package com.ideia.projetoideia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ideia.projetoideia.model.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
	
	public Optional<Usuario> findByEmail(String email);
	
	public List<Usuario> findByNomeUsuario(String nomeUsuario);
	
	
	@Query(value = "SELECT * FROM db_ideia.tb_usuario AS user Join db_ideia.tb_papel_usuario_competicao AS papel on user.id = papel.usuario_fk \r\n"
			+ "where  papel.competicao_fk = ?1 And user.id = ?2 " , nativeQuery = true)
	public Optional<Usuario> listarSeUsuarioTemRelacaoComCompeticao(Integer idCompeticao , Integer idUsuario);

	@Query(value ="SELECT * FROM db_ideia.tb_usuario AS user Join db_ideia.tb_convite AS convite on user.id = convite.usuario_fk\r\n"
			+ "where  convite.competicao_fk = ?1 And user.id = ?2" ,nativeQuery = true)
	public Optional<Usuario>listarSeUsuarioTemConvitesDeUmaCompeticao(Integer idCompeticao, Integer idUsuario);
}
