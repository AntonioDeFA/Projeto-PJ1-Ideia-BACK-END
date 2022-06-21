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

import com.ideia.projetoideia.model.CategoriaMaterialEstudo;
import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Convite;
import com.ideia.projetoideia.model.Etapa;
import com.ideia.projetoideia.model.MaterialEstudo;
import com.ideia.projetoideia.model.PapelUsuarioCompeticao;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.dto.CompeticaoDadosGeraisDto;
import com.ideia.projetoideia.model.dto.CompeticaoEtapaVigenteDto;
import com.ideia.projetoideia.model.dto.CompeticaoPatchDto;
import com.ideia.projetoideia.model.dto.CompeticaoPutDto;
import com.ideia.projetoideia.model.dto.ConsultorEAvaliadorDto;
import com.ideia.projetoideia.model.dto.EmailDto;
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
		// aqui tem que converter a string base64 em um arquivo
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
	
	public void verificarSeEstaEncerrada(Competicao comp) {
		
		LocalDate hoje = LocalDate.now();
		
		
		if(!comp.getIsEncerrada() && !comp.getIsElaboracao()) {
			
			for (Etapa etapa :comp.getEtapas()) {
				
				if (hoje.isAfter(etapa.getDataTermino()) && etapa.getTipoEtapa().equals(TipoEtapa.PITCH)) {
					comp.setIsEncerrada(true);
					competicaoRepositorio.save(comp);
				}
				
			} 
			
		}
		
	}

	public void atualizarCompeticao(Integer idCompeticao, CompeticaoPutDto competicaoPutDto)
			throws Exception, NotFoundException {
		Competicao comp = recuperarCompeticaoId(idCompeticao);

		List<Competicao> competicoes = competicaoRepositorio.findByNomeCompeticao(competicaoPutDto.getNomeCompeticao());
		boolean entrou = false;

		for (Competicao competicao : competicoes) {
			if (competicao.getNomeCompeticao().equals(competicaoPutDto.getNomeCompeticao())
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
		// aqui tem que converter a string base64 em um arquivo
		ConversorDeArquivos.converterStringParaArquivo(competicaoPutDto.getArquivoRegulamentoCompeticao(),
				idCompeticao);
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
						&& competicaoPatchDto.getMateriaisDeEstudo() != null) {

					for (MaterialEstudo material : competicaoPatchDto.getMateriaisDeEstudo()) {

						material.setEtapa(EtapaCompeticaoVingente);

						CategoriaMaterialEstudo cat = material.getCategoriaMaterialEstudo();
						categoriaMaterialEstudoRepositorio.save(cat);
						materialEstudoRepositorio.save(material);
					}
					// aqui a gente salva na pasta da competição, os respectivos materiais de estudo
					ConversorDeArquivos.converterStringParaArquivo(competicaoPatchDto.getMateriaisDeEstudo(),
							idCompeticao);

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

	public CompeticaoDadosGeraisDto listarDasdosGeraisCompeticao(Integer idCompeticao) throws Exception {
		Competicao competicao = competicaoRepositorio.findById(idCompeticao).get();
		List<Etapa> estapas = etapaRepositorio.findByCompeticao(competicao);

		if (competicao == null) {
			throw new NotFoundException("Competição não encontrada");
		}

		return new CompeticaoDadosGeraisDto(competicao, estapas);
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
	
}
