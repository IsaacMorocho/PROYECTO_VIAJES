package org.example;

import javax.swing.*;
import java.awt.*;
public class Main {
    public static void main(String[] args) {
        //String url = "mongodb+srv://joshuamorocho:Joshua2002@cluster0.rc4vm.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        ConexionMongo.getDatabase();
        JFrame frame = new JFrame("TRAVELBUDDY");
        frame.setContentPane(new LOGIN().loginP);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }
}