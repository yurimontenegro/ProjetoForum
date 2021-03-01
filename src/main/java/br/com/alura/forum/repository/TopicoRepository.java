package br.com.alura.forum.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.model.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> { //UTILIZAMOS O JPA. PRIMEIRO FICOU ENTIDADE E DEPOIS O TIPO DELA.

	Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao); //'CURSO' PRIMEIRO PORQUE É ENTIDADE, E O 'NOME' É O ATRIBUTO 
																		//DENTRO DA ENTIDADE; É MAIS OU MENOS COM UMA NAVEGAÇÃO.

	//@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	//List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso); //CASO EU QUEIRA CRIAR UM MÉTODO PERSONALIZADO, É NECESSÁRIO 
														   					     //CRIAR UMA BUSCA MANUALMENTE COM O QUERY.
}
