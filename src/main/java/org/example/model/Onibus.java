package org.example.model;

import jakarta.persistence.*;
import org.example.model.Veiculo;

@Entity
public class Onibus extends Veiculo{

    private int passageiros;

    public Onibus(){}

    public Onibus(String placa, String cor, String renavan, int passageiros){
        super(placa, cor, renavan);
        this.passageiros = passageiros;
    }

    public int getPassageiros() {
        return passageiros;
    }

    public void setPassageiros(int passageiros) {
        this.passageiros = passageiros;
    }
}
