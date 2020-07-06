import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * This class represents the data structure which holds a TSP representation.
 * This class implements the interface IIndividual
 */
public class TSP implements IIndividual {
	private List<City> representation;
	private double fitness;
	
	/**
	 * @param representation The list of cities.
	 * @param fitness The fitness value.
	 */
	public TSP() {
		representation = new ArrayList<City>();
		fitness = 0;
	}
	
	/**
	 * @param a A list of cities.
	 * @param fitness The fitness value.
	 */
	public TSP(List<City> a, double fitness) {
		this.representation=a;
		this.fitness=fitness;
	}
	
	/**
	 * @param a A list of cities.
	 */
	public TSP(List<City> a) {
		this.representation=a;
		calculateFitness();
	}
	
	/**
	 * Calculates the fitness value and sets it.
	 */
	public void calculateFitness(){
		double fitness=0;
		for(int i=0;i<representation.size()-1;i++) {
			fitness += representation.get(i).getDistance(representation.get(i+1));
		}
		fitness += representation.get(representation.size()-1).getDistance(representation.get(0));
		if(fitness == 0)
			throw new IllegalArgumentException("Fitness can not be 0!");
		this.fitness=1/fitness;
	}
	
	/**
	 * @return The list of cities that represent the path of the TSP.
	 */
	public List<City> getRepresentation() {
		return representation;
	}
	
	/**
	 * @return The fitness value of the TSP.
	 */
	public double getFitness() {
		return fitness;
	}
	
	/**
	 * Sets the fitness value of the TSP accordingly.
	 * @param fitness The fitness value to be set.
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	/**
	 * Changes the list of cities of the TSP accordingly.
	 * @param cities The list of the cities to be set. 
	 */
	public void setRepresentation(List<City> cities) {
		this.representation=cities;
	}

	/**
	 * @return An integer which defines the comparison between objects of type TSP.
	 */
	@Override
	public int compareTo(IIndividual o) {
		TSP a = (TSP) o;
		return (int) Math.signum(a.fitness-fitness);
	}
	
	/**
	 * Returns a new instance of TSP where its representation has been permuted.
	 * @return A new instance of type TSP
	 */
	@Override
	public TSP permutation(Random generator){
		int i,randomNumber;
		City temp;
		ArrayList<City> newIndividuals = new ArrayList<City>(representation.size());
		for(i = 0; i < representation.size(); i++) {
			newIndividuals.add(representation.get(i));
		}
		for(i = 0 ; i < newIndividuals.size()-1; i++) {
			randomNumber =  i+ (int)Math.round(generator.nextDouble() * (newIndividuals.size()-1-i) ) ;
			temp = newIndividuals.get(i);
			newIndividuals.set(i, newIndividuals.get(randomNumber));
			newIndividuals.set(randomNumber, temp);
		}
		return new TSP(newIndividuals,fitness);
	}
	
	/**
	 * @return A new instance of the type TSP and initializes all its fields
	 * with exactly the same contents of the corresponding fields of the TSP object which called the method.
	 */
	@Override
	public Object clone(){
		TSP newTSP = new TSP();
		List<City> cities = new ArrayList<City>();
		for(City city : representation) {
			cities.add(city);
		}
		newTSP.setRepresentation(cities);
		newTSP.setFitness(fitness);
		return newTSP;
	}
	
	/**
	 * @return A new instance of the implementing type, which may be mutated, by swapping
	 * two cities of the TSP's representation.
	 */
	@Override
	public TSP twoGeneSwapMutation(Random ran,double mutationProbability) {
		if(ran.nextDouble() < mutationProbability) {
			int i = (int) Math.round(ran.nextDouble()*(getRepresentation().size()-1));
			int j = (int) Math.round(ran.nextDouble()*(getRepresentation().size()-1));
			City gene = representation.get(i);
			representation.set(i, representation.get(j));
			representation.set(j, gene);
		}
		return new TSP(representation);
	}
	
	/**
	 * The elements between start and end are copied to the childs representation.
	 * Afterwards the sequence is filled up with the elements 
	 * @param start The starting point of the sequence 
	 * @param end The end point of the sequence.
	 * @param ran The Random number generator.
	 * @param child The resulting child.
	 * @param father2 The second father involved in the crossover.
	 * @return An instance of type TSP, resulting from the order crossover.
	 */
	public TSP orderOneCrossOver(int start, int end,Random ran,TSP child, TSP father2) {
		int i;
		HashSet<City> sequence = new HashSet<City>();
		for(i = 0; i < representation.size(); i++) {
			if(i>=start && i<=end) {
				sequence.add(father2.representation.get(i));
			}
			child.representation.add(father2.representation.get(i));
		}
		int sequenceSize = sequence.size();
		i =  (end+1) % representation.size();
		int j = i;
		while(sequenceSize < representation.size()) {
			if(!sequence.contains(representation.get(i))){
				child.representation.set(j, representation.get(i));
				j = (j+1) % representation.size();
				sequenceSize++;
			}
			i = (i + 1) % representation.size();
		}
		return child;
	}
	
	/**
	 * If the two fathers are not selected for crossover, then the resulting offspring contains a copy of the fathers.
	 * Else, orderCrossOver will occur.
	 * @return A list of the resulting offspring between two instances of TSP, given a probability.
	 */
	@Override
	public List<IIndividual> crossOver(Random ran,double crossOverProbability,IIndividual father2) {
		List<IIndividual> childs = new ArrayList<IIndividual>();
		TSP secondFather = (TSP) father2;
		if(ran.nextDouble() < crossOverProbability) {
			TSP child1 = new TSP();
			TSP child2 = new TSP();
			int firstPoint = (int) Math.round( ran.nextDouble()*(representation.size()-1) );
			int secondPoint = (int) Math.round( ran.nextDouble()*(representation.size()-1) );
			int start = Math.min(firstPoint, secondPoint);
			int end = Math.max(firstPoint, secondPoint);
			childs.add(orderOneCrossOver(start,end,ran,child1,secondFather));
			childs.add(secondFather.orderOneCrossOver(start,end,ran,child2,this));
		}
		else {
			childs.add(new TSP(representation));
			childs.add(new TSP(secondFather.representation));
		}
		return childs;
	}
	
	/**
	 * @return True If the two TSP's representations are equal, i.e, if the list of cities are the same, and if
	 * TSP's fitness values are equal, else False.
	 */
	@Override
	public boolean equals(Object o) {
		if(o == null || getClass() != o.getClass()) return false;
		if(o == this) return true;
		TSP a = (TSP) o;
		return representation.equals(a.representation) && fitness == a.fitness;
	}
	
	/**
	 * @return A string representation of an object of type TSP.
	 */
	public String toString() {
		String representationInString = "";
		for(int i=0;i<representation.size();i++) {
			representationInString += representation.get(i) +"->";
		}
		representationInString += representation.get(0) + "\n"+ "Path length: " + 1/fitness;
		return representationInString;
	}
		
}