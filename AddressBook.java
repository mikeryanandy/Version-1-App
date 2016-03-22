package version1;

import version1.*;
import java.nio.file.*;
import java.util.stream.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;

public class AddressBook{
private static AddressBook application;
private String fileName; 
private List<AddressBookEntry> listOfAddressRecords;
private AddressBookEntry abe;


public static void main(String[] args){
	System.out.println("Welcome to the AddressBook application");
	application = new AddressBook();
	application.runApp();
	application.writeToFile();
}

public void runApp(){	
	setFileName("C:\\apps\\Version1 App\\version1\\AddressBook.txt");
	listOfAddressRecords = application.readFromFile();
	Scanner myScanner = new Scanner(System.in);
	
	String input = "";
	do{
		System.out.print("Enter S to Search or L to list all entries(E to exit:)");
		input = myScanner.nextLine();
		switch(input){
			case "S":
			case "s":
				openSearchDialogue();
				break;
			case "L":
			case "l":
				openListDialogue();
				break;
			default:
				break;
		}
	}while(!(input.equals("E")));
}

public void openSearchDialogue(){
	System.out.print("Search for name: ");
	Scanner s = new Scanner(System.in);
	String name = s.nextLine();
	String address;
	String email;
	String zipCode;
	long phoneNumber;
	
	Collections.sort(listOfAddressRecords);
	AddressBookEntry d = new AddressBookEntry(name);
	int index = (Collections.binarySearch(listOfAddressRecords,d));
	if(index >=0){
		System.out.println("Record found - Full details of "+name+":");
		d=listOfAddressRecords.get(index);
		System.out.println(d);
		String input = null;
		do{
		System.out.println("Do you want to (E)dit or (D)elete this record");
		 input = s.nextLine();
		
			switch(input){
				case "E":
					System.out.println("Enter address: ");
					d.setAddress(s.nextLine());
					System.out.print("Enter phone number: ");
					d.setPhoneNumber(s.nextLong());
					s.nextLine();
					System.out.print("Enter email addess: ");
					d.setEmail(s.nextLine());
					System.out.print("Enter zipCode: ");
					d.setZipCode(s.nextLine());
					System.out.println(name +" updated");
					System.out.println(input);
					break;
				case "D":
					listOfAddressRecords.remove(index);
					System.out.println("Item removed");
					break;
				}
		}while (!(input.equals("E")||input.equals("D")));
	}
	else{
		System.out.println("Record with name "+name+" not found. Do you want to create it Y/N");
		if(s.nextLine().equals("Y")){
			System.out.println("Enter address: ");
			address = s.nextLine();
			System.out.print("Enter phone number: ");
			phoneNumber = s.nextLong();
			s.nextLine();
			System.out.print("Enter email addess: ");
			email = s.nextLine();
			System.out.print("Enter zipCode: ");
			zipCode= s.nextLine();
			listOfAddressRecords.add(new AddressBookEntry(name,address,phoneNumber,email,zipCode));
			
		}
		
	}

}
	

public void openListDialogue(){
	String input= null;
	Scanner s = new Scanner(System.in);
	do{
	System.out.print("Do you want to list Address Book entries by (N)ame or by (Z)ip Code: ");
	input = s.nextLine();
	if(input.equals("N")){
		Collections.sort(listOfAddressRecords);
		System.out.println(listOfAddressRecords);
		}
	if(input.equals("Z")){
		Collections.sort(listOfAddressRecords,new AddressBookEntry("name"));
		System.out.println(listOfAddressRecords);
		}
	}while(input.equals("N")||input.equals("Z"));
}

public void setFileName(String fileName){
	this.fileName = fileName;
}

public List<AddressBookEntry> readFromFile(){
	List<String> addressEntryConstituents;
	String lineFromFile;
	BufferedReader myBuffer;
	
	try{
	myBuffer = new BufferedReader(new FileReader(fileName));
	
	listOfAddressRecords = new ArrayList<AddressBookEntry>();
	
	
	while ((lineFromFile = myBuffer.readLine()) != null) {
				
				addressEntryConstituents = Arrays.asList(lineFromFile.split("\\s*,\\s*"));
				listOfAddressRecords.add(new AddressBookEntry(addressEntryConstituents.get(0),addressEntryConstituents.get(1),Long.parseLong(addressEntryConstituents.get(2)),addressEntryConstituents.get(3),addressEntryConstituents.get(4)));
			}
	
	}
	catch (IOException e) {
			e.printStackTrace();
		} 
	return listOfAddressRecords;
}

public void writeToFile()  {
	try{
    PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
    for (AddressBookEntry entry : listOfAddressRecords)
        pw.println(entry.toFileRecord());
    pw.close();
	}
	catch(IOException e){
		System.out.println("Failing on writing to " + fileName);
		}
}
	

}// end of class


