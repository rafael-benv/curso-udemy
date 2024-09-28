package com.rafaelb.cursoudemy.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafaelb.cursoudemy.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	boolean existsByNome(String nome);
}
