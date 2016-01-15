package genetic.salesman;

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

    }

    public void evalPopulation(Population population) {

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


    }

    public Population mutatePopulation(Population population) {

    }


}
