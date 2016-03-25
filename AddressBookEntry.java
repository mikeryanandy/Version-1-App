package version1;
import version1.*;
import java.util.*;

public class AddressBookEntry implements Comparable<AddressBookEntry>,Comparator<AddressBookEntry>{
private String name;
private String address;
private String phoneNumber;
private String email;
private String zipCode;

public AddressBookEntry(String name){
	this(name,null,null,null,null);
}

public AddressBookEntry(String name,String address,String phoneNumber, String email,String zipCode){
	this.name = name;
	this.address = address;
	this.phoneNumber = phoneNumber;
	this.email = email;
	this.zipCode = zipCode;
}

public String getName(){
	return this.name;
}

public String getZipCode(){
	return this.zipCode;
}

public void setAddress(String address){
	this.address=address;
}
public void setPhoneNumber(String phone){
	this.phoneNumber=phone;
}
public void setEmail(String email){
	this.zipCode=email;
}
public void setZipCode(String zip){
	this.zipCode=zip;
}



public String toString(){
	return "\n\nname: " + name
			+ "\naddress: " + address
			+ "\nphone: " + phoneNumber
			+ "\nemail: " + email
			+ "\nzipCode: " + zipCode;
}

public String toFileRecord(){
	return name + "," + address + "," + phoneNumber + "," + email + "," + zipCode;

}
public boolean equals(Object o) {
 
        // If the object is compared with itself then return true  
        if (o == this) {
            return true;
        }
 
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof AddressBookEntry)) {
            return false;
        }
         
        // typecast o to Complex so that we can compare data members 
        AddressBookEntry c = (AddressBookEntry) o;
         
        // Compare the data members and return accordingly 
        return (this.name).equals(c.name);
    }
	
// Overriding the compareTo method	
   public int compareTo(AddressBookEntry d){
      return (this.name.toLowerCase()).compareTo(d.getName().toLowerCase());
   }

   // Overriding the compare method to sort the zip 
   public int compare(AddressBookEntry d, AddressBookEntry d1){
      return (d.zipCode).compareTo(d1.getZipCode());
   }
}