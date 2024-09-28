package com.rafaelb.cursoudemy.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table (name = "pedido", schema = "testeidr")
@Data
@Builder
public class Pedido {
	public static enum QPD {
		Q,
		P,
		D
	}
	
	public static enum STATUS {
		PENDENTE,
		COMPLETO
	}

	@Column (name = "id")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@Column (name = "nome")
	private String nome;
	
	@Column (name = "obs")
	private String obs;

	@Column (name = "status")
	@Enumerated (value = EnumType.STRING)
	private String status;

	@Column (name = "qpd")
	@Enumerated (value = EnumType.STRING)
	private Character qpd;
}