#language: pt
#enconding: UTF-8

@CriarEquipe
Funcionalidade: Criar Equipe

	Contexto: Realizar Login
		Dado que eu passe o email "usuarioteste@gmail.com"
		E passe a senha "1"
		Quando eu clicar no botao login
		
	Cenario: Criar Equipe Sucesso
		Dado que eu selecione uma competicao na fase de inscricao
		E coloque o nome da equipe "Equipe Teste"
		E clicar no botao adicionar membro
		Quando eu passar o nome do membro "Usuario Test 1"
		E passar o email do membro "usuarioteste1@gmail.com"
		E clicar no botao adicionar 
		Quando clicar no botao confirmar
		E clicar no botao confirmar inscricao 
		Entao o programa valida que a equipe foi criada
		