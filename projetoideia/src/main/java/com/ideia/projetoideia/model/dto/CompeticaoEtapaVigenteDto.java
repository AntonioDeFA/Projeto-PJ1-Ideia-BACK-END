package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Etapa;
import com.ideia.projetoideia.model.TipoEtapa;
import com.ideia.projetoideia.model.Usuario;

import lombok.Data;

@Data
public class CompeticaoEtapaVigenteDto {

	private Integer id;

	private String nomeCompeticao;

	private String dominioCompeticao;

	private Usuario organizador;

	private Etapa etapaVigente;

	private Integer quantidadeDeEquipes;

	public CompeticaoEtapaVigenteDto(Competicao competicao) {
		this.id = competicao.getId();
		this.nomeCompeticao = competicao.getNomeCompeticao();
		this.dominioCompeticao = competicao.getDominioCompeticao();
		this.organizador = competicao.getOrganizador();
		this.quantidadeDeEquipes = competicao.getEquipesCadastradas().size();
		for (Etapa etapa : competicao.getEtapas()) {
			if (etapa.getTipoEtapa().equals(TipoEtapa.INSCRICAO)) {
				this.etapaVigente = etapa;
				break;
			}
		}
	}

}
