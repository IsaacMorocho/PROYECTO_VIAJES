package org.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;

public class LoginCliente {
    public JPanel LoginC;
    private JTextField CorreoClientetxt;
    private JButton sesionClientebtn;
    private JButton CrearCuentaClientebtn;
    private JButton regresarBtn;
    private JPasswordField PasswClientetxt;
    private JCheckBox VerContraTxt;

    public LoginCliente() {
        sesionClientebtn.addActionListener(e -> iniciarSesionCliente());
        CrearCuentaClientebtn.addActionListener(e -> REGISTRACION());
        regresarBtn.addActionListener(e -> REGRESAR());
        VerContraTxt.addActionListener(e -> {
            if (VerContraTxt.isSelected()) {
                PasswClientetxt.setEchoChar((char) 0); //Indicara la contrasea por caracteres
            } else {
                PasswClientetxt.setEchoChar('•'); //Oculta la contraseña por puntos
            }
        });
    }
    private void iniciarSesionCliente() {
        String correo = CorreoClientetxt.getText().trim();
        String password = new String(PasswClientetxt.getPassword());
        if (correo.isEmpty() ||password.isEmpty()) {
            JOptionPane.showMessageDialog(LoginC, "Por favor, llena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (autenticarCliente(correo, password)) {
            JOptionPane.showMessageDialog(LoginC, "¡Bienvenido!");
            SwingUtilities.getWindowAncestor(LoginC).dispose();
            abrirMenuCliente(correo);
        } else {
            JOptionPane.showMessageDialog(LoginC, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private boolean autenticarCliente(String correo, String password) {
        try {
            MongoDatabase database = ConexionMongo.getDatabase();
            MongoCollection<Document> collection = database.getCollection("RegistrosClientes");
            //Buscar el documento que coincida con el usuario y contraseña
            Document query = new Document("correo", correo).append("contrasena", password);
            Document cliente = collection.find(query).first();

            //Retorna verdadero si el documento existe
            return cliente != null;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(LoginC, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    private void abrirMenuCliente(String correo){
        JFrame frame = new JFrame("Menu Cliente");
        MenuCliente menuCliente = new MenuCliente(correo);
        frame.setContentPane(menuCliente.MenuClienteP);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void REGISTRACION() {
        JFrame frameAdmin = new JFrame("Crear Cuenta Cliente ");
        CuentasClientes cuentasClientes = new CuentasClientes();
        frameAdmin.setContentPane(cuentasClientes.CuentasP);
        frameAdmin.setPreferredSize(new Dimension(800, 600));
        frameAdmin.pack();
        frameAdmin.setLocationRelativeTo(null);
        frameAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAdmin.setVisible(true);
        SwingUtilities.getWindowAncestor(LoginC).dispose();
    }
    private void REGRESAR(){
        SwingUtilities.getWindowAncestor(LoginC).dispose();
        JFrame frame = new JFrame("TRAVELBUDDY");
        frame.setContentPane(new Roles().RolesP);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
