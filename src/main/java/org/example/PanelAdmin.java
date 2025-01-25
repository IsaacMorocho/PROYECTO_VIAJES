package org.example;
import javax.swing.*;
import java.awt.*;

public class PanelAdmin {
    private JButton crearUsuariosbtn;
    private JButton actualizarUsuariobtn;
    private JButton mostrarUsuariosBtn;
    private JButton eliminarUsuariosBtn;
    private JButton crearUnNuevoPaqueteButton;
    private JButton actualizarDatosDeUnButton;
    private JButton mostrarPaquetesButton;
    private JButton eliminarUnPaqueteButton;
    public JPanel AdminCrud;
    private JButton cerrasSesionBtn;

    public PanelAdmin() {
        //Accion de los botones
        crearUsuariosbtn.addActionListener( e -> CREAR());
        actualizarUsuariobtn.addActionListener( e -> ACTUALIZAR());
        mostrarUsuariosBtn.addActionListener(e -> MOSTRAR());
        eliminarUsuariosBtn.addActionListener(e -> ELIMINAR());
        cerrasSesionBtn.addActionListener( e -> CERRAR());

    }
    //Manejo de metodos para los JFrames
    private void CREAR(){
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

    private void ACTUALIZAR(){
        JFrame frame = new JFrame("Actualizar Usuarios");
        frame.setContentPane(new ActualizarUsuario().ActualizarUsuarioP);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        SwingUtilities.getWindowAncestor(AdminCrud).dispose();
    }
    private void MOSTRAR(){
        JFrame frame = new JFrame("Mostrar Usuarios");
        frame.setContentPane(new MostrarUsuario().MostrarUsuarioP);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        SwingUtilities.getWindowAncestor(AdminCrud).dispose();
    }
    private void ELIMINAR(){
        JFrame frame = new JFrame("Eliminar Usuarios");
        frame.setContentPane(new EliminarUsuario().EliminarUsuarioP);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        SwingUtilities.getWindowAncestor(AdminCrud).dispose();

    }
    private void CERRAR(){
        JFrame frameActual = (JFrame) SwingUtilities.getWindowAncestor(AdminCrud);
        frameActual.dispose();
        JFrame frameAdmin = new JFrame("ROLES");
        Roles roles = new Roles();
        frameAdmin.setContentPane(roles.RolesP);
        frameAdmin.setLocationRelativeTo(null);
        frameAdmin.setSize(800, 600);
        frameAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAdmin.setVisible(true);
    }
}
