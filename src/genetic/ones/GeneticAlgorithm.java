package genetic.ones;

public class GeneticAlgorithm {

    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitisvCount;

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitisvCount) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitisvCount = elitisvCount;
    }


    public Population initPopulation(int chromosomeLength) {
        return new Population(populationSize, chromosomeLength);
    }

    public double calcFitness(Individual individual) {
        int correctGenes = 0;

        for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
            if (individual.getGene(geneIndex) == 1) correctGenes++;
        }

        double fitness = (double) correctGenes / individual.getChromosomeLength();
        individual.setFitness(fitness);

        return fitness;
    }

    public void evalPopulation(Population population) {
        double populationFitness = 0;
        for (Individual individual : population.getIndividuals()) {
            populationFitness += calcFitness(individual);
        }

        population.setPopulationFitness(populationFitness);
    }

    public boolean isTerminationConditionMet(Population population) {
        for (Individual individual : population.getIndividuals()) {
            if (individual.getFitness() == 1) return true;
        }
        return false;
    }

    public Individual selectParent(Population population) {
        final double rouletteWheelPosition = population.getPopulationFitness() * Math.random();
        double spinWheel = 0;

        for (Individual individual : population.getIndividuals()) {
            spinWheel += individual.getFitness();
            if (spinWheel >= rouletteWheelPosition) return individual;
        }

        return population.getIndividuals()[populationSize - 1];

    }

    public Population crossoverPopulation(Population population) {

        Population newPopulation = new Population(population.size());
        for (int populationIndex = 0; populationIndex < populationSize; populationIndex++) {
            Individual parent1 = population.getFittest(populationIndex);
            if (crossoverRate > Math.random() && populationIndex >= elitisvCount) {
                Individual offspring = new Individual(parent1.getChromosomeLength());
                Individual parent2 = selectParent(population);
                for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
                    if (0.5 > Math.random()) offspring.setGene(geneIndex, parent1.getGene(geneIndex));
                    else offspring.setGene(geneIndex, parent2.getGene(geneIndex));
                }
                newPopulation.setIndividual(populationIndex, offspring);
            } else {
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }
        return newPopulation;
    }

    public Population mutatePopulation(Population population) {
        Population newPopulation = new Population(populationSize);
        for (int populationIndex = 0; populationIndex < populationSize; populationIndex++) {
            Individual individual = population.getFittest(populationIndex);
            if (populationIndex >= elitisvCount) {
                for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
                    if (mutationRate > Math.random()) {
                        int newGene = individual.getGene(geneIndex) == 1 ? 0 : 1;
                        individual.setGene(geneIndex, newGene);
                    }
                }
            }
            newPopulation.setIndividual(populationIndex, individual);
        }
        return newPopulation;
    }


}
