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
        Population tournament = new Population(this.tournamentSize);
        population.shuffle();
        for (int i = 0; i < tournamentSize; i++) {
            Individual tournamentIndividual = population.getIndividual(i);
            tournament.setIndividual(i, tournamentIndividual);
        }

        return tournament.getFittest(0);
    }

    public Population crossoverPopulation(Population population) {
        Population newPopulation = new Population(population.size());
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Individual parent1 = population.getFittest(populationIndex);

            if (crossoverRate > Math.random() && populationIndex >= elitisvCount) {
                Individual offspring = new Individual(parent1.getChromosomeLength());
                Individual parent2 = selectParent(population);
                int swapPoint = (int) (Math.random() * (parent1.getChromosomeLength() + 1));

                for (int geneIndex = 0; geneIndex < offspring.getChromosomeLength(); geneIndex++) {
                    if (geneIndex < swapPoint) offspring.setGene(geneIndex, parent1.getGene(geneIndex));
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

    public double calcFitness(Individual individual, Maze maze) {
        Robot robot = new Robot(individual.getChromosome(), maze, 100);
        robot.run();
        int fitness = maze.scoreRoute(robot.getRoute());
        individual.setFitness(fitness);
        return fitness;
    }
}
