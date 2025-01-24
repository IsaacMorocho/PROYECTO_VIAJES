package org.example;

import javax.swing.*;
import java.awt.*;

public class Roles {
    private JRadioButton administradorRadioButton;
    private JRadioButton clienteRadioButton;
    private JRadioButton invitadoRadioButton;
    private JButton iniciarSesionButton;
    public JPanel RolesP;
    private JComboBox<String> OpcionesRolBox;
    private JButton Siguientebtn;
    private String rolSeleccionado;

    public Roles() {
        //Se añade los roles como opcion en JComboBox
        OpcionesRolBox.addItem("Administrador");
        OpcionesRolBox.addItem("Cliente");
        OpcionesRolBox.addItem("Invitado");
        //Para garantizar la seleccion de rol
        //Inicializamos la variable rolSeleccionado con el valor predeterminado del JComboBox
        rolSeleccionado = (String) OpcionesRolBox.getSelectedItem();

        OpcionesRolBox.addActionListener(e -> {
            rolSeleccionado = (String) OpcionesRolBox.getSelectedItem();
            System.out.println("Rol seleccionado: " + rolSeleccionado);
        });

        Siguientebtn.addActionListener(e -> {
            if (rolSeleccionado == null || rolSeleccionado.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona un rol antes de continuar.");
            } else {
                abrirLoginPorRol(rolSeleccionado);
                SwingUtilities.getWindowAncestor(RolesP).dispose();
            }
        });


    /*public static void main(String[] args) {
        //Conexión a MongoDB
        ConexionMongo.getDatabase();
        // Lanza  de usuario
        SwingUtilities.invokeLater(Roles::new);
    }

     */
    }
    private void abrirLoginPorRol(String rol) {
        switch (rol) {
            case "Administrador":
                JFrame frame = new JFrame("Administrador");
                frame.setContentPane(new LoginAdministrador().loginA);
                frame.setLocationRelativeTo(null);
                frame.setSize(800, 600);
                frame.setPreferredSize(new Dimension(800, 600));
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

                break;
            case "Cliente":
                JOptionPane.showMessageDialog(null, "Login de cliente no implementado aún.");
                break;
            case "Invitado":
                JOptionPane.showMessageDialog(null, "Login de invitado no implementado aún.");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Rol desconocido.");
        }
    }

}


