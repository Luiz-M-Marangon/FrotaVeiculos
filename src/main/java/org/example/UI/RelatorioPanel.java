package org.example.UI;

import org.example.UI.selects.SelecionarMotorista;
import org.example.dao.ViagemDAO;
import org.example.model.Motorista;
import org.example.model.Viagem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RelatorioPanel extends JPanel {

    private JLabel motoristaLabel;
    private Motorista motoristaSelecionado;

    private JTable tabela;
    private DefaultTableModel model;

    private ViagemDAO viagemDAO = new ViagemDAO();

    public RelatorioPanel() {

        setLayout(new BorderLayout());

        // ================= TOPO =================
        JPanel top = new JPanel(new BorderLayout(5,5));
        top.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        motoristaLabel = new JLabel("Nenhum motorista selecionado");

        JButton selecionarBtn = new JButton("Selecionar Motorista");
        selecionarBtn.addActionListener(e -> abrirSelecao());

        JButton buscarBtn = new JButton("Buscar");
        buscarBtn.addActionListener(e -> buscar());

        top.add(motoristaLabel, BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        botoes.add(selecionarBtn);
        botoes.add(buscarBtn);

        top.add(botoes, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        // ================= TABELA =================
        model = new DefaultTableModel(
                new Object[]{"ID", "Origem", "Destino", "Cliente", "Veículo"}, 0
        );

        tabela = new JTable(model);

        // trava edição
        tabela.setDefaultEditor(Object.class, null);
        tabela.setRowSelectionAllowed(true);
        tabela.setColumnSelectionAllowed(false);
        tabela.setCellSelectionEnabled(false);

        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    // ================= SELEÇÃO =================
    private void abrirSelecao() {
        Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);

        SelecionarMotorista dialog = new SelecionarMotorista(frame);
        dialog.setVisible(true);

        motoristaSelecionado = dialog.getSelecionado();

        if (motoristaSelecionado != null) {
            motoristaLabel.setText("Motorista: " + motoristaSelecionado.getNome());
        }
    }

    // ================= BUSCA =================
    private void buscar() {

        if (motoristaSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um motorista!");
            return;
        }

        List<Viagem> lista = viagemDAO.listarPorMotorista(motoristaSelecionado.getId());

        // limpa tabela
        model.setRowCount(0);

        // preenche
        for (Viagem v : lista) {
            model.addRow(new Object[]{
                    v.getId(),
                    v.getOrigem(),
                    v.getDestino(),
                    v.getCliente(),
                    v.getVeiculo()
            });
        }

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma viagem encontrada!");
        }
    }
}