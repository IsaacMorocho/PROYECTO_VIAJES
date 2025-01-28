package org.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CuentasClientes {
    public JPanel CuentasP;
    public JTextField NombreCuentaTxt;
    private JPasswordField PasswCuentaTxt;
    private JButton CrearCuentaBtn;
    private JButton IrLoginBtn;
    private JCheckBox VerContraTxt;
    private JTextField CorreoCuentaTxt;

    public CuentasClientes() {
        VerContraTxt.addActionListener(e -> {
            if (VerContraTxt.isSelected()) {
                PasswCuentaTxt.setEchoChar((char) 0); //Indicara la contrasea por caracteres
            } else {
                PasswCuentaTxt.setEchoChar('•'); //Oculta la contraseña por puntos
            }
        });
        CrearCuentaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = NombreCuentaTxt.getText().trim();
                String correo = CorreoCuentaTxt.getText().trim();
                String contrasena = String.valueOf(PasswCuentaTxt.getPassword()).trim();

                //Validaciones
                if (nombre.isEmpty() ||correo.isEmpty() ||contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(CuentasP, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (contrasena.length()<6) {
                    JOptionPane.showMessageDialog(CuentasP, "La contraseña debe tener al menos 6 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                registrarCliente(nombre, correo, contrasena);
            }
        });
        IrLoginBtn.addActionListener(e -> REGRESAR());
    }
    private void registrarCliente(String nombre, String correo, String contrasena) {
        try {
            MongoDatabase database = ConexionMongo.getDatabase();
            MongoCollection<Document> collection = database.getCollection("RegistrosClientes");

            //Verificacion de si el correo ya existe en la coleccion.
            Document correoRepetido = collection.find(new Document("correo", correo)).first();
            if (correoRepetido != null) {
                JOptionPane.showMessageDialog(CuentasP, "El correo no esta disponible. Intente con otro.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Document cliente = new Document("nombre", nombre)
                    .append("correo", correo)
                    .append("contrasena", contrasena);
            collection.insertOne(cliente);

            JOptionPane.showMessageDialog(CuentasP, "Cuenta creada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            NombreCuentaTxt.setText("");
            CorreoCuentaTxt.setText("");
            PasswCuentaTxt.setText("");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(CuentasP, "Ocurrió un error al registrar la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void REGRESAR(){
        SwingUtilities.getWindowAncestor(CuentasP).dispose();
        JFrame frameAdmin = new JFrame("Login Cliente");
        LoginCliente loginCliente = new LoginCliente();
        frameAdmin.setContentPane(loginCliente.LoginC);
        frameAdmin.setLocationRelativeTo(null);
        frameAdmin.setSize(800, 600);
        frameAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAdmin.setVisible(true);
    }
}
