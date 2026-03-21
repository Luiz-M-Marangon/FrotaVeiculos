package org.example.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Motorista {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nome;
    private String cpf;
    private String cnh;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToMany(mappedBy = "motorista")
    private List<Viagem> viagens;

    public int getId() {
        return id;}
    public void setId(int id) {
        this.id = id;}
    public String getNome() {
        return nome;}
    public void setNome(String nome) {
        this.nome = nome;}
    public String getCpf() {
        return cpf;}
    public void setCpf(String cpf) {
        this.cpf = cpf;}
    public String getCnh() {
        return cnh;}
    public void setCnh(String cnh) {
        this.cnh = cnh;}
    public Endereco getEndereço() {
        return endereco;}
    public void setEndereço(Endereco endereco) {
        this.endereco = endereco;}
    public List<Viagem> getViagens() {
        return viagens;}
    public void setViagens(List<Viagem> viagens) {
        this.viagens = viagens;}
}
