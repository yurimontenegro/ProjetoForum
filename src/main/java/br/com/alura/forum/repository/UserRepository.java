package br.com.alura.forum.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.model.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Long>{
	Page<Usuario> findByEmail(String email, Pageable paginacao); //IRÁ UTILIZAR COMO PARÂMETRO O EMAIL DO USUÁRIO
	
	Optional<Usuario> findByEmail(String email);
}
