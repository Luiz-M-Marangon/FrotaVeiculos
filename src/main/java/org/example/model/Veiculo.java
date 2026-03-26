package org.example.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String placa;
    private String cor;
    private String renavam;
    private String tipo;
    private Integer valorExtra;

    @OneToMany(mappedBy = "veiculo")
    private List<Viagem> viagens;

    public Veiculo(){}

    public Veiculo(String placa, String cor, String renavan){
        this.placa = placa;
        this.cor = cor;
        this.renavam = renavan;
    }

    public int getId() {
        return id;}
    public void setId(int id) {
        this.id = id;}
    public String getPlaca() {
        return placa;}
    public void setPlaca(String placa) {
        this.placa = placa;}
    public String getCor() {
        return cor;}
    public void setCor(String cor) {
        this.cor = cor;}
    public String getRenavam() {
        return renavam;}
    public void setRenavam(String renavan) {
        this.renavam = renavan;}
    public List<Viagem> getViagens() {
        return viagens;}
    public void setViagens(List<Viagem> viagens) {
        this.viagens = viagens;}
    public String getTipo() {
        return tipo;}
    public void setTipo(String tipo) {
        this.tipo = tipo;}
    public Integer getValorExtra() {
        return valorExtra;}
    public void setValorExtra(Integer valorExtra) {
        this.valorExtra = valorExtra;}

    @Override
    public String toString() {
        return placa;}
}
