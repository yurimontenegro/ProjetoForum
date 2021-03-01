package br.com.alura.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${forum.jwt.expiration}") //VALOR BUSCADO NO ARQUIVO APPLICATION.PROPERTIES - TEMPO DE LOGIN
	private String expiration;
	
	@Value("${forum.jwt.secret}") //VALOR BUSCADO NO ARQUIVO APPLICATION.PROPERTIES - SENHA
	private String secret;
	
	public String gerarToken(Authentication authentication) {
		Usuario logado = (Usuario) authentication.getPrincipal(); //RECUPERAR O USUÁRIO QUE ESTÁ LOGADO;
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration)); //DATA E HORA DO LOGIN + TEMPO DE EXPIRAÇÃO;
		
		return Jwts.builder()
				.setIssuer("API do Fórum da Alura")         //QUEM ESTÁ GERANDO O TOKEN;
				.setSubject(logado.getId().toString())      //A QUEM PERTENCE O TOKEN;
				.setIssuedAt(hoje)                          //DATA DO LOGIN;
				.setExpiration(dataExpiracao)               //TEMPO DE EXPIRAÇÃO DE LOGIN;
				.signWith(SignatureAlgorithm.HS256, secret) //ALGORÍTMO DE CRIPTOGRAFIA E A SENHA DA MINHA APLICAÇÃO, PARA ASSINATURA;
				.compact();                                 //COMPACTAR TUDO E TRANSFORMAR EM UMA STRING; 
	}

	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token); //IRÁ DESCRIPTOGRAFAR A CHAVE E FARÁ UMA VERIFICAÇÃO;
			return true; //SE OK, RETORNA TRUE;
		} catch (Exception e) {
			return false; //SE NÃO, RETORNA FALSE;
		}
		
	}

	public Long getIdUsuario(String token) { //MÉTODO QUE IRÁ BUSCAR O ID DO USUÁRIO;
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

}
