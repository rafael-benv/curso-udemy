package com.rafaelb.cursoudemy.service;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rafaelb.cursoudemy.exceptions.DbAuthenticationException;
import com.rafaelb.cursoudemy.exceptions.RegraException;
import com.rafaelb.cursoudemy.model.entity.Usuario;
import com.rafaelb.cursoudemy.model.repository.UsuarioRepository;
import com.rafaelb.cursoudemy.service.impl.UsuarioServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	UsuarioService service;
	
	@MockBean
	UsuarioRepository repository;
	
	@BeforeEach
	public void setup() {
		service = new UsuarioServiceImpl(repository);
	}
	
	@Test
	public void deveValidarNome() {
		Throwable exception = catchThrowable( () -> {
			Mockito.when(repository.existsByNome(anyString())).thenReturn(false);
			
			service.validarNome("rafa");
		});
		Assertions.assertThat(exception).isNull();
	}
	
	@Test	
	public void deveNaoValidarNome() {
		Throwable exception = catchThrowable( () -> {
			Mockito.when(repository.existsByNome(anyString())).thenReturn(true);
			
			service.validarNome("rafa");
		});
		Assertions.assertThat(exception).isInstanceOf(RegraException.class);
	}
	
	@Test
	public void deveAutenticarUsuario() {
		Throwable exception = catchThrowable( () -> {
			String nome = "rafa";
			String senha = "1234";
			
			Usuario userMock = Usuario.builder().nome(nome).senha(senha).build();
			Mockito.when(repository.findByNome(nome)).thenReturn(Optional.of(userMock));
			Usuario userAutenticado = service.autenticarUsuario(nome, senha);
			
			Assertions.assertThat(userAutenticado.getNome()).isNotNull();
		});
		Assertions.assertThat(exception).isNull();
	}
	
	@Test
	public void deveNaoAutenticarUsuarioPorNome() {
		Throwable exception = catchThrowable( () -> {
			Mockito.when(repository.findByNome(anyString())).thenReturn(Optional.empty());
			Usuario userAutenticado = service.autenticarUsuario("nome", "senha");
			
			Assertions.assertThat(userAutenticado).isNull();
		});
		Assertions.assertThat(exception).isInstanceOf(DbAuthenticationException.class);
	}
	
	@Test
	public void deveNaoAutenticarSenhaIncorreta() {
		Throwable exception = catchThrowable( () -> {
			Usuario user = Usuario.builder().nome("rafa").senha("1234").build();
			
			Mockito.when(repository.findByNome(anyString())).thenReturn(Optional.of(user));
			Usuario userAutenticado = service.autenticarUsuario("rafa", "0");

			Assertions.assertThat(userAutenticado).isNull();
		});
		Assertions.assertThat(exception).isInstanceOf(DbAuthenticationException.class);
	}
}
