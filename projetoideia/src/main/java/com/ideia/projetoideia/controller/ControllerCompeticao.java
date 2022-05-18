package com.ideia.projetoideia.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.dto.CompeticaoEtapaVigenteDto;
import com.ideia.projetoideia.model.dto.CompeticaoPatchDto;
import com.ideia.projetoideia.model.dto.CompeticaoPutDto;
import com.ideia.projetoideia.model.dto.ConsultorEAvaliadorDto;
import com.ideia.projetoideia.model.dto.ConviteDto;
import com.ideia.projetoideia.model.dto.MaterialEstudoDTO;
import com.ideia.projetoideia.model.dto.QuestoesAvaliativasDto;
import com.ideia.projetoideia.model.enums.TipoConvite;
import com.ideia.projetoideia.response.IdeiaResponseFile;
import com.ideia.projetoideia.services.CompeticaoService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/ideia")
public class ControllerCompeticao {
	@Autowired
	CompeticaoService competicaoService;

	@PostMapping("/competicao")
	public ResponseEntity<?> criarCompeticao(@Valid @RequestBody Competicao competicao, BindingResult result)
			throws Exception {
		if (!result.hasErrors()) {
			try {

				return ResponseEntity.status(HttpStatus.CREATED).body(new IdeiaResponseFile("Criada com sucesso",
						HttpStatus.CREATED, competicaoService.criarCompeticao(competicao)));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IdeiaResponseFile(
						"Não foi possível criar a competição", e.getMessage(), HttpStatus.BAD_REQUEST));
			}

		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IdeiaResponseFile(
				"Não foi possível criar a competição", result.getFieldErrors(), HttpStatus.BAD_REQUEST));
	}

	@GetMapping("/competicao/{competicaoId}")
	public Competicao encontrarCompeticao(@PathVariable("competicaoId") Integer competicaoId) throws Exception {
		try {
			return competicaoService.recuperarCompeticaoId(competicaoId);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	// Retorna todas as competições na fase de INSCRIÇÕES
	@GetMapping("/competicoes/inscricoes")
	public List<CompeticaoEtapaVigenteDto> consultarCompeticoesPorNomeMesAno(
			@RequestParam(value = "nomeCompeticao", required = false) String nomeCompeticao,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "ano", required = false) Integer ano) throws Exception {

		return competicaoService.consultarCompeticoesFaseInscricao(nomeCompeticao, mes, ano);

	}

	// Retorna todas as competições relacionadas a um USUÁRIO
	@GetMapping("/competicoes/usuario-logado")
	public List<CompeticaoEtapaVigenteDto> consultarMinhasCompeticoes(
			@RequestParam(value = "nomeCompeticao", required = false) String nomeCompeticao,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "ano", required = false) Integer ano) throws Exception {
		return competicaoService.consultarMinhasCompeticoes(nomeCompeticao, mes, ano);
	}

	@GetMapping("/competicoes")
	public List<Competicao> consultarCompeticoes() {
		return competicaoService.consultarCompeticoes();
	}

	@GetMapping("/competicoes/usuario/{usuarioId}")
	public List<Competicao> consultarCompeticoesDoUsuario(@PathVariable("usuarioId") Integer usuarioId) {
		return competicaoService.consultarCompeticoesDoUsuario(usuarioId);
	}

	@GetMapping("/competicao/{idCompeticao}/usuarios-nao-relacionados")
	public ResponseEntity<?> consultarUsuariosSemCompeticao(@PathVariable("idCompeticao") Integer idCompeticao) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(new IdeiaResponseFile("Criada com sucesso",
					HttpStatus.CREATED, competicaoService.consultarUsuariosSemCompeticao(idCompeticao)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new IdeiaResponseFile("Nenhum usuario foi encontrado", e.getMessage(), HttpStatus.BAD_REQUEST));
		}
	}

	@PutMapping("/competicao/update/{competicaoId}")
	public ResponseEntity<?> atualizarCompeticao(@Valid @RequestBody CompeticaoPutDto competicao, BindingResult result,
			@PathVariable("competicaoId") Integer competicaoId) {

		if (!result.hasErrors()) {

			try {
				competicaoService.atualizarCompeticao(competicaoId, competicao);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new IdeiaResponseFile("Atualizada com sucesso", HttpStatus.OK));
			} catch (NotFoundException e) {

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
						new IdeiaResponseFile("Não foi possível atualizar", e.getMessage(), HttpStatus.NOT_FOUND));
			} catch (Exception e) {

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						new IdeiaResponseFile("Não foi possível atualizar", e.getMessage(), HttpStatus.BAD_REQUEST));
			}

		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				new IdeiaResponseFile("Não foi possível atualizar", result.getFieldErrors(), HttpStatus.BAD_REQUEST));

	}

	@DeleteMapping("/competicao/delete/{competicaoId}")
	public ResponseEntity<?> deletarCompeticao(@PathVariable("competicaoId") Integer competicaoId) {

		try {

			competicaoService.deletarCompeticaoPorId(competicaoId);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new IdeiaResponseFile("Deletada com sucesso", HttpStatus.OK));

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new IdeiaResponseFile("Não foi possível deletar", e.getMessage(), HttpStatus.NOT_FOUND));
		}

	}

	@GetMapping("/competicao/{idCompeticao}/regulamento")
	public byte[] recuperarRegulamentoDaCompeticao(@PathVariable("idCompeticao") Integer idCompeticao) {

		try {
			return competicaoService.recuperarRegulamentoCompeticao(idCompeticao);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/competicao/convidar-usuario")
	public ResponseEntity<?> convidarUsuario(@RequestBody ConviteDto conviteDto) {

		try {
			competicaoService.convidarUsuario(conviteDto);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new IdeiaResponseFile("Usuario convidado com sucesso", HttpStatus.CREATED));
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new IdeiaResponseFile("Não foi possível convidar", e.getMessage(), HttpStatus.NOT_FOUND));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new IdeiaResponseFile("Não foi possível convidar", e.getMessage(), HttpStatus.BAD_REQUEST));
		}

	}

	@GetMapping("/competicao/{idCompeticao}/consultores")
	public List<ConsultorEAvaliadorDto> listarConsultoresDeUmaCompeticao(
			@PathVariable("idCompeticao") Integer idCompeticao) {
		try {
			return competicaoService.listarConsultoresEAaliadoresDeUmaCompeticao(idCompeticao, TipoConvite.CONSULTOR);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

	}

	@GetMapping("/competicao/{idCompeticao}/avaliadores")
	public List<ConsultorEAvaliadorDto> listarAvaliadoresDeUmaCompeticao(
			@PathVariable("idCompeticao") Integer idCompeticao) {
		try {
			return competicaoService.listarConsultoresEAaliadoresDeUmaCompeticao(idCompeticao, TipoConvite.AVALIADOR);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

	}

	@GetMapping("/questoes-por-competicao/{competicaoId}")
	public List<QuestoesAvaliativasDto> consultarQuestoesAvaliativasDaCompeticao(
			@PathVariable("competicaoId") Integer competicaoId) {
		try {
			return competicaoService.consultarQuestoesDaCompeticao(competicaoId);

		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@PatchMapping("/competicao/update/{competicaoId}")
	public ResponseEntity<?> patchCompeticao(@PathVariable("competicaoId") Integer competicaoId,
			@RequestBody CompeticaoPatchDto competicao) {
		try {
			competicaoService.patchCompeticao(competicao, competicaoId);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new IdeiaResponseFile("Competição Atualizada / Criada com Sucesso", HttpStatus.OK));
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new IdeiaResponseFile("Não atualizar / criar a competição", e.getMessage(), HttpStatus.NOT_FOUND));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IdeiaResponseFile(
					"Não atualizar / criar a competiçãor", e.getMessage(), HttpStatus.BAD_REQUEST));
		}

	}

	@DeleteMapping("/{idCompeticao}/remover-usuario-convidado/{idUsuario}")
	public ResponseEntity<?> removerUsuarioConvidado(@PathVariable("idCompeticao") Integer idCompeticao,
			@PathVariable("idUsuario") Integer idUsuario) {
		try {

			competicaoService.removerUsuarioConvidado(idCompeticao, idUsuario);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new IdeiaResponseFile("Usuario removido com sucesso com sucesso", HttpStatus.OK));

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new IdeiaResponseFile("Não foi possível remover usuario", e.getMessage(), HttpStatus.NOT_FOUND));
		}

	}

	@GetMapping("/{idCompeticao}/materiais-estudo")
	public List<MaterialEstudoDTO> listarMateriaisEstudoCompeticao(@PathVariable("idCompeticao") Integer idCompeticao) {
		try {
			return competicaoService.listarMateriaisEstudoCompeticao(idCompeticao);

		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/{idCompeticao}/questoes-avaliativas")
	public List<QuestoesAvaliativasDto> listarQuestoesAvaliativasCompeticao(
			@PathVariable("idCompeticao") Integer idCompeticao) {
		try {
			return competicaoService.listarQuestoesAvaliativasCompeticao(idCompeticao);

		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
