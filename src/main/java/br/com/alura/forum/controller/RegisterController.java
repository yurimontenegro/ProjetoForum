package br.com.alura.forum.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.UsuarioDto;
import br.com.alura.forum.controller.form.RegisterForm;
import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;

@RestController
@RequestMapping("/register") 
public class RegisterController {
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@PostMapping 
	@Transactional 
	public ResponseEntity<UsuarioDto> cadastrar(@RequestBody @Valid RegisterForm form, UriComponentsBuilder uriBuilder) {
		
		Usuario usuario = form.converter();
		usuarioRepository.save(usuario);

		URI uri = uriBuilder.path("/user/{id}").buildAndExpand(usuario.getId()).toUri(); 
		
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));

	}
}
