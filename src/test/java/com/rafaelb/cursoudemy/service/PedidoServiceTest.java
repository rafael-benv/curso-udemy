package com.rafaelb.cursoudemy.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rafaelb.cursoudemy.model.entity.Pedido;
import com.rafaelb.cursoudemy.model.repository.PedidoRepository;
import com.rafaelb.cursoudemy.model.repository.PedidoRepositoryTest;
import com.rafaelb.cursoudemy.service.impl.PedidoServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PedidoServiceTest {
	@SpyBean
	PedidoServiceImpl service;
	
	@MockBean
	PedidoRepository repository;
	
	@Test
	public void deveSalvarPedido() {
		Pedido pedido = PedidoRepositoryTest.fakePedido();
		Mockito.doNothing().when(service).validar(pedido);
		
	}
}
