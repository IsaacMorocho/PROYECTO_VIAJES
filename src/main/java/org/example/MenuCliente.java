package org.example;

import javax.swing.*;
import java.awt.*;

public class MenuCliente {
    public JPanel MenuClienteP;
    private JButton verTodosLosPaquetesButton;
    private JButton cerrarSesionBtn;
    private JButton comprarUnPaqueteBtn;
    private JLabel quitoIMG;
    private JLabel galapagosIMG;
    private JLabel cuencaIMG;
    private JLabel usuarioCuentaTxt;
    private String correo;
    public MenuCliente(String correo) {
        usuarioCuentaTxt.setText("Usuario: "+ correo);
        this.correo = correo;

        //Imagenes
        quitoIMG.setIcon(cargarImagenAbsoluta("C:\\Users\\USER\\Documents\\POO\\PROYECTOS POO\\PROYECTO_VIAJES\\src\\imagenes\\quito.jpg", 200, 150));
        galapagosIMG.setIcon(cargarImagenAbsoluta("C:\\Users\\USER\\Documents\\POO\\PROYECTOS POO\\PROYECTO_VIAJES\\src\\imagenes\\galapagos.jpg", 200, 150));
        cuencaIMG.setIcon(cargarImagenAbsoluta("C:\\Users\\USER\\Documents\\POO\\PROYECTOS POO\\PROYECTO_VIAJES\\src\\imagenes\\cuenca.jpg", 200, 150));

        verTodosLosPaquetesButton.addActionListener(e -> MENUCLIENTE());
        cerrarSesionBtn.addActionListener(e -> CERRAR());
    }

    private ImageIcon cargarImagenAbsoluta(String ruta, int ancho, int alto) {
            //Se carga la imagen con su ruta completa
            ImageIcon iconoOriginal = new ImageIcon(ruta);
            //Establecer valores de entrada para su alto y ancho
            Image imagen = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagen);
    }
    private void MENUCLIENTE() {
        SwingUtilities.getWindowAncestor(MenuClienteP).dispose();
        JFrame frameEditar = new JFrame("Mostrar Paquetes");
        MostrarPaquetesCliente mostrarPaquetesCliente = new MostrarPaquetesCliente(correo);
        frameEditar.setContentPane(mostrarPaquetesCliente.MostrarPaqueteP);
        frameEditar.setLocationRelativeTo(null);
        frameEditar.setSize(800, 600);
        frameEditar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameEditar.setVisible(true);
    }
    private void CERRAR() {
        SwingUtilities.getWindowAncestor(MenuClienteP).dispose();
        JFrame frameAdmin = new JFrame("ROLES");
        Roles roles=new Roles();
        frameAdmin.setContentPane(roles.RolesP);
        frameAdmin.setLocationRelativeTo(null);
        frameAdmin.setSize(800, 600);
        frameAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAdmin.setVisible(true);
    }
}
