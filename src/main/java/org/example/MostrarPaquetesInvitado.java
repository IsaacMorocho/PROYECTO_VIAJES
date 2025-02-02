package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.awt.*;
import java.util.Vector;
import org.bson.Document;


public class MostrarPaquetesInvitado {
    public JPanel MostrarPaquetesInvP;
    private JTable TuristicosTable;
    private JScrollPane scrollPane;
    private JButton REGISTRARSEButton;

    public MostrarPaquetesInvitado() {
        DefaultTableModel tableModel = (DefaultTableModel) TuristicosTable.getModel();
        //se asigna isCellEditable en el modelo para evitar la edición en las celdas
        TuristicosTable.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        scrollPane.setViewportView(TuristicosTable);
        cargarPaquetes();

        REGISTRARSEButton.addActionListener(e -> AtrasRegistro());
    }
    private void cargarPaquetes() {

        MongoDatabase database = ConexionMongo.getDatabase();
        MongoCollection<Document> collection = database.getCollection("PaquetesTuristicos");

        //Se añaden las columnas a la tablaPaquetes
        DefaultTableModel tableModel = (DefaultTableModel) TuristicosTable.getModel();
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
        TuristicosTable.setModel(tableModel); // Se asigna el modelo a la tabla antes de cargar los datos

        //Se toman los datos de la colección en mongodb
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
    }
    private void AtrasRegistro(){
        SwingUtilities.getWindowAncestor(MostrarPaquetesInvP).dispose();
        JFrame frameAdmin = new JFrame("Crear Cuenta Cliente ");
        CuentasClientes cuentasClientes = new CuentasClientes();
        frameAdmin.setContentPane(cuentasClientes.CuentasP);
        frameAdmin.setPreferredSize(new Dimension(800, 600));
        frameAdmin.pack();
        frameAdmin.setLocationRelativeTo(null);
        frameAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAdmin.setVisible(true);
    }
}
