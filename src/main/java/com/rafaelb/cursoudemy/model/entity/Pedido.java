package com.rafaelb.cursoudemy.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table (name = "pedido", schema = "testeidr")
@Data
@Builder
public class Pedido {
	@Id
	@Column (name = "id")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@Column (name = "nome")
	private String nome;
	
	@Column (name = "obs")
	private String obs;
}