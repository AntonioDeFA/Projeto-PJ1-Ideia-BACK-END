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
import org.springframework.web.bind.annotation.PathVariable;

import com.ideia.projetoideia.model.AcessoMaterialEstudo;
import com.ideia.projetoideia.model.AvaliacaoPitch;
import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.Etapa;
import com.ideia.projetoideia.model.FeedbackAvaliativo;
import com.ideia.projetoideia.model.LeanCanvas;
import com.ideia.projetoideia.model.MaterialEstudo;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.UsuarioMembroComum;
import com.ideia.projetoideia.model.PapelUsuarioCompeticao;
import com.ideia.projetoideia.model.Pitch;
import com.ideia.projetoideia.model.QuestaoAvaliativa;
import com.ideia.projetoideia.model.dto.EquipeComEtapaDTO;
import com.ideia.projetoideia.model.dto.EquipeDtoCriacao;
import com.ideia.projetoideia.model.dto.EquipeNomeDto;
import com.ideia.projetoideia.model.dto.EquipeNotaDto;
import com.ideia.projetoideia.model.dto.FeedbacksAvaliativosDto;
import com.ideia.projetoideia.model.dto.LeanCanvasDto;
import com.ideia.projetoideia.model.dto.MaterialEstudoEnvioDto;
import com.ideia.projetoideia.model.dto.UsuarioDto;
import com.ideia.projetoideia.model.enums.EtapaArtefatoPitch;
import com.ideia.projetoideia.model.enums.TipoEtapa;
import com.ideia.projetoideia.model.enums.TipoPapelUsuario;
import com.ideia.projetoideia.repository.AcessoMaterialEstudoRepositorio;
import com.ideia.projetoideia.repository.AvaliacaoPitchRpositorio;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.EquipeRepositorio;
import com.ideia.projetoideia.repository.EtapaRepositorio;
import com.ideia.projetoideia.repository.FeedbackAvaliativoRepositorio;
import com.ideia.projetoideia.repository.LeanCanvasRepositorio;
import com.ideia.projetoideia.repository.MaterialEstudoRepositorio;
import com.ideia.projetoideia.repository.PapelUsuarioCompeticaoRepositorio;
import com.ideia.projetoideia.repository.PitchRepositorio;
import com.ideia.projetoideia.repository.QuestaoAvaliativaRepositorio;
import com.ideia.projetoideia.repository.UsuarioMembroComumRepositorio;
import com.ideia.projetoideia.repository.UsuarioRepositorio;
import com.ideia.projetoideia.services.utils.GeradorEquipeToken;
import com.ideia.projetoideia.utils.EnviarEmail;

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

	@Autowired
	private LeanCanvasRepositorio leanCanvasRepositorio;

	@Autowired
	private AvaliacaoPitchRpositorio avaliacaoPitchRpositorio;

	@Autowired
	private PitchRepositorio pitchRepositorio;

	@Autowired
	private QuestaoAvaliativaRepositorio questaoAvaliativaRepositorio;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private EtapaRepositorio etapaRepositorio;

	@Autowired
	private MaterialEstudoRepositorio materialEstudoRepositorio;
	@Autowired
	private AcessoMaterialEstudoRepositorio acessoMaterialEstudoRepositorio;

	@Autowired
	private EnviarEmail enviarEmail;
	
	@Autowired
	private FeedbackAvaliativoRepositorio feedbackAvaliativoRepositorio;

	public void criarEquipe(EquipeDtoCriacao equipeDto) throws Exception {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.consultarUsuarioPorEmail(autenticado.getName());

		List<String> lista = new ArrayList<String>();
		for (UsuarioDto user : equipeDto.getUsuarios()) {
			lista.add(user.getEmail());
		}

		StringBuilder erros = new StringBuilder();

		if (equipeRepositorio.validarUsuarioLiderEOrganizador(usuario.getId(), equipeDto.getIdCompeticao()) > 0) {
			erros.append(
					"Observe se você não é o organizador desta competição ou se já não está inscrito nesta competição. ");
		}
		if (equipeRepositorio.validarNomeDeEquipe(equipeDto.getNomeEquipe(), equipeDto.getIdCompeticao()) > 0) {
			erros.append(
					"Já existe uma equipe inscrita nesta competição com este nome. Por gentiliza, escolha outro nome para sua equipe. ");
		}
		if (equipeRepositorio.validarMembrosDeUmaEquipeEmUmaCompeticao(lista, equipeDto.getIdCompeticao()) > 0) {
			erros.append("Algum usuário de sua equipe já está participando dessa competição em outra equipe. ");
		}

		if (erros.length() != 0) {
			throw new Exception(erros.toString());
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
		papelUsuarioCompeticao.setCompeticaoCadastrada(equipe.getCompeticaoCadastrada());

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

	public LeanCanvasDto recuperarLeanCanvasElaboracao(Integer idEquipe) throws NotFoundException {
		this.recuperarEquipe(idEquipe);

		LeanCanvas LeanCanvas = leanCanvasRepositorio.findByIdEquipeEEtapa(idEquipe,
				EtapaArtefatoPitch.EM_ELABORACAO.getValue());

		if (LeanCanvas == null) {
			throw new NotFoundException("Não existe lean canvas em elaboração para esta equipe");
		}

		return new LeanCanvasDto(LeanCanvas);

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
			equipes.add(new EquipeNomeDto(equipe.getId(), equipe.getNomeEquipe(), equipe.getConsultor()));
		}
		return equipes;
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

	public void deletarEquipe(Integer idCompeticao, Integer idEquipe) throws Exception {
		Equipe equipe = equipeRepositorio.findById(idEquipe).get();
		if (equipe.getCompeticaoCadastrada().getId() == idCompeticao) {
			equipe.setCompeticaoCadastrada(null);
			equipeRepositorio.save(equipe);
			return;
		} else {
			throw new NotFoundException("Equipe não cadastrada nesta competição!");
		}
	}

	public EquipeComEtapaDTO dadosGeraisEquipe(Integer idEquipe) {
		Equipe equipe = equipeRepositorio.findById(idEquipe).get();

		LocalDate hoje = LocalDate.now();
		Competicao competicao = equipe.getCompeticaoCadastrada();
		String etapaVigenteStr = "";

		Etapa etapaInscricao = null;

		for (int i = 0; i < competicao.getEtapas().size(); i++) {
			Etapa etapa = competicao.getEtapas().get(i);
			if (etapa.getTipoEtapa().equals(TipoEtapa.INSCRICAO)) {
				etapaInscricao = etapa;
				break;
			}

		}

		if (hoje.isBefore(etapaInscricao.getDataInicio()) && !competicao.getIsElaboracao()) {
			etapaVigenteStr = "NAO_INICIADA";
		} else if (!competicao.getIsElaboracao()) {
			for (Etapa etapa : competicao.getEtapas()) {
				if (etapa.isVigente()) {
					etapaVigenteStr = etapa.getTipoEtapa().getValue();
					break;
				}
			}
		} else if (competicao.getIsElaboracao()) {
			etapaVigenteStr = "ELABORACAO";
		}

		return new EquipeComEtapaDTO(equipe, etapaVigenteStr, usuarioMembroComumRepositorio.findByEquipe(equipe));
	}

	public void criarLeanCanvas(Integer idEquipe) throws Exception {

		Equipe equipe = recuperarEquipe(idEquipe);

		if (leanCanvasRepositorio.findByIdEquipeEEtapa(idEquipe, EtapaArtefatoPitch.EM_ELABORACAO.getValue()) != null) {
			throw new Exception("Já existe um lean canvas na etapa de elaboração para esta equipe");
		}

		LeanCanvas leanCanvas = new LeanCanvas();

		leanCanvas.setEtapaSolucaoCanvas(EtapaArtefatoPitch.EM_ELABORACAO);

		equipe.getCanvasDaEquipe().add(leanCanvas);

		equipeRepositorio.save(equipe);
		leanCanvas.setEquipe(equipe);

		leanCanvasRepositorio.save(leanCanvas);
	}

	public void removerMembroEquipe(Integer idEquipe, String email) throws Exception {
		Equipe equipe = equipeRepositorio.findById(idEquipe).get();
		List<UsuarioMembroComum> usuariosMembroComum = usuarioMembroComumRepositorio.findByEquipe(equipe);

		if (usuariosMembroComum.size() + 1 == equipe.getCompeticaoCadastrada().getQntdMinimaMembrosPorEquipe()) {
			throw new Exception("Você não pode ir além do limite mínimo de membros por equipe desta competição.");
		}
		boolean entrou = false;
		for (UsuarioMembroComum usuarioMembroComum : usuariosMembroComum) {
			if (usuarioMembroComum.getEmail().equals(email)) {
				usuarioMembroComumRepositorio.delete(usuarioMembroComum);
				entrou = true;
			}
		}
		if (!entrou) {
			throw new NotFoundException("Nenhum membro foi encontrado com este email!");
		}
		equipe.setToken(GeradorEquipeToken.gerarTokenEquipe(equipe.getNomeEquipe()));
		equipeRepositorio.save(equipe);
	}

	public void patchNomeEquipe(Integer idEquipe, String nome) throws Exception {
		Equipe equipe = equipeRepositorio.findById(idEquipe).get();
		if (equipeRepositorio.validarNomeDeEquipe(nome, equipe.getCompeticaoCadastrada().getId()) > 0) {
			throw new Exception("Já existe uma equipe com este nome nesta competição.");
		}
		equipe.setNomeEquipe(nome);
		equipeRepositorio.save(equipe);
	}

	public LeanCanvasDto enviarLeanCanvasParaConsultoria(Integer idEquipe) throws Exception {

		Equipe equipe = recuperarEquipe(idEquipe);

		if (leanCanvasRepositorio.findByIdEquipeEEtapa(idEquipe,
				EtapaArtefatoPitch.EM_CONSULTORIA.getValue()) != null) {
			throw new Exception("Essa equipe já possíu um lean canvas que está em consultoria");
		}
		if (equipe.getConsultor() == null) {

			enviarEmail.enviarEmailExigindoConsultorParaEquipe(equipe);

			throw new Exception("Não foi possível enviar para consultoria, pois esta equipe não possui um consultor. "
					+ "Já foi requisitado ao organizador um consultor para esta equipe. Por favor, aguarde e tente novamente mais tarde.");
		}

		LeanCanvas leanCanvasConsultoria = leanCanvasRepositorio.findByIdEquipeEEtapa(idEquipe,
				EtapaArtefatoPitch.EM_ELABORACAO.getValue());

		leanCanvasConsultoria.setEtapaSolucaoCanvas(EtapaArtefatoPitch.EM_CONSULTORIA);

		leanCanvasRepositorio.save(leanCanvasConsultoria);

		LeanCanvas leanCanvas = new LeanCanvas(leanCanvasConsultoria);

		leanCanvas.setEtapaSolucaoCanvas(EtapaArtefatoPitch.EM_ELABORACAO);

		equipe.getCanvasDaEquipe().add(leanCanvas);

		equipeRepositorio.save(equipe);
		leanCanvas.setEquipe(equipe);

		leanCanvasRepositorio.save(leanCanvas);

		LeanCanvasDto leanCanvasDto = new LeanCanvasDto(
				leanCanvasRepositorio.findByIdEquipeEEtapa(idEquipe, EtapaArtefatoPitch.EM_ELABORACAO.getValue()));

		return leanCanvasDto;

	}

	public void atualizarLeanCanvas(Integer idEquipe, LeanCanvasDto leanCanvasDto) throws Exception {
		recuperarEquipe(idEquipe);

		Optional<LeanCanvas> canvas = leanCanvasRepositorio.findById(leanCanvasDto.getId());

		if (canvas.isEmpty()) {
			throw new Exception("O canvas passado não existe");
		}
		LeanCanvas leanCanvas = canvas.get();

		if (leanCanvas.getEquipe().getId() != idEquipe) {
			throw new Exception("Essa equipe não possuí esse lean canvas ");
		}

		if (!leanCanvas.getEtapaSolucaoCanvas().equals(EtapaArtefatoPitch.EM_ELABORACAO)) {
			throw new Exception("O canvas passado não está na etapa de elaboração");
		}

		leanCanvas.setProblema(leanCanvasDto.getProblema());
		leanCanvas.setSolucao(leanCanvasDto.getSolucao());
		leanCanvas.setMetricasChave(leanCanvasDto.getMetricasChave());
		leanCanvas.setPropostaValor(leanCanvasDto.getPropostaValor());
		leanCanvas.setVantagemCompetitiva(leanCanvasDto.getVantagemCompetitiva());
		leanCanvas.setCanais(leanCanvasDto.getCanais());
		leanCanvas.setSegmentosDeClientes(leanCanvasDto.getSegmentosDeClientes());
		leanCanvas.setEstruturaDeCusto(leanCanvasDto.getEstruturaDeCusto());
		leanCanvas.setFontesDeReceita(leanCanvasDto.getFontesDeReceita());

		leanCanvasRepositorio.save(leanCanvas);

	}

	public List<MaterialEstudoEnvioDto> getMateriasDeEstudoDeUmaEquipe(Integer idEquipe) throws Exception {

		Equipe equipe = recuperarEquipe(idEquipe);

		Etapa etapa = etapaRepositorio.findEtapaCompeticao(TipoEtapa.AQUECIMENTO.getValue(),
				equipe.getCompeticaoCadastrada().getId());

		List<MaterialEstudo> materiasCompeticao = materialEstudoRepositorio.findByEtapa(etapa);

		List<MaterialEstudoEnvioDto> envio = new ArrayList<>();

		Boolean isConcluido = true;

		for (MaterialEstudo material : materiasCompeticao) {

			if (acessoMaterialEstudoRepositorio.findByEquipeEMaterialEstudo(idEquipe, material.getId()).isPresent()) {
				isConcluido = true;
			}

			else {
				isConcluido = false;
			}

			MaterialEstudoEnvioDto materialEnvio = new MaterialEstudoEnvioDto(material, isConcluido);
			envio.add(materialEnvio);

		}

		return envio;

	}

	public void marcarMaterialComoConcluido(Integer idEquipe, Integer idMaterialEstudo) throws Exception {

		Equipe equipe = recuperarEquipe(idEquipe);

		MaterialEstudo materialEstudo = materialEstudoRepositorio.findById(idMaterialEstudo).get();

		AcessoMaterialEstudo acessoMaterialEstudo = new AcessoMaterialEstudo();

		acessoMaterialEstudo.setDataAcesso(LocalDate.now());

		acessoMaterialEstudo.setEquipe(equipe);

		acessoMaterialEstudo.setMaterialEstudo(materialEstudo);

		equipe.getAcessoMaterialEstudo().add(acessoMaterialEstudo);

		equipeRepositorio.save(equipe);

		acessoMaterialEstudoRepositorio.save(acessoMaterialEstudo);

	}

	public List<FeedbacksAvaliativosDto> listarFeedbacksLeanCanvas(@PathVariable("idLeanCanvas") Integer idLeanCanvas)
			throws Exception {

		LeanCanvas leanCanvas = leanCanvasRepositorio.findById(idLeanCanvas).get();
		List<FeedbackAvaliativo> feedbackAvaliativos = feedbackAvaliativoRepositorio.findby
	}
}
