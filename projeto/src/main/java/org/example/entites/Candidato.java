package org.example.entites;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Candidato implements Serializable {
    private String nome;
    private String email;
    private String telefone;
    private Integer idade;
    private int id;
    private File formulario;

    public Candidato() {
    }

    public Candidato(String nome, String email, String telefone, Integer idade, int id, File formulario) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.idade = idade;
        this.id = id;
        this.formulario = formulario;
    }

    @Override
    public String toString() {
        return "Candidato{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", idade=" + idade +
                ", id=" + id +
                ", formulario=" + formulario +
                '}';
    }

    public static void adicionaCandidato(Candidato candidato) {
        // implementação do método para adicionar candidato
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public File getFormulario() {
        return formulario;
    }

    public void setFormulario(File formulario) {
        this.formulario = formulario;
    }

    public static void salvarCandidato(Candidato candidato) {
        String nomeArquivo = candidato.getNome() + ".txt";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(candidato);
        } catch (IOException e) {
            System.out.println("Erro ao salvar candidato: " + e.getMessage());
        }
    }
}