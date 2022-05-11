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
import org.springframework.stereotype.Service;

import com.ideia.projetoideia.model.CategoriaMaterialEstudo;
import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Convite;
import com.ideia.projetoideia.model.Etapa;
import com.ideia.projetoideia.model.MaterialEstudo;
import com.ideia.projetoideia.model.PapelUsuarioCompeticao;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.dto.CompeticaoEtapaVigenteDto;
import com.ideia.projetoideia.model.dto.CompeticaoPatchDto;
import com.ideia.projetoideia.model.dto.ConsultorDto;
import com.ideia.projetoideia.model.dto.ConviteDto;
import com.ideia.projetoideia.repository.CategoriaMaterialEstudoRepositorio;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.CompeticaoRepositorioCustom;
import com.ideia.projetoideia.repository.ConviteRepositorio;
import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.QuestaoAvaliativa;
import com.ideia.projetoideia.model.dto.QuestoesAvaliativasDto;
import com.ideia.projetoideia.model.dto.UsuarioNaoRelacionadoDTO;
import com.ideia.projetoideia.model.enums.StatusConvite;
import com.ideia.projetoideia.model.enums.TipoConvite;
import com.ideia.projetoideia.model.enums.TipoEtapa;
import com.ideia.projetoideia.model.enums.TipoPapelUsuario;
import com.ideia.projetoideia.repository.EquipeRepositorio;
import com.ideia.projetoideia.repository.EtapaRepositorio;
import com.ideia.projetoideia.repository.MaterialEstudoRepositorio;
import com.ideia.projetoideia.repository.PapelUsuarioCompeticaoRepositorio;
import com.ideia.projetoideia.repository.QuestaoAvaliativaRepositorio;
import com.ideia.projetoideia.repository.UsuarioRepositorio;
import com.ideia.projetoideia.utils.EnviarEmail;

import javassist.NotFoundException;

@Service
public class CompeticaoService {

	@Autowired
	CompeticaoRepositorio competicaoRepositorio;

	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@Autowired
	EtapaRepositorio etapaRepositorio;

	@Autowired
	PapelUsuarioCompeticaoRepositorio papelUsuarioCompeticaoRepositorio;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	EnviarEmail enviarEmail;

	@Autowired
	ConviteRepositorio conviteRepositorio;

	@Autowired
	EquipeRepositorio equipeRepositorio;

	@Autowired
	QuestaoAvaliativaRepositorio questaoAvaliativaRepositorio;

	@Autowired
	MaterialEstudoRepositorio materialEstudoRepositorio;
	@Autowired
	CategoriaMaterialEstudoRepositorio categoriaMaterialEstudoRepositorio;

	private final CompeticaoRepositorioCustom competicaoRepositorioCustom;

	public CompeticaoService(CompeticaoRepositorioCustom competicaoRepositorioCustom) {
		this.competicaoRepositorioCustom = competicaoRepositorioCustom;
	}

	public Integer criarCompeticao(Competicao competicao) throws Exception {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.consultarUsuarioPorEmail(autenticado.getName());
		List<Etapa> etapas = competicao.getEtapas();
		for (Etapa etapa : etapas) {
			etapa.setCompeticao(null);
			etapaRepositorio.save(etapa);
		}
		if (competicao.getQntdMaximaMembrosPorEquipe() < competicao.getQntdMinimaMembrosPorEquipe()) {
			throw new Exception("Quantidade mínima de membros não pode ser maior que a quantidade máxima!");
		}
		competicao.setOrganizador(usuario);
		Integer idCompeticao = competicaoRepositorio.save(competicao).getId();
		for (Etapa etapa : etapas) {
			etapa.setCompeticao(competicao);
			etapaRepositorio.save(etapa);
		}
		PapelUsuarioCompeticao papelUsuarioCompeticao = new PapelUsuarioCompeticao();
		papelUsuarioCompeticao.setTipoPapelUsuario(TipoPapelUsuario.ORGANIZADOR);
		papelUsuarioCompeticao.setUsuario(usuario);
		papelUsuarioCompeticao.setCompeticaoCadastrada(competicao);

		papelUsuarioCompeticaoRepositorio.save(papelUsuarioCompeticao);

		for (QuestaoAvaliativa questao : competicao.getQuestoesAvaliativas()) {
			questao.setCompeticaoCadastrada(competicao);
			questaoAvaliativaRepositorio.save(questao);

		}
		return idCompeticao;
	}

	public void atualizarCompeticao(Integer id, Competicao competicaoTemp) throws Exception, NotFoundException {
		Competicao comp = recuperarCompeticaoId(id);
		if (comp.getQntdMaximaMembrosPorEquipe() < comp.getQntdMinimaMembrosPorEquipe()) {
			throw new Exception("Quantidade mínima de membros não pode ser maior que a quantidade máxima!");
		}
		comp.setNomeCompeticao(competicaoTemp.getNomeCompeticao());
		comp.setArquivoRegulamentoCompeticao(competicaoTemp.getArquivoRegulamentoCompeticao());
		comp.setEtapas(competicaoTemp.getEtapas());
		comp.setDominioCompeticao(competicaoTemp.getDominioCompeticao());
		comp.setQntdMaximaMembrosPorEquipe(competicaoTemp.getQntdMaximaMembrosPorEquipe());
		comp.setQntdMinimaMembrosPorEquipe(competicaoTemp.getQntdMinimaMembrosPorEquipe());
		comp.setTempoMaximoVideoEmSeg(competicaoTemp.getTempoMaximoVideoEmSeg());

		competicaoRepositorio.save(comp);

	}

	public List<Competicao> consultarCompeticoes() {
		return competicaoRepositorio.findAll();
	}

	public Competicao recuperarCompeticaoId(Integer id) throws NotFoundException {
		Optional<Competicao> comp = competicaoRepositorio.findById(id);

		if (comp.isPresent())
			return comp.get();

		throw new NotFoundException("Competição não encontrada");
	}

	public List<Competicao> consultarCompeticoesDoUsuario(Integer idUsuario) {

		List<Competicao> competicoesDoUsuario = new ArrayList<>();

		List<Competicao> competicoesComoLiderEquipe = competicaoRepositorio.findByEquipe(idUsuario);
		List<Competicao> competicoesComoOrganizador = competicaoRepositorio.findByOrganizador(idUsuario);

		competicoesDoUsuario.addAll(competicoesComoLiderEquipe);

		competicoesDoUsuario.addAll(competicoesComoOrganizador);

		return competicoesDoUsuario;
	}

	public Page<Competicao> consultarCompeticoes(Integer numeroPagina) {
		Direction sortDirection = Sort.Direction.ASC;
		Sort sort = Sort.by(sortDirection, "nome_competicao");
		Page<Competicao> page = competicaoRepositorio.findAll(PageRequest.of(--numeroPagina, 6, sort));
		return page;
	}

	public List<CompeticaoEtapaVigenteDto> consultarCompeticoesFaseInscricao(String nomeCompeticao, Integer mes,
			Integer ano) throws Exception {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.consultarUsuarioPorEmail(autenticado.getName());

		List<Competicao> competicoes = competicaoRepositorioCustom.findByTodasCompeticoesFaseInscricao(nomeCompeticao,
				mes, ano, usuario.getId());

		List<CompeticaoEtapaVigenteDto> competicoesDto = new ArrayList<>();
		for (Competicao competicao : competicoes) {
			CompeticaoEtapaVigenteDto competicaoEtapaVigenteDto = new CompeticaoEtapaVigenteDto(competicao, "INSCRICAO",
					usuario);
			competicoesDto.add(competicaoEtapaVigenteDto);
		}
		return competicoesDto;
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
			if (!entrou) {
				usuarios.add(new UsuarioNaoRelacionadoDTO(usuarioRecuperado));
			}

		}
		if (usuarios.size() == 0) {
			throw new Exception("Não existe nenhum usuario não cadastrado nessa competição.");
		}
		return usuarios;
	}

	public void deletarCompeticaoPorId(Integer id) throws Exception {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.consultarUsuarioPorEmail(autenticado.getName());

		Competicao competicao = recuperarCompeticaoId(id);
		PapelUsuarioCompeticao papelUsuarioCompeticaoRecuperada = null;

		for (PapelUsuarioCompeticao papelUsuarioCompeticao : papelUsuarioCompeticaoRepositorio.findByUsuario(usuario)) {
			if (papelUsuarioCompeticao.getCompeticaoCadastrada().getId() == competicao.getId()) {
				papelUsuarioCompeticaoRecuperada = papelUsuarioCompeticao;
				break;
			}

		}

		if (papelUsuarioCompeticaoRecuperada.getTipoPapelUsuario() == TipoPapelUsuario.ORGANIZADOR) {
			competicaoRepositorio.delete(competicao);
		} else if (papelUsuarioCompeticaoRecuperada.getTipoPapelUsuario() == TipoPapelUsuario.COMPETIDOR) {
			List<Equipe> equipes = equipeRepositorio.findByLider(usuario);
			Equipe equipeRecuperada = null;
			for (Equipe equipe : equipes) {
				if (equipe.getCompeticaoCadastrada().getId() == competicao.getId()) {
					equipeRecuperada = equipe;
					break;
				}
			}
			equipeRepositorio.delete(equipeRecuperada);
		}
		papelUsuarioCompeticaoRepositorio.delete(papelUsuarioCompeticaoRecuperada);

	}

	public List<QuestoesAvaliativasDto> consultarQuestoesDaCompeticao(Integer competicaoId) throws NotFoundException {
		Competicao competicaoRecuperada = recuperarCompeticaoId(competicaoId);

		List<QuestaoAvaliativa> questaoAvaliativas = questaoAvaliativaRepositorio
				.findByCompeticaoCadastrada(competicaoRecuperada);

		List<QuestoesAvaliativasDto> questoesAvaliativasDto = new ArrayList<QuestoesAvaliativasDto>();
		for (QuestaoAvaliativa questaoAvaliativa : questaoAvaliativas) {
			QuestoesAvaliativasDto questaoDto = new QuestoesAvaliativasDto(questaoAvaliativa);
			questoesAvaliativasDto.add(questaoDto);
		}

		return questoesAvaliativasDto;

	}

	public void convidarUsuario(ConviteDto conviteDto) throws Exception {

		Competicao competicao = this.recuperarCompeticaoId(conviteDto.getIdCompeticao());

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

	public List<ConsultorDto> listarConsultoresEAaliadoresDeUmaCompeticao(Integer idCompeticao, TipoConvite tipoConvite)
			throws Exception {
		Competicao competicao = recuperarCompeticaoId(idCompeticao);
		List<ConsultorDto> consultoresDto = new ArrayList<ConsultorDto>();

		List<Convite> convites = conviteRepositorio.findByCompeticao(competicao);

		for (Convite convite : convites) {

			if (convite.getTipoConvite().equals(tipoConvite)) {
				ConsultorDto consultorDto = new ConsultorDto(convite.getUsuario(), convite.getStatusConvite());
				consultoresDto.add(consultorDto);
			}
		}
		return consultoresDto;

//		List<Usuario> todosOsUsuarios = usuarioRepositorio.findAll();
//		
//		for (Usuario usuario : todosOsUsuarios) {
//			if(usuarioRepositorio.listarSeUsuarioTemConvitesDeUmaCompeticao(idCompeticao, usuario.getId()).isEmpty()) {
//				if(usuarioRepositorio.listarSeUsuarioTemRelacaoComCompeticao(idCompeticao, usuario.getId()).isEmpty()) {
//					ConsultorDto consultorDto = new ConsultorDto(usuario,null);
//					consultoresDto.add(consultorDto);
//				}	
//			}
//		}
	}

	public void patchCompeticao(CompeticaoPatchDto competicaoPatchDto, Integer idCompeticao) throws Exception {
		Competicao competicao = recuperarCompeticaoId(idCompeticao);

		List<Etapa> etapasDoDto = competicaoPatchDto.getEtapas();

		for (Etapa etapa : etapasDoDto) {

			Etapa EtapaCompeticaoVingente = etapaRepositorio.findEtapaCompeticao(etapa.getTipoEtapa().getValue(),
					idCompeticao);

			EtapaCompeticaoVingente.setDataInicio(etapa.getDataInicio());
			EtapaCompeticaoVingente.setDataTermino(etapa.getDataTermino());

			if (etapa.getTipoEtapa().equals(TipoEtapa.AQUECIMENTO)) {

				for (MaterialEstudo material : competicaoPatchDto.getMateriaisDeEstudo()) {

					material.setEtapa(EtapaCompeticaoVingente);

					CategoriaMaterialEstudo cat = material.getCategoriaMaterialEstudo();
					categoriaMaterialEstudoRepositorio.save(cat);
					materialEstudoRepositorio.save(material);

				}

			}

			etapaRepositorio.save(EtapaCompeticaoVingente);
		}

		if (competicaoPatchDto.getArquivoRegulamentoCompeticao() != null) {
			competicao.setArquivoRegulamentoCompeticao(competicaoPatchDto.getArquivoRegulamentoCompeticao());
		}

		if (competicaoPatchDto.getTempoMaximoVideoEmSeg() != null) {
			competicao.setTempoMaximoVideoEmSeg(competicaoPatchDto.getTempoMaximoVideoEmSeg());
		}

		if (competicaoPatchDto.getNomeCompeticao() != null) {
			competicao.setNomeCompeticao(competicaoPatchDto.getNomeCompeticao());
		}

		for (QuestaoAvaliativa questaoAvaliativa : competicaoPatchDto.getQuestoesAvaliativas()) {
			questaoAvaliativa.setCompeticaoCadastrada(competicao);
			questaoAvaliativaRepositorio.save(questaoAvaliativa);
		}

		competicao.setElaboracao(false);

		competicaoRepositorio.save(competicao);

	}

}
