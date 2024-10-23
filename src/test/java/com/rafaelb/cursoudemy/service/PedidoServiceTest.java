package com.rafaelb.cursoudemy.service;

import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rafaelb.cursoudemy.exceptions.RegraException;
import com.rafaelb.cursoudemy.model.entity.Pedido;
import com.rafaelb.cursoudemy.model.entity.PedidoStatus;
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
		Pedido pedidoSeraSalvado = PedidoRepositoryTest.fakePedido();
		Mockito.doNothing().when(service).validar(pedidoSeraSalvado);
		
		Pedido pedidoSalvo = PedidoRepositoryTest.fakePedido();
		pedidoSalvo.setId(1l);
		pedidoSalvo.setStatus(PedidoStatus.PENDENTE);
		Mockito.when(repository.save(pedidoSeraSalvado)).thenReturn(pedidoSalvo);
		
		Pedido pedido = service.salvar(pedidoSeraSalvado);
		
		Assertions.assertThat(pedido.getId()).isEqualTo(pedidoSalvo.getId());
		Assertions.assertThat(pedido.getStatus()).isEqualTo(PedidoStatus.PENDENTE);
	}

	@Test
	public void deveNaoSalvarPedidoErroValidacao() {
		Pedido pedidoSeraSalvado = PedidoRepositoryTest.fakePedido();
		Mockito.doThrow(RegraException.class).when(service).salvar(pedidoSeraSalvado);
		
		Assertions.catchThrowableOfType( () -> service.salvar(pedidoSeraSalvado), RegraException.class);
		Mockito.verify(repository, Mockito.never()).save(pedidoSeraSalvado);
	}

	@Test
	public void deveAtualizarPedido() {
		Pedido pedidoSalvo = PedidoRepositoryTest.fakePedido();
		pedidoSalvo.setId(1l);
		pedidoSalvo.setStatus(PedidoStatus.PENDENTE);

		Mockito.doNothing().when(service).validar(pedidoSalvo);
		
		Mockito.when(repository.save(pedidoSalvo)).thenReturn(pedidoSalvo);
		
		service.atualizar(pedidoSalvo);
		
		Mockito.verify(repository, Mockito.times(1)).save(pedidoSalvo);
	}
	
	@Test
	public void deveNaoAtualizarPedidoErroId() {
		Pedido pedidoSalvo = PedidoRepositoryTest.fakePedido();
		
		Assertions.catchThrowableOfType( () -> service.atualizar(pedidoSalvo), NullPointerException.class);
		Mockito.verify(repository, Mockito.never()).save(pedidoSalvo);
	}
	
	@Test
	public void deveDeletarPedido() {
		Pedido pedidoSalvo = PedidoRepositoryTest.fakePedido();
		pedidoSalvo.setId(1l);
		
		service.deletar(pedidoSalvo);
		
		Mockito.verify(repository).delete(pedidoSalvo);
	}

	@Test
	public void deveNaoDeletarPedido() {
		Pedido pedidoSalvo = PedidoRepositoryTest.fakePedido();
		
		Assertions.catchThrowableOfType( () -> service.deletar(pedidoSalvo), NullPointerException.class);
		
		Mockito.verify(repository, Mockito.never()).delete(pedidoSalvo);
	}
	
	@Test
	public void deveAtualizarStatusPedido() {
		Pedido pedidoSalvo = PedidoRepositoryTest.fakePedido();
		pedidoSalvo.setId(1l);
		pedidoSalvo.setStatus(PedidoStatus.PENDENTE);


		PedidoStatus novoStatus = PedidoStatus.COMPLETO;
		Mockito.doReturn(pedidoSalvo).when(service).atualizar(pedidoSalvo);
		
		service.atualizarStatus(pedidoSalvo, novoStatus);
		
		Assertions.assertThat(pedidoSalvo.getStatus()).isEqualTo(novoStatus);
		Mockito.verify(service).atualizar(pedidoSalvo);
	}
	
	@Test
	public void deveObterPedidoPorId() {
		Long id = 1l;
		Pedido pedidoSalvo = PedidoRepositoryTest.fakePedido();
		pedidoSalvo.setId(id);
		
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(pedidoSalvo));
		
		Optional<Pedido> resultado = service.obterPorId(id);
		
		Assertions.assertThat(resultado.isPresent()).isTrue();
	}

	@Test
	public void deveRetornarVazioAoObterPedidoPorId() {
		Long id = 1l;
		Pedido pedidoSalvo = PedidoRepositoryTest.fakePedido();
		pedidoSalvo.setId(id);
		
		Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
		
		Optional<Pedido> resultado = service.obterPorId(id);
		
		Assertions.assertThat(resultado.isPresent()).isFalse();
	}
	
	@Test
	public void deveLancarErrosValidarPedido() {
		Pedido pedido = new Pedido();
		
		Throwable erro;
		erro = catchThrowable( () -> service.validar(pedido));
		Assertions.assertThat(erro).isInstanceOf(RegraException.class).hasMessage("Informe um nome valido");
		
		pedido.setNome("rafa");
		
		erro = catchThrowable( () -> service.validar(pedido));
		Assertions.assertThat(erro).isInstanceOf(RegraException.class).hasMessage("Informe uma quantidade valida");
		
		pedido.setQtd(3);

		erro = catchThrowable( () -> service.validar(pedido));
		Assertions.assertThat(erro).isInstanceOf(RegraException.class).hasMessage("Informe se e quantidade, peso, ou duzia");
	}
}
