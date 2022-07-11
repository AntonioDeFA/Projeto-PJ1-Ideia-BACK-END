#language: pt
#enconding: UTF-8

@TelaEquipe
Funcionalidade: TelaEquipe

	Cenario: FuncoesBasicas
		Dado que eu passe o email "usuarioteste@gmail.com"
		E passe a senha "1"
		Quando eu clicar no botao login
		E clicar no botao de minhas competicoes 
		Quando clicar no botao de entrar na competicao
		E clicar no botao de aba de equipe
		E passar um novo nome para a equipe "Testes Nova"
		E clicar no botao atualizar nome equipe 
		Quando clicar no botao copiar token 
		E clicar no botao deletar membro 
	  Quando clicar no botao copiar token 
  
 	
	Cenario: logarComToken
		Dado que eu clique no link logar com token 
		E passe um token de acesso valido 
		Entao o programa verifica que logou 
	


 