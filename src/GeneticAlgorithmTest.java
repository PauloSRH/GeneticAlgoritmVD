import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

/**
 * This class tests the solve method of a GeneticAlgorithm.
 * The method applies the genetic algorithm with the selection, crossover, mutation,
 * and replacement operators, and returns the best individual.
 */
class GeneticAlgorithmTest {

	@Test
	void testSolve1tournament24Queens() {
		Random generator = new Random(0);
		List<Integer> a = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23);
        QueensProblem first = new QueensProblem(a);
        GeneticAlgorithm test = new GeneticAlgorithm(first,generator,500,2048,0.05,0.7,0.5,2);

		QueensProblem result = (QueensProblem)test.solveWithTournament();
		assertEquals(result.getFitness(),2);
	}
	
	@Test
	void testSolve2tournament14Queens() {
		Random generator = new Random(0);
		List<Integer> a = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13);
        QueensProblem first = new QueensProblem(a);
        GeneticAlgorithm test = new GeneticAlgorithm(first,generator,200,1024,0.01,0.7,0.5,2);
		QueensProblem result = (QueensProblem)test.solveWithTournament();
		assertEquals(result.getFitness(),2);
	}
	
	@Test
	void testSolve1tournament() {
		Random generator = new Random(0);
		List<City> a = new ArrayList<City>();
		City a1 = new City("London",new PointTwoDimensions(0,1));
		City a2 = new City("Tokyo",new PointTwoDimensions(-88,-44));
		City a3 = new City("Moscovo",new PointTwoDimensions(6878,9999));
        City a4 = new City("Chaves",new PointTwoDimensions(6,9));
        City a5 = new City("Loulé",new PointTwoDimensions(1337,1337));
		City a6 = new City("Quarteira",new PointTwoDimensions(-1,-2));
		City a7 = new City("Berlin",new PointTwoDimensions(49,-12312321));
        City a8 = new City("Amsterdan",new PointTwoDimensions(9898,123));
        a.add(a1);a.add(a2);a.add(a3);a.add(a4);a.add(a5);a.add(a6);a.add(a7);a.add(a8);
        TSP first = new TSP(a);
        GeneticAlgorithm test = new GeneticAlgorithm(first,generator,30,64,0.05,0.7,0.5,2);
        List<City> b = new ArrayList<City>();
		b.add(a1);b.add(a6);b.add(a2);b.add(a7);b.add(a8);b.add(a3);b.add(a5);b.add(a4);
		
		TSP best = new TSP(b);
		TSP result = (TSP)test.solveWithTournament();
		assertEquals(result.getFitness(),best.getFitness());
	}
	
	@Test
	void testSolve2tournament() {
		Random generator = new Random(0);
		List<City> a = new ArrayList<City>();
		for(int i=60; i<80;i+=2) {
			a.add(new City("Andre" + (i/2),new PointTwoDimensions(1,i)));
		}
		for(int i=0; i<20;i+=2) {
			a.add(new City("Andre" + (i/2),new PointTwoDimensions(1,i)));
		}
		for(int i=40; i<60;i+=2) {
			a.add(new City("Andre" + (i/2),new PointTwoDimensions(1,i)));
		}
		for(int i=20; i<40;i+=2) {
			a.add(new City("Andre" + (i/2),new PointTwoDimensions(1,i)));
		}
		
        TSP first = new TSP(a);
        GeneticAlgorithm test = new GeneticAlgorithm(first,generator,500,1024,0.05,0.7,0.5,2);
        List<City> b = new ArrayList<City>();
        for(int i=0;i<80;i+=2) {
        	b.add(0,new City("Andre" + (i/2),new PointTwoDimensions(1,i)));
        }
	
		TSP best = new TSP(b);
		TSP result = (TSP)test.solveWithTournament();

		assertEquals(best.getFitness(),result.getFitness());
	}
	
	@Test
	void testSolve3tournament() {
		Random generator = new Random(0);
		List<City> a = new ArrayList<City>();
		for(int i=120; i<160;i+=2) {
			a.add(new City("Andre" + (i/2),new PointTwoDimensions(1,i)));
		}
		for(int i=0; i<40;i+=2) {
			a.add(new City("Andre" + (i/2),new PointTwoDimensions(1,i)));
		}
		for(int i=80; i<120;i+=2) {
			a.add(new City("Andre" + (i/2),new PointTwoDimensions(1,i)));
		}
		for(int i=40; i<80;i+=2) {
			a.add(new City("Andre" + (i/2),new PointTwoDimensions(1,i)));
		}
		
        TSP first = new TSP(a);
        GeneticAlgorithm test = new GeneticAlgorithm(first,generator,2000,4096,0.05,0.7,0.5,2);
        List<City> b = new ArrayList<City>();
        for(int i=0;i<160;i+=2) {
        	b.add(new City("Andre" + (i/2),new PointTwoDimensions(1,i)));
        }
	
		TSP best = new TSP(b);

		TSP result = (TSP)test.solveWithTournament();
		assertEquals(best.getFitness(),result.getFitness());
	}
	
	@Test
	void testSolve4tournament() {
		Random generator = new Random(0);
		List<City> a = new ArrayList<City>();
		for(int i=225; i<300;i+=2) {
			a.add(new City("Andre" + (i/2),new PointTwoDimensions(1,i)));
		}
		for(int i=0; i<75;i+=2) {
			a.add(new City("Andre" + (i/2),new PointTwoDimensions(1,i)));
		}
		for(int i=150; i<225;i+=2) {
			a.add(new City("Andre" + (i/2),new PointTwoDimensions(1,i)));
		}
		for(int i=75; i<150;i+=2) {
			a.add(new City("Andre" + (i/2),new PointTwoDimensions(1,i)));
		}
		
        TSP first = new TSP(a);
        GeneticAlgorithm test = new GeneticAlgorithm(first,generator,2000,16384,0.05,0.7,0.5,2);
        List<City> b = new ArrayList<City>();
        for(int i=0;i<300;i+=2) {
        	b.add(new City("Andre" + (i/2),new PointTwoDimensions(1,i)));
        }
	
		TSP best = new TSP(b);

		TSP result = (TSP)test.solveWithTournament();
		assertEquals(best.getFitness(),result.getFitness());
	}
}