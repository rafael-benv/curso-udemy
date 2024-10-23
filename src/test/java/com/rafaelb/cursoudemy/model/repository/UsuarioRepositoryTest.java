package com.rafaelb.cursoudemy.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rafaelb.cursoudemy.model.entity.Usuario;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {
	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	public static Usuario fakeUser() {
		return Usuario.builder()
				.nome("rafa")
				.senha("123")
				.build();
	}
	
	@Test
	public void deveExistirUsuarioComNome() {
		Usuario user = fakeUser();
		entityManager.persist(user);
		
		boolean resultado = repository.existsByNome("rafa");
		
		Assertions.assertThat(resultado).isTrue();
	}
	
	@Test
	public void deveNaoExistirUsuarioComNome() {
		boolean resultado = repository.existsByNome("rafa");
		
		Assertions.assertThat(resultado).isFalse();
	}
	
	@Test
	public void deveRetornarUsuarioPeloNome() {
		Usuario user = fakeUser();
		entityManager.persist(user);
		
		Optional<Usuario> teste = repository.findByNome("rafa");
		
		Assertions.assertThat(teste.isPresent()).isTrue();
	}
	
	@Test
	public void deveNaoRetornarUsuarioPeloNome() {
		Optional<Usuario> teste = repository.findByNome("rafa");
		
		Assertions.assertThat(teste.isPresent()).isFalse();
	}
		
	@Test
	public void devePersistirUsuario() {
		Usuario user = fakeUser();
		Usuario userSaved = repository.save(user);
		
		Assertions.assertThat(userSaved.getId()).isNotNull();
	}
}
