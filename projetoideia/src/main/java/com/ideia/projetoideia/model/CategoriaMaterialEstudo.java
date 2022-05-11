package com.ideia.projetoideia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "tb_categoria_material_estudo")
@Data
public class CategoriaMaterialEstudo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(nullable = false)
	private Integer enumeracao; 
	
	@Column(nullable = false)
	@NotNull(message = "Você deve preencher o nome.")
	private String nome;
}
