package org.example.UI;

import org.example.UI.selects.SelecionarTipoVeiculo;
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

    private JLabel tipoLabel;
    private String tipoSelecionado;
    private Integer valorExtra;

    private JTable tabela;
    private DefaultTableModel model;

    private VeiculoDAO veiculoDAO = new VeiculoDAO();

    public VeiculoPanel() {

        setLayout(new BorderLayout());

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        placaField = new JTextField();
        corField = new JTextField();
        renavamField = new JTextField();

        form.add(criarLinha("Placa:", placaField));
        form.add(criarLinha("Cor:", corField));
        form.add(criarLinha("Renavam:", renavamField));
        form.add(criarLinhaTipo());
        form.add(criarBotoes());

        add(form, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new Object[]{"ID", "Placa", "Cor", "Renavam", "Tipo"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(model);

        tabela.setRowSelectionAllowed(true);
        tabela.setColumnSelectionAllowed(false);
        tabela.setCellSelectionEnabled(false);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setDefaultEditor(Object.class, null);

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherCampos();
            }
        });

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

    private JPanel criarLinhaTipo() {
        JPanel p = new JPanel(new BorderLayout(5,5));
        p.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        tipoLabel = new JLabel("Nenhum selecionado");

        JButton btn = new JButton("Selecionar");

        btn.addActionListener(e -> {
            SelecionarTipoVeiculo dialog =
                    new SelecionarTipoVeiculo((Frame) SwingUtilities.getWindowAncestor(this));

            dialog.setVisible(true);

            tipoSelecionado = dialog.getTipoSelecionado();
            valorExtra = dialog.getValorExtra();

            if (tipoSelecionado != null) {
                tipoLabel.setText(tipoSelecionado + " - " + valorExtra);
            }
        });

        p.add(new JLabel("Tipo:"), BorderLayout.WEST);
        p.add(tipoLabel, BorderLayout.CENTER);
        p.add(btn, BorderLayout.EAST);

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


    private void salvar() {
        Veiculo v = new Veiculo();

        v.setPlaca(placaField.getText());
        v.setCor(corField.getText());
        v.setRenavam(renavamField.getText());
        v.setTipo(tipoSelecionado);
        v.setValorExtra(valorExtra);

        veiculoDAO.salvar(v);

        atualizarTabela();
        limparCampos();

        JOptionPane.showMessageDialog(this, "Veículo salvo com sucesso!");
    }

    private void atualizar() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            int id = (int) tabela.getValueAt(linha, 0);

            Veiculo v = veiculoDAO.consultar(id);

            v.setPlaca(placaField.getText());
            v.setCor(corField.getText());
            v.setRenavam(renavamField.getText());
            v.setTipo(tipoSelecionado);
            v.setValorExtra(valorExtra);

            veiculoDAO.atualizar(v);

            atualizarTabela();
            limparCampos();

            JOptionPane.showMessageDialog(this, "Veículo atualizado com sucesso!");
        }
    }

    private void deletar() {
        int linha = tabela.getSelectedRow();

        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha!");
            return;
        }

        int id = (int) tabela.getValueAt(linha, 0);

        veiculoDAO.deletar(id);

        atualizarTabela();
        limparCampos();

        JOptionPane.showMessageDialog(this, "Veículo deletado com sucesso!");
    }


    private void atualizarTabela() {
        List<Veiculo> lista = veiculoDAO.listar();

        model.setRowCount(0);

        for (Veiculo v : lista) {
            model.addRow(new Object[]{
                    v.getId(),
                    v.getPlaca(),
                    v.getCor(),
                    v.getRenavam(),
                    v.getTipo() + " - " + v.getValorExtra()
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

        tipoLabel.setText("Nenhum selecionado");
        tipoSelecionado = null;
        valorExtra = null;
    }
}