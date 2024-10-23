package com.rafaelb.cursoudemy.api.resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafaelb.cursoudemy.api.dto.UsuarioDto;
import com.rafaelb.cursoudemy.exceptions.DbAuthenticationException;
import com.rafaelb.cursoudemy.exceptions.RegraException;
import com.rafaelb.cursoudemy.model.entity.Usuario;
import com.rafaelb.cursoudemy.service.PedidoService;
import com.rafaelb.cursoudemy.service.UsuarioService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioResource.class)
@AutoConfigureMockMvc
public class UsuarioResourceTest {
	static final String API = "/api/usuarios";
	static final MediaType JSON = MediaType.APPLICATION_JSON;
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	UsuarioService service;

	@MockBean
	PedidoService pedidoService;
	
	@Test
	public void deveAutenticarUsuario() throws Exception {
		String nome = "usuario";
		String senha = "123";
		
		UsuarioDto dto = UsuarioDto.builder().nome(nome).senha(senha).build();
		Usuario usuario = Usuario.builder().nome(nome).senha(senha).id(1l).build();
		
		Mockito.when(service.autenticarUsuario(nome, senha)).thenReturn(usuario);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(API.concat("/autenticar"))
				.accept(JSON)
				.contentType(JSON)
				.content(json);
				
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
			.andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuario.getNome()));
	}

	@Test
	public void deveRetornarBadRequestAutenticarUsuario() throws Exception {
		String nome = "usuario";
		String senha = "123";
		
		UsuarioDto dto = UsuarioDto.builder().nome(nome).senha(senha).build();
		
		Mockito.when(service.autenticarUsuario(nome, senha)).thenThrow(DbAuthenticationException.class);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(API.concat("/autenticar"))
				.accept(JSON)
				.contentType(JSON)
				.content(json);
				
		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void deveSalvarUsuario() throws Exception {
		String nome = "usuario";
		String senha = "123";
		
		UsuarioDto dto = UsuarioDto.builder().nome(nome).senha(senha).build();
		Usuario usuario = Usuario.builder().nome(nome).senha(senha).id(1l).build();
		
		Mockito.when(service.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(API)
				.accept(JSON)
				.contentType(JSON)
				.content(json);
				
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
			.andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuario.getNome()));
	}

	@Test
	public void deveRetornarBadRequestSalvarUsuario() throws Exception {
		String nome = "usuario";
		String senha = "123";
		
		UsuarioDto dto = UsuarioDto.builder().nome(nome).senha(senha).build();
		
		Mockito.when(service.salvarUsuario(Mockito.any(Usuario.class))).thenThrow(RegraException.class);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(API)
				.accept(JSON)
				.contentType(JSON)
				.content(json);
				
		mvc.perform(request)
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
