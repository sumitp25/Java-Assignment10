import java.util.List;

public class Contact {
	private int contactid;
	private String contactName;
	private String email;
	private List<String> contactNumber;
	
	public Contact() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String toString() {
		return "Contact [contactid=" + contactid + ", contactName=" + contactName + ", email=" + email
				+ ", contactNumber=" + contactNumber + "]";
	}


	public Contact(int contactid, String contactName, String email, List<String> contactNumber) {
		super();
		this.contactid = contactid;
		this.contactName = contactName;
		this.email = email;
		this.contactNumber = contactNumber;
	}


	public int getContactid() {
		return contactid;
	}
	public void setContactid(int contactid) {
		this.contactid = contactid;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(List<String> contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	
	
}
