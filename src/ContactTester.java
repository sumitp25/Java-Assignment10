import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ContactService cs  = new ContactService();
		List<Contact>contact_list = new ArrayList<Contact>();
		try {
			cs.readContactFromFile(contact_list, "Contacts.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> temp = new ArrayList<String>();
		temp.add("9444111122");
		Contact contact1 = new Contact(10,"Ajay","ayayp@gmail.com",temp);
		Contact contact2 = new Contact(11,"Mayank","mayankb@gmail.com",null);
		// Adding contacts
		cs.addContact(contact1, contact_list);
		cs.addContact(contact2, contact_list);
		
		// Removing contacts
		try {
			cs.removeContact(contact2, contact_list);
		} catch (ContactNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Search by Name
		try {
			Contact c1 = cs.searchContactByName("Ajay", contact_list);
		} catch (ContactNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Search by contact number
		List<Contact> list;
		try {
			list = cs.SearchContactByNumber("9444111122", contact_list);
			for(Contact c: list) {
				System.out.println(c.getContactName());
			}
		} catch (ContactNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// sorting contact list by name
		cs.sortContactByName(contact_list);
		for(Contact c: contact_list) {
			System.out.println(c.getContactName());
		}
		
		
		
	}

}
