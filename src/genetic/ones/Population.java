package genetic.ones;


import java.util.Arrays;
import java.util.Random;

public class Population {

    private Individual population[];
    private double populationFitness = -1;

    public Population(int populationSize) {
        population = new Individual[populationSize];
    }

    public Population(int populationSize, int chromosomeLength) {
        this(populationSize);
        for (int individualCount = 0; individualCount < populationSize; individualCount++) {
            Individual individual = new Individual(chromosomeLength);
            population[individualCount] = individual;
        }
    }

    public Individual[] getIndividuals() {
        return population;
    }

    public Individual getFittest(int offset) {
        Arrays.sort(population, (i1, i2) -> {
            if (i1.getFitness() > i2.getFitness()) return -1;
            else if (i2.getFitness() > i1.getFitness()) return 1;
            else return 0;
        });

        return population[offset];
    }

    public double getPopulationFitness() {
        return populationFitness;
    }

    public void setPopulationFitness(double populationFitness) {
        this.populationFitness = populationFitness;
    }

    public int size() {
        return population.length;
    }

    public Individual setIndividual(int offset, Individual individual) {
        return population[offset] = individual;
    }

    public Individual getIndividual(int offset) {
        return population[offset];
    }

    public void shuffle() {
        Random rnd = new Random();
        for (int i = population.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Individual individual = population[index];
            population[index] = population[i];
            population[i] = individual;
        }
    }
}
