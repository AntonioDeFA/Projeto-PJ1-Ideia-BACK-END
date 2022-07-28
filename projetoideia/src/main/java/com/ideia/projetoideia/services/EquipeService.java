package com.ideia.projetoideia.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
import com.ideia.projetoideia.model.dto.AvaliacaoDto;
import com.ideia.projetoideia.model.dto.DadosEquipeAvaliacaoDto;
import com.ideia.projetoideia.model.dto.EquipeAvaliacaoDto;
import com.ideia.projetoideia.model.dto.EquipeComEtapaDTO;
import com.ideia.projetoideia.model.dto.EquipeConsultoriaDto;
import com.ideia.projetoideia.model.dto.EquipeDtoCriacao;
import com.ideia.projetoideia.model.dto.EquipeNomeDto;
import com.ideia.projetoideia.model.dto.EquipeNotaDto;
import com.ideia.projetoideia.model.dto.FeedbackSugestaoDto;
import com.ideia.projetoideia.model.dto.FeedbacksAvaliativosDto;
import com.ideia.projetoideia.model.dto.FeedbacksAvaliativosPitchDto;
import com.ideia.projetoideia.model.dto.LeanCanvasAprovadoConsultoriaDto;
import com.ideia.projetoideia.model.dto.LeanCanvasDto;
import com.ideia.projetoideia.model.dto.MaterialEstudoEnvioDto;

import com.ideia.projetoideia.model.dto.PitchDto;

import com.ideia.projetoideia.model.dto.NotaQuestaoDto;
import com.ideia.projetoideia.model.dto.NotasEquipeDto;

import com.ideia.projetoideia.model.dto.UsuarioDto;
import com.ideia.projetoideia.model.enums.EtapaArtefatoPitch;
import com.ideia.projetoideia.model.enums.TipoEtapa;
import com.ideia.projetoideia.model.enums.TipoPapelUsuario;
import com.ideia.projetoideia.model.enums.TipoQuestaoAvaliativa;
import com.ideia.projetoideia.repository.AcessoMaterialEstudoRepositorio;
import com.ideia.projetoideia.repository.AvaliacaoPitchRpositorio;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.EquipeRepositorio;
import com.ideia.projetoideia.repository.EtapaRepositorio;
import com.ideia.projetoideia.repository.FeedbackAvaliativoRepositorio;
import com.ideia.projetoideia.repository.FeedbackRepositorioCustom;
import com.ideia.projetoideia.repository.LeanCanvasRepositorio;
import com.ideia.projetoideia.repository.MaterialEstudoRepositorio;
import com.ideia.projetoideia.repository.PapelUsuarioCompeticaoRepositorio;
import com.ideia.projetoideia.repository.PitchRepositorio;
import com.ideia.projetoideia.repository.QuestaoAvaliativaRepositorio;
import com.ideia.projetoideia.repository.UsuarioMembroComumRepositorio;
import com.ideia.projetoideia.repository.UsuarioRepositorio;
import com.ideia.projetoideia.services.utils.GeradorEquipeToken;
import com.ideia.projetoideia.services.utils.Validador;
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

	private final FeedbackRepositorioCustom feedbackRepositorioCustom;
	
	@Autowired
	private FeedbackAvaliativoRepositorio feedbackAvaliativoRepositorio;
	
	public EquipeService(FeedbackRepositorioCustom feedbackRepositorioCustom) {
		this.feedbackRepositorioCustom = feedbackRepositorioCustom;
	}

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

		equipeRepositorio.save(equipe);
		papelUsuarioCompeticaoRepositorio.save(papelUsuarioCompeticao);
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
				if (pitch.getEtapaAvaliacaoVideo().equals(EtapaArtefatoPitch.AVALIADO_AVALIADOR)) {
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
			this.deletarEquipe(idEquipe);
			return;
		} else {
			throw new NotFoundException("Equipe não cadastrada nesta competição!");
		}
	}

	public EquipeComEtapaDTO dadosGeraisEquipe(Integer idEquipe) {
		Equipe equipe = equipeRepositorio.findById(idEquipe).get();

		String nomeConsultor = equipe.getConsultor().getNomeUsuario();
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

		etapaVigenteStr = "IMERSAO";
		return new EquipeComEtapaDTO(equipe, etapaVigenteStr, usuarioMembroComumRepositorio.findByEquipe(equipe),
				nomeConsultor);
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
			throw new Exception("Essa equipe já possuí um Lean Canvas que está em consultoria.");
		}
		if (equipe.getConsultor() == null) {

			enviarEmail.enviarEmailExigindoConsultorParaEquipe(equipe);

			throw new Exception("Não foi possível enviar para consultoria, pois esta equipe não possui um consultor. "
					+ "Já foi requisitado ao organizador um consultor para esta equipe. Por favor, aguarde e tente novamente mais tarde.");
		}

		LeanCanvas leanCanvasConsultoria = leanCanvasRepositorio.findByIdEquipeEEtapa(idEquipe,
				EtapaArtefatoPitch.EM_ELABORACAO.getValue());

		leanCanvasConsultoria.setEtapaSolucaoCanvas(EtapaArtefatoPitch.EM_CONSULTORIA);
		if (leanCanvasConsultoria.getProblema() == null || leanCanvasConsultoria.getSolucao() == null
				|| leanCanvasConsultoria.getCanais() == null || leanCanvasConsultoria.getEstruturaDeCusto() == null
				|| leanCanvasConsultoria.getFontesDeReceita() == null
				|| leanCanvasConsultoria.getPropostaValor() == null || leanCanvasConsultoria.getMetricasChave() == null
				|| leanCanvasConsultoria.getSegmentosDeClientes() == null
				|| leanCanvasConsultoria.getVantagemCompetitiva() == null) {
			throw new Exception("Não é permitido enviar um Lean Canvas para consultoria com os campos vazios");
		}

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

	public FeedbacksAvaliativosDto listarFeedbacksLeanCanvas(Integer idLeanCanvas, String status) throws Exception {

		Optional<LeanCanvas> canvas = leanCanvasRepositorio.findById(idLeanCanvas);

		if (canvas.isEmpty()) {
			throw new Exception("O canvas passado não existe");
		}

		LeanCanvas leanCanvas = canvas.get();
		if (!leanCanvas.getEtapaSolucaoCanvas().getValue().equals(status)) {
			throw new Exception("O Lean canvas desta equipe ainda não está no status de " + status);
		}
		
		List<FeedbackSugestaoDto> feedbackAvaliativos = feedbackRepositorioCustom.getByLeanCanvas(idLeanCanvas);
		if (feedbackAvaliativos.size() > 0) {			
			LocalDateTime hora = feedbackAvaliativos.get(0).getDataCriacao();
			
			for (FeedbackSugestaoDto feedback : feedbackAvaliativos) {
				if (hora.isBefore(feedback.getDataCriacao())) {
					hora = feedback.getDataCriacao();
				}
			}
			return new FeedbacksAvaliativosDto(leanCanvas, feedbackAvaliativos, hora);
		}
		
		return new FeedbacksAvaliativosDto(leanCanvas, feedbackAvaliativos);
	}

	public List<LeanCanvasAprovadoConsultoriaDto> listarLeanCanvasAprovadoPelaConsultoria(Integer idEquipe)
			throws Exception {
		recuperarEquipe(idEquipe);

		List<LeanCanvas> listCanvasEquipe = leanCanvasRepositorio.findByIdEquipeEEtapaList(idEquipe,
				EtapaArtefatoPitch.AVALIADO_CONSULTOR.getValue());

		if (listCanvasEquipe == null || listCanvasEquipe.size() == 0) {
			throw new Exception("A equipe não possui nenhum lean canvas aprovado por consultoria");
		}

		List<LeanCanvasAprovadoConsultoriaDto> listLeanCavasDto = new ArrayList<>();

		for (LeanCanvas canvas : listCanvasEquipe) {

			LeanCanvasAprovadoConsultoriaDto canvasDto = new LeanCanvasAprovadoConsultoriaDto(canvas);

			listLeanCavasDto.add(canvasDto);

		}

		Collections.sort(listLeanCavasDto);

		return listLeanCavasDto;

	}

	public void enviarPitchConsultoria(Integer idEquipe) throws Exception {
		Equipe equipe = recuperarEquipe(idEquipe);

		if (pitchRepositorio.findByIdEquipeEEtapaList(idEquipe, EtapaArtefatoPitch.EM_CONSULTORIA.getValue()) != null) {
			throw new Exception("Essa equipe já possuí um pitch que está em consultoria.");
		}
		if (equipe.getConsultor() == null) {

			enviarEmail.enviarEmailExigindoConsultorParaEquipe(equipe);

			throw new Exception("Não foi possível enviar para consultoria, pois esta equipe não possui um consultor. "
					+ "Já foi requisitado ao organizador um consultor para esta equipe. Por favor, aguarde e tente novamente mais tarde.");
		}

		Pitch pitchConsultoria = pitchRepositorio.findByIdEquipeEEtapaList(idEquipe,
				EtapaArtefatoPitch.EM_ELABORACAO.getValue());

		pitchConsultoria.setEtapaAvaliacaoVideo(EtapaArtefatoPitch.EM_CONSULTORIA);

		pitchRepositorio.save(pitchConsultoria);

		PitchDto pitch = new PitchDto();

		pitch.setArquivoPitchDeck(pitchConsultoria.getPitchDeck() == null ? pitchConsultoria.getVideo()
				: pitchConsultoria.getPitchDeck());
		;
		pitch.setTipo(pitchConsultoria.getPitchDeck() == null ? "VIDEO" : "ARQUIVO");
		pitch.setTitulo(pitchConsultoria.getTitulo());
		pitch.setDescricao(pitchConsultoria.getDescricao());

		criarPitch(idEquipe, pitch);

	}

	public void criarPitch(Integer idEquipe, PitchDto pitchDto) throws Exception {
		Equipe equipe = recuperarEquipe(idEquipe);

		if (pitchDto.getTipo().equals("VIDEO")) {
			Validador.validarDuracaoVideoPitch(pitchDto.getArquivoPitchDeck());
		}

		Pitch pitch = pitchRepositorio.findByIdEquipeEEtapaList(idEquipe, EtapaArtefatoPitch.EM_ELABORACAO.getValue());

		if (pitch == null) {
			pitch = new Pitch();
		}

		pitch.setDescricao(pitchDto.getDescricao());
		pitch.setTitulo(pitchDto.getTitulo());
		pitch.setDataCriacao(LocalDateTime.now());
		pitch.setEtapaAvaliacaoVideo(EtapaArtefatoPitch.EM_ELABORACAO);
		pitch.setEquipe(equipe);
		;
		if (pitchDto.getTipo().equals("VIDEO")) {
			pitch.setPitchDeck(null);
			pitch.setVideo(pitchDto.getArquivoPitchDeck());
		} else {
			pitch.setVideo(null);
			pitch.setPitchDeck(pitchDto.getArquivoPitchDeck());
		}

		pitchRepositorio.save(pitch);
	}

	public NotasEquipeDto getNotasEquipe(Integer idEquipe) throws Exception {
		recuperarEquipe(idEquipe);

		Pitch pitch = pitchRepositorio.findByIdEquipeEEtapaList(idEquipe,
				EtapaArtefatoPitch.AVALIADO_AVALIADOR.getValue());

		if (pitch == null || pitch.getAvaliacaoPitch().size() == 0) {
			throw new Exception("A sua equipe não possui notas no momento");
		}

		NotasEquipeDto notas = new NotasEquipeDto();

		Integer notaAtribuidaAdaptabilidade = 0;
		Integer notaMaximaAdaptabilidade = 0;
		Integer notaAtribuidaInovacao = 0;
		Integer notaMaximaInovacao = 0;
		Integer notaAtribuidaUtilidade = 0;
		Integer notaMaximaUtilidade = 0;
		Integer notaAtribuidaSustentabilidade = 0;
		Integer notaMaximaSustentabilidade = 0;

		List<AvaliacaoPitch> avaliacoes = pitch.getAvaliacaoPitch();

		for (AvaliacaoPitch avaliacaoPitch : avaliacoes) {
			QuestaoAvaliativa questao = avaliacaoPitch.getQuestaoAvaliativa();

			NotaQuestaoDto notaQuestaoDto = new NotaQuestaoDto();

			notaQuestaoDto.setAvaliador(avaliacaoPitch.getAvaliador().getNomeUsuario());
			notaQuestaoDto.setComentario(avaliacaoPitch.getObservacao());
			notaQuestaoDto.setQuestao(questao.getQuestao());
			notaQuestaoDto.setNota(avaliacaoPitch.getNotaAtribuida());
			notaQuestaoDto.setNotaMax(questao.getNotaMax());

			if (questao.getTipoQuestaoAvaliativa().equals(TipoQuestaoAvaliativa.ADAPTABILIDADE)) {
				notas.getListaAdaptabilidade().add(notaQuestaoDto);
				notaAtribuidaAdaptabilidade += notaQuestaoDto.getNota();
				notaMaximaAdaptabilidade += notaQuestaoDto.getNotaMax();
			} else if (questao.getTipoQuestaoAvaliativa().equals(TipoQuestaoAvaliativa.INOVACAO)) {
				notas.getListaInovacao().add(notaQuestaoDto);
				notaAtribuidaInovacao += notaQuestaoDto.getNota();
				notaMaximaInovacao += notaQuestaoDto.getNotaMax();
			} else if (questao.getTipoQuestaoAvaliativa().equals(TipoQuestaoAvaliativa.SUSTENTABILIDADE)) {
				notas.getListaSustentabilidade().add(notaQuestaoDto);
				notaAtribuidaSustentabilidade += notaQuestaoDto.getNota();
				notaMaximaSustentabilidade += notaQuestaoDto.getNotaMax();
			} else if (questao.getTipoQuestaoAvaliativa().equals(TipoQuestaoAvaliativa.UTILIDADE)) {
				notas.getListaUtilidade().add(notaQuestaoDto);
				notaAtribuidaUtilidade += notaQuestaoDto.getNota();
				notaMaximaUtilidade += notaQuestaoDto.getNotaMax();
			}

		}

		notas.setNotaAtribuidaAdaptabilidade(notaAtribuidaAdaptabilidade);
		notas.setNotaAtribuidaInovacao(notaAtribuidaInovacao);
		notas.setNotaAtribuidaSustentabilidade(notaAtribuidaSustentabilidade);
		notas.setNotaAtribuidaUtilidade(notaAtribuidaUtilidade);

		notas.setNotaMaximaAdaptabilidade(notaMaximaAdaptabilidade);
		notas.setNotaMaximaInovacao(notaMaximaInovacao);
		notas.setNotaMaximaSustentabilidade(notaMaximaSustentabilidade);
		notas.setNotaMaximaUtilidade(notaMaximaUtilidade);

		return notas;

	}

	public PitchDto getArquivoPitch(Integer idEquipe) throws Exception {

		Pitch pitch = pitchRepositorio.findByIdEquipe(idEquipe).orElse(null);

		if (pitch == null) {
			throw new Exception("Não existe nenhum pitch para essa equipe no momento");
		}

		String tipo = "ARQUIVO";
		String pitchDeck = pitch.getPitchDeck();

		if (pitch.getPitchDeck() == null) {
			tipo = "VIDEO";
			pitchDeck = pitch.getVideo();
		}

		PitchDto dto = new PitchDto();
		dto.setArquivoPitchDeck(pitchDeck);
		dto.setTipo(tipo);
		dto.setDescricao(pitch.getDescricao());
		dto.setTitulo(pitch.getTitulo());

		return dto;
	}

	public FeedbacksAvaliativosPitchDto listarFeedbacksPitchDecks(Integer idEquipe) throws Exception {

		List<Pitch> feedbacksPitch = pitchRepositorio.findByIdEquipeFeedbacksPitch(idEquipe);

		if (feedbacksPitch.size() == 0) {
			throw new Exception("Não existe nenhuma versão com feedback");
		}

		return new FeedbacksAvaliativosPitchDto(feedbacksPitch);
	}

	public void enviarParaAvaliacao(Integer idEquipe) throws Exception {

		if (pitchRepositorio.findByIdEquipeEEtapaList(idEquipe, EtapaArtefatoPitch.EM_AVALIACAO.getValue()) != null
				|| leanCanvasRepositorio.findByIdEquipeEEtapa(idEquipe,
						EtapaArtefatoPitch.EM_AVALIACAO.getValue()) != null) {
			throw new Exception("Essa equipe já possuí um lean canvas e um pitch em avaliação.");
		}

		LeanCanvas canvas = leanCanvasRepositorio.findByIdEquipeEEtapa(idEquipe,
				EtapaArtefatoPitch.EM_ELABORACAO.getValue());
		Pitch pitch = pitchRepositorio.findByIdEquipeEEtapaList(idEquipe, EtapaArtefatoPitch.EM_ELABORACAO.getValue());

		canvas.setEtapaSolucaoCanvas(EtapaArtefatoPitch.EM_AVALIACAO);
		pitch.setEtapaAvaliacaoVideo(EtapaArtefatoPitch.EM_AVALIACAO);

		leanCanvasRepositorio.save(canvas);
		pitchRepositorio.save(pitch);

	}

	public List<EquipeConsultoriaDto> getEquipesQuePrecisamDeConsultoria(Integer idCompeticao) throws Exception {
		List<EquipeConsultoriaDto> equipes = new ArrayList<EquipeConsultoriaDto>();

		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();

		Usuario consultor = usuarioService.consultarUsuarioPorEmail(autenticado.getName());

		if (idCompeticao != 0) {

			Competicao competicao = competicaoRepositorio.getById(idCompeticao);

			Etapa etapaVigente = competicao.getEtapaVigente();

			if (etapaVigente == null || !etapaVigente.getTipoEtapa().equals(TipoEtapa.IMERSAO)) {
				throw new Exception("A Competição não está na etapa de Imersão");
			}

			for (Equipe equipe : competicao.getEquipesCadastradas()) {

				if (equipe.getConsultor().getId() == consultor.getId()) {

					LeanCanvas leanCanvasEmConsultoria = leanCanvasRepositorio.findByIdEquipeEEtapa(equipe.getId(),
							EtapaArtefatoPitch.EM_CONSULTORIA.getValue());

					Pitch pitchEmConsultoria = pitchRepositorio.findByIdEquipeEEtapaList(equipe.getId(),
							EtapaArtefatoPitch.EM_CONSULTORIA.getValue());

					if (pitchEmConsultoria != null || leanCanvasEmConsultoria != null) {
						EquipeConsultoriaDto equipeConsultoriaDto = new EquipeConsultoriaDto(equipe,
								leanCanvasEmConsultoria, pitchEmConsultoria);
						equipes.add(equipeConsultoriaDto);
					}
				}
			}
		}

		else {
			List<Competicao> competicoesDoConsultor = competicaoRepositorio
					.findByTipoPapelUsuarioEIdUsuario(TipoPapelUsuario.CONSULTOR.getValue(), consultor.getId());

			for (Competicao comp : competicoesDoConsultor) {

				Competicao competicao = competicaoRepositorio.getById(comp.getId());

				Etapa etapaVigente = competicao.getEtapaVigente();

				if (etapaVigente != null && etapaVigente.getTipoEtapa().equals(TipoEtapa.IMERSAO)) {
					for (Equipe equipe : competicao.getEquipesCadastradas()) {
						if (equipe.getConsultor().getId() == consultor.getId()) {

							LeanCanvas leanCanvasEmConsultoria = leanCanvasRepositorio
									.findByIdEquipeEEtapa(equipe.getId(), EtapaArtefatoPitch.EM_CONSULTORIA.getValue());

							Pitch pitchEmConsultoria = pitchRepositorio.findByIdEquipeEEtapaList(equipe.getId(),
									EtapaArtefatoPitch.EM_CONSULTORIA.getValue());

							if (pitchEmConsultoria != null || leanCanvasEmConsultoria != null) {
								EquipeConsultoriaDto equipeConsultoriaDto = new EquipeConsultoriaDto(equipe,
										leanCanvasEmConsultoria, pitchEmConsultoria);
								equipes.add(equipeConsultoriaDto);
							}
						}
					}
				}
			}
		}
		return equipes;
	}

	public List<EquipeAvaliacaoDto> getEquipesParaAvaliacao(Integer idCompeticao) throws Exception {

		List<EquipeAvaliacaoDto> equipes = new ArrayList<EquipeAvaliacaoDto>();

		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();

		Usuario avaliador = usuarioService.consultarUsuarioPorEmail(autenticado.getName());

		List<Competicao> competicoesDoAvaliador = new ArrayList<Competicao>();

		if (idCompeticao != 0) {

			Competicao comp = competicaoRepositorio.findById(idCompeticao).get();
			competicoesDoAvaliador.add(comp);

		} else {
			competicoesDoAvaliador = competicaoRepositorio
					.findByTipoPapelUsuarioEIdUsuario(TipoPapelUsuario.AVALIADOR.getValue(), avaliador.getId());

		}
		for (Competicao competicao : competicoesDoAvaliador) {

			Etapa etapaVigente = competicao.getEtapaVigente();

			if (etapaVigente == null || !etapaVigente.getTipoEtapa().equals(TipoEtapa.PITCH)) {
				throw new Exception("A Competição não está na etapa de Pitch");
			}

			for (Equipe equipe : competicao.getEquipesCadastradas()) {

				Integer idPapel = papelUsuarioCompeticaoRepositorio
						.findByCompeticaoCadastradaAndUsuario(avaliador.getId(), competicao.getId());
				if (papelUsuarioCompeticaoRepositorio.findById(idPapel).get().getTipoPapelUsuario()
						.equals(TipoPapelUsuario.AVALIADOR)) {
					LeanCanvas leanCanvasEmAvaliacao = leanCanvasRepositorio.findByIdEquipeEEtapa(equipe.getId(),
							EtapaArtefatoPitch.EM_AVALIACAO.getValue());

					Pitch pitchEmAvaliacao = pitchRepositorio.findByIdEquipeEEtapaList(equipe.getId(),
							EtapaArtefatoPitch.EM_AVALIACAO.getValue());

					if (pitchEmAvaliacao != null || leanCanvasEmAvaliacao != null) {
						EquipeAvaliacaoDto equipeConsultoriaDto = new EquipeAvaliacaoDto(equipe);
						equipes.add(equipeConsultoriaDto);
					}
				}
			}
		}
		return equipes;
	}

	public DadosEquipeAvaliacaoDto getDadosEquipeAvaliacao(Integer idEquipe) throws Exception {

		Equipe equipe = recuperarEquipe(idEquipe);

		LeanCanvas leanCanvas = leanCanvasRepositorio.findByIdEquipeEEtapa(idEquipe,
				EtapaArtefatoPitch.EM_AVALIACAO.getValue());

		Pitch pitch = pitchRepositorio.findByIdEquipeEEtapaList(idEquipe, EtapaArtefatoPitch.EM_AVALIACAO.getValue());

		if (pitch == null || leanCanvas == null) {
			throw new Exception("A equipe não possui pitch ou lean canvas em condições de ser avaliado ");
		}

		Competicao competicao = equipe.getCompeticaoCadastrada();

		DadosEquipeAvaliacaoDto dados = new DadosEquipeAvaliacaoDto(equipe, leanCanvas, pitch,
				competicao.getQuestoesAvaliativas());

		return dados;

	}

	public void registarNota(Integer idEquipe, AvaliacaoDto avaliacaoDto) throws Exception {

		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();

		Usuario avaliador = usuarioService.consultarUsuarioPorEmail(autenticado.getName());

		QuestaoAvaliativa questaoAvaliativa = questaoAvaliativaRepositorio.findById(avaliacaoDto.getIdQuestao()).get();

		if (questaoAvaliativa == null) {
			throw new Exception("A questão avaliativa não existe!");
		}
		if (questaoAvaliativa.getNotaMax() < avaliacaoDto.getNotaAtribuida()) {
			throw new Exception("A não pode ser maior que a nota máxima!");
		}

		LeanCanvas leanCanvasEmAvaliacao = leanCanvasRepositorio.findByIdEquipeEEtapa(idEquipe,
				EtapaArtefatoPitch.EM_AVALIACAO.getValue());

		Pitch pitchEmAvaliacao = pitchRepositorio.findByIdEquipeEEtapaList(idEquipe,
				EtapaArtefatoPitch.EM_AVALIACAO.getValue());

		if (leanCanvasEmAvaliacao == null || pitchEmAvaliacao == null) {
			throw new Exception("A equipe não possui pitch ou lean canvas ja avaliada ");
		}

		AvaliacaoPitch avaliacaoPitch = new AvaliacaoPitch(LocalDate.now(), avaliacaoDto.getNotaAtribuida(),
				avaliacaoDto.getObservacao(), pitchEmAvaliacao, questaoAvaliativa, avaliador);

		avaliacaoPitchRpositorio.save(avaliacaoPitch);

	}
	
	public void removerFeedback(Integer idFeedbackAvaliativo) {
		FeedbackAvaliativo feedbackAvaliativo = feedbackAvaliativoRepositorio.findById(idFeedbackAvaliativo).get();
		
		if(feedbackAvaliativo == null) {
			return;
		}
		
		feedbackAvaliativoRepositorio.delete(feedbackAvaliativo);
	}
}
