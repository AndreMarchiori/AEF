package Classes;

public class Avaliacao {
	private Aluno aluno;
	private double peso;
	private double gorduraCorporal;
	private double gorduraVisceral;
	private int idadeMetabolica;

	public Avaliacao() {
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getGorduraCorporal() {
		return gorduraCorporal;
	}

	public void setGorduraCorporal(double gorduraCorporal) {
		this.gorduraCorporal = gorduraCorporal;
	}

	public double getGorduraVisceral() {
		return gorduraVisceral;
	}

	public void setGorduraVisceral(double gorduraVisceral) {
		this.gorduraVisceral = gorduraVisceral;
	}

	public int getIdadeMetabolica() {
		return idadeMetabolica;
	}

	public void setIdadeMetabolica(int idadeMetabolica) {
		this.idadeMetabolica = idadeMetabolica;
	}

	@Override
	public String toString() {
		return aluno.getId() + ";" + peso + ";" + gorduraCorporal + ";" 
	+ gorduraVisceral + ";" + idadeMetabolica;
	}
	
	
}
