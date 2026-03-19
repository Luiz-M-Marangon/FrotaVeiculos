package org.example.model.veiculo;

import jakarta.persistence.*;
import org.example.model.Veiculo;

@Entity
public class Motocicleta extends Veiculo {

    private int cilindradas;

    public Motocicleta(){}

    public Motocicleta(String placa, String cor, String renavan, int cilindradas){
        super(placa, cor, renavan);
        this.cilindradas = cilindradas;
    }

    public int getCilindradas() {
        return cilindradas;
    }

    public void setCilindradas(int cilindradas) {
        this.cilindradas = cilindradas;
    }
}
