package Classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Aluno {
	private int id;
	private String nome;
	private LocalDate dataNascimento;

	public Aluno() {
	}

	public int getId() {
		return id;
	}

	public void setId(String id) {
		this.id = Integer.parseInt(id);
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		formatter = formatter.withLocale(Locale.getDefault());
		this.dataNascimento = LocalDate.parse(dataNascimento, formatter);;
	}
	
	@Override
	public String toString() {
		var dataFormatada = DateTimeFormatter.ISO_DATE.format(dataNascimento);
		return String.format("%d;%s;%s", this.id, this.nome, dataFormatada);
	}
}
