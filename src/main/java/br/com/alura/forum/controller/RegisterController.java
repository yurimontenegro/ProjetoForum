package br.com.alura.forum.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.UsuarioDto;
import br.com.alura.forum.controller.form.RegisterForm;
import br.com.alura.forum.repository.UserRepository;

@RestController
@RequestMapping("/register") 
public class RegisterController {
	
	@Autowired
	UserRepository usuarioRepository;
	
	@PostMapping 
	@Transactional 
	public ResponseEntity<UsuarioDto> cadastrar(@RequestBody @Valid RegisterForm form, UriComponentsBuilder uriBuilder) {
		return new ResponseEntity<UsuarioDto>(new UsuarioDto(usuarioRepository.save(form.converter())), HttpStatus.CREATED);
	}
	
}
