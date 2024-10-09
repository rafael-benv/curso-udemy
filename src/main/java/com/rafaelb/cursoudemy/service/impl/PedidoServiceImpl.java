package com.rafaelb.cursoudemy.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rafaelb.cursoudemy.exceptions.RegraException;
import com.rafaelb.cursoudemy.model.entity.Pedido;
import com.rafaelb.cursoudemy.model.entity.PedidoStatus;
import com.rafaelb.cursoudemy.model.repository.PedidoRepository;
import com.rafaelb.cursoudemy.service.PedidoService;

import jakarta.transaction.Transactional;

@Service
public class PedidoServiceImpl implements PedidoService {
	private PedidoRepository repository;
	
	public PedidoServiceImpl(PedidoRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Pedido salvar(Pedido pedido) {
		validar(pedido);
		pedido.setStatus(PedidoStatus.PENDENTE);
		return repository.save(pedido);
	}

	@Override
	@Transactional
	public Pedido atualizar(Pedido pedido) {
		Objects.requireNonNull(pedido.getId());
		return repository.save(pedido);
	}

	@Override
	@Transactional
	public void deletar(Pedido pedido) {
		Objects.requireNonNull(pedido.getId());
		repository.delete(pedido);
	}

	@Override
	public List<Pedido> buscar(Pedido pedidoFiltro) {
		return null;
	}

	@Override
	public void atualizarStatus(Pedido pedido, PedidoStatus status) {
		pedido.setStatus(status);
		atualizar(pedido);
	}

	@Override
	public void validar(Pedido pedido) {
		if(pedido.getNome() == null || pedido.getNome().trim().equals("")) {
			throw new RegraException("Informe um nome valido");
		}
		if(pedido.getQtd() == null) {
			throw new RegraException("Informe uma quantidade valida");
		}
		if(pedido.getQpd() == null) {
			throw new RegraException("Informe se e quantidade, peso, ou duzia");
		}
	}

	@Override
	public Optional<Pedido> obterPorId(Long id) {
		return repository.findById(id);
	}
}
