package com.ideia.projetoideia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ideia.projetoideia.model.AcessoMaterialEstudo;

public interface AcessoMaterialEstudoRepositorio extends JpaRepository<AcessoMaterialEstudo, Integer> {

	@Query(value = "SELECT * FROM  db_ideia.tb_acesso_material_estudo AS am join  db_ideia.tb_material_estudo AS m on m.id = am.material_estudo_fk\r\n"
			+ "WHERE am.equipe_fk = ?1 and am.material_estudo_fk = ?2"
			,nativeQuery = true)
	Optional<AcessoMaterialEstudo>findByEquipeEMaterialEstudo(Integer idEquipe , Integer idMaterialEstudo);
	
}
