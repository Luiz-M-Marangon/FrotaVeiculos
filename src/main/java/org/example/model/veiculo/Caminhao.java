package org.example.model.veiculo;

import jakarta.persistence.*;
import org.example.model.Veiculo;

@Entity
public class Caminhao extends Veiculo {

    public int eixos;

    public Caminhao(){}

    public Caminhao(String placa, String cor, String renavan, int eixos){
        super(placa, cor, renavan);
        this.eixos = eixos;
    }

    public int getEixos() {
        return eixos;
    }

    public void setEixos(int eixos) {
        this.eixos = eixos;
    }
}
