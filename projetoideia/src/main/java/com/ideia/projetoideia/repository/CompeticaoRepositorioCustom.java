package com.ideia.projetoideia.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.ideia.projetoideia.model.Competicao;

@Repository
public class CompeticaoRepositorioCustom {
	private final EntityManager entityManager;

	public CompeticaoRepositorioCustom(EntityManager en) {
		entityManager = en;
	}

	public List<Competicao> findByTodasCompeticoesFaseInscricao(String nome, Integer mes, Integer ano, Integer idUser) {
		String query = "SELECT c FROM Competicao AS c JOIN c.etapas e WHERE e.tipoEtapa = "
				+ "com.ideia.projetoideia.model.TipoEtapa.INSCRICAO AND e.dataTermino >= curdate() "
				+ "AND (c.organizador.id != :idUser AND c.equipesCadatradas.lider.id != :idUser )";

		var q = montarQuery(query, nome, mes, ano);

		q.setParameter("idUser", idUser);

		return q.getResultList();
	}

	public List<Competicao> findByCompeticoesDoUsuario(String nome, Integer mes, Integer ano, Integer idUser) {
		String query = "SELECT c FROM Competicao AS c JOIN c.etapas e where c.organizador.id =:idUser "
				+ "OR c.equipesCadatradas.lider.id =:idUser ";

		var q = montarQuery(query, nome, mes, ano);

		q.setParameter("idUser", idUser);

		return q.getResultList();

	}

	private TypedQuery<Competicao> montarQuery(String query, String nome, Integer mes, Integer ano) {

		if (nome != null) {
			query += " AND c.nomeCompeticao =:nome";
		}
		if (mes != null) {
			query += " AND (month(e.dataInicio) = :mes OR month(e.dataTermino) = :mes)";
		}
		if (ano != null) {
			query += " and (year(e.dataInicio) = :ano OR year(e.dataTermino) = :ano)";
		}

		var q = entityManager.createQuery(query, Competicao.class);

		if (nome != null) {
			q.setParameter("nome", nome);
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
