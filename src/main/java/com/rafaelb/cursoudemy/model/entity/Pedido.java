package com.rafaelb.cursoudemy.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "pedido", schema = "testeidr")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
	@Id
	@Column (name = "id")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@Column (name = "nome")
	private String nome;
	
	@Column (name = "qtd")
	private Integer qtd;
	
	@Column (name = "qpd")
	@Enumerated(value = EnumType.STRING)
	private PedidoQpd qpd;

	@Column (name = "status")
	@Enumerated(value = EnumType.STRING)
	private PedidoStatus status;
	
	@Column (name = "obs")
	private String obs;
	
	@ManyToOne
	@JoinColumn (name = "id_usuario")
	private Usuario id_usuario;
}