/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectocalculo;

/**
 *
 * @author juanpablo
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuDerivacion extends JFrame {
    

    public MenuDerivacion() {
        setTitle("Menú de Derivación");
        setSize(400, 300);
        setLocationRelativeTo(null); // Centra la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnRolle = new JButton("Teorema de Rolle");
        JButton btnValorMedio = new JButton("Teorema del Valor Medio");
        JButton btnNewton = new JButton("Método de Newton");
        JButton btnMaxMin = new JButton("Máximos y Mínimos");

        add(btnRolle);
        add(btnValorMedio);
        add(btnNewton);
        add(btnMaxMin);

        // Eventos de los botones (abrir nuevas ventanas)
        btnRolle.addActionListener(e -> {
            new VentanaRolle().setVisible(true);
        });

        btnValorMedio.addActionListener(e -> {
            new VentanaValorMedio().setVisible(true);
            
        });

        btnNewton.addActionListener(e -> {
            new VentanaNewton().setVisible(true);
        });

        btnMaxMin.addActionListener(e -> {
            new VentanaMaxyMin().setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuDerivacion().setVisible(true);
        });
    }
}

