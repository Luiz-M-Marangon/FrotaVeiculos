package org.example.UI;

import org.example.dao.ClienteDAO;
import org.example.model.Cliente;
import org.example.model.Endereco;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClientePanel extends JPanel {
    private JTextField nomeField;
    private JLabel enderecoLabel;
    private Endereco enderecoSelecionado;

    private JTable tabela;

    private ClienteDAO clienteDAO = new ClienteDAO();

    public ClientePanel() {

        setLayout(new BorderLayout());

        // FORM
        JPanel form = new JPanel(new GridLayout(3, 2));

        form.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        form.add(nomeField);

        form.add(new JLabel("Endereço:"));

        JPanel enderecoPanel = new JPanel(new BorderLayout());
        enderecoLabel = new JLabel("Nenhum selecionado");

        JButton selecionarBtn = new JButton("Selecionar");
        selecionarBtn.addActionListener(e -> abrirSelecao());

        enderecoPanel.add(enderecoLabel, BorderLayout.CENTER);
        enderecoPanel.add(selecionarBtn, BorderLayout.EAST);

        form.add(enderecoPanel);

        JButton salvarBtn = new JButton("Salvar");
        JButton atualizarBtn = new JButton("Atualizar");
        JButton deletarBtn = new JButton("Deletar");

        form.add(salvarBtn);
        form.add(atualizarBtn);

        add(form, BorderLayout.NORTH);

        // TABELA
        tabela = new JTable();
        atualizarTabela();
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel south = new JPanel();
        south.add(deletarBtn);
        add(south, BorderLayout.SOUTH);

        // AÇÕES
        salvarBtn.addActionListener(e -> salvar());
        atualizarBtn.addActionListener(e -> atualizar());
        deletarBtn.addActionListener(e -> deletar());
    }

    private void abrirSelecao() {
        SelecionarEndereco dialog =
                new SelecionarEndereco((Frame) SwingUtilities.getWindowAncestor(this));

        dialog.setVisible(true);

        enderecoSelecionado = dialog.getEnderecoSelecionado();

        if (enderecoSelecionado != null) {
            enderecoLabel.setText(
                    enderecoSelecionado.getRua() + " - " +
                            enderecoSelecionado.getCidade()
            );
        }
    }

    private void salvar() {
        Cliente c = new Cliente();
        c.setNome(nomeField.getText());
        c.setEndereco(enderecoSelecionado);

        clienteDAO.salvar(c);
        atualizarTabela();
    }

    private void atualizar() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            int id = (int) tabela.getValueAt(linha, 0);

            Cliente c = clienteDAO.consultar(id);
            c.setNome(nomeField.getText());
            c.setEndereco(enderecoSelecionado);

            clienteDAO.atualizar(c);
            atualizarTabela();
        }
    }

    private void deletar() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            int id = (int) tabela.getValueAt(linha, 0);
            clienteDAO.deletar(id);
            atualizarTabela();
        }
    }

    private void atualizarTabela() {
        List<Cliente> lista = clienteDAO.listar();

        String[] col = {"ID", "Nome", "Endereço"};
        Object[][] dados = new Object[lista.size()][3];

        for (int i = 0; i < lista.size(); i++) {
            Cliente c = lista.get(i);
            dados[i][0] = c.getId();
            dados[i][1] = c.getNome();
            dados[i][2] = c.getEndereco();
        }

        tabela.setModel(new DefaultTableModel(dados, col));
    }
}
