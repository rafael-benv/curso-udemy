package com.rafaelb.cursoudemy.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "usuario", schema = "testeidr")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	@Id
	@Column (name = "id")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@Column (name = "nome")
	private String nome;

	@Column (name = "senha")
	@JsonIgnore
	private String senha;
}
