import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * This class represents the data structure which holds a QueensProblem representation.
 * This class implements the interface IIndividual
 */
public class QueensProblem implements IIndividual{
	private List<Integer> queens;
	private double fitness;
	
	@Override
	public String toString() {
		String representation = new String();
		for(int i = 0; i < queens.size(); i++) {
			representation += queens.get(i) + " ";
		}
		double fitnessInverted = 0;
		if(fitness != 2) {
			fitnessInverted = 1/fitness;
		}
		return "queens: " + representation + " , fitness: "+ fitnessInverted;
	}
	
	public QueensProblem() {
		this.queens = new ArrayList<Integer>();
		fitness = 0;
	}
	
	public QueensProblem(List<Integer> queens) {
		this.queens = queens;
		calculateFitness();
	}

	public QueensProblem(List<Integer> newIndividuals, double fitness) {
		this.queens = newIndividuals;
		this.fitness = fitness;
	}

	/**
	 * @return An integer which defines the comparison between objects of type QueensProblem.
	 */
	@Override
	public int compareTo(IIndividual o) {
		QueensProblem qp = (QueensProblem) o;
		return (int) Math.signum(qp.fitness-fitness);
	}
	
	/**
	 * @return A new instance of the type queensProblem and initializes all its fields
	 * with exactly the same contents of the corresponding fields of the QueensProblem object which called the method.
	 */
	@Override
	public Object clone(){
		return new QueensProblem(queens,fitness);
	}
	
	/**
	 * Checks if two points are either in the same vertical, horizontal or diagonal line.
	 * @param x The x coordinate of the first point
	 * @param y The y coordinate of the first point
	 * @param x2 The x coordinate of the second point
	 * @param y2 The y coordinate of the second point
	 * @return true if the above condition is accomplished, false otherwise.
	 */
	public boolean isConflict(int x,int y, int x2,int y2) {
		return Math.abs(y2-y) == Math.abs(x2-x);
	}

	/**
	 * @return The fitness value of the QueensProblem.
	 */
	@Override
	public double getFitness() {
		return fitness;
	}

	/**
	 * Calculates the fitness value and sets it.
	 */
	@Override
	public void calculateFitness() {
		double conflicts = 0;
		int i,j;
		for(i = 0; i < queens.size()-1; i++) {
			for(j = i+1; j < queens.size(); j++) {
				if(isConflict(i,queens.get(i),j,queens.get(j))) {
					conflicts++;
				}
			}
		}
		if(conflicts == 0) {
			fitness = 2;
		} else {
			fitness = 1/conflicts;
		}
	}

	/**
	 * Returns a new instance of QueensProblem where its representation has been permuted.
	 * @return A new instance of type QueensProblem
	 */
	@Override
	public QueensProblem permutation(Random generator) {
		int i,randomNumber;
		int temp;
		ArrayList<Integer> newIndividuals = new ArrayList<Integer>(queens.size());
		for(i = 0; i < queens.size(); i++) {
			newIndividuals.add(queens.get(i));
		}
		for(i = 0 ; i < newIndividuals.size()-1; i++) {
			randomNumber =  i+ (int)Math.round(generator.nextDouble() * (newIndividuals.size()-1-i) ) ;
			temp = newIndividuals.get(i);
			newIndividuals.set(i, newIndividuals.get(randomNumber));
			newIndividuals.set(randomNumber, temp);
		}
		return new QueensProblem(newIndividuals,fitness);
	}

	/**
	 * @return A new instance of type QueensProblem. The representation of QueensProblem may be mutated, by swapping
	 * two random queens in the QueensProblem representation, according to a given probability.
	 */
	@Override
	public QueensProblem twoGeneSwapMutation(Random ran, double mutationProbability) {
		if(ran.nextDouble() < mutationProbability) {
			int i = (int) Math.round(ran.nextDouble()*(queens.size()-1));
			int j = (int) Math.round(ran.nextDouble()*(queens.size()-1));
			int gene = queens.get(i);
			queens.set(i, queens.get(j));
			queens.set(j, gene);
		}
		return new QueensProblem(queens);
	}

	/**
	 * If the two fathers are not selected for crossover, then the resulting offspring contains a copy of the fathers.
	 * Else, orderCrossOver will occur.
	 * @return A list of the resulting offspring between two instances of QueensProblem, given a probability.
	 */
	@Override
	public List<IIndividual> crossOver(Random ran, double crossOverProbability, IIndividual father2) {
		List<IIndividual> childs = new ArrayList<IIndividual>();
		QueensProblem secondFather = (QueensProblem) father2;
		if(ran.nextDouble() < crossOverProbability) {
			int firstPoint = (int) Math.round( ran.nextDouble()*(queens.size()-1) );
			int secondPoint = (int) Math.round( ran.nextDouble()*(queens.size()-1) );
			int start = Math.min(firstPoint, secondPoint);
			int end = Math.max(firstPoint, secondPoint);
			childs.add(orderOneCrossOver(start,end,secondFather));
			childs.add(secondFather.orderOneCrossOver(start,end,this));
		}
		else {
			childs.add(new QueensProblem(queens));
			childs.add(new QueensProblem(secondFather.queens));
		}
		return childs;
	}
	
	/**
	 * The elements between start and end are copied to the child's representation.
	 * Afterwards the sequence is filled up with the elements 
	 * @param start The starting point of the sequence 
	 * @param end The end point of the sequence.
	 * @param father2 The second father involved in the crossover.
	 * @return An instance of type QueensProblem, resulting from the order crossover.
	 */
	public QueensProblem orderOneCrossOver(int start, int end, QueensProblem father2) {
		QueensProblem child = new QueensProblem();
		int i;
		HashSet<Integer> sequence = new HashSet<Integer>();
		for(i = 0; i < queens.size(); i++) {
			if(i>=start && i<=end) {
				sequence.add(father2.queens.get(i));
			}
			child.queens.add(father2.queens.get(i));
		}
		int sequenceSize = sequence.size();
		i = (end+1) % queens.size();
		int j = i;
		while(sequenceSize < queens.size()) {
			if(!sequence.contains(queens.get(i))){
				child.queens.set(j, queens.get(i));
				j = (j+1) % queens.size();
				sequenceSize++;
			}
			i = (i + 1) % queens.size();
		}
		return child;
	}

}