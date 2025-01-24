package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAdmin {
    private JButton crearUsuariosbtn;
    private JButton actualizarInformacionDeUsuariosButton;
    private JButton mostrarUsuariosButton;
    private JButton eliminarUsuariosButton;
    private JButton crearUnNuevoPaqueteButton;
    private JButton actualizarDatosDeUnButton;
    private JButton mostrarPaquetesButton;
    private JButton eliminarUnPaqueteButton;
    public JPanel AdminCrud;

    public PanelAdmin() {
        crearUsuariosbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //CrearUsuario crearUsuario = new CrearUsuario();
                //crearUsuario.mostrarFormulario();
                JFrame frame = new JFrame("Crear Usuarios");
                frame.setContentPane(new CrearUsuario().CrearUsuarioP);
                frame.setLocationRelativeTo(null);
                frame.setSize(800, 600);
                frame.setPreferredSize(new Dimension(800, 600));
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                SwingUtilities.getWindowAncestor(AdminCrud).dispose();

            }
        });
    }
}
