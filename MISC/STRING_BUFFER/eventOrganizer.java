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
        StringBuffer details = new StringBuffer();
        details.append("Event Name: ").append(name)
               .append("\nCost: Rs").append(cost)
               .append("\nAttendees: ").append(attendees);
        
        // Insert a note before attendees
        details.insert(details.indexOf("Attendees"), "(Important) ");
        
        // Replace "Cost" with "Price" for variety
        int startCostIndex = details.indexOf("Cost:");
        int endCostIndex = startCostIndex + 4; // Length of "Cost"
        details.replace(startCostIndex, endCostIndex, "Price");

        // Reverse event name to add fun detail
        StringBuffer reverseName = new StringBuffer(name);
        details.append("\nReversed Event Name: ").append(reverseName.reverse());
        
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

    @Override
    public void displayEvent() {
        super.displayEvent();
        StringBuffer details = new StringBuffer();
        details.append("Speaker: ").append(speaker);
        
        // Delete extra characters if speaker name is too long
        if (speaker.length() > 10) {
            details.delete(10, speaker.length());
            details.append("...");
        }

        System.out.println(details);
    }
}

class Concert extends Event {
    String artist;

    public Concert(String name, double cost, int attendees, String artist) {
        super(name, cost, attendees);
        this.artist = artist;
    }

    @Override
    public void displayEvent() {
        super.displayEvent();
        StringBuffer details = new StringBuffer();
        details.append("Artist: ").append(artist);
        
        // Reverse artist name for fun
        StringBuffer reverseArtist = new StringBuffer(artist);
        details.append("\nReversed Artist Name: ").append(reverseArtist.reverse());

        System.out.println(details);
    }
}

public class eventOrganizer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of events you want to input: ");
        int numEvents = scanner.nextInt();
        scanner.nextLine();

        Event[] events = new Event[numEvents];

        for (int i = 0; i < numEvents; i++) {
            System.out.println("\nEnter details for event " + (i + 1));

            System.out.print("Enter event type (Conference/Concert): ");
            String eventType = scanner.nextLine();

            System.out.print("Enter event name: ");
            String name = scanner.nextLine();

            System.out.print("Enter cost per attendee: ");
            double cost = scanner.nextDouble();

            System.out.print("Enter number of attendees: ");
            int attendees = scanner.nextInt();
            scanner.nextLine();

            if (eventType.equalsIgnoreCase("Conference")) {
                System.out.print("Enter speaker name: ");
                String speaker = scanner.nextLine();
                events[i] = new Conference(name, cost, attendees, speaker);
            } else if (eventType.equalsIgnoreCase("Concert")) {
                System.out.print("Enter artist name: ");
                String artist = scanner.nextLine();
                events[i] = new Concert(name, cost, attendees, artist);
            } else {
                System.out.println("Invalid event type! Please enter either 'Conference' or 'Concert'.");
                i--;
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
