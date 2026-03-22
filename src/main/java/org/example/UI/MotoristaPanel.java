package org.example.UI;

import org.example.UI.selects.SelecionarEndereco;
import org.example.dao.MotoristaDAO;
import org.example.model.Motorista;
import org.example.model.Endereco;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MotoristaPanel extends JPanel {

    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField cnhField;

    private JLabel enderecoLabel;
    private Endereco enderecoSelecionado;

    private JTable tabela;
    private DefaultTableModel model;

    private MotoristaDAO motoristaDAO = new MotoristaDAO();

    public MotoristaPanel() {

        setLayout(new BorderLayout());

        // ================= FORM =================
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        nomeField = new JTextField();
        cpfField = new JTextField();
        cnhField = new JTextField();

        enderecoLabel = new JLabel("Nenhum selecionado");

        form.add(criarLinha("Nome:", nomeField));
        form.add(criarLinha("CPF:", cpfField));
        form.add(criarLinha("CNH:", cnhField));
        form.add(criarLinhaEndereco());
        form.add(criarBotoes());

        add(form, BorderLayout.NORTH);

        // ================= TABELA =================
        model = new DefaultTableModel(
                new Object[]{"ID", "Nome", "CPF", "CNH", "Endereço"}, 0
        );

        tabela = new JTable(model);

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

    // ================= AÇÕES =================

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
        if (enderecoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um endereço!");
            return;
        }

        Motorista m = new Motorista();

        m.setNome(nomeField.getText());
        m.setCpf(cpfField.getText());
        m.setCnh(cnhField.getText());
        m.setEndereco(enderecoSelecionado);

        motoristaDAO.salvar(m);
        atualizarTabela();
        limparCampos();
    }

    private void atualizar() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {

            if (enderecoSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um endereço!");
                return;
            }

            int id = (int) tabela.getValueAt(linha, 0);

            Motorista m = motoristaDAO.consultar(id);

            m.setNome(nomeField.getText());
            m.setCpf(cpfField.getText());
            m.setCnh(cnhField.getText());
            m.setEndereco(enderecoSelecionado);

            motoristaDAO.atualizar(m);
            atualizarTabela();
            limparCampos();
        }
    }

    private void deletar() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            int id = (int) tabela.getValueAt(linha, 0);

            motoristaDAO.deletar(id);
            atualizarTabela();
            limparCampos();
        }
    }

    // ================= TABELA =================

    private void atualizarTabela() {
        List<Motorista> lista = motoristaDAO.listar();

        model.setRowCount(0);

        for (Motorista m : lista) {
            model.addRow(new Object[]{
                    m.getId(),
                    m.getNome(),
                    m.getCpf(),
                    m.getCnh(),
                    m.getEndereco()
            });
        }
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            nomeField.setText(tabela.getValueAt(linha, 1).toString());
            cpfField.setText(tabela.getValueAt(linha, 2).toString());
            cnhField.setText(tabela.getValueAt(linha, 3).toString());
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        cpfField.setText("");
        cnhField.setText("");
        enderecoLabel.setText("Nenhum selecionado");
        enderecoSelecionado = null;
    }
}