package genetic.ones;

public class Individual {

    private int[] chromosome;
    private double fitness = -1;

    public Individual(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public Individual(int chromosomeLength) {
        this.chromosome = new int[chromosomeLength];
        for (int gene = 0; gene < chromosomeLength; gene++) {
            if (0.5 < Math.random()) {
                setGene(gene, 1);
            } else {
                setGene(gene, 0);
            }
        }
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
