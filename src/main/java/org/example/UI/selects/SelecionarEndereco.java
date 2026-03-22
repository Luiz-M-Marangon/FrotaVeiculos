package org.example.UI.selects;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import org.example.dao.EnderecoDAO;
import org.example.model.Endereco;

public class SelecionarEndereco extends JDialog {
    private JTable tabela;
    private Endereco enderecoSelecionado;
    private EnderecoDAO dao = new EnderecoDAO();

    public SelecionarEndereco(Frame parent) {
        super(parent, "Selecionar Endereço", true);

        setSize(500, 300);
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
        List<Endereco> lista = dao.listar();

        String[] col = {"ID", "Rua", "Cidade", "CEP", "UF"};
        Object[][] dados = new Object[lista.size()][5];

        for (int i = 0; i < lista.size(); i++) {
            Endereco e = lista.get(i);
            dados[i][0] = e.getId();
            dados[i][1] = e.getRua();
            dados[i][2] = e.getCidade();
            dados[i][3] = e.getCep();
            dados[i][4] = e.getEstado();
        }

        tabela.setModel(new DefaultTableModel(dados, col));
    }

    private void selecionar() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            int id = (int) tabela.getValueAt(linha, 0);
            enderecoSelecionado = dao.consultar(id);
            dispose();
        }
    }

    public Endereco getEnderecoSelecionado() {
        return enderecoSelecionado;
    }

}
