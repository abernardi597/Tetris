package genetic;

import java.util.Random;

public class Run {
    //[13.0, -9.0, 19.0, 1.0, 11.0, 1.0, 16.0, 14.0, 7.0, 0.0, -7.0, 17.0, 1.0] Best: 147420
    //{9,1,1,1,1,1,1,14,1,13,1,1,0] Best: 31385
    //{13.0, -9.0, 19.0, 1.0, 11.0, 1.0, 16.0, 14.0, 7.0, 4.0, -7.0, 17.0, 1.0}
    public static void main(String[] args) {
        double[] dna1 = {13.0, -9.0, 19.0, 1.0, 11.0, 1.0, 16.0, 14.0, 7.0, 0.0, -7.0, 17.0, 1.0};
        double[] dna2 = {13.0, -9.0, 19.0, 1.0, 11.0, 1.0, 16.0, 14.0, 7.0, 0.0, -7.0, 17.0, 1.0};
        //(Fitness,Run Time,total threads)
        Simulation simulation = new Simulation(300000, 1000 * 600 * 3000, 5, dna1, dna2);
        Offspring o = simulation.startSimulation(new Random());
        System.out.println(o);
    }

}
