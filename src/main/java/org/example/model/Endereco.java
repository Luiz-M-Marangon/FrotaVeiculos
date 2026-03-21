package org.example.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String rua;
    private String cidade;
    private String estado;
    private String cep;

    @OneToMany(mappedBy = "cliente")
    private List<Cliente> enderecos;

    public int getId() {
        return id;}
    public void setId(int id) {
        this.id = id;}
    public String getRua() {
        return rua;}
    public void setRua(String rua) {
        this.rua = rua;}
    public String getCidade() {
        return cidade;}
    public void setCidade(String cidade) {
        this.cidade = cidade;}
    public String getEstado() {
        return estado;}
    public void setEstado(String estado) {
        this.estado = estado;}
    public String getCep() {
        return cep;}
    public void setCep(String cep) {
        this.cep = cep;}
    public List<Cliente> getEnderecos() {
        return enderecos;}
    public void setEnderecos(List<Cliente> enderecos) {
        this.enderecos = enderecos;}
}
