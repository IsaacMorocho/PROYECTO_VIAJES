package org.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static jdk.internal.org.jline.utils.AttributedStringBuilder.append;

public class LoginAdministrador {
    public JPanel loginA;
    private JTextField UserAdmintxt;
    private JButton sesionAdminbtn;
    private JPasswordField PasswAdmintxt;

    // Credenciales predefinidas del administrador
    private static final String userAdmin = "admin";
    private static final String passwAdmin = "2003";

    public LoginAdministrador() {
        // Agregar el usuario por defecto a la colección al inicializar la interfaz
        inicializarAdminEnMongo();

        sesionAdminbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = UserAdmintxt.getText().trim();
                String password = new String(PasswAdmintxt.getPassword());

                // Validar campos vacíos
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(loginA, "Por favor, llena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar credenciales en MongoDB
                if (autenticarAdministrador(username, password)) {
                    JOptionPane.showMessageDialog(loginA, "¡Bienvenido, Administrador!");
                    SwingUtilities.getWindowAncestor(loginA).dispose();
                    // Lógica para abrir la siguiente interfaz
                    abrirPanelAdministrador();
                } else {
                    JOptionPane.showMessageDialog(loginA, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean autenticarAdministrador(String username, String password) {
        try {
            // Obtener la base de datos y la colección
            MongoDatabase database = ConexionMongo.getDatabase();
            MongoCollection<Document> collection = database.getCollection("UsuariosAdmin");

            // Buscar documento que coincida con el usuario y contraseña
            Document query = new Document("username", username).append("password", password);
            Document admin = collection.find(query).first();

            // Retorna true si el documento existe, false en caso contrario
            return admin != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lógica para abrir el panel de administrador después de iniciar sesión
     */
    public void abrirPanelAdministrador() {
        System.out.println("Redirigiendo al panel de administrador...");
        // Aquí debes implementar la lógica para mostrar la interfaz de administrador
        JFrame frame = new JFrame("Panel Administrador");
        frame.setContentPane(new PanelAdmin().AdminCrud);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void inicializarAdminEnMongo() {
        try {
            // Obtener la base de datos y la colección
            MongoDatabase database = ConexionMongo.getDatabase();
            MongoCollection<Document> collection = database.getCollection("UsuariosAdmin");

            // Verificar si el usuario administrador ya existe
            Document query = new Document("username", userAdmin);
            Document admin = collection.find(query).first();

            // Si no existe, insertarlo en la colección
            if (admin == null) {
               // Document nuevoAdmin = new Document("username", userAdmin);
                 //       .append("password", passwAdmin);
               // collection.insertOne(nuevoAdmin);
                System.out.println("Usuario administrador agregado en la base de datos.");
            } else {
                System.out.println("El usuario administrador ya existe.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
