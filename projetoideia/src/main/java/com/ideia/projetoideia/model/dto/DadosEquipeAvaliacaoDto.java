package com.ideia.projetoideia.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.LeanCanvas;
import com.ideia.projetoideia.model.Pitch;
import com.ideia.projetoideia.model.QuestaoAvaliativa;
import com.ideia.projetoideia.model.enums.TipoQuestaoAvaliativa;

import lombok.Data;

@Data
public class DadosEquipeAvaliacaoDto {

	private String nomeEquipe;
	
	private String nomeCompeticao;
	
	private LeanCanvasDto leanCanvas;
	
	private PitchDto pitchDeck;
	
	private List<QuestaoAvaliativaDto> questoesAdaptabilidade = new ArrayList<>();

	private List<QuestaoAvaliativaDto> questoesInovacao = new ArrayList<>();
	
	private List<QuestaoAvaliativaDto> questoesUtilidade = new ArrayList<>();
	
	private List<QuestaoAvaliativaDto> questoesSustentabilidade = new ArrayList<>();
	
	
	public DadosEquipeAvaliacaoDto (Equipe equipe ,LeanCanvas canvas , Pitch pitch , List<QuestaoAvaliativa> questoes ) {
		
		
		nomeEquipe = equipe.getNomeEquipe();
		nomeCompeticao = equipe.getCompeticaoCadastrada().getNomeCompeticao();
		leanCanvas = new LeanCanvasDto(canvas);
		pitchDeck = new PitchDto(pitch);
		
		for(QuestaoAvaliativa questao : questoes) {
			QuestaoAvaliativaDto dto = new QuestaoAvaliativaDto(questao);
			
			if (questao.getTipoQuestaoAvaliativa() == TipoQuestaoAvaliativa.ADAPTABILIDADE) {
				questoesAdaptabilidade.add(dto);
			} else if(questao.getTipoQuestaoAvaliativa() == TipoQuestaoAvaliativa.INOVACAO) {
				questoesInovacao.add(dto);
			} else if(questao.getTipoQuestaoAvaliativa() == TipoQuestaoAvaliativa.UTILIDADE) {
				questoesUtilidade.add(dto);
			} else {
				questoesSustentabilidade.add(dto);
			}
		}
		
	}
	
	
}
