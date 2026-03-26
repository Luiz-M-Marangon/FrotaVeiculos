package org.example.UI.selects;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import org.example.dao.VeiculoDAO;
import org.example.model.Veiculo;

public class SelecionarVeiculo extends JDialog {

    private JTable tabela;
    private Veiculo selecionado;
    private VeiculoDAO dao = new VeiculoDAO();

    public SelecionarVeiculo(Frame parent) {
        super(parent, "Selecionar Veículo", true);

        setSize(400, 300);
        setLocationRelativeTo(parent);

        tabela = new JTable();
        carregarTabela();

        tabela.setRowSelectionAllowed(true);
        tabela.setColumnSelectionAllowed(false);    //  Não permite que seja editado diretamente nas tabelas
        tabela.setCellSelectionEnabled(false);      //  Não permite que seja editado diretamente nas tabelas
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setDefaultEditor(Object.class, null);

        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2 && tabela.getSelectedRow() != -1) {
                    selecionar();
                }
            }
        });

        JButton selecionarBtn = new JButton("Selecionar");
        selecionarBtn.addActionListener(e -> selecionar());

        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(selecionarBtn, BorderLayout.SOUTH);
    }

    private void carregarTabela() {
        List<Veiculo> lista = dao.listar();

        String[] col = {"ID", "Placa", "Cor", "Renavam"};
        Object[][] dados = new Object[lista.size()][4];

        for (int i = 0; i < lista.size(); i++) {
            Veiculo v = lista.get(i);
            dados[i][0] = v.getId();
            dados[i][1] = v.getPlaca();
            dados[i][2] = v.getCor();
            dados[i][3] = v.getRenavam();
        }

        tabela.setModel(new DefaultTableModel(dados, col));
    }

    private void selecionar() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            int id = (int) tabela.getValueAt(linha, 0);
            selecionado = dao.consultar(id);
            dispose();
        }
    }

    public Veiculo getSelecionado() {
        return selecionado;
    }
}