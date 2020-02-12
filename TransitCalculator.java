/* @author Daniel Xiang
 * @version 1.2
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
	
	
	// Utils
	public static void prt(String str, boolean newline) {
		if (newline)
			System.out.println(str);
		else
			System.out.print(str);
	}
	
	public static void greeting() {
		prt("\nHi there, I'm Best Fare Calculator. I can help you ", false);
		prt("determine which fare option is the cheapest!", true);
		prt("(You can quit the program by typing 'quit' or 'q')", true);
	}
	
	public static boolean checkInput(String input, String type) {
		boolean valid = false;
		if (type.equals("bool")) {
			if (input.equals("yes") || input.equals("y") ||
				input.equals("no")  || input.equals("n"))
				valid = true;
		}
		else if (type.equals("num")) {
			try {
				int i = Integer.parseInt(input);
				if (i > 0) valid = true;
			} catch (Exception e) {
				valid = false;
			}
		}
		
		return valid;
	}
	
	
	// Main
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String input = "";
		
		while (true) {
			greeting();
			
			// To see if the user qualifies for reduced fares
			prt("Do you qualify for the reduced fare option? ['yes' or 'y'/'no' or 'n'] ", true); 
			prt("(Reduced fare is only for those who are at least 65 years old or have disability) ", false);
			input = sc.nextLine();
			if (input.equals("quit") || input.equals("q")) return;
			
			// Check for valid input
			while (! checkInput(input, "bool")) {
				prt("Invalid input. Please try again: ", false);
				input = sc.nextLine();
				if (input.equals("quit") || input.equals("q")) return;
			}
			
			// Create hashmap to store fare options
			if (input.equals("yes") || input.equals("y")) {
				fares.put("Pay-per-ride", 1.35);
				fares.put("7-day Unlimited Rides", 16.50);
				fares.put("30-day Unlimited Rides", 63.50);
			} 
			else { // input == 'no' or 'n'
				fares.put("Pay-per-ride", 2.75);
				fares.put("7-day Unlimited Rides", 33.00);
				fares.put("30-day Unlimited Rides", 127.00);
			}
			
			// Read user input
			prt("How many days will you be using the transit system? ", false);
			input = sc.nextLine();
			if (input.equals("quit") || input.equals("q")) return;			
			while (! checkInput(input, "num")) {
				prt("Invalid input. Please try again: ", false);
				input = sc.nextLine();
				if (input.equals("quit") || input.equals("q")) return;
			}
			int days = Integer.parseInt(input); 
			
			prt("Enter the number of individual rides you expect to take during this period of time: ", false);
			input = sc.nextLine();
			if (input.equals("quit") || input.equals("q")) return;			
			while (! checkInput(input, "num")) {
				prt("Invalid input. Please try again: ", false);
				input = sc.nextLine();
				if (input.equals("quit") || input.equals("q")) return;
			}
			int rides = Integer.parseInt(input);
			
			TransitCalculator tc = new TransitCalculator(rides, days);
			HashMap<String, Double> prices = tc.getRidePrices();
			String result = tc.getBestFare();
			
			prt("The prices are: ", true);
			for (Map.Entry<String, Double> entry : prices.entrySet()) {
				prt(entry.getKey() + " : ", false);
				System.out.printf("$%.2f\n", entry.getValue());
			}
			
			prt("Thus, you should get the option '" + result + "', which is equivelent to $", false);
			System.out.printf("%.2f per ride.", prices.get(result));
			prt("", true);
		}
	}
  
}
