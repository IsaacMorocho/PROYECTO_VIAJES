package org.example;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
public class DetallesCompra {
    public JPanel DetallesCompraP;
    private JLabel idPaqueteTxt;
    private JTable paqueteInfo;
    private JScrollPane scrollPane;
    private JLabel costoTotal;
    private JButton FacturaBtn;
    private JButton cancelarBtn;
    private String correoCliente;

    public DetallesCompra(String idPaquete, String correoCliente) {
        this.correoCliente = correoCliente; //se almacena el correo del usuario logueado

        idPaqueteTxt.setText("ID del paquete turístico: " + idPaquete);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Origen");
        tableModel.addColumn("Destino");
        tableModel.addColumn("Precio Destino");
        tableModel.addColumn("Fecha Salida");
        tableModel.addColumn("Comida Incluida");
        tableModel.addColumn("Precio Comida");
        tableModel.addColumn("Guía Turístico");
        tableModel.addColumn("Precio Guía");
        tableModel.addColumn("Transporte");
        tableModel.addColumn("Precio Transporte");

        paqueteInfo.setModel(tableModel);
        scrollPane.setViewportView(paqueteInfo);
        //Llenamos los datos en la tabla
        cargarPaquete(idPaquete, tableModel);
        //Boton factura con el metodo generar y su variable de entrada IDpaquete
        FacturaBtn.addActionListener(e -> generarFactura(idPaquete));
        cancelarBtn.addActionListener(e -> CANCELAR());
    }

    private void cargarPaquete(String idPaquete, DefaultTableModel tableModel) {
        MongoDatabase database = ConexionMongo.getDatabase();
        MongoCollection<org.bson.Document> collection = database.getCollection("PaquetesTuristicos");

        //buscar el documento con el ID
        org.bson.Document query = new org.bson.Document("idPaquete", idPaquete);
        org.bson.Document paquete = collection.find(query).first();
            Vector<Object> row = new Vector<>();
            row.add(paquete.getString("origen"));
            row.add(paquete.getString("destino"));
            row.add(paquete.getDouble("precioDestino"));
            row.add(paquete.getString("fechaSalida"));
            row.add(paquete.getString("comidaIncluida"));
            row.add(paquete.getDouble("precioComida"));
            row.add(paquete.getString("guiaTuristico"));
            row.add(paquete.getDouble("precioGuia"));
            row.add(paquete.getString("transporte"));
            row.add(paquete.getDouble("precioTransporte"));
            tableModel.addRow(row);

            double precioTotal = paquete.getDouble("precioDestino") +
                    paquete.getDouble("precioComida") +
                    paquete.getDouble("precioGuia") +
                    paquete.getDouble("precioTransporte");

            costoTotal.setText("Precio Total del Paquete: $" + precioTotal);

    }

    private void generarFactura(String idPaquete) {
        Document pdfDoc = null;
        FileOutputStream fos = null;

        try {
            MongoDatabase database = ConexionMongo.getDatabase();
            MongoCollection<org.bson.Document> clientesCollection = database.getCollection("RegistrosClientes");
            MongoCollection<org.bson.Document> paquetesCollection = database.getCollection("PaquetesTuristicos");

            //Se busca al cliente usando el correo proporcionado
            org.bson.Document cliente = clientesCollection.find(new org.bson.Document("correo", correoCliente)).first();
            String nombreCliente = cliente.getString("nombre");

            //Se busca al paquete usando su ID
            org.bson.Document paquete = paquetesCollection.find(new org.bson.Document("idPaquete", idPaquete)).first();


            //preparar PDF
            String filePath = "Factura_" + idPaquete + "_" + correoCliente + ".pdf";
            fos = new FileOutputStream(filePath);
            pdfDoc = new Document();
            PdfWriter.getInstance(pdfDoc, fos);
            pdfDoc.open();

            //agregar contenido al PDF
            pdfDoc.add(new Paragraph("Factura de Compra"));
            pdfDoc.add(new Paragraph("*******************************"));
            pdfDoc.add(new Paragraph("Cliente: " + nombreCliente));
            pdfDoc.add(new Paragraph("Correo: " + correoCliente));
            pdfDoc.add(new Paragraph("Paquete Turístico:"));
            pdfDoc.add(new Paragraph(" - ID: " + idPaquete));
            pdfDoc.add(new Paragraph(" - Origen: " + paquete.getString("origen")));
            pdfDoc.add(new Paragraph("- Destino: " + paquete.getString("destino")));
            pdfDoc.add(new Paragraph("  - Fecha de Salida: " + paquete.getString("fechaSalida")));
            pdfDoc.add(new Paragraph("  - Precio Total: $" + costoTotal.getText().split("\\$")[1]));
            pdfDoc.add(new Paragraph("*******************************"));
            pdfDoc.add(new Paragraph("¡Gracias por su compra!"));

            JOptionPane.showMessageDialog(DetallesCompraP, "Factura generada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(DetallesCompraP, "Error al generar la factura.", "Error", JOptionPane.ERROR_MESSAGE);
        }finally {
            if (pdfDoc!=null && pdfDoc.isOpen()) {
                pdfDoc.close();
            }
            try {
                if (fos!=null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void CANCELAR(){
        SwingUtilities.getWindowAncestor(DetallesCompraP).dispose();
        JFrame frameEditar = new JFrame("Mostrar Paquetes");
        MostrarPaquetesCliente mostrarPaquetesCliente = new MostrarPaquetesCliente(correoCliente);
        frameEditar.setContentPane(mostrarPaquetesCliente.MostrarPaqueteP);
        frameEditar.setPreferredSize(new Dimension(1200,700));
        frameEditar.pack();
        frameEditar.setLocationRelativeTo(null);
        frameEditar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameEditar.setVisible(true);
    }
}
