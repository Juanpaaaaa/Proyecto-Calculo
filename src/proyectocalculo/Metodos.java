package proyectocalculo;

import java.util.Scanner;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.ui.Layer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.Marker;
import java.awt.BasicStroke;
import java.awt.Color;
import org.jfree.chart.axis.NumberTickUnit;

public class Metodos {
    Scanner scanner = new Scanner(System.in);

    public static class Graficador extends ApplicationFrame {

    public Graficador(String titulo, String funcion, double a, double b, double c, double fa, double fb, double fc, Metodos metodos) {
        super(titulo);

        XYSeries serieFuncion = new XYSeries("f(x)");
        double paso = 0.1;
        for (double x = a - 1; x <= b + 1; x += paso) {
            double y = metodos.evaluarFuncion(funcion, x);
            serieFuncion.add(x, y);
        }

        XYSeries puntos = new XYSeries("Puntos");
        puntos.add(a, fa);
        puntos.add(b, fb);
        puntos.add(c, fc);

        XYSeries lineaSecante = new XYSeries("Línea Secante");
        lineaSecante.add(a, fa);
        lineaSecante.add(b, fb);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(serieFuncion);
        dataset.addSeries(lineaSecante);
        dataset.addSeries(puntos);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Teorema del Valor Medio",
                "x",
                "f(x)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesShapesVisible(0, false);

        renderer.setSeriesPaint(1, Color.GREEN.darker());
        renderer.setSeriesStroke(1, new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                new float[]{6.0f, 6.0f}, 0));
        renderer.setSeriesShapesVisible(1, false);

        renderer.setSeriesPaint(2, Color.RED);
        renderer.setSeriesStroke(2, new BasicStroke(0.0f));
        renderer.setSeriesLinesVisible(2, false);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesShape(2, new java.awt.geom.Ellipse2D.Double(-4, -4, 8, 8));

        plot.setRenderer(renderer);

        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setTickUnit(new NumberTickUnit(1));
        domain.setRange(a - 10, b + 10);

        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        double minY = Math.min(fa, Math.min(fb, fc));
        double maxY = Math.max(fa, Math.max(fb, fc));
        for (double x = a - 2; x <= b + 2; x += 0.1) {
            double y = metodos.evaluarFuncion(funcion, x);
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;
        }
        range.setRange( -20,+20);
        range.setTickUnit(new NumberTickUnit(1));

        Marker ejeX = new ValueMarker(0);
        ejeX.setPaint(Color.BLACK);
        ejeX.setStroke(new BasicStroke(1.5f));
        plot.addRangeMarker(ejeX);

        Marker ejeY = new ValueMarker(0);
        ejeY.setPaint(Color.BLACK);
        ejeY.setStroke(new BasicStroke(1.5f));
        plot.addDomainMarker(ejeY);

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    public static void mostrarGrafica(String funcion, double a, double b, double c, double fa, double fb, double fc, Metodos metodos) {
        Graficador demo = new Graficador("Gráfica del Teorema del Valor Medio", funcion, a, b, c, fa, fb, fc, metodos);
        demo.setSize(800, 600);
        UIUtils.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}


    public double evaluarFuncion(String funcion, double x) {
        Expression exp = new ExpressionBuilder(funcion)
                .variable("x")
                .build()
                .setVariable("x", x);
        return exp.evaluate();
    }

    public double derivada(String funcion, double x) {
        double h = 1e-5;
        return (evaluarFuncion(funcion, x + h) - evaluarFuncion(funcion, x - h)) / (2 * h);
    }

    public void TeoremaValorMedio() {
        System.out.println("Teorema del Valor Medio");

        System.out.print("Ingrese la función en términos de x (ejemplo: x*x - 4*x + 3): ");
        String funcion = scanner.nextLine().toLowerCase().replace(" ", "");

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
            
            

           Graficador.mostrarGrafica(funcion, a, b, c, fa, fb, fc, this);


        } catch (Exception e) {
            System.out.println("Error al evaluar la función. Verifique que la sintaxis sea correcta.");
        }
    }

    public void TeoremaRolle() {
        System.out.println("Teorema de Rolle");
        System.out.print("Ingrese la función en términos de x (ejemplo: x*x - 4*x + 3): ");
    String funcion = scanner.nextLine().toLowerCase().replace(" ", "");

    System.out.print("Ingrese el valor de a: ");
    double a = Double.parseDouble(scanner.nextLine());

    System.out.print("Ingrese el valor de b: ");
    double b = Double.parseDouble(scanner.nextLine());

    try {
        double fa = evaluarFuncion(funcion, a);
        double fb = evaluarFuncion(funcion, b);

        System.out.printf("f(a) = %.5f\n", fa);
        System.out.printf("f(b) = %.5f\n", fb);

        if (Math.abs(fa - fb) > 1e-6) {
            System.out.println("No se cumple que f(a) = f(b). No se puede aplicar el Teorema de Rolle.");
            return;
        }

        System.out.println("Se cumple f(a) = f(b). Buscando c en (a, b) tal que f'(c) ≈ 0...");

        double c = buscarDerivadaCero(funcion, a, b);

        if (!Double.isNaN(c)) {
            double derivadaC = derivada(funcion, c);
            System.out.printf("Se encontró un punto c ≈ %.5f tal que f'(c) ≈ %.5f\n", c, derivadaC);
        } else {
            System.out.println("No se encontró un punto c en (a, b) con f'(c) ≈ 0.");
        }

        // ------------------ NUEVO BLOQUE: FACTORIZACIÓN CUADRÁTICA ------------------
        if (funcion.matches("[-+]?\\d*\\?x\\*x[-+]\\d\\*?x[-+]\\d+")) {
            System.out.println("\nIntentando factorizar la función usando fórmula cuadrática:");

            // Extraer coeficientes a, b, c
            String[] tokens = funcion.replace("*x*x", "#").replace("*x", "@").split("(?=[+-])");
            double coefA = 0, coefB = 0, coefC = 0;
            for (String token : tokens) {
                if (token.contains("#")) coefA = Double.parseDouble(token.replace("#", ""));
                else if (token.contains("@")) coefB = Double.parseDouble(token.replace("@", ""));
                else coefC = Double.parseDouble(token);
            }

            // Aplicar fórmula cuadrática
            double discriminante = coefB * coefB - 4 * coefA * coefC;
            if (discriminante < 0) {
                System.out.println("No tiene soluciones reales. Discriminante < 0.");
            } else {
                double x1 = (-coefB + Math.sqrt(discriminante)) / (2 * coefA);
                double x2 = (-coefB - Math.sqrt(discriminante)) / (2 * coefA);
                System.out.printf("Soluciones reales de la ecuación cuadrática:\n x1 = %.5f\n x2 = %.5f\n", x1, x2);
            }
        }
        // ---------------------------------------------------------------------------

    } catch (Exception e) {
        System.out.println("Error al evaluar la función. Verifique la sintaxis.");
    }
}


// Método para buscar un c donde f'(c) ≈ 0 (derivada numérica)
public double buscarDerivadaCero(String funcion, double a, double b) {
    double paso = (b - a) / 1000;

    for (double x = a + paso; x < b; x += paso) {
        double derivada = derivada(funcion, x);
        if (Math.abs(derivada) < 1e-5) {
            return x;
        }
    }

    return Double.NaN;

    }

    public void MetodoNewton() {
        System.out.println("Método de Newton");
        System.out.print("Ingrese la función en términos de x (ejemplo: x^3 - 2*x - 5): ");
        String funcion = scanner.nextLine();

        System.out.print("Ingrese la derivada de la función (ejemplo: 3*x^2 - 2): ");
        String derivada = scanner.nextLine();

        System.out.print("Ingrese el valor inicial de xi: ");
        double xi = scanner.nextDouble();

        int iteraciones = 0;
        double tolerancia = 0.0001;
        double xrAnterior = 0;
        double xr = 0;

        System.out.println("\nTabla de Iteraciones:");
        System.out.printf("%-3s | %-10s | %-10s | %-10s | %-10s | %-10s\n", "i", "xi", "f(xi)", "f'(xi)", "xr", "Tolerancia");
        System.out.println("--------------------------------------------------------------------------");

        while (true) {
            double fxi = evaluarFuncion(funcion, xi);
            double fpxi = evaluarFuncion(derivada, xi);
            xr = xi - (fxi / fpxi);

            if (iteraciones == 0) {
                System.out.printf("%-3d | %-10.4f | %-10.4f | %-10.4f | %-10.4f | %-10s\n",
                        iteraciones + 1, xi, fxi, fpxi, xr, "N/A");
            } else {
                double diferencia = Math.abs(xr - xrAnterior);
                System.out.printf("%-3d | %-10.4f | %-10.4f | %-10.4f | %-10.4f | %-10.4f\n",
                        iteraciones + 1, xi, fxi, fpxi, xr, diferencia);
                if (diferencia < tolerancia) {
                    break;
                }
            }

            xrAnterior = xr;
            xi = xr;
            iteraciones++;
            if (iteraciones > 100) break;
        }

        System.out.println("\nRaíz encontrada: " + String.format("%.10f", xr));
    }

    public void DerivadaMaxyMin() {
        System.out.println("Máximos y mínimos");
        System.out.println("Aplicación de Máximos y Mínimos");

    System.out.print("Ingrese la función en términos de x (ejemplo: x*x - 4*x + 3): ");
    String funcion = scanner.nextLine().toLowerCase().replace(" ", "");

    System.out.print("Ingrese el valor de a (inicio del intervalo): ");
    double a = Double.parseDouble(scanner.nextLine());

    System.out.print("Ingrese el valor de b (fin del intervalo): ");
    double b = Double.parseDouble(scanner.nextLine());

    try {
        double paso = 0.01;
        double xCritico = Double.NaN;
        double derivadaAnt = derivada(funcion, a);
        boolean encontrado = false;

        // Búsqueda de punto crítico (donde derivada ≈ 0)
        for (double x = a + paso; x < b; x += paso) {
            double derivadaAct = derivada(funcion, x);
            if (Math.signum(derivadaAnt) != Math.signum(derivadaAct)) {
                xCritico = x;
                encontrado = true;
                break;
            }
            derivadaAnt = derivadaAct;
        }

        if (encontrado) {
            double fCritico = evaluarFuncion(funcion, xCritico);
            double segundaDerivada = (derivada(funcion, xCritico + paso) - derivada(funcion, xCritico)) / paso;

            System.out.printf("Punto crítico encontrado en x ≈ %.5f\n", xCritico);
            System.out.printf("f(x) ≈ %.5f\n", fCritico);

            if (segundaDerivada > 0) {
                System.out.println("Clasificación: Mínimo local.");
            } else if (segundaDerivada < 0) {
                System.out.println("Clasificación: Máximo local.");
            } else {
                System.out.println("Clasificación: Punto de inflexión o dudoso.");
            }
        } else {
            System.out.println("No se encontró punto crítico en el intervalo.");
        }

        // ------------------ BLOQUE DE FÓRMULA CUADRÁTICA ------------------
        if (funcion.matches("[-+]?\\d*\\?x\\*x[-+]\\d\\*?x[-+]\\d+")) {
            System.out.println("\nIntentando factorizar la función usando fórmula cuadrática:");

            // Extraer coeficientes a, b, c
            String[] tokens = funcion.replace("*x*x", "#").replace("*x", "@").split("(?=[+-])");
            double coefA = 0, coefB = 0, coefC = 0;
            for (String token : tokens) {
                if (token.contains("#")) coefA = Double.parseDouble(token.replace("#", ""));
                else if (token.contains("@")) coefB = Double.parseDouble(token.replace("@", ""));
                else coefC = Double.parseDouble(token);
            }

            // Aplicar fórmula cuadrática
            double discriminante = coefB * coefB - 4 * coefA * coefC;
            if (discriminante < 0) {
                System.out.println("No tiene soluciones reales. Discriminante < 0.");
            } else {
                double x1 = (-coefB + Math.sqrt(discriminante)) / (2 * coefA);
                double x2 = (-coefB - Math.sqrt(discriminante)) / (2 * coefA);
                System.out.printf("Soluciones reales de la ecuación cuadrática:\n x1 = %.5f\n x2 = %.5f\n", x1, x2);
            }
        }

    } catch (Exception e) {
        System.out.println("Error al evaluar la función. Verifique la sintaxis.");
    }

    }

    public void lanzarGraficaDemo() {
        String funcion = "x*x - 4*x + 3";
        double a = 1;
        double b = 3;
        double c = 2;

    
    }
}
