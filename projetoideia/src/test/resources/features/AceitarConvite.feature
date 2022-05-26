#language: pt
#enconding: UTF-8

@Convite
Funcionalidade: Convites

Cenario: Aceitar Convite Consultor
		Dado que eu passe o email "consultor@gmail.com"
		E passe a senha "1"
		Quando eu clicar no botao login
		E clicar no botao convites consultor
		E clicar no botao aceitar convite consultor
		E clicar no botao trofeu competicao
		Entao o programa valida que a equipe foi criada
		
Cenario: Recusar Convite Avaliador
		Dado que eu passe o email "avaliador@gmail.com"
		E passe a senha "1"
		Quando eu clicar no botao login
		E clicar no botao convites avaliador
		E clicar no botao recusar convite avaliador
		
		
		
		

