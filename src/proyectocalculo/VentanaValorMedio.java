package proyectocalculo;

import javax.swing.*;
import java.awt.*;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class VentanaValorMedio extends JFrame {
    private JTextField tfFuncion, tfA, tfB;
    private JTextArea taResultados;
    private JButton btnCalcular;
    Metodos metodos = new Metodos();
    
    public VentanaValorMedio() {
        setTitle("Teorema Valor Medio");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Panel de entrada con labels y campos de texto
        JPanel panelEntrada = new JPanel(new GridLayout(4, 2, 5, 5));
        panelEntrada.add(new JLabel("Función f(x):"));
        tfFuncion = new JTextField();
        panelEntrada.add(tfFuncion);

        panelEntrada.add(new JLabel("Valor de a:"));
        tfA = new JTextField();
        panelEntrada.add(tfA);

        panelEntrada.add(new JLabel("Valor de b:"));
        tfB = new JTextField();
        panelEntrada.add(tfB);

        btnCalcular = new JButton("Calcular");
        panelEntrada.add(btnCalcular);

        // Área para mostrar resultados
        taResultados = new JTextArea();
        taResultados.setEditable(false);
        JScrollPane scroll = new JScrollPane(taResultados);

        // Layout general de la ventana
        setLayout(new BorderLayout(10, 10));
        add(panelEntrada, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // Acción del botón calcular
        btnCalcular.addActionListener(e -> calcularTeoremaValorMedio());
    }

    private void calcularTeoremaValorMedio() {
        String funcion = tfFuncion.getText().toLowerCase().replace(" ", "");
        double a, b;

        try {
            a = Double.parseDouble(tfA.getText());
            b = Double.parseDouble(tfB.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos para a y b.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double fa = evaluarFuncion(funcion, a);
            double fb = evaluarFuncion(funcion, b);

            if (Math.abs(fa - fb) < 1e-10) {
                taResultados.setText("No se puede aplicar el Teorema del Valor Medio porque F(a) = F(b).");
                return;
            }

            double pendiente = (fb - fa) / (b - a);
            double c = (a + b) / 2;
            double derivadaEnC = derivada(funcion, c);
            double fc = evaluarFuncion(funcion, c);

            StringBuilder sb = new StringBuilder();
            sb.append("Función original: ").append(funcion).append("\n");
            sb.append("Función derivada: derivada numérica (no simbólica)").append("\n");
            sb.append(String.format("Valor de derivada en c = %.5f\n", derivadaEnC));
            sb.append(String.format("Valor sustituyendo x=c en la función original (F(c)) = %.5f\n", fc));
            sb.append(String.format("Pendiente entre a y b = %.5f\n\n", pendiente));
            sb.append("Tabla de valores:\n");
            sb.append(" x\t|\tF(x)\n");
            sb.append("------------------------\n");
            sb.append(String.format("%.5f\t|\t%.5f\n", a, fa));
            sb.append(String.format("%.5f\t|\t%.5f\n", b, fb));
            sb.append(String.format("%.5f\t|\t%.5f\n", c, fc));

            taResultados.setText(sb.toString());

            // Aquí llamas a la gráfica, si tienes la clase Graficador bien implementada
            Graficador.mostrarGrafica(funcion, a, b, c, fa, fb, fc,metodos);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al evaluar la función. Verifique la sintaxis.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double evaluarFuncion(String funcion, double x) {
        Expression exp = new ExpressionBuilder(funcion)
                .variable("x")
                .build()
                .setVariable("x", x);
        return exp.evaluate();
    }

    private double derivada(String funcion, double x) {
        double h = 1e-5;
        return (evaluarFuncion(funcion, x + h) - evaluarFuncion(funcion, x - h)) / (2 * h);
    }
}

    

