import java.util.Arrays;
import java.util.ArrayList;

public class TransitCalculator {
  
	/* Number of days a person will be 
	* using the transit system (up to 30 days)
	*/
	public int numDays;
	
	/* Number of individual rides the person
	* expects to take in that time.
	*/
	public int numRides;
	
	// Arrays to keep track of fare options
	public static ArrayList<Double> fares = new ArrayList<>(
		Arrays.asList(2.75, 33.00, 127.00)
	);
	public static ArrayList<String> options = new ArrayList<>(
		Arrays.asList("Pay-per-ride", "7-day Unlimited Rides", "30-day Unlimited Rides")
	);
	
	// Constructor
	public TransitCalculator(int rides, int days) 
	{ numRides = rides; numDays = days; }
	
	// Methods
	public double unlimited7Price() {
		int numPasses = 0;
		if (numDays % 7 > 0)
		numPasses = numDays / 7 + 1;
		else
		numPasses = numDays / 7;
		
		double totalFare = numPasses * fares.get(1);
		return totalFare / numRides;
	}
  
	public ArrayList<Double> getRidePrices() {
		double payPerRide = fares.get(0);
		double unlimited7 = unlimited7Price();
		double monthly = fares.get(2) / numRides;
		return new ArrayList<Double>(
			Arrays.asList(payPerRide, unlimited7, monthly)
		);
	}
	
	public String getBestFare() {
		ArrayList<Double> prices = getRidePrices();
		double bestFare = Double.POSITIVE_INFINITY;
		for (double p : prices) 
			if (p < bestFare) bestFare = p;
		
		int bestIndex = prices.indexOf(bestFare);
		return options.get(bestIndex);
	}
	
	// Main
	public static void main(String[] args) {
		TransitCalculator test = new TransitCalculator(12, 5);
		ArrayList<Double> rates = test.getRidePrices();
		String result = test.getBestFare();
		int index = options.indexOf(result);
		System.out.println("You should get the " + result +
				" option at $" + rates.get(index) +
				" per ride.");
	}
  
}
