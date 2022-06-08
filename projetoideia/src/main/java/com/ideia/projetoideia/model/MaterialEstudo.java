package com.ideia.projetoideia.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ideia.projetoideia.model.enums.TipoEtapa;
import com.ideia.projetoideia.model.enums.TipoMaterialEstudo;

import lombok.Data;

@Entity
@Table(name = "tb_material_estudo")
@Data
public class MaterialEstudo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@OneToMany(mappedBy = "materialEstudo", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<AcessoMaterialEstudo> acessoMaterialEstudo = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private TipoMaterialEstudo tipoMaterialEstudo;
	
	@Column(name = "nome")
	private String nomeMaterial;

	@Lob
	@Column(name = "arquivo_estudo")
	private String arquivoEstudo;

	private String link;

	@Column(name = "dica_estudo_material")
	private String dicaEstudoMaterial;

	@Column(name = "estimativa_tempo_consumo_minutos")
	private Float estimativaTempoConsumoMinutos;

	@ManyToOne
	@JoinColumn(name = "etapa_fk")
	private Etapa etapa;

	@OneToOne
	@JoinColumn(name = "categoria_material_estudo_fk")
	private CategoriaMaterialEstudo categoriaMaterialEstudo;

//	public void setArquivoEstudo(File arquivoEstudo) throws Exception {
//		if (!isLink()) {
//			throw new Exception("O tipo é link, então um arquivo não pode ser atribuido!");
//		}
//		this.arquivoEstudo = arquivoEstudo;
//	}
//
//	public void setLink(String link) throws Exception {
//		if (isLink()) {
//			throw new Exception("O tipo não é link, então um link não pode ser atribuido!");
//		}
//		this.link = link;
//	}
//
//	private boolean isLink() {
//		return (tipoMaterialEstudo == TipoMaterialEstudo.LINK);
//	}

	public void setEtapa(Etapa etapa) throws Exception {
		if (etapa.getTipoEtapa() != TipoEtapa.AQUECIMENTO) {
			throw new Exception("A etapa deve ser somente a de aquecimento!");
		}
		this.etapa = etapa;
	}
}
