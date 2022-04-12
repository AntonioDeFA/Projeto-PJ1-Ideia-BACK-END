package com.ideia.projetoideia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.repository.EquipeRepositorio;

import javassist.NotFoundException;

@Service
public class EquipeService {

	@Autowired
	private EquipeRepositorio equipeRepositorio;

	public void criarEquipe(Equipe equipe) throws Exception {
		equipeRepositorio.save(equipe);
	}

	public Equipe recuperarEquipe(Integer equipeId) throws NotFoundException {
		Optional<Equipe> equipe = equipeRepositorio.findById(equipeId);

		if (equipe.isPresent()) {
			return equipe.get();
		}

		throw new NotFoundException("Equipe não encontrada");

	}

	public List<Equipe> consultarEquipes() {
		return equipeRepositorio.findAll();
	}

	public Page<Equipe> consultarEquipesDeUmaCompeticao(Integer numeroPagina, Integer competicaoId) {
		Direction sortDirection = Sort.Direction.ASC;
		Sort sort = Sort.by(sortDirection, "nome_equipe");
		Page<Equipe> page = equipeRepositorio.findByCompeticao(competicaoId, PageRequest.of(--numeroPagina, 6, sort));
		return page;
	}

	public Page<Equipe> consultarEquipes(Integer numeroPagina) {
		Direction sortDirection = Sort.Direction.ASC;
		Sort sort = Sort.by(sortDirection, "nome_equipe");
		Page<Equipe> page = equipeRepositorio.findAll(PageRequest.of(--numeroPagina, 6, sort));
		return page;
	}

	public void atualizarEquipe(Equipe equipe, Integer id) throws Exception {
		Equipe equipeRecuperada = this.recuperarEquipe(id);
		equipeRecuperada.setNomeEquipe(equipe.getNomeEquipe());
		equipeRecuperada.setDataInscricao(equipe.getDataInscricao());
		equipeRecuperada.setLider(equipe.getLider());
		equipeRecuperada.setToken(equipe.getToken());
		equipeRepositorio.save(equipeRecuperada);
	}

	public void deletarEquipe(Integer id) throws NotFoundException {
		Equipe equipe = this.recuperarEquipe(id);
		equipeRepositorio.delete(equipe);
	}
}
