package com.ideia.projetoideia.model.dto;

import java.util.List;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Etapa;

import lombok.Data;

@Data
public class CompeticaoDadosGeraisDto {

	private String nomeCompeticao;

	private Integer qntdMaximaMembrosPorEquipe;

	private Integer qntdMinimaMembrosPorEquipe;

	private Float tempoMaximoVideoEmSeg;

	private byte[] arquivoRegulamentoCompeticao;

	private String dominioCompeticao = "";
	
	private String etapaVigente;
	
	private List<Etapa> estapas;
	
	public CompeticaoDadosGeraisDto(Competicao competicao, List<Etapa> estapas) {
		this.nomeCompeticao = competicao.getNomeCompeticao();
		this.qntdMaximaMembrosPorEquipe = competicao.getQntdMaximaMembrosPorEquipe();
		this.qntdMinimaMembrosPorEquipe = competicao.getQntdMinimaMembrosPorEquipe();
		this.tempoMaximoVideoEmSeg = competicao.getTempoMaximoVideoEmSeg();
		this.arquivoRegulamentoCompeticao = competicao.getArquivoRegulamentoCompeticao();
		this.dominioCompeticao = competicao.getDominioCompeticao();
		this.estapas = estapas;
		
		for (Etapa etapa : competicao.getEtapas()) {
			if (etapa.isVigente()) {
				this.etapaVigente = etapa.getTipoEtapa().getValue();
				break;
			}
		}
	}
	
}
