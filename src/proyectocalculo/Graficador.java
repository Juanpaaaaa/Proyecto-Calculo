package proyectocalculo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graficador extends ApplicationFrame {

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
        renderer.setSeriesShape(2, new Ellipse2D.Double(-4, -4, 8, 8));

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
        range.setRange(-20, 20);
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

    // Método para mostrar la gráfica desde otras clases
    public static void mostrarGrafica(String funcion, double a, double b, double c, double fa, double fb, double fc, Metodos metodos) {
        Graficador demo = new Graficador("Gráfica del Teorema del Valor Medio", funcion, a, b, c, fa, fb, fc, metodos);
        demo.setSize(800, 600);
        UIUtils.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}
