#language: pt
#enconding: UTF-8

@ConsultoriaPitch
	Funcionalidade: Consultoria
	
	Contexto: 
		Dado que eu passe o email "consultor@gmail.com"
		E passe a senha "1"
		Quando eu clicar no botao login
		E clicar no icone de consultor 
		E clicar no icone de consultar pitch
		 
	Cenario: tentar realizar consultoria com erro 
	E digite o feedback sem salvar "Feedback teste"
	E clique no botao salvar feedback de consultoria
	Entao o sistema recebe o erro "Você precisa fornecer feedbacks antes de devolver à equipe!"
	
	
	
	Cenario: realizar consultoria de feedback
	E digite o feedback sem salvar "Feedback teste correto"
	E clicar no botao adicionar feedback 
	E clique no botao salvar feedback de consultoria
	E clique no botao de confirmacao de envio de consultoria
	Entao o sistema valida que o feedback foi enviado com sucesso 