#language: pt
#enconding: UTF-8

@Login
Funcionalidade: Login
	
	@Login-inicial
	Cenario: Login inicial
		Dado que o usuario exista

	Cenario: Login Sucesso
		Dado que eu passe o email "usuarioteste@gmail.com"
		E passe a senha "1"
		Quando eu clicar no botao login
		Entao o sistema valida que entrou
	
	Cenario: Login email errado
		Dado que eu passe o email "usuarioteste_errado@gmail.com"
		E passe a senha "1"
		Quando eu clicar no botao login
		Entao o sistema valida que ocorreu um erro "Houve um erro!"