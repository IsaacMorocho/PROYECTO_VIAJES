package org.example;

import javax.swing.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class MostrarUsuario {
    public JPanel MostrarUsuarioP;
    private JTable tablaUsuarios;
    private JButton editarUsuariosBtn;
    private JScrollPane scrollPane;
    private JButton RegresarBtn;

    public MostrarUsuario() {
        scrollPane.setViewportView(tablaUsuarios);
        cargarUsuarios();
        //Botones
        editarUsuariosBtn.addActionListener(e -> EditarUsuario());
        RegresarBtn.addActionListener(e -> Regresar());

    }
    private void cargarUsuarios() {
        try {
            MongoDatabase database = ConexionMongo.getDatabase();
            MongoCollection<Document> collection = database.getCollection("RegistrosClientes");
            //Añadimos el nombre de las columnas a la tabla
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID (Default MongoDB)");
            tableModel.addColumn("Nombre");
            tableModel.addColumn("Correo");
            tableModel.addColumn("Contraseña");
            tablaUsuarios.setModel(tableModel);

            //Obtener los datos desde MongoDB y agregarlos al modelo de la tabla
            MongoCursor<Document> cursor = collection.find().iterator();
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Vector<String> row = new Vector<>();
                row.add(doc.getObjectId("_id").toString());
                row.add(doc.getString("nombre"));
                row.add(doc.getString("correo"));
                row.add(doc.getString("contrasena"));
                tableModel.addRow(row);
            }
            //Se le asigna el modelo Jtable a la tabla usuarios
            tablaUsuarios.setModel(tableModel);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(MostrarUsuarioP, "Error al cargar los usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void EditarUsuario() {
        SwingUtilities.getWindowAncestor(MostrarUsuarioP).dispose();
        JFrame frameAdmin = new JFrame("Actualizar Usuarios");
        ActualizarUsuario actualizarUsuario = new ActualizarUsuario();
        frameAdmin.setContentPane(actualizarUsuario.ActualizarUsuarioP);
        frameAdmin.setLocationRelativeTo(null);
        frameAdmin.setSize(800, 600);
        frameAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAdmin.setVisible(true);
    }
    private void Regresar() {
        SwingUtilities.getWindowAncestor(MostrarUsuarioP).dispose();
        JFrame frameAdmin = new JFrame("Panel Administrador");
        PanelAdmin panelAdmin = new PanelAdmin();
        frameAdmin.setContentPane(panelAdmin.AdminCrud);
        frameAdmin.setLocationRelativeTo(null);
        frameAdmin.setSize(800, 600);
        frameAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAdmin.setVisible(true);
    }
}
