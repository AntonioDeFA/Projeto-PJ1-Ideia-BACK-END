package utils;

import com.ideia.projetoideia.model.Competicao;

public class CompeticaoUtils {
	
	public static String nomeCompeticao = "Competicao Teste";
	
	public static Competicao criarCompeticao () {
		
		Competicao competicao = new Competicao();
		
		competicao.setNomeCompeticao("Competicao Teste");
		competicao.setArquivoRegulamentoCompeticao(new byte[2]);
		competicao.setOrganizador(null);
		competicao.setTempoMaximoVideoEmSeg(new Float(30));
		competicao.setQntdMaximaMembrosPorEquipe(5);
		competicao.setQntdMinimaMembrosPorEquipe(2);
		
		return competicao;
	}

}
