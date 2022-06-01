package com.ideia.projetoideia.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Convite;
import com.ideia.projetoideia.model.PapelUsuarioCompeticao;
import com.ideia.projetoideia.model.Perfil;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.dto.CompeticaoEtapaVigenteDto;
import com.ideia.projetoideia.model.dto.ConviteDto;
import com.ideia.projetoideia.model.dto.UsuarioDto;
import com.ideia.projetoideia.model.dto.UsuarioNaoRelacionadoDTO;
import com.ideia.projetoideia.model.dto.UsuarioPatchDto;
import com.ideia.projetoideia.model.enums.StatusConvite;
import com.ideia.projetoideia.model.enums.TipoConvite;
import com.ideia.projetoideia.model.enums.TipoPapelUsuario;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.CompeticaoRepositorioCustom;
import com.ideia.projetoideia.repository.ConviteRepositorio;
import com.ideia.projetoideia.repository.EquipeRepositorio;
import com.ideia.projetoideia.repository.PapelUsuarioCompeticaoRepositorio;
import com.ideia.projetoideia.repository.PerfilRepositorio;
import com.ideia.projetoideia.repository.UsuarioRepositorio;
import com.ideia.projetoideia.utils.EnviarEmail;

import javassist.NotFoundException;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@Autowired
	PerfilRepositorio perfilRepositorio;

	@Autowired
	EnviarEmail enviarEmail;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	PapelUsuarioCompeticaoRepositorio papelUsuarioCompeticaoRepositorio;

	@Autowired
	EquipeRepositorio equipeRepositorio;

	@Autowired
	ConviteRepositorio conviteRepositorio;

	@Autowired
	CompeticaoRepositorio competicaoRepositorio;

	@Autowired
	CompeticaoService competicaoService;

	private final CompeticaoRepositorioCustom competicaoRepositorioCustom;

	public UsuarioService(CompeticaoRepositorioCustom competicaoRepositorioCustom) {
		this.competicaoRepositorioCustom = competicaoRepositorioCustom;
	}

	public void criarUsuario(Usuario user) throws Exception {
		if (usuarioRepositorio.findByEmail(user.getEmail()).isPresent()) {
			throw new Exception(
					"Não foi possível criar esta conta, pois já existe um usuário com este email cadastrado");
		}
		if (user.getSenha() == null) {
			throw new Exception("A senha não deve pode ser nula");
		}
		user.setSenha(new BCryptPasswordEncoder().encode(user.getSenha()));
		List<Perfil> perfis = new ArrayList<>();
		Perfil perfil = new Perfil();
		perfil.setId(Perfil.PERFIL_USUARIO);
		perfis.add(perfil);

		user.setPerfis(perfis);

		usuarioRepositorio.save(user);
	}

	public List<Usuario> consultarUsuarios() {
		return usuarioRepositorio.findAll();
	}

	public UsuarioDto consultarUsuarioLogado() throws Exception {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		UsuarioDto dto = new UsuarioDto(this.consultarUsuarioPorEmail(autenticado.getName()));
		return dto;
	}

	public Page<Usuario> consultarUsuarios(Integer numeroPagina) {
		Direction sortDirection = Sort.Direction.ASC;
		Sort sort = Sort.by(sortDirection, "nomeUsuario");
		Page<Usuario> page = usuarioRepositorio.findAll(PageRequest.of(--numeroPagina, 4, sort));
		return page;
	}

	public Usuario consultarUsuarioPorEmail(String email) throws Exception {
		Optional<Usuario> usuario = usuarioRepositorio.findByEmail(email);

		if (usuario.isPresent()) {
			return usuario.get();
		}
		throw new NotFoundException("Não foi encontrado um usuário com esse email");

	}

	public Usuario consultarUsuarioPorId(Integer id) throws Exception {
		Optional<Usuario> user = usuarioRepositorio.findById(id);
		if (user.isPresent()) {
			return user.get();
		}
		throw new NotFoundException("Usuario não encontrado");
	}

	public void atualizarUsuario(UsuarioPatchDto user) throws Exception {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = this.consultarUsuarioPorEmail(autenticado.getName());

		if (user.getSenha() != null) {
			if (!user.getSenha().equals("")) {
				usuario.setSenha(new BCryptPasswordEncoder().encode(user.getSenha()));
			}
		}
		if (user.getNomeUsuario() != null) {
			if (!user.getNomeUsuario().equals("")) {
				usuario.setNomeUsuario(user.getNomeUsuario());
			}
		}

		usuarioRepositorio.save(usuario);
	}

	public void deletarUsuarioPorId(Integer id) throws Exception {
		Usuario usuario = consultarUsuarioPorId(id);
		usuarioRepositorio.delete(usuario);
	}

	public void resetarSenhaDoUsuario(String email) throws Exception {
		Usuario user = consultarUsuarioPorEmail(email);
		String novaSenha = "";
		novaSenha += System.currentTimeMillis();

		user.setSenha(new BCryptPasswordEncoder().encode(novaSenha));

		usuarioRepositorio.save(user);

		enviarEmail.enviarEmailDeResetDeSenha(user, novaSenha);
	}

	public void inicializarPerfil() {
		List<Perfil> perfis = perfilRepositorio.findAll();

		if (perfis.size() == 0) {
			Perfil perfil = new Perfil();
			perfil.setNomePerfil("USUARIO");
			perfil.setId(1);
			perfilRepositorio.save(perfil);
		}
	}

	public List<CompeticaoEtapaVigenteDto> consultarMinhasCompeticoes(String nomeCompeticao, Integer mes, Integer ano)
			throws Exception {

		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.consultarUsuarioPorEmail(autenticado.getName());
		List<CompeticaoEtapaVigenteDto> competicoesDto = new ArrayList<>();
		List<Competicao> competicoes = competicaoRepositorioCustom.findByCompeticoesDoUsuario(nomeCompeticao, mes, ano,
				usuario.getId());
		for (Competicao competicao : competicoes) {
			CompeticaoEtapaVigenteDto competicaoEtapaVigenteDto = new CompeticaoEtapaVigenteDto(competicao,
					"COMPETICAO", usuario);
			PapelUsuarioCompeticao papelUsuarioCompeticao = papelUsuarioCompeticaoRepositorio
					.findById(papelUsuarioCompeticaoRepositorio.findByCompeticaoCadastradaAndUsuario(usuario.getId(),
							competicao.getId()))
					.get();
			if (papelUsuarioCompeticao.getTipoPapelUsuario().equals(TipoPapelUsuario.COMPETIDOR)) {
				competicaoEtapaVigenteDto.setIdEquipe(
						equipeRepositorio.findByCompeticaoCadastradaAndUsuario(usuario.getId(), competicao.getId()));
			}
			competicoesDto.add(competicaoEtapaVigenteDto);
		}
		return competicoesDto;
	}

	public List<UsuarioNaoRelacionadoDTO> consultarUsuariosSemCompeticao(Integer idCompeticao) throws Exception {
		List<UsuarioNaoRelacionadoDTO> usuarios = new ArrayList<UsuarioNaoRelacionadoDTO>();
		Competicao competicao = competicaoRepositorio.findById(idCompeticao).get();
		List<PapelUsuarioCompeticao> papeis = papelUsuarioCompeticaoRepositorio.findByCompeticaoCadastrada(competicao);
		for (Usuario usuarioRecuperado : usuarioRepositorio.findAll()) {
			boolean entrou = false;
			for (PapelUsuarioCompeticao papelUsuarioCompeticao : papeis) {
				if (papelUsuarioCompeticao.getUsuario().getId() == usuarioRecuperado.getId()) {
					entrou = true;
				}
			}
			for (Convite convite : conviteRepositorio.findByUsuario(usuarioRecuperado)) {
				if (convite.getUsuario().getId() == usuarioRecuperado.getId()
						&& competicao.getId() == convite.getCompeticao().getId()) {
					entrou = true;
				}
			}
			if (!entrou) {
				usuarios.add(new UsuarioNaoRelacionadoDTO(usuarioRecuperado));
			}

		}
		if (usuarios.size() == 0) {
			throw new Exception("Não existe nenhum usuario não cadastrado nessa competição.");
		}
		return usuarios;
	}

	public void convidarUsuario(ConviteDto conviteDto) throws Exception {

		Competicao competicao = competicaoService.recuperarCompeticaoId(conviteDto.getIdCompeticao());

		Usuario usuario = usuarioService.consultarUsuarioPorEmail(conviteDto.getEmailDoUsuario());

		List<Convite> convites = conviteRepositorio.findAll();

		for (Convite convi : convites) {
			if (convi.getCompeticao().getId() == competicao.getId() && convi.getUsuario().getId() == usuario.getId()) {
				throw new DuplicateKeyException("Usuário já possui convites para essa competição");
			}
		}

		List<PapelUsuarioCompeticao> papeis = papelUsuarioCompeticaoRepositorio.findByCompeticaoCadastrada(competicao);

		for (PapelUsuarioCompeticao papelUsuarioCompeticao : papeis) {

			if (papelUsuarioCompeticao.getUsuario().getId() == usuario.getId()) {
				throw new DuplicateKeyException("Usuário já possui ligação com essa competição");
			}

		}

		Convite convite = new Convite();

		competicao.getConvites().add(convite);

		usuario.getConvites().add(convite);
		convite.setCompeticao(competicao);

		convite.setUsuario(usuario);

		convite.setStatusConvite(StatusConvite.ENVIADO);
		if (conviteDto.getTipoConvite().equals(TipoConvite.CONSULTOR)) {
			convite.setTipoConvite(TipoConvite.CONSULTOR);
		} else {
			convite.setTipoConvite(TipoConvite.AVALIADOR);
		}
		competicaoRepositorio.save(competicao);

		usuarioRepositorio.save(usuario);

		enviarEmail.enviarEmailConviteUsuario(usuario, convite.getTipoConvite(), competicao);

	}

}
