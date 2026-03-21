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
    private String renavan;

    @OneToMany(mappedBy = "veiculo")
    private List<Viagem> viagens;

    public Veiculo(){}

    public Veiculo(String placa, String cor, String renavan){
        this.placa = placa;
        this.cor = cor;
        this.renavan = renavan;
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
    public String getRenavan() {
        return renavan;}
    public void setRenavan(String renavan) {
        this.renavan = renavan;}
    public List<Viagem> getViagens() {
        return viagens;}
    public void setViagens(List<Viagem> viagens) {
        this.viagens = viagens;}
}
