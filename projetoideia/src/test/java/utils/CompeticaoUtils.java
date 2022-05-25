package utils;

import com.ideia.projetoideia.model.Competicao;

public class CompeticaoUtils {
	
	public static String nomeCompeticao = "Competição";
	
	public static Competicao criarCompeticao () {
		
		Competicao competicao = new Competicao();
		
		competicao.setNomeCompeticao(nomeCompeticao);
		competicao.setArquivoRegulamentoCompeticao("");
		competicao.setOrganizador(null);
		competicao.setTempoMaximoVideoEmSeg(30f);
		competicao.setQntdMaximaMembrosPorEquipe(5);
		competicao.setQntdMinimaMembrosPorEquipe(2);
		
		return competicao;
	}

}
