package com.ideia.projetoideia.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.PapelUsuarioCompeticao;


@Repository
public class CompeticaoRepositorioCustom {
	private final EntityManager entityManager;
	
	private String nomeCompeticao;
	private Integer mes;
	private Integer ano;

	public CompeticaoRepositorioCustom(EntityManager en) {
		entityManager = en;
	}

	public List<Competicao> findByTodasCompeticoesFaseInscricao(String nome, Integer mes, Integer ano, Integer idUser) {
		String query = "SELECT c FROM Competicao AS c JOIN  c.etapas e WHERE "
				+ "com.ideia.projetoideia.model.TipoEtapa.INSCRICAO = e.tipoEtapa "
				+ "AND e.dataInicio <= curdate() "
				+ "AND e.dataTermino >= curdate()";
		
		this.nomeCompeticao = nome;
		this.mes = mes;
		this.ano = ano;
		
		this.sanitizar();
		var q = montarQuery(query);
		return this.retirarCompeticoesInvalidas(idUser, q.getResultList());
	}

	public List<Competicao> findByCompeticoesDoUsuario(String nome, Integer mes, Integer ano, Integer idUser) {
		String query = "SELECT DISTINCT(c) FROM Competicao AS c JOIN c.etapas e "
				+ "JOIN c.papeisUsuarioCompeticao tp "
				+ "WHERE tp.usuario.id =: idUser";
		
		this.nomeCompeticao = nome;
		this.mes = mes;
		this.ano = ano;
		
		this.sanitizar();
		var q = montarQuery(query);

		q.setParameter("idUser", idUser);

		return q.getResultList();

	}
	
	private List<Competicao> retirarCompeticoesInvalidas(Integer idUser , List<Competicao> competicoesComDadosInvalidos){
		List<Competicao> competicoesValidas = new ArrayList<>();
		
		for (Competicao competicao : competicoesComDadosInvalidos) {
			
			boolean entrou = false;
			for (PapelUsuarioCompeticao papelUsuarioCompeticao : competicao.getPapeisUsuarioCompeticao()) {
				if(papelUsuarioCompeticao.getUsuario().getId() == idUser || competicao.isElaboracao()) {
					entrou = true;
				}
			}
			if (entrou == false) {
				competicoesValidas.add(competicao);
			}
		}
		
		return competicoesValidas;
	
	}
	

	private void sanitizar() {
		if (nomeCompeticao != null) {
			if(nomeCompeticao.equals("")) {
				nomeCompeticao = null;
			}
		}
		if (mes != null ) {
			if(mes <= 0 || mes > 12) {
				mes = null;
			}
			
		}

		if (ano != null ) {
			if(ano <= 0) {
				ano = null;
			}
		}
	}

	private TypedQuery<Competicao> montarQuery(String query) {

		if (nomeCompeticao != null) {
			query += " AND c.nomeCompeticao LIKE CONCAT('%',:nome,'%')";
		}
		if (mes != null) {
			query += " AND (month(e.dataInicio) = :mes OR month(e.dataTermino) = :mes)";
		}
		if (ano != null) {
			query += " AND (year(e.dataInicio) = :ano OR year(e.dataTermino) = :ano)";
		}

		var q = entityManager.createQuery(query, Competicao.class);

		if (nomeCompeticao != null) {
			q.setParameter("nome", nomeCompeticao);
		}
		if (mes != null) {
			q.setParameter("mes", mes);

		}
		if (ano != null) {
			q.setParameter("ano", ano);
		}

		return q;

	}

}
