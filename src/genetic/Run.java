package genetic;

import java.util.Random;

public class Run {

    public static void main(String[] args) {
        double[] dna1 = new double[13];
        double[] dna2 = new double[13];
        for(int i = 0; i < 13; i++)
            dna1[i] = dna2[i] = 1;
        Simulation simulation = new Simulation(3000, 1000 * 60 * 30, 15, dna1, dna2);
        Offspring o = simulation.startSimulation(new Random());
        System.out.println(o);
    }

}
