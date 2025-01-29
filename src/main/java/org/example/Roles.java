package org.example;

import javax.swing.*;
import java.awt.*;

public class Roles {
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
                abrirLoginPorRol(rolSeleccionado);
                SwingUtilities.getWindowAncestor(RolesP).dispose();
        });
    }
    private void abrirLoginPorRol(String rol) {
        switch (rol) {
            case "Administrador":
                JFrame frame = new JFrame("Administrador");
                frame.setContentPane(new LoginAdministrador().loginA);
                frame.setSize(850, 1900);
                frame.setPreferredSize(new Dimension(1080, 580));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                break;
            case "Cliente":
                JFrame frame2 = new JFrame("Cliente");
                frame2.setContentPane(new LoginCliente().LoginC);
                frame2.setSize(800, 600);
                frame2.setPreferredSize(new Dimension(800, 600));
                frame2.pack();
                frame2.setLocationRelativeTo(null);
                frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame2.setVisible(true);
                break;
            case "Invitado":
                JFrame frame3 = new JFrame("Invitado");
                frame3.setContentPane(new MenuInvitado().MenuInvitadoP);
                frame3.setLocationRelativeTo(null);
                frame3.setSize(1920, 1080);
                frame3.setPreferredSize(new Dimension(1920, 1080));
                frame3.pack();
                frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame3.setVisible(true);                break;
            default:
              JOptionPane.showMessageDialog(null, "Rol desconocido.");
        }
    }

}


