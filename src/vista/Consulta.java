/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import dao.FiltroDao;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Filtro;

/**
 *
 * @author LN710Q
 */
public class Consulta extends JFrame {

    public JLabel lblCarnet, lblUniversidad, lblEstado, lblNombres, lblApellidos;

    public JTextField carnet, nombre, apellidos, edad;
    public JComboBox universidad;

    ButtonGroup estado = new ButtonGroup();
    public JRadioButton no;
    public JRadioButton si;
    public JTable resultados;

    public JPanel table;

    public JButton buscar, eliminar, insertar, limpiar, actualizar;

    private static final int ANCHOC = 100, ALTOC = 30;

    DefaultTableModel tm;

    public Consulta() {
        super("Inscripciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        agregarLabels();
        formulario();
        llenarTabla();
        Container container = getContentPane();
        container.add(lblCarnet);
        container.add(lblNombres);
        container.add(lblApellidos);
        container.add(lblUniversidad);
        container.add(lblEstado);
                container.add(nombre);
        container.add(apellidos);
        container.add(carnet);
        container.add(edad);

        container.add(universidad);
        container.add(si);
        container.add(no);
        container.add(buscar);
        container.add(insertar);
        container.add(actualizar);
        container.add(eliminar);
        container.add(limpiar);
        container.add(table);
        setSize(600, 800);
        eventos();

    }

    private void agregarLabels() {
        lblCarnet = new JLabel("Carnet");
        lblNombres = new JLabel("Nombres");
        lblApellidos = new JLabel("Apellidos");
        lblUniversidad = new JLabel("Universidad");
        lblEstado = new JLabel("Estado");
        lblCarnet.setBounds(20, 10, ANCHOC, ALTOC);
        lblNombres.setBounds(20, 60, ANCHOC, ALTOC);
        lblApellidos.setBounds(20, 110, ANCHOC, ALTOC);
        lblUniversidad.setBounds(20, 160, ANCHOC, ALTOC);
        lblEstado.setBounds(20, 200, ANCHOC, ALTOC);
    }

    private void formulario() {
        carnet = new JTextField();
        nombre = new JTextField();
        apellidos = new JTextField();
        universidad = new JComboBox();
        edad = new JTextField();
        si = new JRadioButton("si", true);
        no = new JRadioButton("no");
        resultados = new JTable();
        buscar = new JButton("Buscar");
        insertar = new JButton("Insertar");
        eliminar = new JButton("Eliminar");
        actualizar = new JButton("Actualizar");
        limpiar = new JButton("Limpiar");

        table = new JPanel();

        universidad.addItem("UCA");
        universidad.addItem("UDB");
        universidad.addItem("UFG");
        universidad.addItem("UGB");

        estado = new ButtonGroup();
        estado.add(si);
        estado.add(no);
        //-------------------------------------------

//        
//        lblCarnet.setBounds(20, 10, ANCHOC, ALTOC);
//        lblNombres.setBounds(20, 60, ANCHOC, ALTOC);
//        lblApellidos.setBounds(20, 110, ANCHOC, ALTOC);
//        lblUniversidad.setBounds(20, 160, ANCHOC, ALTOC);
//        lblEstado.setBounds(20, 200, ANCHOC, ALTOC);
//    }
        carnet.setBounds(140, 10, ANCHOC, ALTOC);
        universidad.setBounds(140, 160, ANCHOC, ALTOC);
        nombre.setBounds(140, 60, ANCHOC, ALTOC);
        apellidos.setBounds(140, 110, ANCHOC, ALTOC);

        si.setBounds(140, 200, 50, ALTOC);
        no.setBounds(210, 200, 50, ALTOC);

        buscar.setBounds(300, 10, ANCHOC, ALTOC);
        insertar.setBounds(10, 600, ANCHOC, ALTOC);
        actualizar.setBounds(150, 600, ANCHOC, ALTOC);
        eliminar.setBounds(300, 600, ANCHOC, ALTOC);
        limpiar.setBounds(450, 600, ANCHOC, ALTOC);
        resultados = new JTable();
        table.setBounds(10, 250, 500, 200);
        table.add(new JScrollPane(resultados));

    }

    private void llenarTabla() {
        tm = new DefaultTableModel() {
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };

        tm.addColumn("Carnet");
        tm.addColumn("Nombre");
        tm.addColumn("Apellido");
        tm.addColumn("Universidad");
        tm.addColumn("Estado");

        FiltroDao fd = new FiltroDao();
        ArrayList<Filtro> filtros = fd.readAll();

        for (Filtro fi : filtros) {
            tm.addRow(new Object[]{fi.getCarnet(), fi.getNombres(), fi.getApellidos(), fi.getUniversidad(), fi.getEstado()});
        }

        resultados.setModel(tm);

    }

    private void eventos() {
        insertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FiltroDao fd = new FiltroDao();
                Filtro f = new Filtro(carnet.getText(),nombre.getText(), apellidos.getText(),universidad.getSelectedItem().toString(), true);

                if (no.isSelected()) {
                    f.setEstado(false);
                }

                if (fd.create(f)) {
                    JOptionPane.showMessageDialog(null, "Filtro registrado con éxito");
                    limpiarCampos();
                    llenarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema con la creación de este filtro.");
                }
            }
        });

        actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FiltroDao fd = new FiltroDao();
                Filtro f = new Filtro(carnet.getText(),nombre.getText(), apellidos.getText(),universidad.getSelectedItem().toString(), true);

                if (no.isSelected()) {
                    f.setEstado(false);
                }

                if (fd.update(f)) {
                    JOptionPane.showMessageDialog(null, "Filtro modificado con éxito");
                    limpiarCampos();
                    llenarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema al momento de creación de este filtro.");
                }
            }
        });

        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FiltroDao fd = new FiltroDao();
                Filtro f = new Filtro(carnet.getText(),nombre.getText(), apellidos.getText(),universidad.getSelectedItem().toString(), true);
                if (fd.delete(carnet.getText())) {
                    JOptionPane.showMessageDialog(null, "Filtro eliminado con éxito");
                    limpiarCampos();
                    llenarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un problema al momento de eliminar este filtro.");
                }
            }
        });

        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FiltroDao fd = new FiltroDao();
                Filtro f = fd.read(carnet.getText());
                if (f == null) {
                    JOptionPane.showMessageDialog(null, "El Filtro buscado no ha sido encontrado");
                } else {

                    carnet.setText(f.getCarnet());
                    nombre.setText(f.getNombres());
                                        apellidos.setText(f.getApellidos());
                    universidad.setSelectedItem(f.getUniversidad());

                    if (f.getEstado()) {
                        si.setSelected(true);
                    } else {
                        no.setSelected(true);
                    }
                }
            }
        });

        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    public void limpiarCampos() {
        carnet.setText("");
        nombre.setText("");
                apellidos.setText("");
                edad.setText("");

        universidad.setSelectedItem("UCA");

    }

}
