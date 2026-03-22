package org.example.UI;

import org.example.UI.selects.SelecionarEndereco;
import org.example.dao.ClienteDAO;
import org.example.model.Cliente;
import org.example.model.Endereco;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClientePanel extends JPanel {
    private JTextField nomeField;
    private JTextField telefoneField;
    private JLabel enderecoLabel;
    private Endereco enderecoSelecionado;

    private JTable tabela;
    private DefaultTableModel model;

    private ClienteDAO clienteDAO = new ClienteDAO();

    public ClientePanel() {

//        setLayout(new BorderLayout());
//
//        // FORM
//        JPanel form = new JPanel(new GridLayout(3, 2));
//
//        form.add(new JLabel("Nome:"));
//        nomeField = new JTextField();
//        form.add(nomeField);
//
//        form.add(new JLabel("Nome:"));
//        telefoneField = new JTextField();
//        form.add(telefoneField);
//
//        form.add(new JLabel("Endereço:"));
//
//        JPanel enderecoPanel = new JPanel(new BorderLayout());
//        enderecoLabel = new JLabel("Nenhum selecionado");
//
//        JButton selecionarBtn = new JButton("Selecionar");
//        selecionarBtn.addActionListener(e -> abrirSelecao());
//
//        enderecoPanel.add(enderecoLabel, BorderLayout.CENTER);
//        enderecoPanel.add(selecionarBtn, BorderLayout.EAST);
//
//        form.add(enderecoPanel);
//
//        JButton salvarBtn = new JButton("Salvar");
//        JButton atualizarBtn = new JButton("Atualizar");
//        JButton deletarBtn = new JButton("Deletar");
//
//        form.add(salvarBtn);
//        form.add(atualizarBtn);
//
//        add(form, BorderLayout.NORTH);
//
//        // TABELA
//        tabela = new JTable();
//        atualizarTabela();
//        add(new JScrollPane(tabela), BorderLayout.CENTER);
//
//        JPanel south = new JPanel();
//        south.add(deletarBtn);
//        add(south, BorderLayout.SOUTH);
//
//        // AÇÕES
//        salvarBtn.addActionListener(e -> salvar());
//        atualizarBtn.addActionListener(e -> atualizar());
//        deletarBtn.addActionListener(e -> deletar());
        setLayout(new BorderLayout());

        // ================= FORM =================
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        nomeField = new JTextField();
        telefoneField = new JTextField();
        enderecoLabel = new JLabel("Nenhum selecionado");

        form.add(criarLinha("Nome:", nomeField));
        form.add(criarLinha("Telefone:", telefoneField));
        form.add(criarLinhaEndereco());
        form.add(criarBotoes());

        add(form, BorderLayout.NORTH);

        // ================= TABELA =================
        model = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Telefone", "Endereço"}, 0
        );

        tabela = new JTable(model);

        //nao permitir edição na tebela principal do crud
        tabela.setRowSelectionAllowed(true);
        tabela.setColumnSelectionAllowed(false);
        tabela.setCellSelectionEnabled(false);
        tabela.setRowSelectionAllowed(true);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setDefaultEditor(Object.class, null); // 🔥 trava edição

        tabela.getSelectionModel().addListSelectionListener(e -> preencherCampos());

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        atualizarTabela();
    }

    // ================= UI HELPERS =================

    private JPanel criarLinha(String label, JTextField field) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        p.add(new JLabel(label), BorderLayout.WEST);
        p.add(field, BorderLayout.CENTER);

        return p;
    }

    private JPanel criarLinhaEndereco() {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        p.add(new JLabel("Endereço:"), BorderLayout.WEST);

        JPanel enderecoPanel = new JPanel(new BorderLayout());

        JButton selecionarBtn = new JButton("Selecionar");
        selecionarBtn.addActionListener(e -> abrirSelecao());

        enderecoPanel.add(enderecoLabel, BorderLayout.CENTER);
        enderecoPanel.add(selecionarBtn, BorderLayout.EAST);

        p.add(enderecoPanel, BorderLayout.CENTER);

        return p;
    }
    private JPanel criarBotoes() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton salvarBtn = new JButton("Salvar");
        JButton atualizarBtn = new JButton("Atualizar");
        JButton deletarBtn = new JButton("Deletar");

        salvarBtn.addActionListener(e -> salvar());
        atualizarBtn.addActionListener(e -> atualizar());
        deletarBtn.addActionListener(e -> deletar());

        p.add(salvarBtn);
        p.add(atualizarBtn);
        p.add(deletarBtn);

        return p;
        }

    private void abrirSelecao() {
        SelecionarEndereco dialog = new SelecionarEndereco((Frame) SwingUtilities.getWindowAncestor(this));

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
        if (enderecoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um endereço!");
            return;
        }

        Cliente c = new Cliente();
        c.setNome(nomeField.getText());
        c.setTelefone(telefoneField.getText());
        c.setEndereco(enderecoSelecionado);

        clienteDAO.salvar(c);
        atualizarTabela();
    }

    private void atualizar() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {

            if (enderecoSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um endereço!");
                return;
            }


            int id = (int) tabela.getValueAt(linha, 0);

            Cliente c = clienteDAO.consultar(id);
            c.setNome(nomeField.getText());
            c.setTelefone(telefoneField.getText());
            c.setEndereco(enderecoSelecionado);

            clienteDAO.atualizar(c);
            atualizarTabela();
        }
    }

    private void deletar() {
        int linha = tabela.getSelectedRow();

        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha!");
            return;
        }else {
            int id = (int) tabela.getValueAt(linha, 0);
            clienteDAO.deletar(id);
            atualizarTabela();
        }
//        if (linha > 0) {
//            int id = (int) tabela.getValueAt(linha, 0);
//            clienteDAO.deletar(id);
//            atualizarTabela();
//        }
    }

    private void atualizarTabela() {
        List<Cliente> lista = clienteDAO.listar();

        String[] col = {"ID", "Nome", "Telefone", "Endereço"};
        Object[][] dados = new Object[lista.size()][4];

        for (int i = 0; i < lista.size(); i++) {
            Cliente c = lista.get(i);
            dados[i][0] = c.getId();
            dados[i][1] = c.getNome();
            dados[i][2] = c.getTelefone();
            dados[i][3] = c.getEndereco();
        }

        tabela.setModel(new DefaultTableModel(dados, col));
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            nomeField.setText(tabela.getValueAt(linha, 1).toString());
            telefoneField.setText(tabela.getValueAt(linha, 2).toString());
        }
    }
}
