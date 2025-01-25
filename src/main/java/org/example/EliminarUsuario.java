package org.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

public class EliminarUsuario {
    public JPanel EliminarUsuarioP;
    private JTextField correoTxt;
    private JButton mostrarClienteBtn;
    private JTable clienteTabla;
    private JButton eliminarClienteBtn;
    private JScrollPane scrollPane;
    private JButton cancelarBtn;
    private DefaultTableModel tableModel;

    public EliminarUsuario() {
        //Añadimos los encabezados en la tabla
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Correo");
        tableModel.addColumn("Contraseña");
        clienteTabla.setModel(tableModel);
        mostrarClienteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correo = correoTxt.getText().trim();
                if (correo.isEmpty()) {
                    JOptionPane.showMessageDialog(EliminarUsuarioP, "Por favor, ingrese el correo del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mostrarCliente(correo);
            }
        });

        eliminarClienteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correo = correoTxt.getText().trim();
                if (correo.isEmpty()) {
                    JOptionPane.showMessageDialog(EliminarUsuarioP, "Por favor, ingrese el correo del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(EliminarUsuarioP, "¿Está seguro de eliminar la información del cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    //Si se confirma en el cuadro de dialogo se elimina la info del cliente
                    eliminarCliente(correo);
                }
            }
        });
        cancelarBtn.addActionListener(e -> {
            JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(EliminarUsuarioP);
            frameActual.dispose();
            JFrame frameAdmin = new JFrame("Panel Administrador");
            PanelAdmin panelAdmin = new PanelAdmin();
            frameAdmin.setContentPane(panelAdmin.AdminCrud);
            frameAdmin.setLocationRelativeTo(null);
            frameAdmin.setSize(800, 600);
            frameAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameAdmin.setVisible(true);
        });
    }
    private void mostrarCliente(String correo) {
        try {
            MongoDatabase database = ConexionMongo.getDatabase();
            MongoCollection<Document> collection = database.getCollection("RegistrosClientes");
            Document query = new Document("correo", correo);
            Document cliente = collection.find(query).first();

            if (cliente != null) {
                tableModel.setRowCount(0); //Para tener el JTable limpio
                tableModel.addRow(new Object[]{
                        cliente.getObjectId("_id"),
                        cliente.getString("nombre"),
                        cliente.getString("correo"),
                        cliente.getString("contrasena")
                });
            } else {
                JOptionPane.showMessageDialog(EliminarUsuarioP, "No se encontró un cliente con el correo proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
                tableModel.setRowCount(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(EliminarUsuarioP, "Ocurrió un error al buscar al cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCliente(String correo) {
        try {
            MongoDatabase database = ConexionMongo.getDatabase();
            MongoCollection<Document> collection = database.getCollection("RegistrosClientes");
            //Eliminar el cliente por su correo
            Document query = new Document("correo", correo);
            long deleteCount = collection.deleteOne(query).getDeletedCount();

            if (deleteCount > 0) {
                JOptionPane.showMessageDialog(EliminarUsuarioP, "Cliente eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                tableModel.setRowCount(0); //Para limpiar la tabla después de eliminar
            } else {
                JOptionPane.showMessageDialog(EliminarUsuarioP, "No se pudo eliminar al cliente. Verifique el correo proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(EliminarUsuarioP, "Ocurrió un error al eliminar al cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
