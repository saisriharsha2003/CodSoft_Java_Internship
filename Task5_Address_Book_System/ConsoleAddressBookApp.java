import java.io.*;
import java.util.*;

public class ConsoleAddressBookApp {
    private static AddressBook addressBook = new AddressBook();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("Address Book System");
            System.out.println("1. Add Contact");
            System.out.println("2. Remove Contact");
            System.out.println("3. Search Contacts");
            System.out.println("4. Display All Contacts");
            System.out.println("5. Save Contacts to File");
            System.out.println("6. Load Contacts from File");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    removeContact();
                    break;
                case 3:
                    searchContacts();
                    break;
                case 4:
                    displayContacts();
                    break;
                case 5:
                    saveContacts();
                    break;
                case 6:
                    loadContacts();
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }

        System.out.println("Exiting Address Book System.");
    }

    private static void addContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter email address: ");
        String emailAddress = scanner.nextLine();

        Contact newContact = new Contact(name, phoneNumber, emailAddress);
        addressBook.addContact(newContact);
        System.out.println("Contact added successfully.");
    }

    private static void removeContact() {
        System.out.print("Enter the name of the contact to remove: ");
        String nameToRemove = scanner.nextLine();

        addressBook.removeContactByName(nameToRemove);
    }

    private static void searchContacts() {
        System.out.print("Enter search term (name): ");
        String searchTerm = scanner.nextLine();

        List<Contact> searchResults = addressBook.searchContactsByName(searchTerm);

        if (searchResults.isEmpty()) {
            System.out.println("No contacts found with the given name.");
        } else {
            System.out.println("Search Results:");
            for (Contact contact : searchResults) {
                System.out.println("Name: " + contact.getName());
                System.out.println("Phone: " + contact.getPhoneNumber());
                System.out.println("Email: " + contact.getEmailAddress());
                System.out.println();
            }
        }
    }

    private static void displayContacts() {
        List<Contact> contacts = addressBook.getAllContacts();

        System.out.println("All Contacts:");
        for (Contact contact : contacts) {
            System.out.println("Name: " + contact.getName());
            System.out.println("Phone: " + contact.getPhoneNumber());
            System.out.println("Email: " + contact.getEmailAddress());
            System.out.println();
        }
    }

    private static void saveContacts() {
        System.out.print("Enter filename to save contacts: ");
        String filename = scanner.nextLine();
        addressBook.saveContactsToFile(filename);
    }

    private static void loadContacts() {
        System.out.print("Enter filename to load contacts from: ");
        String filename = scanner.nextLine();
        addressBook.loadContactsFromFile(filename);
    }
}
