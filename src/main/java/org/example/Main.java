package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.bd.Cliente;

import java.util.Scanner;


public class Main {
    static void main(String[] args) {
        Scanner scann = new Scanner(System.in);

        EntityManagerFactory sf = Persistence.createEntityManagerFactory("FrotaVeiculo");

        EntityManager em = sf.createEntityManager();
        em.getTransaction().begin();

        Cliente cliente = new Cliente();

        System.out.println("=====Dados de cadastro cliente=====\n");
        System.out.println("Nome: ");
        cliente.setNome(scann.nextLine());
    }
}
