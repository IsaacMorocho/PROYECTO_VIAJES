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
    private JButton eliminarPaqueteBtn;
    public JPanel AdminCrud;
    private JButton cerrasSesionBtn;

    public PanelAdmin() {
        //Accion de los botones
        //CLIENTES
        crearUsuariosbtn.addActionListener( e -> CREAR());
        actualizarUsuariobtn.addActionListener( e -> ACTUALIZAR());
        mostrarUsuariosBtn.addActionListener(e -> MOSTRAR());
        eliminarUsuariosBtn.addActionListener(e -> ELIMINAR());
        //PAQUETES
        crearUnNuevoPaqueteButton.addActionListener(e  -> CREARP());
        actualizarDatosDeUnButton.addActionListener( e -> ACTUALIZARP());
        mostrarPaquetesButton.addActionListener(e -> MOSTRARP());
        eliminarPaqueteBtn.addActionListener(e -> ELIMINARP());
        cerrasSesionBtn.addActionListener( e -> CERRAR());

    }
    //Manejo de metodos para los JFrames
    private void CREAR(){
        JFrame frame = new JFrame("Crear Usuarios");
        frame.setContentPane(new CrearUsuario().CrearUsuarioP);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        SwingUtilities.getWindowAncestor(AdminCrud).dispose();
    }

    private void ACTUALIZAR(){
        JFrame frame = new JFrame("Actualizar Usuarios");
        frame.setContentPane(new ActualizarUsuario().ActualizarUsuarioP);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        SwingUtilities.getWindowAncestor(AdminCrud).dispose();
    }
    private void MOSTRAR(){
        JFrame frame = new JFrame("Mostrar Usuarios");
        frame.setContentPane(new MostrarUsuario().MostrarUsuarioP);
        frame.setPreferredSize(new Dimension(1000, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        SwingUtilities.getWindowAncestor(AdminCrud).dispose();
    }
    private void ELIMINAR(){
        JFrame frame = new JFrame("Eliminar Usuarios");
        frame.setContentPane(new EliminarUsuario().EliminarUsuarioP);
        frame.setPreferredSize(new Dimension(990, 750));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        SwingUtilities.getWindowAncestor(AdminCrud).dispose();

    }
    //PAQUETES
    private void CREARP(){
        JFrame frame = new JFrame("Crear Paquetes");
        frame.setContentPane(new CrearPaquetes().CrearPaqueteP);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        SwingUtilities.getWindowAncestor(AdminCrud).dispose();

    }
    private void ACTUALIZARP(){
        JFrame frame = new JFrame("Actualizar Paquetes");
        frame.setContentPane(new ActualizarPaquetes().ActualizarPaqueteP);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        SwingUtilities.getWindowAncestor(AdminCrud).dispose();
    }
    private void MOSTRARP(){
        JFrame frame = new JFrame("Mostrar Paquetes");
        frame.setContentPane(new MostrarPaquetes().MostrarPaqueteP);
        frame.setPreferredSize(new Dimension(1400, 500));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        SwingUtilities.getWindowAncestor(AdminCrud).dispose();
    }
    private void ELIMINARP(){
        JFrame frame = new JFrame("Eliminar Paquetes");
        frame.setContentPane(new EliminarPaquetes().EliminarPaqueteP);
        frame.setPreferredSize(new Dimension(1500, 700));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        SwingUtilities.getWindowAncestor(AdminCrud).dispose();
    }
    private void CERRAR(){
        SwingUtilities.getWindowAncestor(AdminCrud).dispose();
        JFrame frame = new JFrame("TRAVELBUDDY");
        frame.setContentPane(new Roles().RolesP);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
