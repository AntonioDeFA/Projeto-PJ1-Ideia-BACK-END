package com.ideia.projetoideia.utils;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.enums.TipoConvite;

public class MensagensEmail {

	public static String ASSUNTO_RESETAR_SENHA = "Alerta de segurança";
	
	public static String CONVITE = "Convite Recebido";
	
	public static String EXIGIR_CONSULTOR="Uma equipe requer consultor";

	public static String montarCorpoResetarSenha(Usuario user, String senha) {

		String mensagem = "Olá senhor(a): " + user.getNomeUsuario() + "\n"
				+ "sua senha do Ideia foi resetada, recomendamos que a atualize o mais rápido possível !\n"
				+ "Sua nova senha é: " + senha;

		return mensagem;

	}

	
	public static String montarCorpoConvidar(Usuario user ,Competicao competicao ,TipoConvite convite) {
		
		String mensagem = "Olá senhor(a) "+ user.getNomeUsuario()+"\n"
				+"Você foi convidado para a competição: "+competicao.getNomeCompeticao()
				+" para desempenhar o papel de "+convite +".\n"
				+"Visite a página de convites no Ideia para aceitar ou recusar este convite.";
		
		return mensagem;
	}
	
	public static String montarCorpoExigirConsultor(Equipe equipe) {
		String mensagem = "Olá, "+equipe.getCompeticaoCadastrada().getOrganizador().getNomeUsuario()+"."
				+ " A equipe "+equipe.getNomeEquipe()+", que está inscrita na sua competição "+equipe.getCompeticaoCadastrada().getNomeCompeticao()+" no Ideia, "
				+ "deseja enviar um artefato de pitch para consultoria, mas não lhes foi atribuído um consultor. "
				+ "Por gentileza, acesse o sistema e selecione um consultor para eles.";
		return mensagem;
	}
}
