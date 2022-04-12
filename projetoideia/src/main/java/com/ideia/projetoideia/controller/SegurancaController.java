package com.ideia.projetoideia.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.dto.JwtRespostaDto;
import com.ideia.projetoideia.model.dto.LoginDto;
import com.ideia.projetoideia.security.util.JwtUtils;
import com.ideia.projetoideia.services.AuthenticacaoService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/ideia/seguranca")
public class SegurancaController {

	@SuppressWarnings("unused")
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private AuthenticacaoService userDetailsService;

	@PostMapping("/login")
	public JwtRespostaDto login(@Valid @RequestBody LoginDto loginRequest) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		Usuario userDetails = (Usuario) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		JwtRespostaDto resposta = new JwtRespostaDto(jwt, userDetails.getId(), userDetails.getNomeUsuario(),
				userDetails.getUsername(), userDetails.getEmail(), roles);

		return resposta;
	}

	@PostMapping("/token")
	public String getToken(@Valid @RequestBody LoginDto loginRequest) {

		UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getLogin());

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());

		return jwtUtils.generateJwtToken(authentication);

	}
}
