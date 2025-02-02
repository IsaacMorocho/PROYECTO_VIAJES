package org.example.Administrador;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.ConexionMongo;

import javax.swing.*;
import java.awt.*;

public class ActualizarPaquetes {
    public JPanel ActualizarPaqueteP;
    private JPanel CrearPaqueteP;
    private JTextField origenTxt;
    private JTextField destinoTxt;
    private JTextField precioDestinoTxt;
    private JTextField buscarIdTxt;
    private JTextField fechaSalidaTxt;
    private JComboBox comidaComboBox;
    private JTextField precioComidaTxt;
    private JComboBox guiaComboBox;
    private JTextField precioGuiaTxt;
    private JComboBox transporteComboBox;
    private JTextField precioTransporteTxt;
    private JButton actualizarPaqueteBtn;
    private JButton calcularTotalBtn;
    private JLabel totalLabel;
    private JButton cancelarBtn;
    private JTextField idPaqueteTxt;

    public ActualizarPaquetes() {
        comidaComboBox.addItem("Sí");
        comidaComboBox.addItem("No");
        guiaComboBox.addItem("Sí");
        guiaComboBox.addItem("No");
        transporteComboBox.addItem("Avión");
        transporteComboBox.addItem("Bus");

        comidaComboBox.addActionListener(e -> actualizarEstadoPrecio(precioComidaTxt, comidaComboBox));
        guiaComboBox.addActionListener(e -> actualizarEstadoPrecio(precioGuiaTxt, guiaComboBox));
        calcularTotalBtn.addActionListener(e -> calcularPrecioTotal());

        actualizarPaqueteBtn.addActionListener(e -> buscarYActualizarPaquete());
        cancelarBtn.addActionListener(e -> CANCELAR());
    }
    private void actualizarEstadoPrecio(JTextField precioField, JComboBox<String> comboBox) {
        String seleccion = (String) comboBox.getSelectedItem();
        if ("No".equals(seleccion)) {
            precioField.setText("");
            precioField.setEnabled(false);
        } else {
            precioField.setEnabled(true);
        }
    }
    private void buscarYActualizarPaquete() {
        String idPaqueteOriginal = buscarIdTxt.getText().trim();

        if (idPaqueteOriginal.isEmpty()) {
            JOptionPane.showMessageDialog(ActualizarPaqueteP, "Por favor, ingresa el ID del paquete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            MongoDatabase database = ConexionMongo.getDatabase();
            MongoCollection<Document> collection = database.getCollection("PaquetesTuristicos");

            Document query = new Document("idPaquete", idPaqueteOriginal);
            Document paquete = collection.find(query).first();

            if (paquete == null) {
                JOptionPane.showMessageDialog(ActualizarPaqueteP, "No se encontró un paquete con el ID proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
                actualizarEnMongo(collection, paquete);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(ActualizarPaqueteP, "Error al buscar o actualizar el paquete en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarEnMongo(MongoCollection<Document> collection, Document paqueteExistente) {
        String idNuevo = idPaqueteTxt.getText().trim();
        String origen = origenTxt.getText().trim();
        String destino = destinoTxt.getText().trim();
        String fechaSalida = fechaSalidaTxt.getText().trim();
        String comida = (String) comidaComboBox.getSelectedItem();
        String guia = (String) guiaComboBox.getSelectedItem();
        String transporte = (String) transporteComboBox.getSelectedItem();

        if (idNuevo.isEmpty() || origen.isEmpty() || destino.isEmpty() || fechaSalida.isEmpty() || transporte == null) {
            JOptionPane.showMessageDialog(ActualizarPaqueteP, "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Se crea el documento con los datos actualizados
        Document paqueteActualizado = new Document("idPaquete", idNuevo)
                .append("origen", origen)
                .append("destino", destino)
                .append("fechaSalida", fechaSalida)
                .append("comidaIncluida", comida)
                .append("guiaTuristico", guia)
                .append("transporte", transporte)
                .append("precioDestino", Double.parseDouble(precioDestinoTxt.getText().trim()))
                .append("precioComida", Double.parseDouble(precioComidaTxt.getText().trim()))
                .append("precioGuia", Double.parseDouble(precioGuiaTxt.getText().trim()))
                .append("precioTransporte", Double.parseDouble(precioTransporteTxt.getText().trim()));

        //Actualizamos
        Document query = new Document("idPaquete", paqueteExistente.getString("idPaquete"));
        collection.updateOne(query, new Document("$set", paqueteActualizado));
        JOptionPane.showMessageDialog(ActualizarPaqueteP, "Paquete Turistico actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        limpiarCampos();
    }
    private double obtenerPrecio(JTextField precioField) {
        try {
            return precioField.isEnabled() ? Double.parseDouble(precioField.getText().trim()) : 0.0;
        } catch (NumberFormatException e) {
            return 0.0; //Si lo ingresado no es un numero ni aun convirtiendolo a double se retornara 0.0
        }
    }

    private void calcularPrecioTotal() {
        //Obtener precios de cada campo
        double precioDestino = obtenerPrecio(precioDestinoTxt);
        double precioComida = obtenerPrecio(precioComidaTxt);
        double precioGuia = obtenerPrecio(precioGuiaTxt);
        double precioTransporte = obtenerPrecio(precioTransporteTxt);
        double precioTotal = precioDestino + precioComida + precioGuia + precioTransporte;
        totalLabel.setText("$" + precioTotal);
    }
    private void limpiarCampos() {
        buscarIdTxt.setText("");
        idPaqueteTxt.setText("");
        origenTxt.setText("");
        destinoTxt.setText("");
        fechaSalidaTxt.setText("");
        precioDestinoTxt.setText("");
        precioComidaTxt.setText("");
        precioGuiaTxt.setText("");
        precioTransporteTxt.setText("");
        comidaComboBox.setSelectedIndex(0);
        guiaComboBox.setSelectedIndex(0);
        transporteComboBox.setSelectedIndex(0);
        totalLabel.setText("$0.0");
    }
    private void CANCELAR(){
        SwingUtilities.getWindowAncestor(ActualizarPaqueteP).dispose();
        JFrame frame = new JFrame("Panel Administrador");
        frame.setContentPane(new PanelAdmin().AdminCrud);
        frame.setPreferredSize(new Dimension(1500, 750));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
