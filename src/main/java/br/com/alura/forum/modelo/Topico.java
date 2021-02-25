package br.com.alura.forum.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity // SE FAZ NECESSÁRIO EM TODA ENTIDADE
public class Topico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // A CHAVE PRIMÁRIA VAI SER GERADA AUTOMATICAMENTE.
	private Long id;
	
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@Enumerated(EnumType.STRING) // GRAVA NO BD O NOME DA CONSTANTE DO ENUM NO LUGAR DA ONDEM DE DECLARAÇÃO.
	private StatusTopico status = StatusTopico.NAO_RESPONDIDO;
	@ManyToOne // MUITOS PARA UM
	private Usuario autor;
	@ManyToOne // MUITOS PARA UM
	private Curso curso;
	@OneToMany(mappedBy = "topico") // UM TÓPICO PODE TER VÁRIAS RESPOSTAS. O MAPPEDBY TRAZ UM NOVO MAPEAMENTO.
	private List<Resposta> respostas = new ArrayList<>();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topico other = (Topico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Topico() {
	}

	public Topico(String titulo, String mensagem, Curso curso) {
		super();
		this.titulo = titulo;
		this.mensagem = mensagem;
		this.curso = curso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public StatusTopico getStatus() {
		return status;
	}

	public void setStatus(StatusTopico status) {
		this.status = status;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

}
