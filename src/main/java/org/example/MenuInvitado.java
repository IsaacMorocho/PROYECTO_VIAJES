package org.example;

import javax.swing.*;
import java.awt.*;

public class MenuInvitado {
    private JButton verListaDePaquetesButton;
    private JLabel quitoIMG;
    private JLabel galapagosIMG;
    private JLabel cuencaIMG;
    public JPanel MenuInvitadoP;
    private JButton regresarBtn;

    public MenuInvitado() {
        quitoIMG.setIcon(cargarImagenAbsoluta("C:\\Users\\USER\\Documents\\POO\\PROYECTOS POO\\PROYECTO_VIAJES\\src\\imagenes\\quito.jpg", 490, 360));
        galapagosIMG.setIcon(cargarImagenAbsoluta("C:\\Users\\USER\\Documents\\POO\\PROYECTOS POO\\PROYECTO_VIAJES\\src\\imagenes\\galapagos.jpg", 490, 360));
        cuencaIMG.setIcon(cargarImagenAbsoluta("C:\\Users\\USER\\Documents\\POO\\PROYECTOS POO\\PROYECTO_VIAJES\\src\\imagenes\\cuenca.jpg", 490, 360));

        verListaDePaquetesButton.addActionListener(e -> PaquetesInvitados());
        regresarBtn.addActionListener(e -> CERRAR());
    }
    private ImageIcon cargarImagenAbsoluta(String ruta, int ancho, int alto) {
        //Se carga la imagen con su ruta completa
        ImageIcon iconoOriginal = new ImageIcon(ruta);
        //Establecer valores de entrada para su alto y ancho
        Image imagen = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }
    private void PaquetesInvitados() {
        SwingUtilities.getWindowAncestor(MenuInvitadoP).dispose();
        JFrame frameEditar = new JFrame("Mostrar Paquetes");
        MostrarPaquetesInvitado mostrarPaquetesInvitado= new MostrarPaquetesInvitado();
        frameEditar.setContentPane(mostrarPaquetesInvitado.MostrarPaquetesInvP);
        frameEditar.setPreferredSize(new Dimension(1200,700));
        frameEditar.pack();
        frameEditar.setLocationRelativeTo(null);
        frameEditar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameEditar.setVisible(true);
    }
    private void CERRAR() {
        SwingUtilities.getWindowAncestor(MenuInvitadoP).dispose();
        JFrame frame = new JFrame("TRAVELBUDDY");
        frame.setContentPane(new Roles().RolesP);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
