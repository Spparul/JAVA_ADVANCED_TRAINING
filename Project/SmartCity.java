import java.util.Scanner;

/*
 * SmartCity (Main Class)
 * ----------------------
 * This is the entry point of the program.
 * It acts as a simple user interface (menu-driven system)
 * where a citizen interacts with the smart city services.
 *
 * IMPORTANT:
 * This class only handles input/output.
 * It does NOT contain business logic.
 */
public class SmartCity {

    public static void main(String[] args) {

        // Get the single instance of ControlCenter (Singleton pattern)
        ControlCenter control = ControlCenter.getInstance();

        Scanner sc = new Scanner(System.in);

        // Taking basic citizen details (identity + location context)
        System.out.print("Enter Citizen ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Location: ");
        String location = sc.nextLine();

        // Creating a Citizen object which represents a user in the system
        Citizen citizen = new Citizen(id, name, location);

        int choice;

        /*
         * Menu loop:
         * Keeps running until user exits.
         * This simulates continuous interaction with the smart city.
         */
        do {
            System.out.println("\n--- SMART CITY MENU ---");
            System.out.println("1. Request Transport");
            System.out.println("2. Request Utility");
            System.out.println("3. Report Emergency");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                // Transport request
                case 1:
                    System.out.print("Enter Transport Type (Bus/Taxi/Auto/Bike/Train): ");
                    String tType = sc.nextLine();
                    citizen.requestTransport(control, tType);
                    break;

                // Utility request (waste, water, energy)
                case 2:
                    System.out.print("Enter Utility Type (Waste/Water/Energy): ");
                    String uType = sc.nextLine();
                    citizen.requestUtility(control, uType);
                    break;

                // Emergency request
                case 3:
                    System.out.print("Enter Emergency Type (Police/Medical/Fire): ");
                    String eType = sc.nextLine();
                    citizen.reportEmergency(control, eType);
                    break;

                case 4:
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 4);

        sc.close();
    }
}

//////////////////////////////////////////////////////////

/*
 * Citizen Class
 * -------------
 * Represents a user in the smart city.
 *
 * Key Idea:
 * Citizen does NOT directly interact with services.
 * It always communicates through ControlCenter.
 *
 * This ensures:
 * - Loose coupling
 * - Centralized control
 */
class Citizen {

    private int id;
    private String name;
    private String location;

    // Constructor initializes identity + location (context for all actions)
    public Citizen(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    // Sends transport request to ControlCenter
    public void requestTransport(ControlCenter control, String type) {
        control.handleTransportRequest(this, type);
    }

    // Sends utility request to ControlCenter
    public void requestUtility(ControlCenter control, String type) {
        control.handleUtilityRequest(this, type);
    }

    // Sends emergency alert to ControlCenter
    public void reportEmergency(ControlCenter control, String type) {
        control.handleEmergency(this, type);
    }

    // Getters used by other modules
    public String getName() { return name; }
    public int getId() { return id; }
    public String getLocation() { return location; }
}

//////////////////////////////////////////////////////////

/*
 * ControlCenter Class (Core of the System)
 * ----------------------------------------
 * This acts as the "brain" of the smart city.
 *
 * Responsibilities:
 * - Receives all requests
 * - Decides which service to call
 * - Coordinates system flow
 *
 * Design Pattern:
 * Singleton → Only one ControlCenter exists
 */
class ControlCenter {

    private static ControlCenter instance;

    private UtilityService utilityService;
    private TransportService transportService;
    private EmergencyService emergencyService;

    // Private constructor ensures no external object creation
    private ControlCenter() {
        utilityService = new UtilityService();
        transportService = new TransportService();
        emergencyService = new EmergencyService();
    }

    // Provides global access to single instance
    public static ControlCenter getInstance() {
        if (instance == null) {
            instance = new ControlCenter();
        }
        return instance;
    }

    // Handles transport requests and forwards to TransportService
    public void handleTransportRequest(Citizen c, String type) {
        System.out.println("\n[ControlCenter]");
        System.out.println("Citizen " + c.getId() + " (" + c.getName() + ") at " + c.getLocation()
                + " requested " + type);

        transportService.assignVehicle(c, type);
    }

    // Handles utility requests
    public void handleUtilityRequest(Citizen c, String type) {
        System.out.println("\n[ControlCenter]");
        System.out.println("Citizen " + c.getId() + " (" + c.getName() + ") at " + c.getLocation()
                + " requested " + type);

        utilityService.handleUtility(c, type);
    }

    // Handles emergency situations
    public void handleEmergency(Citizen c, String type) {
        System.out.println("\n[EMERGENCY ALERT]");
        System.out.println("Citizen " + c.getId() + " (" + c.getName() + ") at " + c.getLocation());

        emergencyService.dispatch(c, type);
    }
}

//////////////////////////////////////////////////////////

/*
 * UtilityService Class
 * --------------------
 * Combines multiple utility systems:
 * - Waste
 * - Water
 * - Energy
 *
 * Uses COMPOSITION:
 * UtilityService "has" WasteSystem, WaterSystem, EnergySystem
 */
class UtilityService {

    private WasteSystem waste = new WasteSystem();
    private WaterSystem water = new WaterSystem();
    private EnergySystem energy = new EnergySystem();

    // Routes request to appropriate subsystem
    public void handleUtility(Citizen c, String type) {

        switch (type) {
            case "Waste":
                waste.collect(c.getLocation());
                break;
            case "Water":
                water.supply(c.getLocation());
                break;
            case "Energy":
                energy.distribute(c.getLocation(), "Grid");
                break;
            default:
                System.out.println("Invalid utility type");
        }
    }
}

/*
 * Subsystems:
 * Each class performs a specific responsibility.
 * This ensures HIGH COHESION.
 */
class WasteSystem {
    void collect(String location) {
        System.out.println("Waste collected at " + location);
    }
}

class WaterSystem {
    void supply(String location) {
        System.out.println("Water supplied to " + location);
    }
}

class EnergySystem {
    void distribute(String location, String type) {
        System.out.println(type + " energy supplied to " + location);
    }
}

//////////////////////////////////////////////////////////

/*
 * TransportService Class
 * ----------------------
 * Responsible for assigning vehicles.
 *
 * Demonstrates:
 * - Inheritance
 * - Polymorphism
 */
class TransportService {

    public void assignVehicle(Citizen c, String type) {

        Vehicle vehicle;

        // Selecting object based on type (runtime decision)
        switch (type) {
            case "Bus": vehicle = new Bus(); break;
            case "Taxi": vehicle = new Taxi(); break;
            case "Auto": vehicle = new Auto(); break;
            case "Bike": vehicle = new Bike(); break;
            case "Train": vehicle = new Train(); break;
            default:
                System.out.println("Invalid transport type");
                return;
        }

        System.out.println(type + " assigned to " + c.getName() + " at " + c.getLocation());

        // Polymorphism: same method, different behavior
        vehicle.move(c.getLocation());
    }
}

/*
 * Abstract base class:
 * Defines common behavior for all vehicles
 */
abstract class Vehicle {
    abstract void move(String location);
}

class Bus extends Vehicle {
    void move(String location) {
        System.out.println("Bus moving on route from " + location);
    }
}

class Taxi extends Vehicle {
    void move(String location) {
        System.out.println("Taxi picking passenger from " + location);
    }
}

class Auto extends Vehicle {
    void move(String location) {
        System.out.println("Auto serving short ride at " + location);
    }
}

class Bike extends Vehicle {
    void move(String location) {
        System.out.println("Bike quickly moving from " + location);
    }
}

class Train extends Vehicle {
    void move(String location) {
        System.out.println("Train running near " + location);
    }
}

//////////////////////////////////////////////////////////

/*
 * EmergencyService Class
 * ----------------------
 * Handles critical situations like:
 * - Police
 * - Medical
 * - Fire
 *
 * Uses polymorphism to dispatch correct response unit
 */
class EmergencyService {

    public void dispatch(Citizen c, String type) {

        EmergencyUnit unit;

        switch (type) {
            case "Police": unit = new Police(); break;
            case "Medical": unit = new Ambulance(); break;
            case "Fire": unit = new FireService(); break;
            default:
                System.out.println("Invalid emergency type");
                return;
        }

        System.out.println("Type: " + type);
        System.out.println("Dispatching unit to " + c.getLocation());

        // Polymorphism in action
        unit.respond(c.getLocation());
    }
}

/*
 * Abstract base class for emergency units
 */
abstract class EmergencyUnit {
    abstract void respond(String location);
}

class Police extends EmergencyUnit {
    void respond(String location) {
        System.out.println("Police responding at " + location);
    }
}

class Ambulance extends EmergencyUnit {
    void respond(String location) {
        System.out.println("Ambulance responding at " + location);
    }
}

class FireService extends EmergencyUnit {
    void respond(String location) {
        System.out.println("Fire service responding at " + location);
    }
}
