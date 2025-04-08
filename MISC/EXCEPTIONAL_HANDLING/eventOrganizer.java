import java.util.Scanner;

class Event {
	String name;
	double cost;
	int attendees;

	public Event(String name, double cost, int attendees) {
		this.name = name;
		this.cost = cost;
		this.attendees = attendees;
	}

	public void displayEvent() {
		String details = "Event Name: " + name + "\nCost: Rs" + cost + "\nAttendees: " + attendees;
		System.out.println(details);
	}

	public double calculateTotalCost() {
		return cost * attendees;
	}
}

class Conference extends Event {
	String speaker;

	public Conference(String name, double cost, int attendees, String speaker) {
		super(name, cost, attendees);
		this.speaker = speaker;
	}

	public void displayEvent() {
		super.displayEvent();
		System.out.println("Speaker: " + speaker);
	}
}

class Concert extends Event {
	String artist;

	public Concert(String name, double cost, int attendees, String artist) {
		super(name, cost, attendees);
		this.artist = artist;
	}

	public void displayEvent() {
		super.displayEvent();
		System.out.println("Artist: " + artist);
	}
}

public class eventOrganizer {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int numEvents = 0;

		while (true) {
			try {
				System.out.print("Enter the number of events you want to input: ");
				numEvents = Integer.parseInt(scanner.nextLine());
				if (numEvents <= 0) {
					System.out.println("Please enter a number greater than 0.");
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input! Please enter a valid number.");
			}
		}

		Event[] events = new Event[numEvents];

		for (int i = 0; i < numEvents; i++) {
			System.out.println("\nEnter details for event " + (i + 1));

			String eventType = "";
			while (true) {
				System.out.print("Enter event type (Conference/Concert): ");
				eventType = scanner.nextLine();

				if (eventType.equalsIgnoreCase("Conference") || eventType.equalsIgnoreCase("Concert")) {
					break;
				} else {
					System.out.println("Invalid event type! Please enter either 'Conference' or 'Concert'.");
				}
			}

			String name = "";
			double cost = 0;
			int attendees = 0;

			System.out.print("Enter event name: ");
			name = scanner.nextLine();

			while (true) {
				try {
					System.out.print("Enter cost per attendee: ");
					cost = Double.parseDouble(scanner.nextLine());
					if (cost <= 0) {
						System.out.println("Cost must be greater than 0. Try again.");
					} else {
						break;
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid input! Please enter a valid cost.");
				}
			}

			while (true) {
				try {
					System.out.print("Enter number of attendees: ");
					attendees = Integer.parseInt(scanner.nextLine());
					if (attendees <= 0) {
						System.out.println("Attendees must be greater than 0. Try again.");
					} else {
						break;
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid input! Please enter a valid number of attendees.");
				}
			}

			if (eventType.equalsIgnoreCase("Conference")) {
				System.out.print("Enter speaker name: ");
				String speaker = scanner.nextLine();
				events[i] = new Conference(name, cost, attendees, speaker);
			} else if (eventType.equalsIgnoreCase("Concert")) {
				System.out.print("Enter artist name: ");
				String artist = scanner.nextLine();
				events[i] = new Concert(name, cost, attendees, artist);
			}

			System.out.println("-----------");
		}

		System.out.println("\nEvent Details:");
		for (Event event : events) {
			event.displayEvent();
			System.out.println("Total Cost: Rs" + event.calculateTotalCost());
			System.out.println("-----------");
		}
		scanner.close();
	}
}
