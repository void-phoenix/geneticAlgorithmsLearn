package genetic.salesman;

import java.util.Random;

public class Individual {

    private int[] chromosome;
    private double fitness = -1;

    public Individual(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public Individual(int chromosomeLength) {
        int individual[] = new int[chromosomeLength];

        for (int gene = 0; gene < chromosomeLength; gene++ ) {
            individual[gene] = gene;
        }

        Random rnd = new Random();
        for (int i = chromosomeLength - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int gene = individual[index];
            individual[index] = individual[i];
            individual[i] = gene;
        }

        this.chromosome = individual;

    }

    public int[] getChromosome() {
        return chromosome;
    }

    public int getChromosomeLength() {
        return chromosome.length;
    }

    public void setGene(int offset, int gene) {
        chromosome[offset] = gene;
    }

    public int getGene(int offset) {
        return chromosome[offset];
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int gene : chromosome) result.append(gene);
        return result.toString();
    }

}
