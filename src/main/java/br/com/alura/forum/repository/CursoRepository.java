package br.com.alura.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.alura.forum.modelo.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	Curso findByNome(String nomeCurso); // UTILIZAMOS O JPA. PRIMEIRO FICOU ENTIDADE E
										// DEPOIS O TIPO DELA.
}
