package com.rafaelb.cursoudemy.service.impl;

import org.springframework.stereotype.Service;

import com.rafaelb.cursoudemy.service.UsuarioService;
import com.rafaelb.cursoudemy.exceptions.RegraException;
import com.rafaelb.cursoudemy.model.entity.Usuario;
import com.rafaelb.cursoudemy.model.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	private UsuarioRepository repository;

	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}
	
	@Override
	public Usuario autenticarUsuario(String nome, String senha) {
		return null;
	}
	
	@Override
	public Usuario salvarUsuario(Usuario user) {
		return null;
	}
	
	@Override
	public void validarNome(String nome) {
		boolean existe = repository.existsByNome(nome);
		if (existe) {
			throw new RegraException("Ja existe esse usuario.");
		}
	}
}
