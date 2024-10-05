package com.rafaelb.cursoudemy.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafaelb.cursoudemy.api.dto.PedidoDto;
import com.rafaelb.cursoudemy.exceptions.RegraException;
import com.rafaelb.cursoudemy.model.entity.Pedido;
import com.rafaelb.cursoudemy.model.entity.PedidoQpd;
import com.rafaelb.cursoudemy.model.entity.PedidoStatus;
import com.rafaelb.cursoudemy.model.entity.Usuario;
import com.rafaelb.cursoudemy.service.PedidoService;
import com.rafaelb.cursoudemy.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoResource {
	private final PedidoService service;
	
	private final UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity salvar (@RequestBody PedidoDto dto) {
		try {
			Pedido pedido = converter(dto);
			pedido = service.salvar(pedido);
			return new ResponseEntity(pedido, HttpStatus.CREATED);
		}
		catch(RegraException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody PedidoDto dto) {
		return service.obterPorId(id).map( entity -> {
			try {
				Pedido pedido = converter(dto);
				pedido.setId(entity.getId());
				service.atualizar(pedido);
				return ResponseEntity.ok(pedido);
			}
			catch (RegraException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () -> new ResponseEntity("Pedido nao encontrado", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		return service.obterPorId(id).map( entity -> {
			service.deletar(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet( () -> new ResponseEntity("Pedido nao encontrado", HttpStatus.BAD_REQUEST));

	}
	
	private Pedido converter(PedidoDto dto) {
		Pedido pedido = Pedido.builder()
				.id(dto.getId())
				.nome(dto.getNome())
				.qtd(dto.getQtd())
				.obs(dto.getObs())
				.build();
		
		Usuario user = usuarioService.obterPorId(dto.getUsuario())
				.orElseThrow( () -> new RegraException("Usuario nao encontrado para fazer o pedido") );
		
		if (dto.getQpd() != null) {
			pedido.setQpd(PedidoQpd.valueOf(dto.getQpd()));
		}
		if (dto.getStatus() != null) {
			pedido.setStatus(PedidoStatus.valueOf(dto.getStatus()));
		}
		pedido.setId_usuario(user);

		return pedido;
	}
}
