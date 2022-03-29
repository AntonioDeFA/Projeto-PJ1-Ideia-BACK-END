package com.ideia.projetoideia.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.ideia.projetoideia.model.Competicao;



@Repository
public class CompeticaoRepositorioCustom {
	private final EntityManager entityManager;
	
	public CompeticaoRepositorioCustom(EntityManager en) {
		entityManager = en;
	}
	
	public List<Competicao>findByTodasCompeticoesFaseInscricao(String nome, Integer mes, Integer ano){
		
		
		
		String query = "SELECT c FROM Competicao AS c JOIN c.etapa e WHERE e.tipoEtapa = com.ideia.projetoideia.model.TipoEtapa.INSCRICAO";
	
		if(nome !=null) {
			query+= " AND c.nomeCompeticao = :nome";
		}
		if(mes !=null) {
			query+= " AND (month(e.dataInicio) = :mes OR month(e.dataTermino) = :mes) ";
		}
		if (ano !=null) {
			query+= " AND (year(e.dataInicio) = :ano OR year(e.dataTermino) = :ano) ";
		}
		
		var q = entityManager.createQuery(query,Competicao.class);
		
		//q.setParameter("etapaInscricao", etapaInscricao);
		
		if(nome !=null) {
			q.setParameter("nome", nome);
		}
		if(mes !=null) {
			q.setParameter("mes", mes);
			
		}
		if (ano !=null) {
			q.setParameter("ano", ano);
		}
		
		return q.getResultList();
	}
	
	
	public List<Competicao> fyndCompeticoesDoUsuario(String nome, Integer mes, Integer ano , Integer idUser){
		String query ="Select Comp from Competicao as Comp join Comp.etapa etapa join Comp.organizador org where org.id = :idUser";
		
		if(nome!=null) {
			query+=" and Comp.nomeCompeticao = :nome";
		}
		if(mes !=null) {
			query+= " and month(etapa.dataInicio) = :mes or month(etapa.dataTermino) = :mes";
		}
		if(ano !=null) {
			query+= " and year(etapa.dataInicio) = :ano or year(etapa.dataTermino) = :ano";
		}
		
		var q = entityManager.createQuery(query,Competicao.class);
		
		q.setParameter("idUser", idUser);
		
		if(nome !=null) {
			q.setParameter("nome", nome);
		}
		if(mes !=null) {
			q.setParameter("mes", mes);
			
		}
		if (ano !=null) {
			q.setParameter("ano", ano);
		}
		
		return q.getResultList();
		
	}

}
