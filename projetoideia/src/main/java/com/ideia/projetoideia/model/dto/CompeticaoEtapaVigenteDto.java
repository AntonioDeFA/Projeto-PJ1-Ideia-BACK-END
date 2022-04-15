package com.ideia.projetoideia.model.dto;

import java.time.LocalDate;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Etapa;
import com.ideia.projetoideia.model.PapelUsuarioCompeticao;
import com.ideia.projetoideia.model.TipoEtapa;
import com.ideia.projetoideia.model.TipoPapelUsuario;
import com.ideia.projetoideia.model.Usuario;

import lombok.Data;

@Data
public class CompeticaoEtapaVigenteDto {

	private Integer id;

	private String nomeCompeticao;

	private String dominioCompeticao;

	@JsonInclude(value = Include.NON_NULL)
	private Usuario organizador;

	private Etapa etapaVigente;

	private Integer quantidadeDeEquipes;

	@JsonInclude(value = Include.NON_NULL)
	private TipoPapelUsuario papelUsuario;

	public CompeticaoEtapaVigenteDto(Competicao competicao, String valid, Usuario usuarioLogado) {
		this.id = competicao.getId();
		this.nomeCompeticao = competicao.getNomeCompeticao();
		this.dominioCompeticao = competicao.getDominioCompeticao();
		this.quantidadeDeEquipes = competicao.getEquipesCadastradas().size();

		if (valid.equals("INSCRICAO")) {
			for (Etapa etapa : competicao.getEtapas()) {
				if (etapa.getTipoEtapa().equals(TipoEtapa.INSCRICAO)) {
					this.etapaVigente = etapa;
					break;
				}
			}
		} else if (valid.equals("COMPETICAO")) {
			for (PapelUsuarioCompeticao papelUsuarioCompeticao : competicao.getPapeisUsuarioCompeticao()) {
				if (papelUsuarioCompeticao.getCompeticao().getId() == competicao.getId()
						&& papelUsuarioCompeticao.getUsuario().getId() == usuarioLogado.getId()) {
					this.papelUsuario = papelUsuarioCompeticao.getTipoPapelUsuario();
				}
			}
			LocalDate dataAtual = LocalDate.now();
			for (Etapa etapa : competicao.getEtapas()) {
				if (etapa.getDataInicio().isBefore(dataAtual)
						|| etapa.getDataInicio().isEqual(dataAtual) && etapa.getDataTermino().isAfter(dataAtual)
						|| etapa.getDataTermino().isEqual(dataAtual)) {
					this.etapaVigente = etapa;
					break;
				}
			}
		}

	}
}