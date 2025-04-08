package Main;
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

  public synchronized void displayEvent() {
    String details = "Event Name: " + name + "\nCost: Rs" + cost + "\nAttendees: " + attendees;
    System.out.println(details);
  }

  public synchronized double calculateTotalCost() {
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
  public synchronized void displayEvent() {
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

  @Override
  public synchronized void displayEvent() {
    super.displayEvent();
    System.out.println("Artist: " + artist);
  }
}

// Display Task with synchronized block
class DisplayTask implements Runnable {
  private Event event;
  private static final String ANSI_BLUE = "\u001B[34m";
  private static final String ANSI_RESET = "\u001B[0m";

  public DisplayTask(Event event) {
    this.event = event;
  }

  @Override
  public void run() {
    synchronized (System.out) {
      try {
        System.out.println(ANSI_BLUE + "Displaying Event Details..." + ANSI_RESET);
        event.displayEvent();
      } catch (Exception e) {
        System.err.println(ANSI_BLUE + "Error displaying event details: " + e.getMessage() + ANSI_RESET);
      }
    }
  }
}

// Calculate Task with synchronized block
class CalculateTotalCostTask implements Runnable {
  private Event event;
  private static final String ANSI_GREEN = "\u001B[32m";
  private static final String ANSI_RESET = "\u001B[0m";

  public CalculateTotalCostTask(Event event) {
    this.event = event;
  }

  @Override
  public void run() {
    synchronized (System.out) {
      try {
        double totalCost = event.calculateTotalCost();
        System.out.println(ANSI_GREEN + "Total Cost: Rs" + totalCost + ANSI_RESET);
      } catch (Exception e) {
        System.err.println(ANSI_GREEN + "Error calculating total cost: " + e.getMessage() + ANSI_RESET);
      }
    }
  }
}

public class eventOrganizer {
  private static final String ANSI_RED = "\u001B[31m";
  private static final String ANSI_RESET = "\u001B[0m";

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int numEvents = 0;

    // Get number of events with input validation
    while (true) {
      try {
        System.out.print("Enter the number of events you want to input: ");
        numEvents = Integer.parseInt(scanner.nextLine());
        if (numEvents <= 0) {
          System.out.println(ANSI_RED + "Please enter a number greater than 0." + ANSI_RESET);
        } else {
          break;
        }
      } catch (NumberFormatException e) {
        System.out.println(ANSI_RED + "Invalid input! Please enter a valid number." + ANSI_RESET);
      }
    }

    Event[] events = new Event[numEvents];

    for (int i = 0; i < numEvents; i++) {
      System.out.println("\nEnter details for event " + (i + 1));
      String eventType = "";

      // Get event type with validation
      while (true) {
        System.out.print("Enter event type (Conference/Concert): ");
        eventType = scanner.nextLine();
        if (eventType.equalsIgnoreCase("Conference") || eventType.equalsIgnoreCase("Concert")) {
          break;
        } else {
          System.out.println(ANSI_RED + "Invalid event type! Please enter either 'Conference' or 'Concert'." + ANSI_RESET);
        }
      }

      // Get event name
      System.out.print("Enter event name: ");
      String name = scanner.nextLine();

      // Get cost per attendee with validation
      double cost = 0;
      while (true) {
        try {
          System.out.print("Enter cost per attendee: ");
          cost = Double.parseDouble(scanner.nextLine());
          if (cost <= 0) {
            System.out.println(ANSI_RED + "Cost must be greater than 0. Try again." + ANSI_RESET);
          } else {
            break;
          }
        } catch (NumberFormatException e) {
          System.out.println(ANSI_RED + "Invalid input! Please enter a valid cost." + ANSI_RESET);
        }
      }

      // Get number of attendees with validation
      int attendees = 0;
      while (true) {
        try {
          System.out.print("Enter number of attendees: ");
          attendees = Integer.parseInt(scanner.nextLine());
          if (attendees <= 0) {
            System.out.println(ANSI_RED + "Attendees must be greater than 0. Try again." + ANSI_RESET);
          } else {
            break;
          }
        } catch (NumberFormatException e) {
          System.out.println(ANSI_RED + "Invalid input! Please enter a valid number of attendees." + ANSI_RESET);
        }
      }

      // Create event object based on type
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
      Thread displayThread = new Thread(new DisplayTask(event), "DisplayThread");
      Thread calculateThread = new Thread(new CalculateTotalCostTask(event), "CalculateThread");

      displayThread.start();
      calculateThread.start();

      // Wait for threads to finish with proper exception handling
      try {
        displayThread.join();
        calculateThread.join();
      } catch (InterruptedException e) {
        System.err.println(ANSI_RED + "Thread interrupted: " + e.getMessage() + ANSI_RESET);
      }
      System.out.println("-----------");
    }
    scanner.close();
    System.out.println(ANSI_RED + "Main thread exiting." + ANSI_RESET);
  }
}
