package org.example.UI;

import javax.swing.*;

public class Tela {

    public Tela() {
        JFrame frame = new JFrame("Sistema de Frota");
        JTabbedPane abas = new JTabbedPane();

        abas.add("Cliente", new ClientePanel());
        abas.add("Motorista", new MotoristaPanel());
        abas.add("Endereço", new EnderecoPanel());
        abas.add("Veículo", new VeiculoPanel());
        abas.add("Viagem", new ViagemPanel());
        abas.add("Relatório", new RelatorioPanel());

        frame.add(abas);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}