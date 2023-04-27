package org.example.services;
import org.example.entites.Candidato;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Lista {

    public static  File pesquisarFormulario(String nome, String email) {
        File folder = new File("C:\\workspace\\java-formulario-colecoes\\projeto\\src\\main\\resources");
        File[] arquivos = folder.listFiles(file -> file.isFile() && file.getName().endsWith(".txt"));
        for (File arquivo : arquivos) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                Candidato candidato = (Candidato) ois.readObject();
                if (candidato.getNome().equals(nome) && candidato.getEmail().equals(email)) {
                    return arquivo;
                }
            } catch (StreamCorruptedException e) {
                System.err.println("O arquivo " + arquivo.getName() + " está corrompido ou malformatado.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Formulário não encontrado para o candidato " + nome + " (" + email + ")");
        return null;
    }
    public static List<Candidato> carregarCandidatos() {
        List<Candidato> candidatos = new ArrayList<>();
        File diretorio = new File("C:\\workspace\\java-formulario-colecoes\\projeto\\src\\main\\resources");

        if (diretorio.exists() && diretorio.isDirectory()) {
            File[] arquivos = diretorio.listFiles((dir, nome) -> nome.endsWith(".txt"));

            for (File arquivo : arquivos) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                    Candidato candidato = (Candidato) ois.readObject();
                    candidatos.add(candidato);
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Erro ao ler arquivo: " + e.getMessage());
                }
            }
        }
        return candidatos;
    }
}