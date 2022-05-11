package com.ideia.projetoideia.model.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.ideia.projetoideia.model.Etapa;

import lombok.Data;

@Data
public class CompeticaoPutDto {
	@NotBlank
	private String nomeCompeticao;
	@NotBlank
	private Integer qntdMaximaMembrosPorEquipe;
	@NotBlank
	private Integer qntdMinimaMembrosPorEquipe;
	@NotBlank
	private Float tempoMaximoVideoEmSeg;
	@NotBlank
	private String dominioCompeticao = "";
	@NotBlank
	private byte[] arquivoRegulamentoCompeticao;
	@NotBlank
	private List<Etapa> etapas;
	

}
