package org.example.UI;

import org.example.dao.VeiculoDAO;
import org.example.model.Veiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VeiculoPanel extends JPanel {

    private JTextField placaField;
    private JTextField corField;
    private JTextField renavamField;

    private JTable tabela;
    private DefaultTableModel model;

    private VeiculoDAO veiculoDAO = new VeiculoDAO();

    public VeiculoPanel() {

        setLayout(new BorderLayout());

        // ================= FORM =================
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        placaField = new JTextField();
        corField = new JTextField();
        renavamField = new JTextField();

        form.add(criarLinha("Placa:", placaField));
        form.add(criarLinha("Cor:", corField));
        form.add(criarLinha("Renavam:", renavamField));
        form.add(criarBotoes());

        add(form, BorderLayout.NORTH);

        // ================= TABELA =================
        model = new DefaultTableModel(
                new Object[]{"ID", "Placa", "Cor", "Renavam"}, 0
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

    // ================= CRUD =================

    private void salvar() {
        Veiculo v = new Veiculo();

        v.setPlaca(placaField.getText());
        v.setCor(corField.getText());
        v.setRenavam(renavamField.getText());

        veiculoDAO.salvar(v);
        atualizarTabela();
        limparCampos();
        JOptionPane.showMessageDialog(this, "Veiculo salvo com sucesso!");
    }

    private void atualizar() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            int id = (int) tabela.getValueAt(linha, 0);

            Veiculo v = veiculoDAO.consultar(id);

            v.setPlaca(placaField.getText());
            v.setCor(corField.getText());
            v.setRenavam(renavamField.getText());

            veiculoDAO.atualizar(v);
            atualizarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Veiculo atualizado com sucesso!");
        }
    }

    private void deletar() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            int id = (int) tabela.getValueAt(linha, 0);

            veiculoDAO.deletar(id);
            atualizarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Veiculo deletado com sucesso!");
        }
    }

    // ================= TABELA =================

    private void atualizarTabela() {
        List<Veiculo> lista = veiculoDAO.listar();

        model.setRowCount(0);

        for (Veiculo v : lista) {
            model.addRow(new Object[]{
                    v.getId(),
                    v.getPlaca(),
                    v.getCor(),
                    v.getRenavam()
            });
        }
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            placaField.setText(tabela.getValueAt(linha, 1).toString());
            corField.setText(tabela.getValueAt(linha, 2).toString());
            renavamField.setText(tabela.getValueAt(linha, 3).toString());
        }
    }

    private void limparCampos() {
        placaField.setText("");
        corField.setText("");
        renavamField.setText("");
    }
}