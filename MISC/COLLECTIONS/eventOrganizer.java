
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Event {
  String name;
  float cost;
  int attendees;

  public Event(String name, float cost, int attendees) {
    this.name = name;
    this.cost = cost;
    this.attendees = attendees;
  }

  public void displayEvent() {
    String costDisplay = (cost == 0) ? "free" : "₹" + cost;
    String details = "Event Name: " + name + "\nCost: " + costDisplay + "\nAttendees: " + attendees;
    System.out.println(details);
  }

  public float calculateTotalCost() {
    return cost * attendees;
  }
}

class Conference extends Event {
  String speaker;

  public Conference(String name, float cost, int attendees, String speaker) {
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

  public Concert(String name, float cost, int attendees, String artist) {
    super(name, cost, attendees);
    this.artist = artist;
  }

  public void displayEvent() {
    super.displayEvent();
    System.out.println("Artist: " + artist);
  }
}

// For displaying event details using threads
class DisplayTask implements Runnable {
  private Event event;

  public DisplayTask(Event event) {
    this.event = event;
  }

  @Override
  public void run() {
    event.displayEvent();
  }
}

// For calculating the total cost using threads
class CalculateTotalCostTask implements Runnable {
  private Event event;

  public CalculateTotalCostTask(Event event) {
    this.event = event;
  }

  @Override
  public void run() {
    float totalCost = event.calculateTotalCost();
    System.out.println("Total Cost: ₹" + totalCost);
  }
}

public class eventOrganizer {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int numEvents = 0;
    List<Event> events = new ArrayList<>();
    
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
      float cost = 0;
      int attendees = 0;
      System.out.print("Enter event name: ");
      name = scanner.nextLine();

      while (true) {
        try {
          System.out.print("Enter cost per attendee : ");
          String costInput = scanner.nextLine();
          if (costInput.equals("0")) {
            cost = 0;
            break;
          } else {
            cost = Float.parseFloat(costInput);
            if (cost < 0) {//validation 
              System.out.println("Cost must be greater than or equal to 0.");
            } else {
              break;
            }
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
        System.out.print("Enter speaker name (or type 'TBA' or 'NIL' if not available): ");
        String speaker = scanner.nextLine();
        events.add(new Conference(name, cost, attendees, speaker));
      } else if (eventType.equalsIgnoreCase("Concert")) {
        System.out.print("Enter artist name: ");
        String artist = scanner.nextLine();
        events.add(new Concert(name, cost, attendees, artist));
      }
      System.out.println("-----------------------------------");
    }

    System.out.println("\nEvent Details:");
    for (Event event : events) {
      Thread displayThread = new Thread(new DisplayTask(event), "DisplayThread");
      displayThread.start();
      Thread calculateThread = new Thread(new CalculateTotalCostTask(event), "CalculateThread");
      calculateThread.start();
      try {
        displayThread.join();
        calculateThread.join();
      } catch (InterruptedException e) {
        System.out.println("Thread interrupted.");
      }
      System.out.println("-----------");
    }

    scanner.close();
    System.out.println("Main thread exiting.");
  }
}
