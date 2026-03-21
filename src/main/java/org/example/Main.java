package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.Cliente;
import org.example.model.Endereço;

import java.util.Scanner;


public class Main {
    static void main(String[] args) {
//        Scanner scann = new Scanner(System.in);
//
//        EntityManagerFactory sf = Persistence.createEntityManagerFactory("FrotaVeiculo");
//
//        EntityManager em = sf.createEntityManager();
//        em.getTransaction().begin();
//
//        Cliente cliente = new Cliente();
//
//        System.out.println("=====Dados de cadastro cliente=====\n");
//        System.out.println("Nome: ");
//        cliente.setNome(scann.nextLine());
//
//        System.out.println("telefone: ");
//        cliente.setTelefone(scann.nextLine());
//
//        Endereço endereco = new Endereço();
//
//        System.out.println("===Dados de cadastro do endereço do cliente===");
//        System.out.println("Rua: ");
//        endereco.setRua(scann.nextLine());
//
//        System.out.println("Cidade: ");
//        endereco.setCidade(scann.nextLine());
//
//        System.out.println("Estado: ");
//        endereco.setEstado(scann.nextLine());
//
//        System.out.println("CEP: ");
//        endereco.setCep(scann.nextLine());
//
//        cliente.setEndereço(endereco);
//
//        em.persist(endereco);
//        em.persist(cliente);
//
//        em.getTransaction().commit();
//        em.close();
    }
}
