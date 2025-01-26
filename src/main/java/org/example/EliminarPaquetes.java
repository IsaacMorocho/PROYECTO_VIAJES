package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class EliminarPaquetes {
    private JTextField IdPaqueteEliminar;
    private JButton mostrarInfoBtn;
    private JTable paqueteTabla;
    private JButton cancelarBtn;
    private JButton eliminarBtn;
    private JScrollPane scrollPane;
    public JPanel EliminarPaqueteP;
    private DefaultTableModel tableModel;

    public EliminarPaquetes() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Paquete");
        tableModel.addColumn("Origen");
        tableModel.addColumn("Destino");
        tableModel.addColumn("Fecha Salida");
        tableModel.addColumn("Comida Incluida");
        tableModel.addColumn("Guía Turístico");
        tableModel.addColumn("Transporte");
        tableModel.addColumn("Precio Total");
        paqueteTabla.setModel(tableModel);

        mostrarInfoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idPaquete = IdPaqueteEliminar.getText().trim();
                if (idPaquete.isEmpty()) {
                    JOptionPane.showMessageDialog(EliminarPaqueteP, "Por favor, ingrese el ID del paquete.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                mostrarPaquete(idPaquete);
            }
        });

        eliminarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idPaquete = IdPaqueteEliminar.getText().trim();
                if (idPaquete.isEmpty()) {
                    JOptionPane.showMessageDialog(EliminarPaqueteP, "Por favor, ingrese el ID del paquete.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(EliminarPaqueteP, "¿Está seguro de eliminar el paquete?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    eliminarPaquete(idPaquete);
                }
            }
        });

        cancelarBtn.addActionListener(e -> {
            JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(EliminarPaqueteP);
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

    private void mostrarPaquete(String idPaquete) {
        try {
            MongoDatabase database = ConexionMongo.getDatabase();
            MongoCollection<Document> collection = database.getCollection("PaquetesTuristicos");
            Document query = new Document("idPaquete", idPaquete);
            Document paquete = collection.find(query).first();

            if (paquete != null) {
                tableModel.setRowCount(0); // Limpiar la tabla antes de mostrar los datos
                tableModel.addRow(new Object[]{
                        paquete.getString("idPaquete"),
                        paquete.getString("origen"),
                        paquete.getString("destino"),
                        paquete.getString("fechaSalida"),
                        paquete.getString("comidaIncluida"),
                        paquete.getString("guiaTuristico"),
                        paquete.getString("transporte"),
                        paquete.getDouble("precioTotal")
                });
            } else {
                JOptionPane.showMessageDialog(EliminarPaqueteP, "No se encontró un paquete con el ID proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
                tableModel.setRowCount(0); // Limpiar tabla si no hay resultados
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(EliminarPaqueteP, "Ocurrió un error al buscar el paquete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarPaquete(String idPaquete) {
        try {
            MongoDatabase database = ConexionMongo.getDatabase();
            MongoCollection<Document> collection = database.getCollection("PaquetesTuristicos");
            Document query = new Document("idPaquete", idPaquete);
            long deleteCount = collection.deleteOne(query).getDeletedCount();

            if (deleteCount > 0) {
                JOptionPane.showMessageDialog(EliminarPaqueteP, "Paquete Turistico eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                tableModel.setRowCount(0); // Limpiar la tabla después de eliminar
            } else {
                JOptionPane.showMessageDialog(EliminarPaqueteP, "No se puede eliminar el paquete. Verifique el ID proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(EliminarPaqueteP, "Error al eliminar el paquete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
