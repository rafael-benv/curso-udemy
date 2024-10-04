package com.rafaelb.cursoudemy.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafaelb.cursoudemy.api.dto.UsuarioDto;
import com.rafaelb.cursoudemy.exceptions.RegraException;
import com.rafaelb.cursoudemy.model.entity.Usuario;
import com.rafaelb.cursoudemy.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioResource {
	private UsuarioService service;
	
	public UsuarioResource(UsuarioService service) {
		this.service = service;
	}
	
	public ResponseEntity salvar(@RequestBody UsuarioDto dto) {
		Usuario user = Usuario.builder()
				.nome(dto.getNome())
				.senha(dto.getSenha())
				.build();
		try {
			Usuario userSaved = service.salvarUsuario(user);
			return new ResponseEntity(userSaved, HttpStatus.CREATED);
		}
		catch(RegraException e) {
			return ResponseEntity.badRequest().body(e);
		}
		
	}
}
