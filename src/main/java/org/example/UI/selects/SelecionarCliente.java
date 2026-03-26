package org.example.UI.selects;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import org.example.dao.ClienteDAO;
import org.example.model.Cliente;

public class SelecionarCliente extends JDialog {

    private JTable tabela;
    private Cliente selecionado;
    private ClienteDAO dao = new ClienteDAO();

    public SelecionarCliente(Frame parent) {
        super(parent, "Selecionar Cliente", true);

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
        List<Cliente> lista = dao.listar();

        String[] col = {"ID", "Nome", "Telefone"};
        Object[][] dados = new Object[lista.size()][3];

        for (int i = 0; i < lista.size(); i++) {
            Cliente c = lista.get(i);
            dados[i][0] = c.getId();
            dados[i][1] = c.getNome();
            dados[i][2] = c.getTelefone();
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

    public Cliente getSelecionado() {
        return selecionado;
    }
}