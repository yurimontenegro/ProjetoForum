package br.com.alura.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.config.security.TokenService;
import br.com.alura.forum.controller.dto.TokenDto;
import br.com.alura.forum.controller.form.LoginForm;
import br.com.alura.forum.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product Controller", description = "Product Management")
@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();  // MÉTODO CONVERTER CRIADO PARA RECEBER EMAIL
																			// E SENHA;
		try {

			Authentication authentication = authManager.authenticate(dadosLogin);   // FAZER AUTENTICAÇÃO ATRAVÉS DOS
																					// DADOS DE LOGIN;
			String token = tokenService.gerarToken(authentication);
			System.out.println(token);
			return ResponseEntity.ok(new TokenDto(token, "Bearer")); // DEVOLVE O TOKEN E O TIPO DELE PARA O CLIENTE;

		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build(); // DEVOLVE O BADREQUEST
		}
	}

}
