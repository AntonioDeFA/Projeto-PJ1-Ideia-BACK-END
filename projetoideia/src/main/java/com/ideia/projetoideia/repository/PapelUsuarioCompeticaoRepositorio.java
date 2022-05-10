package com.ideia.projetoideia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.PapelUsuarioCompeticao;
import com.ideia.projetoideia.model.Usuario;

public interface PapelUsuarioCompeticaoRepositorio extends JpaRepository<PapelUsuarioCompeticao, Integer> {
	
	public List<PapelUsuarioCompeticao> findByUsuario(Usuario usuario);
	
	public List<PapelUsuarioCompeticao> findByCompeticaoCadastrada(Competicao competicaoCadastrada);
	
	
	
}