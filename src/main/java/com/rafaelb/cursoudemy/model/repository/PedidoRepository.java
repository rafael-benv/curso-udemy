package com.rafaelb.cursoudemy.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafaelb.cursoudemy.model.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
