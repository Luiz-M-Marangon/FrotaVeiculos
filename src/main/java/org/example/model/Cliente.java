package org.example.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nome;
    private String telefone;

//    @OneToMany(mappedBy = "cliente")
//    private List<Endereco> endereco;
    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;


    @OneToMany(mappedBy = "cliente")
    private List<Viagem> viagens;

    public void setId(int id) {
        this.id = id;}
    public int getId() {
        return id;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

//    public List<Endereco> getEndereco() {return endereco;}
//    public void setEndereco(List<Endereco> endereco) {this.endereco = endereco;}


    public Endereco getEndereco() {
        return endereco;}
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;}

    public List<Viagem> getViagens() {
        return viagens;
    }

    public void setViagens(List<Viagem> viagens) {
        this.viagens = viagens;
    }

    @Override
    public String toString() {
        return nome;}
}
