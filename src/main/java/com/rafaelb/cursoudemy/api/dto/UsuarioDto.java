package com.rafaelb.cursoudemy.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsuarioDto {
	private String nome;
	private String senha;
}
