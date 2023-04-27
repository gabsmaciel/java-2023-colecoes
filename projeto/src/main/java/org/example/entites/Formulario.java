package org.example.entites;

import org.example.application.Menu;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Formulario {
    public static final Path FORMULARIO;

    static {
        FORMULARIO = Paths.get("C:\\workspace\\java-formulario-colecoes\\projeto\\src\\main\\resources\\formulario.txt");
    }

    public static void cadastrarCandidato() throws IOException {
        Scanner inputNome = new Scanner(System.in);
        Candidato candidato = new Candidato();
        System.out.println("Digite o nome do candidato:");
        String nomeCandidato = inputNome.nextLine();
        candidato.setNome(nomeCandidato);
        String nomeArquivo = nomeCandidato + ".txt";
        FileWriter salvaFormulario = new FileWriter(nomeArquivo);
        Scanner lerPergunta = new Scanner(new FileReader(FORMULARIO.toFile()));
        Scanner inputRespostas = new Scanner(System.in);

        while (lerPergunta.hasNext()) {
            String perguntaNome = lerPergunta.nextLine();
            System.out.println(perguntaNome);
            String respostaNome = inputRespostas.nextLine();
            candidato.setNome(respostaNome);
            String perguntaEmail = lerPergunta.nextLine();
            System.out.println(perguntaEmail);
            String respostaEmail = inputRespostas.nextLine();
            candidato.setEmail(respostaEmail);
            String perguntaIdade = lerPergunta.nextLine();
            System.out.println(perguntaIdade);
            int respostaIdade = Integer.parseInt(inputRespostas.nextLine());
            candidato.setIdade(respostaIdade);
            String perguntaTelefone = lerPergunta.nextLine();
            System.out.println(perguntaTelefone);
            String respostaTelefone = inputRespostas.nextLine();
            candidato.setTelefone(respostaTelefone);
            if (respostaIdade < 16) {
                candidato.setIdade(-1);
                System.out.println("Obrigado pelo interesse mas é necessário ter mais de 16 anos.");
                lerPergunta.close();
                salvaFormulario.close();
                return;
            }
            if (candidato.getIdade() >= 16) {
                System.out.println("Cadastro realizado com sucesso:");
                System.out.println(candidato);
                salvaFormulario.write(perguntaNome + "\n" + candidato.getNome() + "\n");
                salvaFormulario.write(perguntaEmail + "\n" + candidato.getEmail() + "\n");
                salvaFormulario.write(perguntaIdade + "\n" + candidato.getIdade() + "\n");
                salvaFormulario.write(perguntaTelefone + "\n" + candidato.getTelefone() + "\n");
                salvaFormulario.flush();
                break;
            }
        }

        Candidato.adicionaCandidato(candidato);
        lerPergunta.close();
        salvaFormulario.close();
        Menu.chamaMenu();
    }

    public static void adicionarPergunta() throws IOException {
        System.out.println("Insira uma pergunta no formulário:");
        Scanner scanner = new Scanner(System.in);
        String pergunta = scanner.nextLine();
        BufferedReader br = Files.newBufferedReader(FORMULARIO);
        String linha;
        while ((linha = br.readLine()) != null) {
            String[] partes = linha.split("\\|");
            if (partes.length > 1 && partes[1].equals(pergunta)) {
                System.out.println("Esta pergunta já existe no formulário.");
                return;
            }
        }
        br.close();
        int linhas = 0;
        br = Files.newBufferedReader(FORMULARIO);
        while (br.readLine() != null) {
            linhas++;
        }
        PrintWriter out = new PrintWriter(new FileWriter(FORMULARIO.toFile(), true));
        out.append(System.getProperty("line.separator")).append("P").
                append(String.valueOf(linhas + 1)).append("|").append(pergunta);
        System.out.println("Pergunta inserida: ");
        out.close();
        br.close();
    }

    public static void removerPergunta() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insira o número da pergunta que deseja remover:");
        int numeroPergunta = scanner.nextInt();
        scanner.nextLine();
        List<String> perguntas = Files.readAllLines(FORMULARIO);
        boolean encontrouPergunta = false;
        for (int i = 0; i < perguntas.size(); i++) {
            String pergunta = perguntas.get(i);
            if (pergunta.startsWith("P" + numeroPergunta)) {
                perguntas.remove(i);
                encontrouPergunta = true;
                break;
            }

        }
        if (!encontrouPergunta) {
            System.out.println("Pergunta não encontrada");
            return;
        }
        if (numeroPergunta < 5) {
            System.out.println("As perguntas de 1 a 4 não podem ser removidas.");
            return;
        }

        for (int i = 0; i < perguntas.size(); i++) {}
        PrintWriter out = new PrintWriter(new FileWriter(FORMULARIO.toFile()));
        for (String pergunta : perguntas) {
            out.println(pergunta);
        }
        out.close();
        System.out.println("Pergunta removida com sucesso");
    }

    public static void listarFormularios() throws IOException {
        Path diretorio = Paths.get("C:\\workspace\\java-formulario-colecoes\\projeto");
        Files.list(diretorio)
                .filter(Files::isRegularFile)
                .forEach(arquivo -> System.out.println(arquivo.getFileName()));
    }

    public static void validarFormularios() throws IOException {
        Path diretorio = Paths.get("C:\\workspace\\java-formulario-colecoes\\projeto");
        Map<String, String> formularioPorEmail = new HashMap<>();
        List<String> duplicados = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(diretorio)) {
            for (Path arquivo : stream) {
                if (Files.isRegularFile(arquivo)) {
                    try (BufferedReader reader = Files.newBufferedReader(arquivo)) {
                        String linha;
                        while ((linha = reader.readLine()) != null) {
                            String[] dados = linha.split(":");
                            if (dados[0].equals("Nome") || dados[0].equals("Email")) {
                                String campo = dados[0];
                                String valor = dados[1];
                                if (campo.equals("Email")) {
                                    if (formularioPorEmail.containsKey(valor)) {
                                        duplicados.add(formularioPorEmail.get(valor));
                                        duplicados.add(arquivo.getFileName().toString());
                                    } else {
                                        formularioPorEmail.put(valor, arquivo.getFileName().toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (duplicados.isEmpty()) {
            System.out.println("Não foram encontrados formulários duplicados.");
        } else {
            System.out.println("Formulários duplicados encontrados:");
            for (int i = 0; i < duplicados.size(); i += 2) {
                System.out.println("Nome: " + duplicados.get(i) + ", Email: " + duplicados.get(i + 1));
            }
        }
    }
}