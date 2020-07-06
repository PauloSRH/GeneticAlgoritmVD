import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

/**
 * In the Main class we create an instance of the N Queens Problem. In this case, we are looking for one of the solutions
 * of having N queens on a NxN board and none of them can be attacking each other. A queen can attack another one if it is
 * on the same horizontal, vertical or diagonal line, than the other queen.
 * The input is a string that represents the queens disposal on the board, with a permutation format.
 * Afterwards the user must provide the population size, number of generations, mutation probability,
 * crossover probability and the replacement fraction.
 * The population size and number of generations are Integers and mutation probability, crossover probability and replacement fraction are doubles.
 * @author Andre Pires
 * @author Paulo Henriques
 * @see QueensProblem
 * @see GeneticAlgorithm
 * @see GeneticAlgorithmTest
 * @see IIndividual
 * @see Population
 * @see PopulationTest
 */
public class QueensClient {
	public static void main(String[] args) {
		Random generator = new Random();
		Scanner scanner = new Scanner(System.in);
		scanner.useLocale(Locale.ENGLISH);
		System.out.print("Number of Queens: "); int numberOfQueens = scanner.nextInt();
		List<Integer> queens = new ArrayList<Integer>(numberOfQueens);
		for(int i = 0; i < numberOfQueens; i++) {
			queens.add(scanner.nextInt());
		}
		System.out.print("Population Size: "); int populationSize = scanner.nextInt();
		System.out.print("Max Generations: "); int generations = scanner.nextInt();
		System.out.print("Mutation Probability(0-1): "); double mutationProbability = scanner.nextDouble();
		System.out.print("CrossOver Probability(0-1): "); double crossoverProbability = scanner.nextDouble();
		System.out.print("Replacement Fraction(0-1): "); double replacementFraction = scanner.nextDouble(); 
		System.out.print("Tournament Size: "); int tournamentSize = scanner.nextInt(); 
		QueensProblem initial = new QueensProblem(queens);
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(initial,generator,generations,populationSize,mutationProbability,crossoverProbability,replacementFraction,tournamentSize);
		
		System.out.println(geneticAlgorithm.solveWithTournament());
		scanner.close();
	}
}