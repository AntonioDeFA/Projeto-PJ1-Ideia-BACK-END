#language: pt
#enconding: UTF-8

@LeanCanvas
Funcionalidade: TelaLeanCanvas

	Cenario: Preencher Lean Canvas Erro
		Dado que eu passe o email "usuarioteste@gmail.com"
		E passe a senha "1"
		Quando eu clicar no botao login
		E clicar no botao de minhas competicoes 
		Quando clicar no botao de entrar na competicao
		E clicar na aba Lean Canvas 
		E clicar no botao salvar lean canvas 
		Entao e verificado se realmente deu erro no salvar lean canvas
		
		
	Cenario: Salvar Lean Canvas e enviar para consultoria
		Dado que eu passe o email "usuarioteste@gmail.com"
		E passe a senha "1"
		Quando eu clicar no botao login
		E clicar no botao de minhas competicoes 
		Quando clicar no botao de entrar na competicao
		E clicar na aba Lean Canvas
		E preencher os campos do lean canvas
		E clicar no botao salvar lean canvas 
		E clicar no botao enviar lean canvas consultoria
		
		