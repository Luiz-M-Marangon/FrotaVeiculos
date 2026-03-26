package org.example.UI.selects;

import javax.swing.*;
import java.awt.*;

public class SelecionarTipoVeiculo extends JDialog {

    private String tipoSelecionado;
    private Integer valorExtra;

    private JComboBox<String> tipoBox;
    private JTextField campoExtra;
    private JLabel labelExtra;

    public SelecionarTipoVeiculo(Frame parent) {
        super(parent, "Selecionar Tipo", true);

        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(3, 2, 5, 5));

        tipoBox = new JComboBox<>(new String[]{
                "Caminhão", "Carro", "Motocicleta", "Ônibus"
        });

        labelExtra = new JLabel("Valor:");
        campoExtra = new JTextField();

        JButton confirmar = new JButton("Confirmar");

        tipoBox.addActionListener(e -> atualizarLabel());

        confirmar.addActionListener(e -> confirmar());

        add(new JLabel("Tipo:"));
        add(tipoBox);
        add(labelExtra);
        add(campoExtra);
        add(new JLabel());
        add(confirmar);
    }

    private void atualizarLabel() {
        String tipo = (String) tipoBox.getSelectedItem();

        switch (tipo) {
            case "Caminhão":
                labelExtra.setText("Eixos:");
                break;
            case "Carro":
                labelExtra.setText("Cavalos:");
                break;
            case "Motocicleta":
                labelExtra.setText("Cilindradas:");
                break;
            case "Ônibus":
                labelExtra.setText("Passageiros:");
                break;
        }
    }

    private void confirmar() {
        try {
            tipoSelecionado = (String) tipoBox.getSelectedItem();
            valorExtra = Integer.parseInt(campoExtra.getText());
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Digite um número válido!");
        }
    }

    public String getTipoSelecionado() {
        return tipoSelecionado;
    }

    public Integer getValorExtra() {
        return valorExtra;
    }
}