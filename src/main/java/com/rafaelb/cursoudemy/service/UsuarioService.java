package com.rafaelb.cursoudemy.service;

import com.rafaelb.cursoudemy.model.entity.Usuario;

public interface UsuarioService {
	public Usuario autenticarUsuario(String nome, String senha);
	
	public Usuario salvarUsuario(Usuario user);
	
	public void validarNome(String nome);
}
