package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import br.com.alura.forum.modelo.Usuario;

public class UsuarioDto {
	private Long id;
	private String nome;
	private String email;
	private LocalDateTime dataCriacao;

	public UsuarioDto(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.dataCriacao = usuario.getDataCriacao();
	}

	public Long getId() {
		return id;
	}

	public String getnome() {
		return nome;
	}

	public String getemail() {
		return email;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public static Page<UsuarioDto> converter(Page<Usuario> usuarios) {
		return usuarios.map(UsuarioDto::new); // ::new CHAMA O CONSTRUTOR;
	}

}
