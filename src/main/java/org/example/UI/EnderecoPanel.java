package org.example.UI;

import org.example.dao.EnderecoDAO;
import org.example.model.Endereco;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EnderecoPanel extends JPanel {

    private JTextField ruaField;
    private JTextField cidadeField;
    private JTextField estadoField;
    private JTextField cepField;

    private JTable tabela;
    private DefaultTableModel model;

    private EnderecoDAO enderecoDAO = new EnderecoDAO();

    public EnderecoPanel() {

        setLayout(new BorderLayout());

        // ================= FORM =================
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        ruaField = new JTextField();
        cidadeField = new JTextField();
        estadoField = new JTextField();
        cepField = new JTextField();

        form.add(criarLinha("Rua:", ruaField));
        form.add(criarLinha("Cidade:", cidadeField));
        form.add(criarLinha("Estado:", estadoField));
        form.add(criarLinha("CEP:", cepField));

        form.add(criarBotoes());

        add(form, BorderLayout.NORTH);

        // ================= TABELA =================
        model = new DefaultTableModel(
                new Object[]{"ID", "Rua", "Cidade", "Estado", "CEP"}, 0
        );

        tabela = new JTable(model);

        tabela.setRowSelectionAllowed(true);
        tabela.setColumnSelectionAllowed(false);
        tabela.setCellSelectionEnabled(false);
        tabela.setRowSelectionAllowed(true);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setDefaultEditor(Object.class, null);

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
        Endereco e = new Endereco();

        e.setRua(ruaField.getText());
        e.setCidade(cidadeField.getText());
        e.setEstado(estadoField.getText());
        e.setCep(cepField.getText());

        enderecoDAO.salvar(e);
        atualizarTabela();
        limparCampos();
        JOptionPane.showMessageDialog(this, "Endereço salvo com sucesso!");
    }

    private void atualizar() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            int id = (int) tabela.getValueAt(linha, 0);

            Endereco e = enderecoDAO.consultar(id);

            e.setRua(ruaField.getText());
            e.setCidade(cidadeField.getText());
            e.setEstado(estadoField.getText());
            e.setCep(cepField.getText());

            enderecoDAO.atualizar(e);
            atualizarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Endereço atualizado com sucesso!");
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
            enderecoDAO.deletar(id);
            atualizarTabela();
            limparCampos();
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Endereço deletado com sucesso!");
        }
    }

    // ================= TABELA =================

    private void atualizarTabela() {
        List<Endereco> lista = enderecoDAO.listar();

        model.setRowCount(0);

        for (Endereco e : lista) {
            model.addRow(new Object[]{
                    e.getId(),
                    e.getRua(),
                    e.getCidade(),
                    e.getEstado(),
                    e.getCep()
            });
        }
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            ruaField.setText(tabela.getValueAt(linha, 1).toString());
            cidadeField.setText(tabela.getValueAt(linha, 2).toString());
            estadoField.setText(tabela.getValueAt(linha, 3).toString());
            cepField.setText(tabela.getValueAt(linha, 4).toString());
        }
    }

    private void limparCampos() {
        ruaField.setText("");
        cidadeField.setText("");
        estadoField.setText("");
        cepField.setText("");
    }
}