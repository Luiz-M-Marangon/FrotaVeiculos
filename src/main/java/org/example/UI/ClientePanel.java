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
        setLayout(new BorderLayout());

        // formulario
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

        // cria a tabela
        model = new DefaultTableModel(new Object[]{"ID", "Nome", "Telefone", "Endereço"}, 0);

        tabela = new JTable(model);

        //nao permitir edição na tebela principal do crud
        tabela.setRowSelectionAllowed(true);
        tabela.setColumnSelectionAllowed(false); // trava selecao de coluna
        tabela.setCellSelectionEnabled(false); // trava a edicao de celula
        tabela.setRowSelectionAllowed(true);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setDefaultEditor(Object.class, null); //

        tabela.getSelectionModel().addListSelectionListener(e -> preencherCampos());

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        atualizarTabela();
    }


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
            enderecoLabel.setText(enderecoSelecionado.getRua() + " - " + enderecoSelecionado.getCidade());
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
        limparCampos();
        JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso!");
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
            limparCampos();
            JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
        }
    }


    private void deletar() {
        int linha = tabela.getSelectedRow();

        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha!");
            return;
        }

        int id = (int) tabela.getValueAt(linha, 0);
        try {
            clienteDAO.deletar(id);
            atualizarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Cliente deletado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Não é possível excluir o cliente.\n" + "Existem viagens vinculadas a ele.");
        }
    }

    // cricao de uma nova tabela atualizada
    private void atualizarTabela() {
        List<Cliente> lista = clienteDAO.listar();

        model.setRowCount(0); // limpa a tabela existente

        for (Cliente c : lista) {
            model.addRow(new Object[]{
                c.getId(),
                c.getNome(),
                c.getTelefone(),
                c.getEndereco()
            });
        }
    }


    // deixa os campos atualizados de acordo com a linha selecionada na tabela
    private void preencherCampos() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            nomeField.setText(tabela.getValueAt(linha, 1).toString());
            telefoneField.setText(tabela.getValueAt(linha, 2).toString());
        }
    }

    // limpa os campos apos qualquer atualização
    private void limparCampos() {
        nomeField.setText("");
        nomeField.setText("");
        enderecoLabel.setText("Nenhum selecionado");
        enderecoSelecionado = null;
    }
}
