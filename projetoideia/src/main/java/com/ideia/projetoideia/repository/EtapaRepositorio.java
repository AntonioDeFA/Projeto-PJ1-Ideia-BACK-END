package com.ideia.projetoideia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Etapa;

public interface EtapaRepositorio extends JpaRepository<Etapa, Integer> {

	List<Etapa> findByCompeticao(Competicao competicao);
	
	@Query(value = "SELECT * FROM db_ideia.tb_etapa AS e where e.tipo_etapa = ?1 AND e.competicao_fk = ?2", 
			nativeQuery = true)
	public Etapa findEtapaCompeticao (String tipoEtapa , Integer idCompeticao);
}
