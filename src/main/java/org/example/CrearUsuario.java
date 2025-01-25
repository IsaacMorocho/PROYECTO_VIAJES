package org.example;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearUsuario {
    private JTextField UserNombreCreartxt;
    private JTextField UserCorreoCreartxt;
    private JPasswordField UserContraCreartxt;
    private JButton GuardarUserbtn;
    private JButton Cancelarbtn;
    public JPanel CrearUsuarioP;

    public CrearUsuario() {

        GuardarUserbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = UserNombreCreartxt.getText().trim();
                String correo = UserCorreoCreartxt.getText().trim();
                String contrasena = new String(UserContraCreartxt.getPassword());
                if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(CrearUsuarioP, "Por favor, llena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Insertar en la base de datos
                try {
                    MongoDatabase database = ConexionMongo.getDatabase();
                    MongoCollection<Document> collection = database.getCollection("RegistrosClientes");
                    Document nuevoUsuario = new Document("nombre", nombre)
                            .append("correo", correo)
                            .append("contrasena", contrasena);
                    collection.insertOne(nuevoUsuario);

                    // Mostrar mensaje de éxito y limpiar los campos
                    JOptionPane.showMessageDialog(CrearUsuarioP, "Usuario creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    UserNombreCreartxt.setText("");
                    UserCorreoCreartxt.setText("");
                    UserContraCreartxt.setText("");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(CrearUsuarioP, "Error al guardar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            });

        Cancelarbtn.addActionListener(e -> {
            JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(CrearUsuarioP);
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
