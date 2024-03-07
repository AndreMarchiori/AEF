package Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AEF {

	private static Map<Integer, Aluno> alunos;

	public static void main(String[] args) {

		alunos = new HashMap<>();

		Boolean loop = true;
		try (Scanner scanner = new Scanner(System.in)) {
			do {
				chamarMenuPrincipal();
				switch (scanner.nextInt()) {
				case 1:
					espacamentoPadrao();
					File file = new File("alunos.txt");
					System.out.println(" Cadastro de Aluno");
					System.out.println(";------------------;");
					if (file.exists()) {
						try {
							cadastrarAluno();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						try {
							file.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					break;
				case 2:
					espacamentoPadrao();
					System.out.println(" Cadastro da Avaliacao");
					System.out.println(";------------------------------------------;");
					cadastrarAvaliacao();
					System.out.println(";------------------------------------------;");
					break;
				case 3:
					espacamentoPadrao();
					System.out.println(" Listagem de Histórico");
					System.out.println(";------------------------------------------------------------------------------------;");
					listarHistorico();
					System.out.println(";------------------------------------------------------------------------------------;");
					break;
				case 4:
					espacamentoPadrao();
					System.out.println(" Listagem de Alunos");
					System.out.println(";------------------------------------------;");
					listarAlunos();
					System.out.println(";------------------------------------------;");
					break;
				case 5:
					espacamentoPadrao();
					System.out.println("inalizado");
					loop = false;
					break;
				default:
					throw new IllegalArgumentException("A opcao inserida não existe");
				}
				try {
					System.out.printf("%nAguarde...%n");
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					System.err.format("IOException: %s%n", e);
				}
				espacamentoPadrao();
			} while (loop == true);
		}
	}

	public static void chamarMenuPrincipal() {
		List<String> menu = new ArrayList<String>();
		menu.add("Analise de Evolucao Fisica");
		menu.add(";------------------------------------------;");
		menu.add("Insira uma opcao:");
		menu.add("1. Cadastro de Aluno");
		menu.add("2. Cadastro de Avaliacao");
		menu.add("3. Historico");
		menu.add("4. Listagem de Alunos");
		menu.add("5. Finalizar");
		menu.add(";------------------------------------------;");

		for (String cadaLinha : menu) {
			System.out.println(cadaLinha);
		}
		menu.clear();

		System.out.print("> ");
	}

	public static void espacamentoPadrao() {
		System.out.printf("%n%n%n%n");
	}

	public static void cadastrarAluno() throws IOException {
		Aluno aluno = new Aluno();
		aluno.setId(proximoId());
		System.out.println("id = " + aluno.getId());
		Scanner scanner = new Scanner(System.in);
		System.out.println("Insira o nome do aluno");
		aluno.setNome(scanner.nextLine());

		System.out.println("Insira a data de nascimento (dd/mm/aaaa)");
		aluno.setDataNascimento(trataData(scanner));

		salvarAluno(aluno);
		System.out.println("Cadastro Concluido");
	}

	public static int proximoId() throws IOException {
		int maiorId = 0;

		BufferedReader bufferedReader = openReaderAluno();
		String linha = bufferedReader.readLine();
		while (linha != null) {
			String[] colunas = linha.split(";");
			maiorId = obtencaoDeMaiorID(maiorId, linha, colunas[0]);
			linha = bufferedReader.readLine();
		}
		bufferedReader.close();

		return ++maiorId;
	}

	private static int obtencaoDeMaiorID(int maiorId, String linha, String coluna) {
		try {
			var id = Integer.parseInt(coluna);
			if (id > maiorId) {
				maiorId = id;
			}
		} catch (NumberFormatException e) {
			System.err.println("falha ao fazer parse da linha: [" + linha + "], falha: [" + e.getMessage() + "]");
			e.printStackTrace();
		}
		return maiorId;
	}

	private static LocalDate trataData(Scanner scanner) {
		String buffer = scanner.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		formatter = formatter.withLocale(Locale.getDefault());
		LocalDate dataNascimento = LocalDate.parse(buffer, formatter);
		return dataNascimento;
	}

	private static void salvarAluno(Aluno aluno) {
		try {
			BufferedWriter bufferedWriter = openWriterAlunos();
			bufferedWriter.newLine();
			bufferedWriter.write(aluno.toString());
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void cadastrarAvaliacao() {
		Scanner scanner = new Scanner(System.in);
		Avaliacao avaliacao = new Avaliacao();
		carregarAlunos();
		extratorDeDados(scanner, avaliacao);

		System.out.println("Cadastro Concluido!");
	}

	private static void carregarAlunos() {
		try {
			BufferedReader bufferedReader = openReaderAluno();
			String linha = bufferedReader.readLine();
			while (linha != null) {
				Aluno aluno = parseAluno(linha);
				alunos.put(aluno.getId(), aluno);
				linha = bufferedReader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Aluno parseAluno(String linha) {
		String[] colunas = linha.split(";");
		Aluno aluno = new Aluno();
		aluno.setId(colunas[0]);
		aluno.setNome(colunas[1]);
		aluno.setDataNascimento(colunas[2]);
		return aluno;
	}

	private static void extratorDeDados(Scanner scanner, Avaliacao avaliacao) {
		Aluno aluno = new Aluno();
		int idAluno;

		while (true) {
			System.out.printf("%nInsira o ID do aluno: ");
			idAluno = scanner.nextInt();
			if (idAluno == -1) {
				return;
			} else if (!alunos.containsKey(idAluno)) {
				System.err.println("Id não encontrado. Insira -1 para encerrar ou tente novamente.");
			} else {
				break;
			}
		}

		avaliacao.setAluno(alunos.get(idAluno));

		System.out.printf("%nInsira o Peso do aluno: ");
		avaliacao.setPeso(scanner.nextDouble());

		System.out.printf("%nInsira a Gordura Corporal do aluno(em porcentagem por exemplo: 65,6%% -> 0,656): ");
		avaliacao.setGorduraCorporal(scanner.nextDouble());

		System.out.printf("%nInsira a Gordura Visceral do aluno(em porcentagem por exemplo: 5%% -> 0,05): ");
		avaliacao.setGorduraVisceral(scanner.nextDouble());

		System.out.printf("%nInsira a Idade Metabolica do aluno: ");
		avaliacao.setIdadeMetabolica(scanner.nextInt());

		salvarAvaliacao(aluno, avaliacao);
	}

	private static void salvarAvaliacao(Aluno aluno, Avaliacao avaliacao) {
		try {
			BufferedWriter bufferedWritter = openWriterAvaliacao();
			bufferedWritter.newLine();
			bufferedWritter.write(avaliacao.toString());
			bufferedWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void listarHistorico() {
		try {
			BufferedReader bufferedReader = openReaderAvaliacao();
			String linhas = bufferedReader.readLine();
			System.out.println("  ID\tPESO\tGORDURA CORPORAL\tGORDURA VISCERAL\tIDADE METABÓLICA");
			while (linhas != null) {
				String[] colunas = linhas.split(";");
				System.out.println(String.format("  %s\t%s\t%s\t\t\t%s\t\t\t%s", colunas[0], colunas[1], colunas[2], colunas[3], colunas[4]));
				linhas = bufferedReader.readLine();
			}
			
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void listarAlunos() {
		try {
			BufferedReader bufferedReader = openReaderAluno();
			String linhas = bufferedReader.readLine();
			System.out.println("  ID\tNOME\t\tDATA NASCIMENTO");
			while (linhas != null) {
				String[] colunas = linhas.split(";");
				System.out.println(String.format("  %s\t%s\t\t%s", colunas[0], colunas[1], colunas[2]));
				linhas = bufferedReader.readLine();
			}

			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static BufferedWriter openWriterAvaliacao() throws IOException {
		FileWriter escreveDados = new FileWriter("avaliacoes.txt", true);
		BufferedWriter bufferedWriter = new BufferedWriter(escreveDados);
		return bufferedWriter;
	}

	private static BufferedReader openReaderAvaliacao() throws FileNotFoundException {
		FileReader leitor = new FileReader("avaliacoes.txt");
		BufferedReader bufferedReader = new BufferedReader(leitor);
		return bufferedReader;
	}

	private static BufferedWriter openWriterAlunos() throws IOException {
		FileWriter escreveDados = new FileWriter("alunos.txt", true);
		BufferedWriter bufferedWriter = new BufferedWriter(escreveDados);
		return bufferedWriter;
	}

	private static BufferedReader openReaderAluno() throws FileNotFoundException {
		FileReader leitor = new FileReader("alunos.txt");
		BufferedReader bufferedReader = new BufferedReader(leitor);
		return bufferedReader;
	}
}