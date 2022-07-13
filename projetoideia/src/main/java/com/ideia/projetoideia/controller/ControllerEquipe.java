package com.ideia.projetoideia.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.dto.EmailDto;
import com.ideia.projetoideia.model.dto.EquipeComEtapaDTO;
import com.ideia.projetoideia.model.dto.EquipeDtoCriacao;
import com.ideia.projetoideia.model.dto.EquipeNomeDto;
import com.ideia.projetoideia.model.dto.EquipeNotaDto;
import com.ideia.projetoideia.model.dto.FeedbacksAvaliativosDto;
import com.ideia.projetoideia.model.dto.LeanCanvasAprovadoConsultoriaDto;
import com.ideia.projetoideia.model.dto.LeanCanvasDto;
import com.ideia.projetoideia.model.dto.MaterialEstudoEnvioDto;
import com.ideia.projetoideia.model.dto.NomeEquipeDto;

import com.ideia.projetoideia.model.dto.PitchDto;

import com.ideia.projetoideia.model.dto.NotasEquipeDto;

import com.ideia.projetoideia.response.IdeiaResponseFile;
import com.ideia.projetoideia.services.EquipeService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/ideia")
public class ControllerEquipe {

	@Autowired
	EquipeService equipeService;

	@PostMapping("/equipe")
	public ResponseEntity<?> criarEquipe(@Valid @RequestBody EquipeDtoCriacao equipe, BindingResult result) {
		if (!result.hasErrors()) {
			try {
				equipeService.criarEquipe(equipe);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new IdeiaResponseFile("Criada com sucesso", HttpStatus.CREATED));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IdeiaResponseFile(
						"Não foi possível criar a equipe", e.getMessage(), HttpStatus.BAD_REQUEST));
			}

		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IdeiaResponseFile(
				"Não foi possível criar a equipe", result.getFieldErrors(), HttpStatus.BAD_REQUEST));

	}

	@GetMapping("/equipes/{competicaoId}")
	public Page<Equipe> consultarEquipes(@RequestParam("page") Integer numeroPagina,
			@PathVariable("competicaoId") Integer competicaoId) {
		return equipeService.consultarEquipesDeUmaCompeticao(numeroPagina, competicaoId);
	}

	@PutMapping("/equipe/update/{equipeId}")
	public ResponseEntity<?> atualizarEquipe(@Valid @RequestBody Equipe equipe, BindingResult result,
			@PathVariable("equipeId") Integer equipeId) {
		if (!result.hasErrors()) {
			try {
				equipeService.atualizarEquipe(equipe, equipeId);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new IdeiaResponseFile("Atualizado com sucesso", HttpStatus.OK));
			} catch (NotFoundException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
						new IdeiaResponseFile("Não foi possível atualizar", e.getMessage(), HttpStatus.NOT_FOUND));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						new IdeiaResponseFile("Não foi possível atualizar", e.getMessage(), HttpStatus.BAD_REQUEST));
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IdeiaResponseFile(
				"Não foi possível atualizar a equipe", result.getFieldErrors(), HttpStatus.BAD_REQUEST));
	}

	@DeleteMapping("/equipe/delete/{equipeId}")
	public ResponseEntity<?> deletarEquipe(@PathVariable("equipeId") Integer equipeId) {
		try {
			equipeService.deletarEquipe(equipeId);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new IdeiaResponseFile("Deletado com sucesso", HttpStatus.OK));

		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new IdeiaResponseFile("Não foi possível deletar", e.getMessage(), HttpStatus.NOT_FOUND));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new IdeiaResponseFile("Não foi possível deletar", e.getMessage(), HttpStatus.BAD_REQUEST));
		}
	}

	@GetMapping("/equipe/{idEquipe}/lean-canvas/elaboracao")
	public LeanCanvasDto getLeanCanvasElaboracao(@PathVariable("idEquipe") Integer idEquipe) {

		try {
			return equipeService.recuperarLeanCanvasElaboracao(idEquipe);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/competicao/resultados-gerais/{idCompeticao}")
	public List<EquipeNotaDto> listarResultadosEquipesCompeticao(@PathVariable("idCompeticao") Integer idCompeticao)
			throws Exception {
		try {
			return equipeService.listarResultadosEquipesCompeticao(idCompeticao);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/competicao/equipes/{idCompeticao}")
	public List<EquipeNomeDto> listarEquipesCompeticao(@PathVariable("idCompeticao") Integer idCompeticao)
			throws Exception {
		try {
			return equipeService.listarEquipesCompeticao(idCompeticao);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/competicao/adicionar-consultor/{idCompeticao}/{idEquipe}/{idConsultor}")
	public ResponseEntity<?> adicionarConsultorEquipe(@PathVariable("idCompeticao") Integer idCompeticao,
			@PathVariable("idEquipe") Integer idEquipe, @PathVariable("idConsultor") Integer idConsultor)
			throws Exception {
		try {
			equipeService.adicionarConsultorEquipe(idCompeticao, idEquipe, idConsultor);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new IdeiaResponseFile("Consultor adcionado ", HttpStatus.OK));
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("/competicao/deletar-equipe/{idCompeticao}/{idEquipe}")
	public ResponseEntity<?> deletarequipe(@PathVariable("idCompeticao") Integer idCompeticao,
			@PathVariable("idEquipe") Integer idEquipe) throws Exception {
		try {
			equipeService.deletarEquipe(idCompeticao, idEquipe);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new IdeiaResponseFile("Equipe removida da competição ", HttpStatus.OK));
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("equipe/dados/{idEquipe}")
	public EquipeComEtapaDTO dadosGeraisEquipe(@PathVariable("idEquipe") Integer idEquipe) {
		try {
			return equipeService.dadosGeraisEquipe(idEquipe);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/equipe/{idEquipe}/remover-membro")
	public ResponseEntity<?> removerMembroEquipe(@Valid @RequestBody EmailDto email,
			@PathVariable("idEquipe") Integer idEquipe) {
		try {
			equipeService.removerMembroEquipe(idEquipe, email.getEmail());
			return ResponseEntity.status(HttpStatus.OK)
					.body(new IdeiaResponseFile("Membro removido com sucesso!", HttpStatus.OK));
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new IdeiaResponseFile("ERRO ", e.getMessage(), HttpStatus.NOT_FOUND));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new IdeiaResponseFile("ERRO", e.getMessage(), HttpStatus.BAD_REQUEST));
		}
	}

	@PatchMapping("/equipe/{idEquipe}/salvar-nome")
	public ResponseEntity<?> patchNomeEquipe(@Valid @RequestBody NomeEquipeDto nomeEquipe,
			@PathVariable("idEquipe") Integer idEquipe) {
		try {
			equipeService.patchNomeEquipe(idEquipe, nomeEquipe.getNomeEquipe());
			return ResponseEntity.status(HttpStatus.OK)
					.body(new IdeiaResponseFile("Membro removido com sucesso!", HttpStatus.OK));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new IdeiaResponseFile("ERRO", e.getMessage(), HttpStatus.BAD_REQUEST));
		}
	}

	@PutMapping("/equipe/{idEquipe}/lean-canvas")
	public ResponseEntity<?> putLeanCanvas(@PathVariable("idEquipe") Integer idEquipe,
			@RequestBody LeanCanvasDto leanCanvasDto) {
		try {
			equipeService.atualizarLeanCanvas(idEquipe, leanCanvasDto);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new IdeiaResponseFile("Lean canvas atualizado com sucesso !", HttpStatus.OK));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new IdeiaResponseFile("ERRO", e.getMessage(), HttpStatus.BAD_REQUEST));
		}

	}

	@PostMapping("/equipe/{idEquipe}/lean-canvas")
	public ResponseEntity<?> criarLeanCanvas(@PathVariable("idEquipe") Integer idEquipe) {

		try {
			equipeService.criarLeanCanvas(idEquipe);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new IdeiaResponseFile("Lean canvas criado com sucesso !", HttpStatus.CREATED));
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new IdeiaResponseFile("ERRO", e.getMessage(), HttpStatus.NOT_FOUND));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new IdeiaResponseFile("ERRO", e.getMessage(), HttpStatus.BAD_REQUEST));
		}

	}

	@PostMapping("/equipe/{idEquipe}/lean-canvas/enviar-consultoria")
	public LeanCanvasDto enviarLeanCanvasConsultoria(@PathVariable("idEquipe") Integer idEquipe) {

		try {
			return equipeService.enviarLeanCanvasParaConsultoria(idEquipe);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/equipe/{idEquipe}/material-estudo")
	public List<MaterialEstudoEnvioDto> getMateriaisDeEstudoEquipe(@PathVariable("idEquipe") Integer idEquipe) {

		try {
			return equipeService.getMateriasDeEstudoDeUmaEquipe(idEquipe);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@PostMapping("/equipe/{idEquipe}/material-estudo/{idMaterialEstudo}")
	public ResponseEntity<?> marcarMaterialConcluido(@PathVariable Integer idEquipe,
			@PathVariable Integer idMaterialEstudo) {

		try {
			equipeService.marcarMaterialComoConcluido(idEquipe, idMaterialEstudo);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new IdeiaResponseFile("Marcado com sucesso !", HttpStatus.CREATED));
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new IdeiaResponseFile("ERRO", e.getMessage(), HttpStatus.NOT_FOUND));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new IdeiaResponseFile("ERRO", e.getMessage(), HttpStatus.BAD_REQUEST));
		}
	}

	@GetMapping("/lean-canvas/{idLeanCanvas}/feedbacks-consultoria")
	public FeedbacksAvaliativosDto listarFeedbacksLeanCanvas(@PathVariable("idLeanCanvas") Integer idLeanCanvas)
			throws Exception {
		try {
			return equipeService.listarFeedbacksLeanCanvas(idLeanCanvas);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/{idEquipe}/lean-canvas/aprovados-consultoria")
	public List<LeanCanvasAprovadoConsultoriaDto> leanCnvasAprovadosPelaConsultoria(
			@PathVariable("idEquipe") Integer idEquipe) {

		try {
			return equipeService.listarLeanCanvasAprovadoPelaConsultoria(idEquipe);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/pitch-deck/{idEquipe}/consultoria")
	public void enviarPitchConsultoria(@PathVariable("idEquipe") Integer idEquipe) throws Exception {

		try {
			equipeService.enviarPitchConsultoria(idEquipe);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@PostMapping("//pitch-deck/{idEquipe}")
	public ResponseEntity<?> criarPitch(@PathVariable("idEquipe") Integer idEquipe,
			@Valid @RequestBody PitchDto pitchDto) throws Exception {
		try {
			equipeService.criarPitch(idEquipe, pitchDto);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new IdeiaResponseFile("Membro removido com sucesso!", HttpStatus.OK));
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new IdeiaResponseFile("ERRO ", e.getMessage(), HttpStatus.NOT_FOUND));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new IdeiaResponseFile("ERRO", e.getMessage(), HttpStatus.BAD_REQUEST));
		}
	}

	@GetMapping("/notas-questoes-avaliativas/{idEquipe}")
	public NotasEquipeDto getNotasEquipe(@PathVariable("idEquipe") Integer idEquipe) {

		try {
			return equipeService.getNotasEquipe(idEquipe);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

}
