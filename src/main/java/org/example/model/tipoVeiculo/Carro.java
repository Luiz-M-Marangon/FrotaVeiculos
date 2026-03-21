package org.example.model.tipoVeiculo;

import jakarta.persistence.*;
import org.example.model.Veiculo;

@Entity
public class Carro extends Veiculo {

    private int quantidadePortas;

    public Carro(){}

    public Carro(String placa, String cor, String renavan, int quantidadePortas){
        super(placa, cor, renavan);
        this.quantidadePortas = quantidadePortas;
    }

    public int getQuantidadePortas() {
        return quantidadePortas;
    }

    public void setQuantidadePortas(int quantidadePortas) {
        this.quantidadePortas = quantidadePortas;
    }
}
