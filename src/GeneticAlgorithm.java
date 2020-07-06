import java.util.Collections;
import java.util.Random;

/**
 * This class represents the data type which holds a Genetic Algorithm representation.
 */
public class GeneticAlgorithm {
	private Population population;
	private Random generator;
	private double crossoverProbability, replacementFraction, mutationProbability;
	private int tournamentSize;
	private int maxGenerations;

	/**
	 * Initializes the genetic algorithm with the corresponding fields.
	 * @param member The first individual of the population.
	 * @param generator The random number generator.
	 * @param popuSize The size of the population.
	 * @param mutProb The mutation probability.
	 * @param crossProb The crossover probability.
	 * @param replaceFraction The replacement fraction.
	 * @param tournamentSize The tournament size for the tournament selection.
	 */
	public GeneticAlgorithm(IIndividual member,Random generator, int generations, int popuSize,double mutProb, double crossProb, double replaceFraction, int tournamentSize){
		population = new Population(member,generator,popuSize);
		this.generator = generator;
		this.crossoverProbability=crossProb;
		this.replacementFraction=replaceFraction;
		this.mutationProbability= mutProb;
		this.tournamentSize = tournamentSize;
		this.maxGenerations = generations;
	}

	/**
	 * Initializes the genetic algorithm with the corresponding fields.
	 * @param member The first individual of the population.
	 * @param generator The random number generator.
	 * @param popuSize The size of the population.
	 * @param mutProb The mutation probability.
	 * @param crossProb The crossover probability.
	 * @param replaceFraction The replacement fraction.
	 */
	public GeneticAlgorithm(IIndividual member,Random generator, int popuSize,double mutProb, double crossProb, double replaceFraction){
		population = new Population(member,generator,popuSize);
		this.generator = generator;
		this.crossoverProbability=crossProb;
		this.replacementFraction=replaceFraction;
		this.mutationProbability= mutProb;
		this.tournamentSize = 0;
	}

	/**
	 * @return The population of the genetic algorithm.
	 */
	public Population getPopulation() { return population; }

	/**
	 * Change the current population of the genetic algorithm.
	 * @param population The population to be set.
	 */
	public void setPopulation(Population population) { this.population =
		population; }

	/**
	 * @return The random number generator used in the genetic algorithm.
	 */
	public Random getRandom() { return generator; }

	/**
	 * @return The probability value for which crossover will occur.
	 */
	public double getCrossProb() { return crossoverProbability; }

	/**
	 * @return The tournament size for the tournament selection.
	 */
	public double getTournamentSize() { return tournamentSize; }
	/**
	 * @return The probability value for which mutation will occur.
	 */
	public double getMutProb() { return mutationProbability; }

	/**
	 * @return The replacement value for which the worst members of the population are replaced by the best.
	 */
	public double getReplaceFraction() { return replacementFraction; }

	/**
	 * This method consists of sorting the population decreasingly by its fitness, then perform tournament selection,
	 * crossover, mutation and replacing individuals of the population.
	 * @return The best individual found after running the algorithm and the average fitness for the last 10 populations remained equal.
	 */
	public IIndividual solveWithTournament(){

		int generations = 0;
		Population parents,childs;
		Collections.sort(population.getPopulation());
		while(generations < maxGenerations){

			parents = new Population();
			childs = new Population();

			parents.getPopulation().addAll(population.TournamentSelection(generator, tournamentSize));

			childs.offspring(parents,generator,crossoverProbability);

			childs.mutatePopulation(generator,mutationProbability);

			population.replaceWith(childs, replacementFraction);
			
			population.updateFitness();

			Collections.sort(population.getPopulation());
			
			generations++;
		}
		System.out.println("Finished after "+generations + " generations");
		System.out.println("The average population fitness is: "+population.getAverageFitness());
		System.out.println("The standard deviation for the population fitness is: "+ population.calculateFitnessStandardDeviation());
		System.out.println("The population fitness total: "+population.getTotalFitness());
		return population.getPopulation().get(0);
	}

}