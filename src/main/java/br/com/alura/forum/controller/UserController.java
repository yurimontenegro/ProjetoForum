package br.com.alura.forum.controller;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.UsuarioDto;
import br.com.alura.forum.model.Usuario;
import br.com.alura.forum.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository usuarioRepository;

	@GetMapping //LISTA OS USUÁRIOS;
	public Page<UsuarioDto> list(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable paginacao) {

		Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
		return UsuarioDto.converter(usuarios);
	}
	
	@GetMapping("/{email}") //BUSCA UM USUÁRIO POR O EMAIL;
	@Transactional 
	public ResponseEntity<UsuarioDto> view(@PathVariable String email) {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email); 
		if (usuario.isPresent()) {
			return ResponseEntity.ok(new UsuarioDto(usuario.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}") //DELETA UM USUÁRIO POR O ID;
	@Transactional 
	public ResponseEntity <?> remove(@PathVariable Long id){ 
		Optional<Usuario> optional = usuarioRepository.findById(id); 
		if (optional.isPresent()) {
			usuarioRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
