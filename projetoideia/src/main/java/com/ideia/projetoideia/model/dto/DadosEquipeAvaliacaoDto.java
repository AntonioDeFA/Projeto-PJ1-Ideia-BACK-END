package com.ideia.projetoideia.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.LeanCanvas;
import com.ideia.projetoideia.model.Pitch;
import com.ideia.projetoideia.model.QuestaoAvaliativa;

import lombok.Data;

@Data
public class DadosEquipeAvaliacaoDto {

	private String nomeEquipe;
	
	private String nomeCompeticao;
	
	private LeanCanvasDto leanCanvas;
	
	private String pitchDeck;
	
	private List<QuestaoAvaliativaDto> questoesAvaliacao = new ArrayList<>();
	
	
	public DadosEquipeAvaliacaoDto (Equipe equipe ,LeanCanvas canvas , Pitch pitch , List<QuestaoAvaliativa> questoes ) {
		
		
		nomeEquipe = equipe.getNomeEquipe();
		nomeCompeticao = equipe.getCompeticaoCadastrada().getNomeCompeticao();
		leanCanvas = new LeanCanvasDto(canvas);
		pitchDeck = pitch.getPitchDeck();
		
		questoes.forEach(questao ->{
			QuestaoAvaliativaDto dto = new QuestaoAvaliativaDto(questao);
			questoesAvaliacao.add(dto);
		});
		
	}
	
	
}
