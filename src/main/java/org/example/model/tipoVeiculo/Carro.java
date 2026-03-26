package org.example.model.tipoVeiculo;

import jakarta.persistence.*;
import org.example.model.Veiculo;

@Entity
public class Carro extends Veiculo {

    private int cavalos;

    public Carro(){}

    public Carro(String placa, String cor, String renavan, int cavalos){
        super(placa, cor, renavan);
        this.cavalos = cavalos;
    }

    public int getCavalos() {
        return cavalos;}

    public void setCavalos(int cavalos) {
        this.cavalos = cavalos;}

}
