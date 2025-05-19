package proyectocalculo;

import java.util.Scanner;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Metodos {
    Scanner scanner = new Scanner(System.in);

    // Método para evaluar función con exp4j
    public double evaluarFuncion(String funcion, double x) {
        Expression exp = new ExpressionBuilder(funcion)
                .variable("x")
                .build()
                .setVariable("x", x);
        return exp.evaluate();
    }

    // Derivada simple con diferencia finita (aproximada)
    public double derivada(String funcion, double x) {
        double h = 1e-5; // paso pequeño
        return (evaluarFuncion(funcion, x + h) - evaluarFuncion(funcion, x - h)) / (2 * h);
    }

    public void TeoremaValorMedio() {
      System.out.println("Teorema del Valor Medio");

        System.out.print("Ingrese la función en términos de x (ejemplo: x*x - 4*x + 3): ");
        String funcion = scanner.nextLine().toLowerCase().replace(" ", ""); // Limpia espacios y minúsculas

        System.out.print("Ingrese el valor de a: ");
        double a = Double.parseDouble(scanner.nextLine());

        System.out.print("Ingrese el valor de b: ");
        double b = Double.parseDouble(scanner.nextLine());

        try {
            double fa = evaluarFuncion(funcion, a);
            double fb = evaluarFuncion(funcion, b);

            System.out.printf("F(a) = %.5f\n", fa);
            System.out.printf("F(b) = %.5f\n", fb);

            if (Math.abs(fa - fb) < 1e-10) {
                System.out.println("No se puede aplicar el Teorema del Valor Medio porque F(a) = F(b).");
                return;
            }

            double pendiente = (fb - fa) / (b - a);
            double c = (a + b) / 2;
            double derivadaEnC = derivada(funcion, c);
            double fc = evaluarFuncion(funcion, c);

            System.out.println("Función original: " + funcion);
            System.out.println("Función derivada: derivada numérica (no simbólica)");
            System.out.printf("Valor de derivada en c = %.5f\n", derivadaEnC);
            System.out.printf("Valor sustituyendo x=c en la función original (F(c)) = %.5f\n", fc);
            System.out.printf("Pendiente entre a y b (resultado fórmula del teorema) = %.5f\n", pendiente);

            System.out.println("\nTabla de valores:");
            System.out.println(" x\t|\tF(x)");
            System.out.println("------------------------");
            System.out.printf("%.5f\t|\t%.5f\n", a, fa);
            System.out.printf("%.5f\t|\t%.5f\n", b, fb);
            System.out.printf("%.5f\t|\t%.5f\n", c, fc);

        } catch (Exception e) {
            System.out.println("Error al evaluar la función. Verifique que la sintaxis sea correcta.");
        }
    }

    public void TeoremaRolle() {
        System.out.println("Teorema de Rolle");
    }

    public void MetodoNewton() {
        System.out.println("Método de Newton");
    }

    public void DerivadaMaxyMin() {
        System.out.println("Máximos y mínimos");
    }
}
