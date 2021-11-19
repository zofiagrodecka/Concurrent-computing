package Charts;

import Arbiter.PhilosopherArbiter;
import StarvingPossibility.PhilosopherStarving;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import java.io.*;
import java.util.concurrent.Semaphore;


public class ChartsCreator {
    private double[] averageWaitingTimesStarving;
    private double[] averageWaitingTimesArbiter;
    private double[][] jstimes;
    private final JFreeChart chart;

    public ChartsCreator(double[] averageWaitingTimes1, double[] averageWaitingTimes2) {
        this.averageWaitingTimesStarving = averageWaitingTimes1;
        this.averageWaitingTimesArbiter = averageWaitingTimes2;
        CategoryDataset dataset = createDatasetJava();
        this.chart = createChart(dataset, "Porównanie czasów oczekiwania filozofów w Javie");
    }

    public ChartsCreator(double[][] times) {
        this.jstimes = times;
        CategoryDataset dataset = createDatasetJS();
        this.chart = createChart(dataset, "Porównanie czasów oczekiwania filozofów w JavaScripcie");
    }

    private CategoryDataset createDatasetJava() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for(int i=0; i<averageWaitingTimesStarving.length; i++){
            dataset.addValue(averageWaitingTimesStarving[i], "Rozwiązanie z możliwością zagłodzenia", String.valueOf(i));
        }

        for(int i=0; i<averageWaitingTimesArbiter.length; i++){
            dataset.addValue(averageWaitingTimesArbiter[i], "Rozwiązanie z arbitrem", String.valueOf(i));
        }

        return dataset;
    }

    private CategoryDataset createDatasetJS() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for(int i=0; i<jstimes[0].length; i++){
            dataset.addValue(jstimes[0][i], "Rozwiązanie asymetryczne", String.valueOf(i));
        }

        for(int i=0; i<jstimes[1].length; i++){
            dataset.addValue(jstimes[1][i], "Rozwiązanie z arbitrem", String.valueOf(i));
        }

        for(int i=0; i<jstimes[2].length; i++){
            dataset.addValue(jstimes[2][i], "Rozwiązanie z jednoczesnym podnoszeniem widelców", String.valueOf(i));
        }

        return dataset;
    }

    private static JFreeChart createChart(CategoryDataset dataset, String title) {
       JFreeChart chart = ChartFactory.createBarChart3D(title, "Indeks filozofa",
               "Czas [ms]", dataset, PlotOrientation.VERTICAL, true, true, false);
       return chart;
    }

    public void showChart(){
        ChartFrame frame = new ChartFrame("Problem n-filozofów", chart);
        frame.setSize(700, 400);
        RefineryUtilities.centerFrameOnScreen(frame);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        int n = 5;
        double[] starvingTimes = new double[n];
        double[] arbiterTimes = new double[n];
        int n_meals = 10;

        // Starving
        Thread[] starvingPhilosophers = new Thread[n];
        Semaphore[] starvingForks = new Semaphore[n];

        for(int i=0; i<n; i++){
            starvingForks[i] = new Semaphore(1);
        }

        for(int i=0; i<n; i++){
            starvingPhilosophers[i] = new Thread(new PhilosopherStarving(starvingForks[i], starvingForks[(i+1) % n], i, n_meals, starvingTimes));
        }

        for(int i=0; i<n; i++){
            starvingPhilosophers[i].start();
        }

        try{
            for(int i=0; i<n; i++){
                starvingPhilosophers[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Arbiter
        Thread[] arbiterPhilosophers = new Thread[n];
        Semaphore[] arbiterForks = new Semaphore[n];
        Semaphore waiter = new Semaphore(n-1);

        for(int i=0; i<n; i++){
            arbiterForks[i] = new Semaphore(1);
        }

        for(int i=0; i<n; i++){
            arbiterPhilosophers[i] = new Thread(new PhilosopherArbiter(arbiterForks[i], arbiterForks[(i+1) % n], waiter, i, n_meals, arbiterTimes));
        }

        for(int i=0; i<n; i++){
            arbiterPhilosophers[i].start();
        }

        try{
            for(int i=0; i<n; i++){
                arbiterPhilosophers[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        ChartsCreator creator = new ChartsCreator(starvingTimes, arbiterTimes);
        creator.showChart();

        // JavaScript
        final int n_graphs = 3; // liczba wariantów implementacji
        String text;
        int w = 0, j = 0, k = 0;
        BufferedReader reader = null;
        double[][] times = new double[n_graphs][n];
        File file = new File("../DiningPhilosophersProblem-JS/asymetric.txt");
        for(int i=0; i<3; i++){
            reader = null;
            j = 0;

            try {
                reader = new BufferedReader(new FileReader(file));
                text = null;
                while ((text = reader.readLine()) != null) {
                    if(j % 2 == 0){
                        k = Integer.parseInt(text);
                    }
                    else{
                        times[w][k] = Double.parseDouble(text);
                        System.out.println(times[w][k]);
                    }
                    j++;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(-1);
                    }
                }
            }

            w++;
            if(i == 0){
                file = new File("../DiningPhilosophersProblem-JS/conductor.txt");
            }
            else if(i == 1){
                file = new File("../DiningPhilosophersProblem-JS/simultaneous.txt");
            }
        }

        ChartsCreator creator2 = new ChartsCreator(times);
        creator2.showChart();
    }

}
