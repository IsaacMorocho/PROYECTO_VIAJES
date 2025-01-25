package org.example;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import javax.swing.*;
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

                // Validar que todos los campos estén completos
                if (correoBuscar.isEmpty() || nuevoNombre.isEmpty() || nuevoCorreo.isEmpty() || nuevaContrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(ActualizarUsuarioP, "Por favor, llena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // Conectar a la base de datos y colección
                    MongoDatabase database = ConexionMongo.getDatabase();
                    MongoCollection<Document> collection = database.getCollection("RegistrosClientes");

                    // Buscar usuario por correo
                    Document buscar = new Document("correo", correoBuscar);
                    Document usuarioExistente = collection.find(buscar).first();

                    if (usuarioExistente != null) {
                        // Crear el documento con los nuevos valores
                        Document nuevosDatos = new Document("nombre", nuevoNombre)
                                .append("correo", nuevoCorreo)
                                .append("contrasena", nuevaContrasena);

                        collection.updateOne(buscar, new Document("$set", nuevosDatos));

                        JOptionPane.showMessageDialog(ActualizarUsuarioP, "Cliente actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                        // Limpiar los campos
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
            JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(ActualizarUsuarioP);
            if (frameActual != null) {
                frameActual.dispose();
            }
            JFrame frameAdmin = new JFrame("Panel Administrador");
            PanelAdmin panelAdmin = new PanelAdmin();
            frameAdmin.setContentPane(panelAdmin.AdminCrud);
            frameAdmin.setLocationRelativeTo(null);
            frameAdmin.setSize(800, 600);
            frameAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameAdmin.setVisible(true);
        });
    }
}
