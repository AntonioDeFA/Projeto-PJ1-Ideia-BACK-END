package com.ideia.projetoideia.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ideia.projetoideia.model.Equipe;

import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.UsuarioMembroComum;
import com.ideia.projetoideia.model.PapelUsuarioCompeticao;
import com.ideia.projetoideia.model.TipoPapelUsuario;
import com.ideia.projetoideia.model.dto.EquipeDtoCriacao;
import com.ideia.projetoideia.model.dto.UsuarioDto;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.EquipeRepositorio;
import com.ideia.projetoideia.repository.PapelUsuarioCompeticaoRepositorio;
import com.ideia.projetoideia.repository.UsuarioMembroComumRepositorio;
import com.ideia.projetoideia.services.utils.GeradorEquipeToken;

import javassist.NotFoundException;

@Service
public class EquipeService {

	@Autowired
	private EquipeRepositorio equipeRepositorio;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private CompeticaoRepositorio competicaoRepositorio;

	@Autowired
	private PapelUsuarioCompeticaoRepositorio papelUsuarioCompeticaoRepositorio;

	@Autowired
	private UsuarioMembroComumRepositorio usuarioMembroComumRepositorio;

	public void criarEquipe(EquipeDtoCriacao equipeDto) throws Exception {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.consultarUsuarioPorEmail(autenticado.getName());
		
		List<String> lista = new ArrayList<String>();
		for(UsuarioDto user: equipeDto.getUsuarios()) {
			lista.add(user.getEmail());
		}
		
		if(equipeRepositorio.validarUsuarioLiderEOrganizador(usuario.getId(), equipeDto.getIdCompeticao())> 0){
			throw new Exception("Observe se você não é o organizador desta competição ou se já não está inscrito nesta competição");
		}
		if(equipeRepositorio.validarMembrosDeUmaEquipeEmUmaCompeticao(lista, equipeDto.getIdCompeticao())> 0){
			throw new Exception("Algum usuário de sua equipe já está participando dessa competição em outra equipe");
		}

		Equipe equipe = new Equipe();
		equipe.setNomeEquipe(equipeDto.getNomeEquipe());
		equipe.setDataInscricao(LocalDate.now());
		equipe.setToken(GeradorEquipeToken.gerarTokenEquipe(equipeDto.getNomeEquipe()));
		equipe.setCompeticaoCadastrada(competicaoRepositorio.findById(equipeDto.getIdCompeticao()).get());
		equipe.setLider(usuario);

		PapelUsuarioCompeticao papelUsuarioCompeticao = new PapelUsuarioCompeticao();
		papelUsuarioCompeticao.setTipoPapelUsuario(TipoPapelUsuario.COMPETIDOR);
		papelUsuarioCompeticao.setUsuario(usuario);
		papelUsuarioCompeticao.setCompeticao(equipe.getCompeticaoCadastrada());

		papelUsuarioCompeticaoRepositorio.save(papelUsuarioCompeticao);
		equipeRepositorio.save(equipe);

		for (UsuarioDto usuarioDto : equipeDto.getUsuarios()) {
			UsuarioMembroComum usuarioComum = new UsuarioMembroComum();
			usuarioComum.setEmail(usuarioDto.getEmail());
			usuarioComum.setNome(usuarioDto.getNomeUsuario());
			usuarioComum.setEquipe(equipe);
			usuarioMembroComumRepositorio.save(usuarioComum);
		}

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

	public Equipe consultarEquipePorToken(String token) {

		return equipeRepositorio.consultarEquipePorToken(token).orElse(null);

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
		List<PapelUsuarioCompeticao> papelUsuarioCompeticoes = papelUsuarioCompeticaoRepositorio
				.findByUsuario(equipe.getLider());
		for (PapelUsuarioCompeticao papelUsuarioCompeticao : papelUsuarioCompeticoes) {
			if (papelUsuarioCompeticao.getTipoPapelUsuario() == TipoPapelUsuario.COMPETIDOR) {
				papelUsuarioCompeticaoRepositorio.delete(papelUsuarioCompeticao);
				break;
			}
		}
		equipeRepositorio.delete(equipe);
	}
}
