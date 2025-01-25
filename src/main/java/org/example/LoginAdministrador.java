package org.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import javax.swing.*;

public class LoginAdministrador {
    public JPanel loginA;
    private JTextField UserAdmintxt;
    private JButton sesionAdminbtn;
    private JPasswordField PasswAdmintxt;
    private JButton regresarBtn;

    public LoginAdministrador() {
        sesionAdminbtn.addActionListener(e -> iniciarSesion());
        regresarBtn.addActionListener(e -> regresarInterfazRoles());
    }

    private void iniciarSesion() {
        String username = UserAdmintxt.getText().trim();
        String password = new String(PasswAdmintxt.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginA, "Por favor, llena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (autenticarAdministrador(username, password)) {
            JOptionPane.showMessageDialog(loginA, "¡Bienvenido, Administrador!");
            SwingUtilities.getWindowAncestor(loginA).dispose();
            abrirPanelAdministrador();
        } else {
            JOptionPane.showMessageDialog(loginA, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean autenticarAdministrador(String username, String password) {
        try {
            MongoDatabase database = ConexionMongo.getDatabase();
            MongoCollection<Document> collection = database.getCollection("UsuariosAdmin");
            //Buscar el documento que coincida con el usuario y contraseña
            Document query = new Document("username", username).append("password", password);
            Document admin = collection.find(query).first();

            //Retorna verdadero si el documento existe
            return admin != null;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(loginA, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void abrirPanelAdministrador() {
        JFrame frame = new JFrame("Panel Administrador");
        frame.setContentPane(new PanelAdmin().AdminCrud);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void regresarInterfazRoles() {
        SwingUtilities.getWindowAncestor(loginA).dispose();
        JFrame frame = new JFrame("Roles interfaz");
        frame.setContentPane(new Roles().RolesP);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
