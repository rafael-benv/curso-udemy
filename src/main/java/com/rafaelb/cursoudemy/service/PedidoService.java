package com.rafaelb.cursoudemy.service;

import java.util.List;
import java.util.Optional;

import com.rafaelb.cursoudemy.model.entity.Pedido;
import com.rafaelb.cursoudemy.model.entity.PedidoStatus;

public interface PedidoService {
	Pedido salvar(Pedido pedido);
	
	Pedido atualizar(Pedido pedido);

	void deletar(Pedido pedido);

	List<Pedido> buscar(Pedido pedidoFiltro);

	void atualizarStatus(Pedido pedido, PedidoStatus status);
	
	void validar(Pedido pedido);
	
	Optional<Pedido> obterPorId(Long id);
}