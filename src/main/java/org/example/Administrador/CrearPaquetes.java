package org.example.Administrador;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.ConexionMongo;

import javax.swing.*;
import java.awt.*;

public class CrearPaquetes {
    public JPanel CrearPaqueteP;
    private JTextField origenTxt;
    private JTextField destinoTxt;
    private JTextField precioDestinoTxt;
    private JTextField idPaqueteTxt;
    private JTextField fechaSalidaTxt;
    private JComboBox comidaComboBox;
    private JTextField precioComidaTxt;
    private JComboBox guiaComboBox;
    private JTextField precioGuiaTxt;
    private JComboBox transporteComboBox;
    private JTextField precioTransporteTxt;
    private JButton guardarPaqueteBtn;
    private JButton calcularTotalBtn;
    private JButton cancelarBtn;
    private JLabel totalLabel;

    public CrearPaquetes() {
        // Inicializar ComboBoxes
        comidaComboBox.addItem("Sí");
        comidaComboBox.addItem("No");

        guiaComboBox.addItem("Sí");
        guiaComboBox.addItem("No");

        transporteComboBox.addItem("Avión");
        transporteComboBox.addItem("Bus");

        //Desactivar campos de precio cuando "No" está seleccionado
        comidaComboBox.addActionListener(e -> actualizarEstadoPrecio(precioComidaTxt, comidaComboBox));
        guiaComboBox.addActionListener(e -> actualizarEstadoPrecio(precioGuiaTxt, guiaComboBox));

        calcularTotalBtn.addActionListener(e -> calcularPrecioTotal());
        guardarPaqueteBtn.addActionListener(e -> guardarPaquete());
        cancelarBtn.addActionListener( e->CANCELAR());

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
    private double obtenerPrecio(JTextField precioField) {
        try {
            return precioField.isEnabled() ? Double.parseDouble(precioField.getText().trim()) : 0.0;
        } catch (NumberFormatException e) {
            return 0.0; //Si lo ingresado no es un numero ni aun convirtiendolo a double se retornara 0.0
        }
    }

    private void calcularPrecioTotal() {
        // Obtener precios de cada campo
        double precioDestino = obtenerPrecio(precioDestinoTxt);
        double precioComida = obtenerPrecio(precioComidaTxt);
        double precioGuia = obtenerPrecio(precioGuiaTxt);
        double precioTransporte = obtenerPrecio(precioTransporteTxt);

        // Calcular el total
        double precioTotal = precioDestino + precioComida + precioGuia + precioTransporte;

        // Mostrar el precio total en el label
        totalLabel.setText("$" + precioTotal);
    }

    private void guardarPaquete() {
        // Validar campos obligatorios
        if (idPaqueteTxt.getText().trim().isEmpty() ||
                origenTxt.getText().trim().isEmpty() ||
                destinoTxt.getText().trim().isEmpty() ||
                fechaSalidaTxt.getText().trim().isEmpty() ||
                transporteComboBox.getSelectedItem() == null ||
                precioDestinoTxt.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(CrearPaqueteP, "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String idPaquete = idPaqueteTxt.getText().trim();
        String origen = origenTxt.getText().trim();
        String destino = destinoTxt.getText().trim();
        String fechaSalida = fechaSalidaTxt.getText().trim();
        String comida = (String) comidaComboBox.getSelectedItem();
        String guia = (String) guiaComboBox.getSelectedItem();
        String transporte = (String) transporteComboBox.getSelectedItem();

        //precios para ponerlos en la coleccion
        double precioDestino = obtenerPrecio(precioDestinoTxt);
        double precioComida = obtenerPrecio(precioComidaTxt);
        double precioGuia = obtenerPrecio(precioGuiaTxt);
        double precioTransporte = obtenerPrecio(precioTransporteTxt);
        double precioTotal = precioDestino + precioComida + precioGuia + precioTransporte;

        try {
            MongoDatabase database = ConexionMongo.getDatabase();
            MongoCollection<Document> collection = database.getCollection("PaquetesTuristicos");

            Document paquete = new Document("idPaquete", idPaquete)
                    .append("origen", origen)
                    .append("destino", destino)
                    .append("fechaSalida", fechaSalida)
                    .append("comidaIncluida", comida)
                    .append("guiaTuristico", guia)
                    .append("transporte", transporte)
                    .append("precioDestino", precioDestino)
                    .append("precioComida", precioComida)
                    .append("precioGuia", precioGuia)
                    .append("precioTransporte", precioTransporte)
                    .append("precioTotal", precioTotal);
            collection.insertOne(paquete);
            JOptionPane.showMessageDialog(CrearPaqueteP, "Paquete Turistico guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(CrearPaqueteP, "Error al guardar el paquete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
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
        SwingUtilities.getWindowAncestor(CrearPaqueteP).dispose();
        JFrame frame = new JFrame("Panel Administrador");
        frame.setContentPane(new PanelAdmin().AdminCrud);
        frame.setPreferredSize(new Dimension(1500, 750));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


}
