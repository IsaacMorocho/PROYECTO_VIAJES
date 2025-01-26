package org.example;

import javax.swing.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
public class MostrarPaquetes {
    public JPanel MostrarPaqueteP;
    private JTable tablaPaquetes;
    private JButton editarInfoBtn;
    private JButton cancelarBtn;
    private JScrollPane scrollPane;
    public MostrarPaquetes() {
        scrollPane.setViewportView(tablaPaquetes);
        cargarPaquetes();
        //Botones
        editarInfoBtn.addActionListener(e -> EditarPaquete());
        cancelarBtn.addActionListener(e -> Regresar());
    }
    private void cargarPaquetes() {
        try {
            MongoDatabase database = ConexionMongo.getDatabase();
            MongoCollection<Document> collection = database.getCollection("PaquetesTuristicos");

            // Añadimos el nombre de las columnas a la tabla
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID Paquete");
            tableModel.addColumn("Origen");
            tableModel.addColumn("Destino");
            tableModel.addColumn("Fecha Salida");
            tableModel.addColumn("Comida Incluida");
            tableModel.addColumn("Guía Turístico");
            tableModel.addColumn("Transporte");
            tableModel.addColumn("Precio Destino");
            tableModel.addColumn("Precio Comida");
            tableModel.addColumn("Precio Guía");
            tableModel.addColumn("Precio Transporte");
            tableModel.addColumn("Precio Total");
            tablaPaquetes.setModel(tableModel);

            // Obtener los datos desde MongoDB y agregarlos al modelo de la tabla
            MongoCursor<Document> cursor = collection.find().iterator();
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Vector<Object> row = new Vector<>();
                row.add(doc.getString("idPaquete"));
                row.add(doc.getString("origen"));
                row.add(doc.getString("destino"));
                row.add(doc.getString("fechaSalida"));
                row.add(doc.getString("comidaIncluida"));
                row.add(doc.getString("guiaTuristico"));
                row.add(doc.getString("transporte"));
                row.add(doc.getDouble("precioDestino"));
                row.add(doc.getDouble("precioComida"));
                row.add(doc.getDouble("precioGuia"));
                row.add(doc.getDouble("precioTransporte"));
                row.add(doc.getDouble("precioTotal"));
                tableModel.addRow(row);
            }
            // Se asigna el modelo Jtable a la tabla paquetes
            tablaPaquetes.setModel(tableModel);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(MostrarPaqueteP, "Error al cargar los paquetes turísticos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void EditarPaquete() {
        JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(MostrarPaqueteP);
        frameActual.dispose();
        JFrame frameEditar = new JFrame("Editar Paquetes");
        ActualizarPaquetes actualizarPaquete = new ActualizarPaquetes();
        frameEditar.setContentPane(actualizarPaquete.ActualizarPaqueteP);
        frameEditar.setLocationRelativeTo(null);
        frameEditar.setSize(800, 600);
        frameEditar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameEditar.setVisible(true);
    }

    private void Regresar() {
        JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(MostrarPaqueteP);
        frameActual.dispose();
        JFrame frameAdmin = new JFrame("Panel Administrador");
        PanelAdmin panelAdmin = new PanelAdmin();
        frameAdmin.setContentPane(panelAdmin.AdminCrud);
        frameAdmin.setLocationRelativeTo(null);
        frameAdmin.setSize(800, 600);
        frameAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAdmin.setVisible(true);
    }
}
