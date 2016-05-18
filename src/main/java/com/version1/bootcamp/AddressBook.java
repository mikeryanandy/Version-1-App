package com.version1.bootcamp;

import java.util.*;

public class AddressBook{
private static AddressBook application;
private Scanner myScanner;
private DAOInterface daoObject;

public static void main(String[] args){
	System.out.println("Welcome to the AddressBook application");
	application = new AddressBook();
	application.runApp();
}

public void runApp(){	
	daoObject = new DAOMysqlImplementation();
	myScanner = new Scanner(System.in);
	String input = "";
	do{
		System.out.print("Enter S to Search or L to list all entries(E to exit:)");
		input = myScanner.nextLine().toUpperCase();
		switch(input){
			case "S":
				openSearchDialogue();
				break;
			case "L":
				openListDialogue();
				break;
			default:
				break;
		}
	}while(!(input.equals("E")));
}

public void openSearchDialogue(){
	System.out.print("Search for name: ");
	String name = myScanner.nextLine();
	AddressBookEntry d = daoObject.findAddressBookEntry(name);
	
	if ((d == null)){	    
			System.out.println("Record with name "+name+" not found. Do you want to create it Y/N");
			if(myScanner.nextLine().toUpperCase().equals("Y")){
				d = new AddressBookEntry(name);
				System.out.println("Enter address: ");
				d.setAddress(myScanner.nextLine());
				System.out.println("Enter phone number: ");
				d.setPhoneNumber(myScanner.nextLine());
				System.out.print("Enter email addess: ");
				d.setEmail(myScanner.nextLine());
				System.out.print("Enter zipCode: ");
				d.setZipCode(myScanner.nextLine());
				daoObject.insertAddressBookEntry(d);
			} 
		}
		else
		{
			System.out.println("Record found - Full details of "+name+":");
			System.out.println(d);
			String input = null;
			//do{
				System.out.println("Do you want to (E)dit or (D)elete this record");
				input = myScanner.nextLine().toUpperCase();
				switch(input){
					case "E":
						System.out.println("Enter address: ");
						d.setAddress(myScanner.nextLine());
						System.out.println("Enter phone number: ");
						d.setPhoneNumber(myScanner.nextLine());
						myScanner.nextLine();
						System.out.print("Enter email addess: ");
						d.setEmail(myScanner.nextLine());
						System.out.print("Enter zipCode: ");
						d.setZipCode(myScanner.nextLine());
						System.out.println(name +" updated");
						System.out.println(input);
						daoObject.updateAddressBookEntry(d);
						break;
					case "D":
						daoObject.deleteAddressBookEntry(name);
						break;
					}
			//}while (!(input.equals("E")||input.equals("D")));
		}
		
}
	
public void openListDialogue(){
	List<AddressBookEntry> listOfAddressRecords;
	String input= null;
	//do{
	System.out.print("Do you want to list Address Book entries by (N)ame or by (Z)ip Code: ");
	input = myScanner.nextLine().toUpperCase();
	switch (input){
		case "N":
		listOfAddressRecords = daoObject.getListOfAddressRecords("name");
		break;
		case "Z":
		listOfAddressRecords = daoObject.getListOfAddressRecords("zipCode");
		break;
		}
	//}while(input.toLowerCase().equals("n")||input.toLowerCase().equals("z"));
}

}// end of class