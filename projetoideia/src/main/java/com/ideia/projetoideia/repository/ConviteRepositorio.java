package com.ideia.projetoideia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Convite;
import com.ideia.projetoideia.model.Usuario;

public interface ConviteRepositorio extends JpaRepository<Convite, Integer> {
	
	List<Convite> findByCompeticao(Competicao competicao);
	
	List<Convite> findByUsuario(Usuario usuario);

}
