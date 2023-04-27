package org.example.application;
import java.io.IOException;
import java.util.Scanner;
import static org.example.entites.Formulario.*;
import static org.example.services.Lista.*;

public class Menu {
    public static void chamaMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        Integer opcao = 0;
        Integer numeroDeArquivos = 0;

        while (opcao != 7) {
            System.out.println("Selecione uma opção:");
            System.out.println("1 - Candidatar-se");
            System.out.println("2 - Adicionar pergunta");
            System.out.println("3 - Remover pergunta");
            System.out.println("4 - Listar formulários cadastrados");
            System.out.println("5 - Pesquisar formulários");
            System.out.println("6 - Validar formulário");
            System.out.println("7 - Sair");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarCandidato();
                case 2:
                    adicionarPergunta();
                    break;
                case 3:
                    removerPergunta();
                    break;
                case 4:
                    listarFormularios();
                    break;
                case 5:
                    System.out.println("Digite o nome do candidato:");
                    String nome = scanner.nextLine();
                    System.out.println("Digite o email do candidato:");
                    String email = scanner.nextLine();
                    pesquisarFormulario(nome, email);
                    break;
                case 6:
                    validarFormularios();
                    break;
                case 7:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente!");
            }
        }

    }
}
