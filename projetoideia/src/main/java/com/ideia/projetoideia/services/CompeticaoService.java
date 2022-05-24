package com.ideia.projetoideia.services;

import java.io.File;
import java.io.FileOutputStream;
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

import com.ideia.projetoideia.model.AvaliacaoPitch;
import com.ideia.projetoideia.model.CategoriaMaterialEstudo;
import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Convite;
import com.ideia.projetoideia.model.Etapa;
import com.ideia.projetoideia.model.MaterialEstudo;
import com.ideia.projetoideia.model.PapelUsuarioCompeticao;
import com.ideia.projetoideia.model.Pitch;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.dto.CompeticaoDadosGeraisDto;
import com.ideia.projetoideia.model.dto.CompeticaoEtapaVigenteDto;
import com.ideia.projetoideia.model.dto.CompeticaoPatchDto;
import com.ideia.projetoideia.model.dto.CompeticaoPutDto;
import com.ideia.projetoideia.model.dto.ConsultorEAvaliadorDto;
import com.ideia.projetoideia.model.dto.ConviteDto;
import com.ideia.projetoideia.model.dto.ConviteRespostaDto;
import com.ideia.projetoideia.model.dto.EmailDto;
import com.ideia.projetoideia.model.dto.EquipeNomeDto;
import com.ideia.projetoideia.model.dto.EquipeNotaDto;
import com.ideia.projetoideia.model.dto.MaterialEstudoDTO;
import com.ideia.projetoideia.repository.AvaliacaoPitchRpositorio;
import com.ideia.projetoideia.repository.CategoriaMaterialEstudoRepositorio;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.CompeticaoRepositorioCustom;
import com.ideia.projetoideia.repository.ConviteRepositorio;
import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.QuestaoAvaliativa;
import com.ideia.projetoideia.model.dto.QuestoesAvaliativasDto;
import com.ideia.projetoideia.model.dto.UsuarioConsultorDto;
import com.ideia.projetoideia.model.dto.UsuarioNaoRelacionadoDTO;
import com.ideia.projetoideia.model.enums.EtapaArtefatoPitch;
import com.ideia.projetoideia.model.enums.StatusConvite;
import com.ideia.projetoideia.model.enums.TipoConvite;
import com.ideia.projetoideia.model.enums.TipoEtapa;
import com.ideia.projetoideia.model.enums.TipoPapelUsuario;
import com.ideia.projetoideia.repository.EquipeRepositorio;
import com.ideia.projetoideia.repository.EtapaRepositorio;
import com.ideia.projetoideia.repository.MaterialEstudoRepositorio;
import com.ideia.projetoideia.repository.PapelUsuarioCompeticaoRepositorio;
import com.ideia.projetoideia.repository.PitchRepositorio;
import com.ideia.projetoideia.repository.QuestaoAvaliativaRepositorio;
import com.ideia.projetoideia.repository.UsuarioRepositorio;
import com.ideia.projetoideia.services.utils.ConversorDeArquivos;
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

	@Autowired
	AvaliacaoPitchRpositorio avaliacaoPitchRpositorio;

	@Autowired
	PitchRepositorio pitchRepositorio;

	private final CompeticaoRepositorioCustom competicaoRepositorioCustom;

	public CompeticaoService(CompeticaoRepositorioCustom competicaoRepositorioCustom) {
		this.competicaoRepositorioCustom = competicaoRepositorioCustom;
	}

	public Integer criarCompeticao(Competicao competicao) throws Exception {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.consultarUsuarioPorEmail(autenticado.getName());
		List<Etapa> etapas = competicao.getEtapas();
		if (competicaoRepositorio.findByNomeCompeticao(competicao.getNomeCompeticao()).size() != 0) {
			throw new Exception(
					"Não foi possível criar a competição, pois já existe uma competição com este nome cadastrado");
		}
		for (Etapa etapa : etapas) {
			etapa.setCompeticao(null);
			etapaRepositorio.save(etapa);
		}
		if (competicao.getQntdMaximaMembrosPorEquipe() < competicao.getQntdMinimaMembrosPorEquipe()) {
			throw new Exception("Quantidade mínima de membros não pode ser maior que a quantidade máxima!");
		}
		competicao.setOrganizador(usuario);
		Integer idCompeticao = competicaoRepositorio.save(competicao).getId();
		//aqui tem que converter a string base64 em um arquivo
		ConversorDeArquivos.converterStringParaArquivo(competicao.getArquivoRegulamentoCompeticao(), idCompeticao);	
		
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

	public void atualizarCompeticao(Integer idCompeticao, CompeticaoPutDto competicaoPutDto)
			throws Exception, NotFoundException {
		Competicao comp = recuperarCompeticaoId(idCompeticao);
		List<Competicao> competicoes = competicaoRepositorio.findByNomeCompeticao(competicaoPutDto.getNomeCompeticao());
		boolean entrou = false;
		
		for (Competicao competicao : competicoes) {
			if(competicao.getNomeCompeticao().equals(competicaoPutDto.getNomeCompeticao())
					&& comp.getId() != competicao.getId()) {
				entrou = true;
			}
		}
		if (entrou) {
			throw new Exception(
					"Não foi possível criar a competição, pois já existe uma competição com este nome cadastrado");
		}
		if (competicaoPutDto.getQntdMaximaMembrosPorEquipe() < competicaoPutDto.getQntdMinimaMembrosPorEquipe()) {
			throw new Exception("Quantidade mínima de membros não pode ser maior que a quantidade máxima!");
		}

		if (!comp.getIsElaboracao()) {
			throw new Exception("A competição deve estar na etapa de elaboração");
		}

		for (Etapa etapa : competicaoPutDto.getEtapas()) {

			Etapa EtapaCompeticaoVingente = etapaRepositorio.findEtapaCompeticao(etapa.getTipoEtapa().getValue(),
					idCompeticao);
			EtapaCompeticaoVingente.setDataInicio(etapa.getDataInicio());
			EtapaCompeticaoVingente.setDataTermino(etapa.getDataTermino());
			etapaRepositorio.save(EtapaCompeticaoVingente);
		}
		comp.setArquivoRegulamentoCompeticao(competicaoPutDto.getArquivoRegulamentoCompeticao());
		//aqui tem que converter a string base64 em um arquivo
		ConversorDeArquivos.converterStringParaArquivo(competicaoPutDto.getArquivoRegulamentoCompeticao(), idCompeticao);
		comp.setDominioCompeticao(competicaoPutDto.getDominioCompeticao());
		comp.setQntdMaximaMembrosPorEquipe(competicaoPutDto.getQntdMaximaMembrosPorEquipe());
		comp.setQntdMinimaMembrosPorEquipe(competicaoPutDto.getQntdMinimaMembrosPorEquipe());
		comp.setNomeCompeticao(competicaoPutDto.getNomeCompeticao());
		comp.setTempoMaximoVideoEmSeg(competicaoPutDto.getTempoMaximoVideoEmSeg());

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
			for (Convite conviteParaDeletar : conviteRepositorio.findByCompeticao(competicao)) {
				conviteRepositorio.delete(conviteParaDeletar);
			}
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

	public List<ConsultorEAvaliadorDto> listarConsultoresEAaliadoresDeUmaCompeticao(Integer idCompeticao,
			TipoConvite tipoConvite) throws Exception {
		Competicao competicao = recuperarCompeticaoId(idCompeticao);
		List<ConsultorEAvaliadorDto> consultoresDto = new ArrayList<ConsultorEAvaliadorDto>();

		List<Convite> convites = conviteRepositorio.findByCompeticao(competicao);

		for (Convite convite : convites) {

			if (convite.getTipoConvite().equals(tipoConvite)) {
				ConsultorEAvaliadorDto consultorDto = new ConsultorEAvaliadorDto(convite.getUsuario(),
						convite.getStatusConvite());
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

	public String recuperarRegulamentoCompeticao(Integer idCompeticao) throws Exception {
		Competicao comp = recuperarCompeticaoId(idCompeticao);

		return comp.getArquivoRegulamentoCompeticao();

	}

	public void patchCompeticao(CompeticaoPatchDto competicaoPatchDto, Integer idCompeticao) throws Exception {
		Competicao competicao = recuperarCompeticaoId(idCompeticao);

		List<Etapa> etapasDoDto = competicaoPatchDto.getEtapas();

		if (etapasDoDto != null) {
			for (Etapa etapa : etapasDoDto) {

				Etapa EtapaCompeticaoVingente = etapaRepositorio.findEtapaCompeticao(etapa.getTipoEtapa().getValue(),
						idCompeticao);

				if (competicaoPatchDto.getMateriaisDeEstudo() != null) {
					for (MaterialEstudo materialEstudo : materialEstudoRepositorio
							.findByEtapa(EtapaCompeticaoVingente)) {
						categoriaMaterialEstudoRepositorio.delete(materialEstudo.getCategoriaMaterialEstudo());
						materialEstudoRepositorio.delete(materialEstudo);
					}
				}

				EtapaCompeticaoVingente.setDataInicio(etapa.getDataInicio());
				EtapaCompeticaoVingente.setDataTermino(etapa.getDataTermino());

				if (etapa.getTipoEtapa().equals(TipoEtapa.AQUECIMENTO) 
						&& competicaoPatchDto.getMateriaisDeEstudo()!= null) {

					for (MaterialEstudo material : competicaoPatchDto.getMateriaisDeEstudo()) {

						material.setEtapa(EtapaCompeticaoVingente);

						CategoriaMaterialEstudo cat = material.getCategoriaMaterialEstudo();
						categoriaMaterialEstudoRepositorio.save(cat);
						materialEstudoRepositorio.save(material);
					}
					//aqui a gente salva na pasta da competição, os respectivos materiais de estudo
					ConversorDeArquivos.converterStringParaArquivo(competicaoPatchDto.getMateriaisDeEstudo(), idCompeticao);

				}

				etapaRepositorio.save(EtapaCompeticaoVingente);
			}
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

		if (competicaoPatchDto.getQuestoesAvaliativas() != null) {
			for (QuestaoAvaliativa questaoAvaliativa : questaoAvaliativaRepositorio
					.findByCompeticaoCadastrada(competicao)) {
				questaoAvaliativaRepositorio.deleteById(questaoAvaliativa.getId());
			}

			for (QuestaoAvaliativa questaoAvaliativa : competicaoPatchDto.getQuestoesAvaliativas()) {
				questaoAvaliativa.setCompeticaoCadastrada(competicao);
				questaoAvaliativaRepositorio.save(questaoAvaliativa);
			}
		}
		
		
		
		competicao.setIsElaboracao(competicaoPatchDto.getIsElaboracao());
		

		competicaoRepositorio.save(competicao);

	}

	public void removerUsuarioConvidado(Integer idCompeticao, EmailDto email) throws Exception {
		Competicao competicao = competicaoRepositorio.findById(idCompeticao).get();
		Usuario usuario = usuarioService.consultarUsuarioPorEmail(email.getEmail());

		List<PapelUsuarioCompeticao> papeisUsuarioCompeticao = papelUsuarioCompeticaoRepositorio.findByUsuario(usuario);
		List<Convite> convites = conviteRepositorio.findByUsuario(usuario);

		for (Convite convite : convites) {
			if (convite.getCompeticao().getId() == competicao.getId()) {
				conviteRepositorio.delete(convite);
			}
		}
		for (PapelUsuarioCompeticao papelUsuarioCompeticao : papeisUsuarioCompeticao) {
			if (papelUsuarioCompeticao.getCompeticaoCadastrada().getId() == competicao.getId()) {
				if (papelUsuarioCompeticao.getTipoPapelUsuario().equals(TipoPapelUsuario.CONSULTOR)
						|| papelUsuarioCompeticao.getTipoPapelUsuario().equals(TipoPapelUsuario.AVALIADOR))
					papelUsuarioCompeticaoRepositorio.delete(papelUsuarioCompeticao);
			}
		}
	}

	public List<MaterialEstudoDTO> listarMateriaisEstudoCompeticao(Integer idCompeticao) throws Exception {

		Competicao competicao = competicaoRepositorio.findById(idCompeticao).get();
		List<Etapa> etapas = etapaRepositorio.findByCompeticao(competicao);

		List<MaterialEstudoDTO> materiaisEstudo = new ArrayList<MaterialEstudoDTO>();

		for (Etapa etapa : etapas) {
			for (MaterialEstudo materialEstudo : materialEstudoRepositorio.findByEtapa(etapa)) {
				materiaisEstudo.add(new MaterialEstudoDTO(materialEstudo));
			}
		}
		if (materiaisEstudo.size() == 0) {
			throw new Exception("Não existe nenhum material cadastrado nessa competição.");
		}
		return materiaisEstudo;
	}

	public List<QuestoesAvaliativasDto> listarQuestoesAvaliativasCompeticao(Integer idCompeticao) throws Exception {

		Competicao competicao = competicaoRepositorio.findById(idCompeticao).get();
		List<QuestaoAvaliativa> questaoAvaliativasRecuperadas = questaoAvaliativaRepositorio
				.findByCompeticaoCadastrada(competicao);

		List<QuestoesAvaliativasDto> questaoAvaliativas = new ArrayList<QuestoesAvaliativasDto>();
		for (QuestaoAvaliativa questaoAvaliativa : questaoAvaliativasRecuperadas) {
			questaoAvaliativas.add(new QuestoesAvaliativasDto(questaoAvaliativa));
		}

		if (questaoAvaliativas.size() == 0) {
			throw new Exception("Não existe nenhuma questão cadastrada nessa competição.");
		}

		return questaoAvaliativas;
	}

	public List<Convite> listarConvites(TipoConvite tipoConvite) throws Exception {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.consultarUsuarioPorEmail(autenticado.getName());

		List<Convite> convitesRecuperada = conviteRepositorio.findByUsuario(usuario);

		List<Convite> convites = new ArrayList<Convite>();

		for (Convite convite : convitesRecuperada) {

			if (convite.getTipoConvite().equals(tipoConvite)) {
				convites.add(convite);
			}
		}
		if (convites.size() == 0) {
			throw new Exception("Não existe nenhum convite para você ser consultor.");
		}

		return convites;
	}

	public void responderConvite(ConviteRespostaDto conviteRespostaDto) throws Exception {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.consultarUsuarioPorEmail(autenticado.getName());

		List<Convite> convites = conviteRepositorio.findByUsuario(usuario);
		if (!conviteRespostaDto.isAceito()) {
			for (Convite convite : convites) {
				if (convite.getCompeticao().getId() == conviteRespostaDto.getIdCompeticao()) {
					conviteRepositorio.delete(convite);
				}
			}
		} else {
			for (Convite convite : convites) {
				if (convite.getCompeticao().getId() == conviteRespostaDto.getIdCompeticao()) {
					convite.setStatusConvite(StatusConvite.ACEITO);
					conviteRepositorio.save(convite);

					TipoPapelUsuario tipoPapelUsuario = TipoPapelUsuario.AVALIADOR;
					if (convite.getTipoConvite().equals(TipoConvite.CONSULTOR)) {
						tipoPapelUsuario = TipoPapelUsuario.CONSULTOR;
					}

					PapelUsuarioCompeticao papelUsuarioCompeticao = new PapelUsuarioCompeticao();
					papelUsuarioCompeticao.setTipoPapelUsuario(tipoPapelUsuario);
					papelUsuarioCompeticao.setUsuario(usuario);
					papelUsuarioCompeticao.setCompeticaoCadastrada(
							competicaoRepositorio.findById(conviteRespostaDto.getIdCompeticao()).get());

					papelUsuarioCompeticaoRepositorio.save(papelUsuarioCompeticao);
				}
			}
		}
	}

	public CompeticaoDadosGeraisDto listarDasdosGeraisCompeticao(Integer idCompeticao) throws Exception {
		Competicao competicao = competicaoRepositorio.findById(idCompeticao).get();
		List<Etapa> estapas = etapaRepositorio.findByCompeticao(competicao);

		if (competicao == null) {
			throw new NotFoundException("Competição não encontrada");
		}

		return new CompeticaoDadosGeraisDto(competicao, estapas);
	}

	public List<EquipeNotaDto> listarResultadosEquipesCompeticao(Integer idCompeticao) throws Exception {
		List<EquipeNotaDto> equipes = new ArrayList<EquipeNotaDto>();
		Competicao competicao = competicaoRepositorio.findById(idCompeticao).get();
		Float notaCompeticao = 0f;

		for (QuestaoAvaliativa questaoAvaliativa : questaoAvaliativaRepositorio
				.findByCompeticaoCadastrada(competicao)) {
			notaCompeticao += questaoAvaliativa.getNotaMax();
		}

		for (Equipe equipe : equipeRepositorio.findByCompeticaoCadastrada(competicao)) {
			Float notaEquipe = 0f;
			for (Pitch pitch : pitchRepositorio.findByEquipe(equipe)) {
				if (pitch.getEtapaAvaliacaoVideo().equals(EtapaArtefatoPitch.APROVADO)) {
					for (AvaliacaoPitch avaliacao : avaliacaoPitchRpositorio.findByPitch(pitch)) {
						notaEquipe += avaliacao.getNotaAtribuida();
					}
				}
			}
			equipes.add(new EquipeNotaDto(equipe.getNomeEquipe(), notaEquipe, notaCompeticao, equipe.getId()));
		}
		return equipes;
	}

	public List<EquipeNomeDto> listarEquipesCompeticao(Integer idCompeticao) throws Exception {
		Competicao competicao = competicaoRepositorio.findById(idCompeticao).get();
		List<EquipeNomeDto> equipes = new ArrayList<EquipeNomeDto>();

		for (Equipe equipe : equipeRepositorio.findByCompeticaoCadastrada(competicao)) {
			equipes.add(new EquipeNomeDto(equipe.getId(), equipe.getNomeEquipe()));
		}
		return equipes;
	}

	public List<UsuarioConsultorDto> listarConsultoresCompeticao(Integer idCompeticao) throws Exception {
		Competicao competicao = competicaoRepositorio.findById(idCompeticao).get();
		List<UsuarioConsultorDto> usuario = new ArrayList<UsuarioConsultorDto>();

		for (PapelUsuarioCompeticao papel : papelUsuarioCompeticaoRepositorio.findByCompeticaoCadastrada(competicao)) {
			if (papel.getTipoPapelUsuario().equals(TipoPapelUsuario.CONSULTOR)) {
				usuario.add(new UsuarioConsultorDto(papel.getUsuario()));
			}
		}
		return usuario;
	}

	public void deletarequipe(Integer idCompeticao, Integer idEquipe) throws Exception {
		Equipe equipe = equipeRepositorio.findById(idEquipe).get();

		if (equipe.getCompeticaoCadastrada().getId() == idCompeticao) {
			equipe.setCompeticaoCadastrada(null);
			equipeRepositorio.save(equipe);
			return;
		} else {
			throw new NotFoundException("Equipe não cadastrada nesta competição!");
		}
	}

	public void adicionarConsultorEquipe(Integer idCompeticao, Integer idEquipe, Integer idConsultor) throws Exception {
		Competicao competicao = competicaoRepositorio.findById(idCompeticao).get();
		Usuario usuario = usuarioRepositorio.findById(idConsultor).get();
		Equipe equipe = equipeRepositorio.findById(idEquipe).get();

		if (equipe.getCompeticaoCadastrada().getId() != idCompeticao) {
			throw new Exception("Equipe não cadastrada nesta competição!");
		}

		for (PapelUsuarioCompeticao papel : papelUsuarioCompeticaoRepositorio.findByCompeticaoCadastrada(competicao)) {
			if (papel.getUsuario().getId() == idConsultor
					&& papel.getTipoPapelUsuario().equals(TipoPapelUsuario.CONSULTOR)) {
				equipe.setConsultor(usuario);
				equipeRepositorio.save(equipe);
				return;
			}
		}
	}
}
