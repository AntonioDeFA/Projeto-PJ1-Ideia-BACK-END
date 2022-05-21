#language: pt
#enconding: UTF-8

@Competicao
Funcionalidade: Competicao

	Contexto: entrar no sistema
		Dado que eu passe o email "usuarioteste@gmail.com"
		E passe a senha "1"
		Quando eu clicar no botao login
		E clique no botao criar competicao 

	Cenario: Criar competicao 
	Dado que eu passe a data de hoje para o inicio da etapa de inscricao
	E passe a data de amanha para o termino das incricoes
	E passe o nome da competicao "Competicao Teste"
	E passe a quantidade minima de membros "1"
	E passe a quantidade maxima de membros "3"
	E passe o tempo maximo de pitch "5"
	E clique no botao regulamento da competicao
	Quando eu clicar no botao salvar dados gerais da competicao 
	E clicar no botao adicionar questao avaliativa
	E passe o valor de "10" para a nota maxima
	E passe o valor de "Qual é o melhor solo de guitarra ?"
	E clique no botao salvar questao
	E clique no botao salvar
	E passe a data de inicio da etapa aquecimento
	E passe a data de termino da etapa aquecimento
	E passe o link "github.com"
	E clique no botao de cadastrar link
	E clique no botao salvar etapa aquecimento  
	E passe a data de inicio da etapa imersao
	E passe a data de termino da etapa imersao
	E clique no botao salvar etapa imersao
	E passe a data de inicio da etapa pitch
	E passe a data de termino da etapa pitch
	E clique no botao salvar etapa pitch
	E clique no botao salvar criacao competicao
		 