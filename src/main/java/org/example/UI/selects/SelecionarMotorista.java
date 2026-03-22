package org.example.UI.selects;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import org.example.dao.MotoristaDAO;
import org.example.model.Motorista;

public class SelecionarMotorista extends JDialog {

    private JTable tabela;
    private Motorista selecionado;
    private MotoristaDAO dao = new MotoristaDAO();

    public SelecionarMotorista(Frame parent) {
        super(parent, "Selecionar Motorista", true);

        setSize(400, 300);
        setLocationRelativeTo(parent);

        tabela = new JTable();
        carregarTabela();

        tabela.setRowSelectionAllowed(true);
        tabela.setColumnSelectionAllowed(false);
        tabela.setCellSelectionEnabled(false);
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
        List<Motorista> lista = dao.listar();

        String[] col = {"ID", "Nome", "CPF", "CNH"};
        Object[][] dados = new Object[lista.size()][4];

        for (int i = 0; i < lista.size(); i++) {
            Motorista m = lista.get(i);
            dados[i][0] = m.getId();
            dados[i][1] = m.getNome();
            dados[i][2] = m.getCpf();
            dados[i][3] = m.getCnh();
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

    public Motorista getSelecionado() {
        return selecionado;
    }
}