package com.ideia.projetoideia.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ideia.projetoideia.model.Convite;
import com.ideia.projetoideia.model.PapelUsuarioCompeticao;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.dto.ConviteListaDto;
import com.ideia.projetoideia.model.dto.ConviteRespostaDto;
import com.ideia.projetoideia.model.dto.ConvitesquantidadeDto;
import com.ideia.projetoideia.model.enums.StatusConvite;
import com.ideia.projetoideia.model.enums.TipoConvite;
import com.ideia.projetoideia.model.enums.TipoPapelUsuario;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.ConviteRepositorio;
import com.ideia.projetoideia.repository.PapelUsuarioCompeticaoRepositorio;

@Service
public class ConviteService {

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	ConviteRepositorio conviteRepositorio;

	@Autowired
	CompeticaoRepositorio competicaoRepositorio;

	@Autowired
	PapelUsuarioCompeticaoRepositorio papelUsuarioCompeticaoRepositorio;

	public List<ConviteListaDto> listarConvites(TipoConvite tipoConvite) throws Exception {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.consultarUsuarioPorEmail(autenticado.getName());

		List<Convite> convitesRecuperada = conviteRepositorio.findByUsuario(usuario);

		List<ConviteListaDto> convites = new ArrayList<ConviteListaDto>();

		for (Convite convite : convitesRecuperada) {

			if (convite.getTipoConvite().equals(tipoConvite)
					&& convite.getStatusConvite().equals(StatusConvite.ENVIADO)) {
				convites.add(new ConviteListaDto(convite));
			}
		}
		if (convites.size() == 0) {
			throw new Exception("Não existe nenhum convite para você ser consultor.");
		}

		return convites;
	}

	public ConvitesquantidadeDto listarQuantidadeConvites(String tipoConvite) throws Exception {
		TipoConvite tipoConviteEnum = TipoConvite.CONSULTOR;

		if (tipoConvite.toUpperCase().equals("AVALIADOR")) {
			tipoConviteEnum = TipoConvite.AVALIADOR;
		}

		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.consultarUsuarioPorEmail(autenticado.getName());

		List<Convite> convitesRecuperados = conviteRepositorio.findByUsuario(usuario);

		ConvitesquantidadeDto quantidadeConvites = new ConvitesquantidadeDto(0);
		for (Convite convite : convitesRecuperados) {
			if (convite.getTipoConvite().equals(tipoConviteEnum)
					&& convite.getStatusConvite().equals(StatusConvite.ENVIADO)) {
				quantidadeConvites.agregar();
			}
		}
		return quantidadeConvites;
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
}
