package com.rafaelb.cursoudemy.api.dto;

import com.rafaelb.cursoudemy.model.entity.PedidoQpd;
import com.rafaelb.cursoudemy.model.entity.PedidoStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PedidoDto {
	private Long id;
	private String nome;
	private Integer qtd;
	private String qpd;
	private String status;
	private Long usuario;
	private String obs;
}
