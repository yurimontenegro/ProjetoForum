package br.com.alura.forum.controller.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sun.istack.NotNull;

import br.com.alura.forum.model.Usuario;

public class RegisterForm {

	@NotNull
	@NotEmpty
	private String nome;

	@Email
	private String email;

	@NotNull
	@NotEmpty
	protected String senha;

	public Usuario converter() {
		Usuario usuario = new Usuario(nome, email, new BCryptPasswordEncoder().encode(senha));
		return usuario;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

}
