package com.rafaelb.cursoudemy.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rafaelb.cursoudemy.model.entity.Usuario;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
	@Autowired
	UsuarioRepository repository;
	
	@Test
	public void verificarExistenciaEmail() {
		Usuario user = Usuario.builder().nome("rafa").build();
		repository.save(user);
		
		boolean resultado = repository.existsByNome("rafa");
		
		Assertions.assertThat(resultado).isTrue();
	}
}
