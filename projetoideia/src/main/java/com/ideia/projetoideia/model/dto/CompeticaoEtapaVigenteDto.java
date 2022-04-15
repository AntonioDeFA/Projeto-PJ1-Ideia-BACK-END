package com.ideia.projetoideia.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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

	@JsonInclude(value = Include.NON_NULL)
	private Usuario organizador;
	@JsonInclude(value = Include.NON_NULL)
	private Etapa etapaVigente;

	private Integer quantidadeDeEquipes;

	public CompeticaoEtapaVigenteDto(Competicao competicao , String valid) {
		this.id = competicao.getId();
		this.nomeCompeticao = competicao.getNomeCompeticao();
		this.dominioCompeticao = competicao.getDominioCompeticao();
		this.quantidadeDeEquipes = competicao.getEquipesCadastradas().size();
		
		if(valid.equals("INSCRICAO")) {
			for (Etapa etapa : competicao.getEtapas()) {
				if (etapa.getTipoEtapa().equals(TipoEtapa.INSCRICAO)) {
					this.etapaVigente = etapa;
					break;
				}
			}
		}
		else if (valid.equals("COMPETICAO")) {
			this.organizador = competicao.getOrganizador();
			LocalDate dataAtual = LocalDate.now();
			for (Etapa etapa : competicao.getEtapas()) {
				if(etapa.getDataInicio().isBefore(dataAtual) && etapa.getDataTermino().isAfter(dataAtual)) {
					this.etapaVigente = etapa;
					break;
				}
			}
		
	}

}
}