package com.ideia.projetoideia.model;

import java.io.File;
import java.net.URL;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@Column(name = "arquivo_estudo")
	private File arquivoEstudo;

	private URL link;

	@Column(nullable = false, name = "dica_estudo_material")
	@NotNull(message = "Você deve escrever uma dica de estudo.")
	private String dicaEstudoMaterial;

	@Column(nullable = false, name = "estimativa_tempo_consumo_minutos")
	@NotNull(message = "Você deve colocar uma estimativa de tempo.")
	private Float estimativaTempoConsumoMinutos;

	@Enumerated(EnumType.STRING)
	private TipoMaterialEstudo tipoMaterial;

	@ManyToOne
	@JoinColumn(name = "etapa_fk")
	private Etapa etapa;

	@ManyToOne
	@JoinColumn(name = "categoria_material_estudo_fk")
	private CategoriaMaterialEstudo categoriaMaterialEstudo;

	public void setArquivoEstudo(File arquivoEstudo) throws Exception {
		if (!isLink()) {
			throw new Exception("O tipo é link, então um arquivo não pode ser atribuido!");
		}
		this.arquivoEstudo = arquivoEstudo;
	}

	public void setLink(URL link) throws Exception {
		if (isLink()) {
			throw new Exception("O tipo não é link, então um link não pode ser atribuido!");
		}
		this.link = link;
	}

	private boolean isLink() {
		return (tipoMaterialEstudo == TipoMaterialEstudo.LINK);
	}

	public void setEtapa(Etapa etapa) throws Exception {
		if (etapa.getTipoEtapa() != TipoEtapa.AQUECIMENTO) {
			throw new Exception("A etapa deve ser somente a de aquecimento!");
		}
		this.etapa = etapa;
	}
}
