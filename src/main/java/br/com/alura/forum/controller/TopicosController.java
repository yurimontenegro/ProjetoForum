package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos") // URL TÓPICOS SETADA PARA TODOS OS MÉTODOS; RESPONDE A REQUISIÇÃO.
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private CursoRepository cursoRepository;
	
	//MÉTODO QUE IRÁ CONSULTAR O CURSO POR NOME, OU LISTAR TODOS DE ACORDO COM A PAGINAÇÃO;
	@GetMapping // MÉTODO QUE PUXA AS INFORMAÇÕES; ESTAREI ENTREGANDO INFORMAÇÕES.
	@Cacheable (value = "listaDeTopicos") //UTILIZEI O MÉTODO CACHE E O IDENTIFIQUEI;
	public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso, //REQUESTPARAM: PARÂMETRO OBRIGATÓRIO 
			@PageableDefault(page = 0, size = 20, sort = "id", direction = Direction.ASC) Pageable paginacao) { //PAGEABLE JÁ RECEBE TODOS OS PARÂMETROS NECESSÁRIOS;

		if (nomeCurso == null) {
			Page<Topico> topicos = topicoRepository.findAll(paginacao); //PAGINAÇÃO NÃO FUNCIONA COM LIST, É NECESSÁRIO USAR O PAGE;
			return TopicoDto.converter(topicos);
		} else {
			Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
			return TopicoDto.converter(topicos);
		}
	}
	
	//MÉTODO QUE IRÁ CRIAR UM NOVO TÓPICO;
	@PostMapping // MÉTODO QUE SALVA AS INFORMAÇÕES; ESTAREI RECEBENDO INFORMAÇÕES.
	@Transactional //COMITAR A TRANSAÇÃO NO FINAL DO MÉTODO - ATUALIZAR
	@CacheEvict (value = "listaDeTopicos", allEntries = true) //APAGA TODOS OS REGISTROS NO CACHE, CRIADOS NA CONSULTA;
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) { 
																			  // PARA PEGAR DO CORPO DA REQUISIÇÃO, E NÃO DA URL.
														                      // REQUESTBODY
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri(); //CRIA UMA URL, UTILIZANDO O ID DO TÓPICO;
																						   //OBS: CRIA A PARTIR DO ENDEREÇO DO SERVIDOR;
		return ResponseEntity.created(uri).body(new TopicoDto(topico));

	}
	
	/*
	SE UTILIZAR UM MÉTODO VOID, O RETORNO DA APLICAÇÃO SERIA UMA REPOSTA SEM CONTEÚDO, NESSE CASO O CÓDIGO HTTP 200, POIS A APLICAÇÃO
	FOI PROCESSADA COM SUCESSO.
	
	O RESPONSEENTITY RECEBE UM GENERIC (QUAL O TIPO DE OBJETO QUE VOU DEVOLVER NO CORPO DA RESPOSTA); 
	*/
	
	//MÉTODO QUE IRÁ CONSULTAR TÓPICO POR ID;
	@GetMapping("/{id}")
	@Transactional //COMITAR A TRANSAÇÃO NO FINAL DO MÉTODO - ATUALIZAR
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) { //O PATH VAI IDENTIFICAR QUE EU SÓ PRECISO DO ID PÓS /.
		Optional<Topico> topico = topicoRepository.findById(id); //FINDBYID VERIFICA SE O ID EXISTE, SE NÃO, LANÇA A EXCEÇÃO;
		if (topico.isPresent()) {
			return ResponseEntity.ok (new DetalhesDoTopicoDto(topico.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	//MÉTODO QUE IRÁ SOBRESCREVER UM TÓPICO EXISTENTE POR ID / ATUALIZAÇÃO DO OBJETO;
	@PutMapping("/{id}") 
	@Transactional //COMITAR A TRANSAÇÃO NO FINAL DO MÉTODO - ATUALIZAR
	@CacheEvict (value = "listaDeTopicos", allEntries = true) //APAGA TODOS OS REGISTROS NO CACHE, CRIADOS NA CONSULTA;
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
		Optional<Topico> optional = topicoRepository.findById(id); //FINDBYID VERIFICA SE O ID EXISTE, SE NÃO, LANÇA A EXCEÇÃO;
		if (optional.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		return ResponseEntity.notFound().build();
	}
	
	//MÉTODO QUE IRÁ APAGAR UM TÓPICO UTILIZANDO O ID COMO REFERÊNCIA.
	@DeleteMapping("{id}")
	@Transactional //COMITAR A TRANSAÇÃO NO FINAL DO MÉTODO - ATUALIZAR
	@CacheEvict (value = "listaDeTopicos", allEntries = true) //APAGA TODOS OS REGISTROS NO CACHE, CRIADOS NA CONSULTA;
	public ResponseEntity <?> remover(@PathVariable Long id){ // <?> É NECESSÁRIO UM GENERICS, E COMO NÃO SABEMOS QUAL É, UTILIZAMOS DE TAL;
		Optional<Topico> optional = topicoRepository.findById(id); //FINDBYID VERIFICA SE O ID EXISTE, SE NÃO, LANÇA A EXCEÇÃO;
		if (optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
