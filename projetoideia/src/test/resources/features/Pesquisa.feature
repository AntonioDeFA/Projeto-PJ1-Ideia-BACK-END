#language: pt
#enconding: UTF-8

Funcionalidade: Test Cucumber

	Cenario: Pesquisar por nome da competicao
		Dado que eu acesse a pagina inicial do ideia 
		E preencha o campo pesquisa com "IFPB"
		E clique no botao filtar
		Entao valide a busca "IFPB" 
		
		