package com.rafaelb.cursoudemy.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rafaelb.cursoudemy.service.UsuarioService;

import jakarta.transaction.Transactional;

import com.rafaelb.cursoudemy.exceptions.DbAuthenticationException;
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
		Optional<Usuario> user = repository.findByNome(nome);

		if(!user.isPresent()) {
			throw new DbAuthenticationException("Nao existe usuario com o nome informado.");
		}
		
		if(!user.get().getSenha().equals(senha)) {
			throw new DbAuthenticationException("Senha incorreta.");
		}
		
		return user.get();
	}
	
	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario user) {
		validarNome(user.getNome());
		return repository.save(user);
	}
	
	@Override
	public void validarNome(String nome) {
		boolean existe = repository.existsByNome(nome);
		if (existe) {
			throw new RegraException("Ja existe esse usuario.");
		}
	}

	@Override
	public Optional<Usuario> obterPorId(Long id) {
		return repository.findById(id);
	}
}
