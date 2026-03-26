package org.example.UI;

import org.example.UI.selects.SelecionarCliente;
import org.example.UI.selects.SelecionarEndereco;
import org.example.UI.selects.SelecionarMotorista;
import org.example.UI.selects.SelecionarVeiculo;
import org.example.dao.*;
import org.example.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViagemPanel extends JPanel {

    private JTextField origemField;
    private JTextField destinoField;

    private JLabel clienteLabel;
    private JLabel motoristaLabel;
    private JLabel veiculoLabel;

    private Cliente clienteSelecionado;
    private Motorista motoristaSelecionado;
    private Veiculo veiculoSelecionado;
    private Endereco origemSelecionada;
    private Endereco destinoSelecionado;

    private JTable tabela;
    private DefaultTableModel model;

    private ViagemDAO viagemDAO = new ViagemDAO();

    public ViagemPanel() {

        setLayout(new BorderLayout());

        // formulario
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        origemField = new JTextField();
        origemField.setEditable(false);

        destinoField = new JTextField();
        destinoField.setEditable(false);

        clienteLabel = new JLabel("Nenhum selecionado");
        motoristaLabel = new JLabel("Nenhum selecionado");
        veiculoLabel = new JLabel("Nenhum selecionado");

        form.add(criarLinhaEndereco("Origem:", origemField, true));
        form.add(criarLinhaEndereco("Destino:", destinoField, false));
        form.add(criarLinhaPessoa("Cliente:", clienteLabel, "cliente"));
        form.add(criarLinhaPessoa("Motorista:", motoristaLabel, "motorista"));
        form.add(criarLinhaPessoa("Veículo:", veiculoLabel, "veiculo"));
        form.add(criarBotoes());

        add(form, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new Object[]{"ID", "Origem", "Destino", "Cliente", "Motorista", "Veículo"}, 0
        );

        tabela = new JTable(model);

        tabela.setRowSelectionAllowed(true);
        tabela.setColumnSelectionAllowed(true);
        tabela.setCellSelectionEnabled(true);
        tabela.setRowSelectionAllowed(true);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setDefaultEditor(Object.class, null);

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        atualizarTabela();
    }

    // ================= UI =================

    private JPanel criarLinhaEndereco(String label, JTextField field, boolean origem) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton btn = new JButton("Selecionar");

        btn.addActionListener(e -> {
            SelecionarEndereco dialog =
                    new SelecionarEndereco((Frame) SwingUtilities.getWindowAncestor(this));

            dialog.setVisible(true);

            Endereco eSel = dialog.getEnderecoSelecionado();

            if (eSel != null) {
                field.setText(eSel.toString());

                if (origem) {
                    origemSelecionada = eSel;
                } else {
                    destinoSelecionado = eSel;
                }
            }
        });

        p.add(new JLabel(label), BorderLayout.WEST);
        p.add(field, BorderLayout.CENTER);
        p.add(btn, BorderLayout.EAST);

        return p;
    }

    private JPanel criarLinhaPessoa(String label, JLabel l, String tipo) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton btn = new JButton("Selecionar");

        btn.addActionListener(e -> abrirDialog(tipo));

        p.add(new JLabel(label), BorderLayout.WEST);
        p.add(l, BorderLayout.CENTER);
        p.add(btn, BorderLayout.EAST);

        return p;
    }

    private JPanel criarBotoes() {
        JPanel p = new JPanel(new FlowLayout());

        JButton salvar = new JButton("Salvar");
        JButton atualizar = new JButton("Atualizar");
        JButton deletar = new JButton("Deletar");

        salvar.addActionListener(e -> salvar());
        atualizar.addActionListener(e -> atualizar());
        deletar.addActionListener(e -> deletar());

        p.add(salvar);
        p.add(atualizar);
        p.add(deletar);

        return p;
    }

    // ================= DIALOGS =================

    private void abrirDialog(String tipo) {

        Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);

        switch (tipo) {
            case "cliente":
                SelecionarCliente c = new SelecionarCliente(frame);
                c.setVisible(true);
                clienteSelecionado = c.getSelecionado();
                if (clienteSelecionado != null)
                    clienteLabel.setText(clienteSelecionado.getNome());
                break;
            case "motorista":
                SelecionarMotorista m = new SelecionarMotorista(frame);
                m.setVisible(true);
                motoristaSelecionado = m.getSelecionado();
                if (motoristaSelecionado != null)
                    motoristaLabel.setText(motoristaSelecionado.getNome());
                break;
            case "veiculo":
                SelecionarVeiculo v = new SelecionarVeiculo(frame);
                v.setVisible(true);
                veiculoSelecionado = v.getSelecionado();
                if (veiculoSelecionado != null)
                    veiculoLabel.setText(veiculoSelecionado.getPlaca());
                break;
        }
    }

    // ================= CRUD =================

    private void salvar() {
        if (clienteSelecionado == null || motoristaSelecionado == null ||
                veiculoSelecionado == null || origemSelecionada == null || destinoSelecionado == null) {

            JOptionPane.showMessageDialog(this, "Preencha tudo!");
            return;
        }

        Viagem v = new Viagem();

        v.setCliente(clienteSelecionado);
        v.setMotorista(motoristaSelecionado);
        v.setVeiculo(veiculoSelecionado);
        v.setOrigem(origemSelecionada);
        v.setDestino(destinoSelecionado);

        viagemDAO.salvar(v);

        atualizarTabela();
        limpar();
        JOptionPane.showMessageDialog(this, "Viagem salva com sucesso!");
    }

    private void atualizar() {
        int linha = tabela.getSelectedRow();
        int coluna = tabela.getSelectedColumn();

        if (linha >= 0 && coluna >= 0) {

            Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);

            int id = (int) tabela.getValueAt(linha, 0);
            Viagem v = viagemDAO.consultar(id);

            switch (coluna) {
                case 1: // Origem
                    SelecionarEndereco o = new SelecionarEndereco(frame);
                    o.setVisible(true);
                    if (o.getEnderecoSelecionado() != null)
                        v.setOrigem(o.getEnderecoSelecionado());
                    break;
                case 2: // Destino
                    SelecionarEndereco d = new SelecionarEndereco(frame);
                    d.setVisible(true);
                    if (d.getEnderecoSelecionado() != null)
                        v.setDestino(d.getEnderecoSelecionado());
                    break;
                case 3: // Cliente
                    SelecionarCliente c = new SelecionarCliente(frame);
                    c.setVisible(true);
                    if (c.getSelecionado() != null)
                        v.setCliente(c.getSelecionado());
                    break;
                case 4: // Motorista
                    SelecionarMotorista m = new SelecionarMotorista(frame);
                    m.setVisible(true);
                    if (m.getSelecionado() != null)
                        v.setMotorista(m.getSelecionado());
                    break;
                case 5: // Veículo
                    SelecionarVeiculo ve = new SelecionarVeiculo(frame);
                    ve.setVisible(true);
                    if (ve.getSelecionado() != null)
                        v.setVeiculo(ve.getSelecionado());
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Selecione uma coluna valida!");
                    return;
            }

            viagemDAO.atualizar(v);
            atualizarTabela();
            limpar();
            JOptionPane.showMessageDialog(this, "Viagem atualizada com sucesso!");
        }
    }

    private void deletar() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            int id = (int) tabela.getValueAt(linha, 0);

            viagemDAO.deletar(id);

            atualizarTabela();
            limpar();
            JOptionPane.showMessageDialog(this, "Viagem deletada com sucesso!");
        }
    }

    // ================= TABELA =================

    private void atualizarTabela() {
        model.setRowCount(0);

        List<Viagem> lista = viagemDAO.listar();

        for (Viagem v : lista) {
            model.addRow(new Object[]{
                    v.getId(),
                    v.getOrigem(),
                    v.getDestino(),
                    v.getCliente(),
                    v.getMotorista(),
                    v.getVeiculo()
            });
        }
    }

    private void limpar() {
        origemField.setText("");
        destinoField.setText("");

        clienteLabel.setText("Nenhum selecionado");
        motoristaLabel.setText("Nenhum selecionado");
        veiculoLabel.setText("Nenhum selecionado");

        clienteSelecionado = null;
        motoristaSelecionado = null;
        veiculoSelecionado = null;
        origemSelecionada = null;
        destinoSelecionado = null;
    }
}