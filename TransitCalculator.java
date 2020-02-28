/* @author Daniel Xiang
 * @version 1.3
 * @since 2020-02-28
 */

import java.util.*;
import static java.lang.System.out;

class TransitCalculator {
  
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
	TransitCalculator(int rides, int days) 
	{ numRides = rides; numDays = days; }
	
	// Methods
	private double unlimited7Price() {
		int numPasses = 0;
		if (numDays % 7 > 0)
			numPasses = numDays / 7 + 1;
		else
			numPasses = numDays / 7;
		
		double totalFare = numPasses * fares.get("7-day Unlimited");
		return totalFare / numRides;
	}
  
	private HashMap<String, Double> getRidePrices() {
		double payPerRide = fares.get("Single Ride");
		double unlimited7 = unlimited7Price();
		double monthly = fares.get("30-day Unlimited") / numRides;
		
		HashMap<String, Double> faresPerRide = new HashMap<>();
		faresPerRide.put("30-day Unlimited", monthly);
		faresPerRide.put("7-day Unlimited", unlimited7);
		faresPerRide.put("Single Ride", payPerRide);
		
		return faresPerRide;
	}
	
	private String getBestFare() {
		HashMap<String, Double> prices = getRidePrices();
		double bestFare = Collections.min(prices.values()); 
		String bestOption = "";
		
		for (Map.Entry<String, Double> entry : prices.entrySet()) 
			if (Objects.equals(bestFare, entry.getValue()))
				bestOption = entry.getKey();
		return bestOption;
	}
	
	
	// Utils
	private static void greeting() {
		out.print("\nHi there, I'm Best Fare Calculator for New York City Metro System. \nI can help you ");
		out.println("determine which fare option is the cheapest!");
		out.println("Currently, the fare options are:");
		out.println("------------------------------");
		out.print("| Single Ride      | $2.75   |\n");
		out.println("------------------------------");
		out.print("| 7-Day Unlimited  | $33.00  |\n");
		out.println("------------------------------");
		out.print("| 30-Day Unlimited | $127.00 |\n");
		out.println("------------------------------");
		out.println("(You can quit the program by typing 'quit' or 'q')");
	}
	
	private static boolean checkInput(String input, String type) {
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
			out.println("Do you qualify for the reduced fare option? ['yes' or 'y'/'no' or 'n'] "); 
			out.print("(Reduced fare is only for those who are at least 65 years old or have disability) ");
			input = sc.nextLine();
			if (input.equals("quit") || input.equals("q")) return;
			
			// Check for valid input
			while (! checkInput(input, "bool")) {
				out.print("Invalid input. Please try again: ");
				input = sc.nextLine();
				if (input.equals("quit") || input.equals("q")) return;
			}
			
			// Create hashmap to store fare options
			if (input.equals("yes") || input.equals("y")) {
				fares.put("Single Ride", 1.35);
				fares.put("7-day Unlimited", 16.50);
				fares.put("30-day Unlimited", 63.50);
				
				out.println("Currently, the reduced fare options are:");
				out.println("------------------------------");
				out.print("| Single Ride      | $1.35   |\n");
				out.println("------------------------------");
				out.print("| 7-Day Unlimited  | $16.50  |\n");
				out.println("------------------------------");
				out.print("| 30-Day Unlimited | $63.50 |\n");
				out.println("------------------------------");
				
			} 
			else { // input == 'no' or 'n'
				fares.put("Single Ride", 2.75);
				fares.put("7-day Unlimited", 33.00);
				fares.put("30-day Unlimited", 127.00);
			}
			
			// Read user input
			out.print("\nHow many days will you be using the transit system? ");
			input = sc.nextLine();
			if (input.equals("quit") || input.equals("q")) return;			
			while (! checkInput(input, "num")) {
				out.print("Invalid input. Please try again: ");
				input = sc.nextLine();
				if (input.equals("quit") || input.equals("q")) return;
			}
			int days = Integer.parseInt(input); 
			
			out.print("Enter the number of individual rides you expect to take during this period of time: ");
			input = sc.nextLine();
			if (input.equals("quit") || input.equals("q")) return;			
			while (! checkInput(input, "num")) {
				out.print("Invalid input. Please try again: ");
				input = sc.nextLine();
				if (input.equals("quit") || input.equals("q")) return;
			}
			int rides = Integer.parseInt(input);
			
			TransitCalculator tc = new TransitCalculator(rides, days);
			HashMap<String, Double> prices = tc.getRidePrices();
			String result = tc.getBestFare();
			
			out.println("\nThe equivelent prices per ride are: ");
			for (Map.Entry<String, Double> entry : prices.entrySet()) {
				out.print("* " + entry.getKey() + " : ");
				out.printf("$%.2f\n", entry.getValue());
			}
			
			out.print("Thus, you should get the option '" + result + "', which is equivelent to $");
			out.printf("%.2f per ride.\n", prices.get(result));
			out.println();
		}
	}
  
}
