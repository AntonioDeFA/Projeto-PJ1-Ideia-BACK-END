package com.ideia.projetoideia.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Etapa;
import com.ideia.projetoideia.model.PapelUsuarioCompeticao;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.enums.TipoEtapa;
import com.ideia.projetoideia.model.enums.TipoPapelUsuario;

import lombok.Data;

@Data
public class CompeticaoEtapaVigenteDto {

	private Integer id;

	private String nomeCompeticao;

	private String dominioCompeticao;

	@JsonInclude(value = Include.NON_NULL)
	private Usuario organizador;

	private Etapa etapaVigente;

	private String etapaVigenteStr;

	private Integer quantidadeDeEquipes;

	@JsonInclude(value = Include.NON_NULL)
	private TipoPapelUsuario papelUsuario;

	@JsonInclude(value = Include.NON_NULL)
	private Integer idEquipe;

	private Boolean isElaboracao;

	private List<Etapa> etapas = new ArrayList<>();

	public CompeticaoEtapaVigenteDto(Competicao competicao, String valid, Usuario usuarioLogado) {
		this.id = competicao.getId();
		this.etapas = competicao.getEtapas();
		this.nomeCompeticao = competicao.getNomeCompeticao();
		this.dominioCompeticao = competicao.getDominioCompeticao();
		this.quantidadeDeEquipes = competicao.getEquipesCadastradas().size();

		LocalDate hoje = LocalDate.now();
		
		Etapa etapaInscricao = null;
		
		for (int i = 0; i < competicao.getEtapas().size(); i++) {
			Etapa etapa = competicao.getEtapas().get(i);
			if (etapa.getTipoEtapa().equals(TipoEtapa.INSCRICAO)) {
				etapaInscricao = etapa;
				break;
			}
			
		}

		if (valid.equals("INSCRICAO")) {
			for (Etapa etapa : competicao.getEtapas()) {
				if (etapa.getTipoEtapa().equals(TipoEtapa.INSCRICAO)) {
					this.etapaVigente = etapa;
					break;
				}
			}
		} else if (valid.equals("COMPETICAO")) {
			for (PapelUsuarioCompeticao papelUsuarioCompeticao : competicao.getPapeisUsuarioCompeticao()) {
				if (papelUsuarioCompeticao.getCompeticaoCadastrada().getId() == competicao.getId()
						&& papelUsuarioCompeticao.getUsuario().getId() == usuarioLogado.getId()) {
					this.papelUsuario = papelUsuarioCompeticao.getTipoPapelUsuario();
				}
			}

			if (hoje.isBefore(etapaInscricao.getDataInicio()) && !competicao.getIsElaboracao()) {
				this.etapaVigenteStr = "NAO_INICIADA";
			} else if (!competicao.getIsElaboracao()) {
				for (Etapa etapa : competicao.getEtapas()) {
					if (etapa.isVigente()) {
						this.etapaVigente = etapa;
						this.etapaVigenteStr = etapa.getTipoEtapa().getValue();
						break;
					}
				}
			} else if (competicao.getIsElaboracao()) {
				this.etapaVigenteStr = "ELABORACAO";
			}
			
			if (competicao.getIsEncerrada()) {
				
				for (Etapa etapa : competicao.getEtapas()) {
					if(etapa.getTipoEtapa().equals(TipoEtapa.PITCH)) {
						etapaVigente = new Etapa();
						etapaVigente.setCompeticao(competicao);
						etapaVigente.setDataInicio(etapa.getDataTermino());
						etapaVigente.setId(etapa.getId());
					}
				}
				
				etapaVigente.setTipoEtapa(TipoEtapa.ENCERRADA);
				etapaVigenteStr = "ENCERRADA";
			}
		}

		this.isElaboracao = competicao.getIsElaboracao();

	}
}