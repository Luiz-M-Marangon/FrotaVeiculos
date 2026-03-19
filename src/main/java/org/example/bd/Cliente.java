package org.example.bd;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nome;
    private String telefone;

    @OneToMany(mappedBy = "cliente")
    private List<Viagem> viagens;

    public void setId(int id) {
        this.id = id;}
    public int getId() {
        return id;}
}
