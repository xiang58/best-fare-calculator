/* @author Daniel Xiang
 * @version 1.1
 * @since 2020-02-11
 */

import java.util.*;

public class TransitCalculator {
  
	/* Number of days a person will be 
	 * using the transit system (up to 30 days)
	 */
	private int numDays;
	
	/* Number of individual rides the person
	 * expects to take in that time.
	 */
	private int numRides;
	
	// Hashmap to keep track of fare options
	private static HashMap<String, Double> fares = new HashMap<>();
	
	
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
		
		double totalFare = numPasses * fares.get("7-day Unlimited Rides");
		return totalFare / numRides;
	}
  
	public HashMap<String, Double> getRidePrices() {
		double payPerRide = fares.get("Pay-per-ride");
		double unlimited7 = unlimited7Price();
		double monthly = fares.get("30-day Unlimited Rides") / numRides;
		
		HashMap<String, Double> faresPerRide = new HashMap<>();
		faresPerRide.put("Pay-per-ride", payPerRide);
		faresPerRide.put("7-day Unlimited Rides", unlimited7);
		faresPerRide.put("30-day Unlimited Rides", monthly);
		
		return faresPerRide;
	}
	
	public String getBestFare() {
		HashMap<String, Double> prices = getRidePrices();
		double bestFare = Collections.min(prices.values()); 
		String bestOption = "";
		
		for (Map.Entry<String, Double> entry : prices.entrySet()) 
			if (Objects.equals(bestFare, entry.getValue()))
				bestOption = entry.getKey();
		return bestOption;
	}
	
	public static void prt(String str, boolean newline) {
		if (newline)
			System.out.println(str);
		else
			System.out.print(str);
	}
	
	
	// Main
	public static void main(String[] args) {
		// Create hashmap to store fare options
		fares.put("Pay-per-ride", 2.75);
		fares.put("7-day Unlimited Rides", 33.00);
		fares.put("30-day Unlimited Rides", 127.00);
		
		// Read user input
		Scanner sc = new Scanner(System.in);
		prt("How many days will you be using the transit system? ", false);
		int days = sc.nextInt();
		prt("The number of individual rides you expect to take during this period of time: ", false);
		int rides = sc.nextInt();
		
		TransitCalculator tc = new TransitCalculator(rides, days);
		HashMap<String, Double> prices = tc.getRidePrices();
		String result = tc.getBestFare();
		prt("You should get the " + result + " at $", false);
		System.out.printf("%.2f per ride.", prices.get(result));
	}
  
}
