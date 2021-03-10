package br.com.alura.forum.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.alura.forum.model.Usuario;
import br.com.alura.forum.repository.UserRepository;

@Service //É UM SERVIÇO DO SPRING.
public class AutenticacaoService implements UserDetailsService{

	@Autowired
	private UserRepository repository; //CHAMEI O REPOSITORY
	
	@Override
	//NO LOGIN, O SPRING VAI ENTENDER QUE ESSA CLASSE É DE IDENTIFICAÇÃO DO USUÁRIO.
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
		Optional <Usuario> usuario = repository.findByEmail(username);
		if (usuario.isPresent() && usuario.get().isContaAtiva()) {
			return usuario.get();
		} 
		throw new UsernameNotFoundException("DADOS INVÁLIDOS");
		
	}

}
