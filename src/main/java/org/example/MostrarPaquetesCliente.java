package org.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class MostrarPaquetesCliente {
    private JTable tablaPaquetes;
    private JTextField IdPaqueteComprarTxt;
    private JButton comprarBtn;
    private JButton atrasButton;
    private JScrollPane scrollPane;
    public JPanel MostrarPaqueteP;
    private String correo;

    public MostrarPaquetesCliente(String correo) {
        this.correo=correo;
        DefaultTableModel tableModel = (DefaultTableModel) tablaPaquetes.getModel();
        //se asigna isCellEditable en el modelo para evitar la edición en las celdas
        tablaPaquetes.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        scrollPane.setViewportView(tablaPaquetes);
        cargarPaquetes();

        comprarBtn.addActionListener(e -> comprarButtonAction());
        atrasButton.addActionListener(e -> REGRESAR());

    }

    private void cargarPaquetes() {
        try {
            MongoDatabase database = ConexionMongo.getDatabase();
            MongoCollection<Document> collection = database.getCollection("PaquetesTuristicos");

            //Se añaden las columnas a la tablaPaquetes
            DefaultTableModel tableModel = (DefaultTableModel) tablaPaquetes.getModel();
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
            tablaPaquetes.setModel(tableModel); // Se asigna el modelo a la tabla antes de cargar los datos

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
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(MostrarPaqueteP, "Error al cargar los paquetes turísticos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void comprarButtonAction() {
        String idPaquete = IdPaqueteComprarTxt.getText().trim();
        if (idPaquete.isEmpty()) {
            JOptionPane.showMessageDialog(MostrarPaqueteP, "Por favor, ingrese un ID de paquete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            MongoDatabase database = ConexionMongo.getDatabase();
            MongoCollection<Document> collection = database.getCollection("PaquetesTuristicos");
            //Verificar si el ID ingresado existe en la base de datos
            Document query = new Document("idPaquete", idPaquete);
            Document paquete = collection.find(query).first();

            if (paquete != null) {
                //Si el paquete existe, abrir la interfaz de "Detalles de compra"
                abrirDetallesCompra(idPaquete);
            } else {
                JOptionPane.showMessageDialog(MostrarPaqueteP, "ID de paquete no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(MostrarPaqueteP, "Error al verificar el paquete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Para redireccionar a la siguiente interfaz con el ID escrito se lo pasa como valor de entrada
    private void abrirDetallesCompra(String idPaquete) {
        SwingUtilities.getWindowAncestor(MostrarPaqueteP).dispose();
        JFrame frameDetalles = new JFrame("Detalles de Compra");
        DetallesCompra detallesCompra = new DetallesCompra(idPaquete,correo);
        frameDetalles.setContentPane(detallesCompra.DetallesCompraP);
        frameDetalles.setLocationRelativeTo(null);
        frameDetalles.setSize(800, 600);
        frameDetalles.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameDetalles.setVisible(true);
    }
    private void REGRESAR() {
        SwingUtilities.getWindowAncestor(MostrarPaqueteP).dispose();
        JFrame frameAdmin = new JFrame("Menu Cliente");
        MenuCliente menuCliente = new MenuCliente(correo);
        frameAdmin.setContentPane(menuCliente.MenuClienteP);
        frameAdmin.setLocationRelativeTo(null);
        frameAdmin.setSize(800, 600);
        frameAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAdmin.setVisible(true);
    }
}
