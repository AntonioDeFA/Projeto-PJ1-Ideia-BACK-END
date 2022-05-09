package com.ideia.projetoideia.model;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tb_acesso_material_estudo")
@Data
public class AcessoMaterialEstudo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(name = "tb_data_acesso")
	private Date dataAcesso;
	
	@ManyToOne
	@JoinColumn(name = "equipe_fk")
	private Equipe equipe;
	
	@ManyToOne
	@JoinColumn(name = "material_estudo_fk")
	private MaterialEstudo materialEstudo;
}
