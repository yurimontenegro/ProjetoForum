package br.com.alura.forum.config.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroDeValidacaoHandler { //TRATAMENTO DE ERROS DE VALIDAÇÃO
	
	@Autowired
	private MessageSource messageSource; //AJUDA A PEGAR A MENSAGEM DE ERRO, COM O IDIOMA SELECIONADO.
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST) //QUAL STATUS IRÁ DEVOLVER.
	@ExceptionHandler(MethodArgumentNotValidException.class) //SE CAIR QUALQUER ERRO DO TIPO, CHAMA O MÉTODO ABAIXO.
	public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) { //UMA LISTA COM OS ERROS QUE PODEM ACONTECER
		List<ErroDeFormularioDto> dto = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors(); //CONTEM TODOS OS ERROS DE FORMULÁRIO.
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagem);
			dto.add(erro);
		});
		
		return dto;
		
	}

}
