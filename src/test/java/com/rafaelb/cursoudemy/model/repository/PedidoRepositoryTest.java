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

import com.rafaelb.cursoudemy.model.entity.Pedido;
import com.rafaelb.cursoudemy.model.entity.PedidoQpd;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PedidoRepositoryTest {
	@Autowired
	PedidoRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void deveSalvarPedido() {
		Pedido pedido = fakePedido();

		pedido = repository.save(pedido);
		
		Assertions.assertThat(pedido.getId()).isNotNull();
	}
	
	@Test
	public void deveDeletarPedido() {
		Pedido pedido = fakePedido();
		
		entityManager.persist(pedido);
		
		pedido = entityManager.find(Pedido.class, pedido.getId());

		repository.delete(pedido);
		
		Pedido pedidoDeletado = entityManager.find(Pedido.class, pedido.getId());
		
		Assertions.assertThat(pedidoDeletado).isNull();
	}
	
	@Test
	public void deveAtualizarPedido() {
		Pedido pedido = fakePedido();
		
		entityManager.persist(pedido);
		
		pedido.setNome("arroz");
		pedido.setObs("nao");
		pedido.setQtd(10);
		
		repository.save(pedido);
		
		Pedido pedidoAtualizado = entityManager.find(Pedido.class, pedido.getId());
		
		Assertions.assertThat(pedidoAtualizado.getNome()).isEqualTo(pedido.getNome());
		Assertions.assertThat(pedidoAtualizado.getObs()).isEqualTo(pedido.getObs());
		Assertions.assertThat(pedidoAtualizado.getQtd()).isEqualTo(pedido.getQtd());
	}
	
	@Test
	public void deveAcharLancamentoPorId() {
		Pedido pedido = fakePedido();
		
		entityManager.persist(pedido);
		
		Optional<Pedido> pedidoAchado = repository.findById(pedido.getId());
		
		Assertions.assertThat(pedidoAchado.isPresent()).isTrue();
	}

	public static Pedido fakePedido() {
		Pedido pedido = Pedido.builder()
				.nome("feijao")
				.qtd(5)
				.qpd(PedidoQpd.P)
				.obs("sim")
				.build();
		return pedido;
	}
}
