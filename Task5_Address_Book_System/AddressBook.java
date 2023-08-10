import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.*;
import java.util.*;

public class AddressBook {
    private List<Contact> contacts;

    public AddressBook() {
        contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContactByName(String name) {
        Iterator<Contact> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (contact.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                System.out.println("Contact removed successfully.");
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    public List<Contact> searchContactsByName(String searchTerm) {
        List<Contact> searchResults = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                searchResults.add(contact);
            }
        }
        return searchResults;
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

     public void saveContactsToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Contact contact : contacts) {
                writer.println(contact.getName() + "," + contact.getPhoneNumber() + "," + contact.getEmailAddress());
            }
            System.out.println("Contacts saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving contacts to file: " + e.getMessage());
        }
    }

    public void loadContactsFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            contacts.clear();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Contact contact = new Contact(parts[0], parts[1], parts[2]);
                    contacts.add(contact);
                }
            }
            System.out.println("Contacts loaded from " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
