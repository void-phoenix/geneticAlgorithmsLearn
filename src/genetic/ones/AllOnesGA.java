package genetic.ones;


public class AllOnesGA {

    public static void main(String[] args) {
        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.01, 0.95, 10);
        Population population = ga.initPopulation(50);

        ga.evalPopulation(population);

        int generation = 1;

        while (!ga.isTerminationConditionMet(population)) {

            System.out.println("Best solution is " + population.getFittest(0));

            population = ga.crossoverPopulation(population);

            population = ga.mutatePopulation(population);

            ga.evalPopulation(population);
            generation++;
        }

        System.out.println("Found solution in " + generation + " generations");
        System.out.println("Best solution: " + population.getFittest(0));

    }
}
