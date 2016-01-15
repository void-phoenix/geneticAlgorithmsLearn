package genetic.robot;

public class RobotController {

    public static int MAX_GENERATIONS = 10000;

    public static void main(String[] args) {



        /**
         * 0 = Empty
         * 1 = Wall
         * 2 = Starting position
         * 3 = Route
         * 4 = Goal position
         */

        Maze maze = new Maze(new int[][]{
                { 0, 0, 0, 0, 1, 0, 1, 3, 2 },
                { 1, 0, 1, 1, 1, 0, 1, 3, 1 },
                { 1, 0, 0, 1, 3, 3, 3, 3, 1 },
                { 3, 3, 3, 1, 3, 1, 1, 0, 1 },
                { 3, 1, 3, 3, 3, 1, 1, 0, 0 },
                { 3, 3, 1, 1, 1, 1, 0, 1, 1 },
                { 1, 3, 0, 1, 3, 3, 3, 3, 3 },
                { 0, 3, 1, 1, 3, 1, 0, 1, 3 },
                { 1, 3, 3, 3, 3, 1, 1, 1, 4 }
        });

        GeneticAlgorithm ga = new GeneticAlgorithm(200, 0.3, 0.9, 5, 30);
        Population population = ga.initPopulation(128);

        ga.evalPopulation(population, maze);

        int generation = 1;

        while (!ga.isTerminationConditionMet(generation, MAX_GENERATIONS)){
            Individual fittest = population.getFittest(0);
            System.out.println("Generation " + generation + "best solution: " + fittest.getFitness() +
                    " - " + fittest);

            population = ga.crossoverPopulation(population);

            population = ga.mutatePopulation(population);

            ga.evalPopulation(population, maze);

            generation++;

        }

        System.out.println("Stopper after " + (generation - 1) + " generations");
        Individual fittest = population.getFittest(0);
        System.out.println("Best solution is " + fittest.getFitness() + " - " + fittest);

    }

}
