package org.example.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Viagem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String origem;
    private String destino;
    private LocalDateTime dataViagem;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;


    public int getId() {
        return id;}
    public void setId(int id) {
        this.id = id;}
    public String getOrigem() {
        return origem;}

    public void setOrigem(String origem) {
        this.origem = origem;}
    public String getDestino() {
        return destino;}
    public void setDestino(String destino) {
        this.destino = destino;}
    public LocalDateTime getDataViagem() {
        return dataViagem;}
    public void setDataViagem(LocalDateTime dataViagem) {
        this.dataViagem = dataViagem;}
    public Motorista getMotorista() {
        return motorista;}
    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;}
    public Veiculo getVeiculo() {
        return veiculo;}
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;}
    public Cliente getCliente() {
        return cliente;}
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;}
}
