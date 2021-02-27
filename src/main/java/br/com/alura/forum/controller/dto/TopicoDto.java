package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

/*
 * SIGNIFICADO DE DTO - Data Transfer Object
 * O próprio nome já diz muito: um objeto simples usado para transferir dados de um local a outro na aplicação, sem lógica de negócios em 
 * seus objetos e comumente associado à transferência de dados entre uma camada de visão (view layer) e outra de persistência dos dados 
 * (model layer).
 */

import br.com.alura.forum.modelo.Topico;

public class TopicoDto {
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;

	public TopicoDto(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public static Page<TopicoDto> converter(Page<Topico> topicos) {
		return topicos.map(TopicoDto::new); //::new CHAMA O CONSTRUTOR;
	}

}
