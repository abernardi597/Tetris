package genetic;

import AIHelper.FinalRater;

import java.util.Arrays;
import java.util.Random;

/**
 * This class is a reusable object that will house the data for determining the best series of coefficients to use when
 * running the provided AI.
 */
public class Offspring {

    /**
     * The number of traits each offspring has.
     */
    public static final int numTraits = FinalRater.raters.length;
    /**
     * Probability that any trait will mutate.
     */
    public static double mutationFrequency = 1D / numTraits;
    /**
     * How different a mutated trait will become from the original.
     */
    public static int mutationRange = 5;

    /**
     * The traits of this offspring correspond to the coefficients of a FinalRater.
     */
    public final double[] traits;
    /**
     * A FitnessTest that will run simulations with this Offspring.
     */
    public final FitnessTest tester;
    /**
     * Fitness is determined by the number of rows cleared.
     */
    public int fitness;
    /**
     * Whether or not this Offspring is empty
     */
    public boolean clear;

    /**
     * Constructs an Offspring whose initial traits are all zero.
     */
    public Offspring() {
        traits = new double[numTraits];
        tester = new FitnessTest(this);
        clear = true;
    }

    /**
     * Makes this Offspring use the given traits as its own.
     * @param d The traits to be used
     * @return This, for convenience
     */
    public Offspring useTraits(double[] d) {
        clear();
        for(int i = 0; i < numTraits; i++)
            traits[i] = d[i];
        clear = false;
        return this;
    }

    /**
     * Removes all data from this offspring. Equivalent to creating a new one.
     * @return This, for convenience
     */
    public Offspring clear() {
        fitness = 0;
        for(int i = 0; i < numTraits; i++)
            traits[i] = 0;
        clear = true;
        tester.clear();
        return this;
    }

    public String toString() {
        return String.format("%d @ %s", fitness, Arrays.toString(traits));
    }

    /**
     * Generates a new series of coefficients using the traits of two parents. There is a 50% chance to inherit a given
     * trait from one parent, and there is a <tt>mutationFrequency</tt> chance of mutating each trait.
     * @param parent1 A parent
     * @param parent2 The other parent
     * @param random A Random to use for selection and mutation of traits
     * @return The new traits in the form of a <tt>double[]</tt>
     */
    public static double[] mate(Offspring parent1, Offspring parent2, Random random) {
        double[] newTraits = new double[numTraits];
        for(int i = 0; i < numTraits; i++) {
            double newTrait = random.nextBoolean() ? parent1.traits[i] : parent2.traits[i];
            newTrait = mutate(newTrait, random);
            newTraits[i] = newTrait;
        }
        return newTraits;
    }

    /**
     * Mutates a trait, or perhaps not. There is a <tt>mutationFrequency</tt> chance of mutation, and the resulting mutation
     * will be within <tt>mutationRange</tt> of the original trait.
     * @param trait - The trait to potentially mutate
     * @param random - The Random used to mutate
     * @return The potentially modified trait
     */
    public static double mutate(double trait, Random random) {
        if(random.nextDouble() < mutationFrequency)
            trait += (random.nextInt(mutationRange * 2)) - mutationRange;
        return trait;
    }

}
