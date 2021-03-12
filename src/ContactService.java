import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sun.net.httpserver.Authenticator.Result;


class SortContactByName implements Comparator<Contact> {
	@Override
	public int compare(Contact o1, Contact o2) {
		// TODO Auto-generated method stub
		
		if(o1.getContactName().compareTo(o2.getContactName()) < -1){
			return -1;
		}else if(o1.getContactName().compareTo(o2.getContactName()) == 0) {
			return 0;
		}
		return 1;
	}
}

public class ContactService {
	
	public void addContact(Contact contact,List<Contact>contacts){
		contacts.add(contact);
	}
	
	public void removeContact(Contact contact, List<Contact> contacts) throws ContactNotFoundException {
		if(contacts.contains(contact)) {
			contacts.remove(contact);
		}else {
			throw new ContactNotFoundException("This contact is not available");
		}
	}
	
	public Contact searchContactByName(String name, List<Contact> contact) throws ContactNotFoundException {
		for(Contact c: contact) {
			if(c.getContactName() == name) {
				return c;
			}
		}
		
		throw new ContactNotFoundException("Contact with this name is not available");
	}
	
	List<Contact> SearchContactByNumber(String number, List<Contact> contact) throws ContactNotFoundException{
		Set<Contact> contSet = new HashSet<Contact>();
		
		for(Contact c: contact) {
			List<String> cont = c.getContactNumber();
			for(String num:cont) {
				if(num.contains(number)) {
					contSet.add(c);
				}
			}
		}
		if(contSet.size() == 0) {
			throw new ContactNotFoundException("This number does not match with any of the contacts");
		}
		List<Contact> contlist = new ArrayList<Contact>();
		contlist.addAll(contSet);
		return contlist;
	}
	
	public void addContactNumber(int contactID,String contactNo, List<Contact> contacts) {
		for(Contact c: contacts) {
			if(c.getContactid() == contactID) {
				if(!c.getContactNumber().contains(contactNo)) {
					c.getContactNumber().add(contactNo);
				}
			}
		}
	}
	
	public void sortContactByName(List<Contact> contacts) {
		Collections.sort(contacts,new SortContactByName());
		for(Contact c: contacts) {
			System.out.println(c.getContactName());
		}
	}
	
	public void readContactFromFile(List<Contact> contacts, String filename) throws IOException {
		FileReader fr=new FileReader(filename);
		BufferedReader br=new BufferedReader(fr);   
		String line ="";
		while((line = br.readLine()) != null) {
			String arr[] = line.split(",");
			int id = Integer.parseInt(arr[0]);
			String name = arr[1];
			String email = arr[2];
			
			String temp[] = arr[3].split(";");
			List<String> list = new ArrayList<String>();
			for(String s: temp) {
				list.add(s);
			}
			
			Contact cnew = new Contact(id,name,email,list);
			contacts.add(cnew);
		}
	}
	 public void serializeContactDetails(List<Contact> contacts,String filename) {
		 try{
	        FileOutputStream fos = new FileOutputStream(filename);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(contacts);
	        oos.close();
	        fos.close();
		    }catch (IOException e) 
		    {
		        e.printStackTrace();
		    }
	 }
	 
	 public List<Contact> deserializeContact(String filename){
		 List<Contact> list = null;
		 
	        FileInputStream fis = null;
			try {
				fis = new FileInputStream(filename);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(fis);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        try {
				list = (ArrayList) ois.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        try {
				ois.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        return list;
		 
	 }
	 
	 Set<Contact> populateContactFromDb(){
		 Set<Contact>  contset =new HashSet<Contact>();
		 
		 Connection cn=DBConnectionUtil.getConnection();
		 
		 try {
			Statement stmt = cn.createStatement();
			String q = "Select * from contact_tbl";
			ResultSet rs = stmt.executeQuery(q);
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String arr = rs.getString(4);
				List<String> contList = new ArrayList<String>();
				if(arr != null) {
					String temp[]= arr.split(","); 
					for(String s:temp) {
						contList.add(s);
					}
				}
				
				Contact c = new Contact(id,name,email,contList);
				contset.add(c);
				
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBConnectionUtil.closeConnection();
		}
		 
		return contset;
	 }
	 
	 boolean addContacts(List<Contact> existingContact,Set<Contact>newContacts) {
		 return existingContact.addAll(newContacts);
	 }
	
}
