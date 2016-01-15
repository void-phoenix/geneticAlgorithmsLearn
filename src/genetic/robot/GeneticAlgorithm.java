package genetic.robot;

public class GeneticAlgorithm {

    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitisvCount;
    protected int tournamentSize;

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitisvCount, int tournamentSize) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitisvCount = elitisvCount;
        this.tournamentSize = tournamentSize;
    }


    public Population initPopulation(int chromosomeLength) {
        return new Population(populationSize, chromosomeLength);
    }

    public void evalPopulation(Population population, Maze maze) {
        double populationFitness = 0;
        for (Individual individual : population.getIndividuals()){
            populationFitness += calcFitness(individual, maze);
        }
        population.setPopulationFitness(populationFitness);
    }

    public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
        return generationsCount > maxGenerations;
    }

    public Individual selectParent(Population population) {

        return null;
    }

    public Population crossoverPopulation(Population population) {
        return null;
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

    public double calcFitness(Individual individual, Maze maze) {
        Robot robot = new Robot(individual.getChromosome(), maze, 100);
        robot.run();
        int fitness = maze.scoreRoute(robot.getRoute());
        individual.setFitness(fitness);
        return fitness;
    }
}
