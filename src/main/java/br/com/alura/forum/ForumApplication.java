package br.com.alura.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication //HABILITA O SPRINGBOOT NA APLICAÇÃO
@EnableSpringDataWebSupport //SPRING PEGA AS REQUISIÇÕES DE PAGINAÇÃO E ORDENAÇÃO E PASSA PARA O SPRING DATA
@EnableCaching //HABILITAR O USO DE CACHE NA APLICAÇÃO

public class ForumApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);

	}

}
