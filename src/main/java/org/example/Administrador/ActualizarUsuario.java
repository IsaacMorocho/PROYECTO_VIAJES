package org.example.Administrador;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.ConexionMongo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActualizarUsuario {
    public JPanel ActualizarUsuarioP;
    private JTextField correoBuscartxt;
    private JTextField nuevoNombretxt;
    private JTextField nuevoCorreotxt;
    private JPasswordField nuevaContrasenatxt;
    private JButton actualizarBtn;
    private JButton cancelarBtn;

    public ActualizarUsuario() {
        actualizarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correoBuscar = correoBuscartxt.getText().trim();
                String nuevoNombre = nuevoNombretxt.getText().trim();
                String nuevoCorreo = nuevoCorreotxt.getText().trim();
                String nuevaContrasena = new String(nuevaContrasenatxt.getPassword());
                if (correoBuscar.isEmpty() || nuevoNombre.isEmpty() || nuevoCorreo.isEmpty() || nuevaContrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(ActualizarUsuarioP, "Por favor, llena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    MongoDatabase database = ConexionMongo.getDatabase();
                    MongoCollection<Document> collection = database.getCollection("RegistrosClientes");
                    //buscamos al cliente por su correo
                    Document buscar = new Document("correo", correoBuscar);
                    Document usuarioExistente = collection.find(buscar).first();

                    if (usuarioExistente != null) {
                        Document nuevosDatos = new Document("nombre", nuevoNombre)
                                .append("correo", nuevoCorreo)
                                .append("contrasena", nuevaContrasena);

                        collection.updateOne(buscar, new Document("$set", nuevosDatos));
                        JOptionPane.showMessageDialog(ActualizarUsuarioP, "Cliente actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        correoBuscartxt.setText("");
                        nuevoNombretxt.setText("");
                        nuevoCorreotxt.setText("");
                        nuevaContrasenatxt.setText("");
                    } else {
                        JOptionPane.showMessageDialog(ActualizarUsuarioP, "No se encontró el cliente con el correo proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ActualizarUsuarioP, "Error al actualizar al cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancelarBtn.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(ActualizarUsuarioP).dispose();
            JFrame frame = new JFrame("Panel Administrador");
            frame.setContentPane(new PanelAdmin().AdminCrud);
            frame.setPreferredSize(new Dimension(1500, 750));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
