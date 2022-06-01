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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.dto.CompeticaoEtapaVigenteDto;
import com.ideia.projetoideia.model.dto.ConviteDto;
import com.ideia.projetoideia.model.dto.EmailDto;
import com.ideia.projetoideia.model.dto.UsuarioDto;
import com.ideia.projetoideia.model.dto.UsuarioPatchDto;
import com.ideia.projetoideia.response.IdeiaResponseFile;
import com.ideia.projetoideia.services.UsuarioService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/ideia")
public class ControllerUsuario {
	@Autowired
	UsuarioService usuarioService;

	@PostMapping("/usuario")
	public ResponseEntity<?> criarUsuario(@Valid @RequestBody Usuario user, BindingResult result) throws Exception {

		if (!result.hasErrors()) {
			try {
				usuarioService.criarUsuario(user);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new IdeiaResponseFile("Criado com sucesso", HttpStatus.CREATED));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IdeiaResponseFile(
						"Não foi possível cadastrar o usuário.", e.getMessage(), HttpStatus.BAD_REQUEST));
			}

		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IdeiaResponseFile(
				"Não foi possível cadastrar o usuário.", result.getFieldErrors(), HttpStatus.BAD_REQUEST));

	}

	@GetMapping("/usuarios")
	public List<Usuario> consultarUsuarios() {
		return usuarioService.consultarUsuarios();
	}
	
	@GetMapping("/usuario-logado")
	public UsuarioDto consultarUsuarioLogado() {
		UsuarioDto usuario = null;
		try {
		  usuario = usuarioService.consultarUsuarioLogado();
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return usuario;
	}

	@PatchMapping("/usuario/update")
	public ResponseEntity<?> atualizarUsuario(@Valid @RequestBody UsuarioPatchDto user, BindingResult result) {

		if (!result.hasErrors()) {

			try {
				usuarioService.atualizarUsuario(user);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new IdeiaResponseFile("Atualizado com sucesso", HttpStatus.OK));
			} catch (NotFoundException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new IdeiaResponseFile("Erro ao atualizar usuário", e.getMessage(), HttpStatus.NOT_FOUND));
			} catch (Exception e) {

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						new IdeiaResponseFile("Erro ao atualizar usuário", e.getMessage(), HttpStatus.BAD_REQUEST));
			}

		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				new IdeiaResponseFile("Erro ao atualizar usuário", result.getFieldErrors(), HttpStatus.BAD_REQUEST));

	}

	@DeleteMapping("/usuario/delete/{usuarioId}")
	public ResponseEntity<?> deletarUsuarioPorId(@PathVariable("usuarioId") Integer usuarioId) {

		try {

			usuarioService.deletarUsuarioPorId(usuarioId);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new IdeiaResponseFile("Usuario deletado com sucesso", HttpStatus.OK));

		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new IdeiaResponseFile("Usuário não encontrado ", e.getMessage(), HttpStatus.NOT_FOUND));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new IdeiaResponseFile("ERRO", e.getMessage(), HttpStatus.BAD_REQUEST));
		}

	}

	@GetMapping("/competicoes/usuario-logado")
	public List<CompeticaoEtapaVigenteDto> consultarMinhasCompeticoes(
			@RequestParam(value = "nomeCompeticao", required = false) String nomeCompeticao,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "ano", required = false) Integer ano) throws Exception {
		return usuarioService.consultarMinhasCompeticoes(nomeCompeticao, mes, ano);
	}

	@PutMapping("/usuario/resetar-senha")
	public @ResponseBody ResponseEntity<?> resetarSenha(@RequestBody EmailDto emailDto) {
		System.out.println(emailDto.getEmail());
		try {
			usuarioService.resetarSenhaDoUsuario(emailDto.getEmail());
			return ResponseEntity.status(HttpStatus.OK)
					.body(new IdeiaResponseFile("Senha resetada com sucesso", HttpStatus.OK));
		} catch (NotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new IdeiaResponseFile("ERRO", e.getMessage(), HttpStatus.NOT_FOUND));
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new IdeiaResponseFile("ERRO", e.getMessage(), HttpStatus.BAD_REQUEST));
		}

	}

	@GetMapping("/competicao/{idCompeticao}/usuarios-nao-relacionados")
	public ResponseEntity<?> consultarUsuariosSemCompeticao(@PathVariable("idCompeticao") Integer idCompeticao) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(new IdeiaResponseFile("Criada com sucesso",
					HttpStatus.CREATED, usuarioService.consultarUsuariosSemCompeticao(idCompeticao)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new IdeiaResponseFile("Nenhum usuario foi encontrado", e.getMessage(), HttpStatus.BAD_REQUEST));
		}
	}
	
	@PostMapping("/competicao/convidar-usuario")
	public ResponseEntity<?> convidarUsuario(@RequestBody ConviteDto conviteDto) {

		try {
			usuarioService.convidarUsuario(conviteDto);
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
}
