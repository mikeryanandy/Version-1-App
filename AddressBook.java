package version1;

import java.util.*;
import java.util.ArrayList;
import java.sql.*;

public class AddressBook{
private static AddressBook application;
private List<AddressBookEntry> listOfAddressRecords;
private Connection connection;
private Scanner myScanner;

public static void main(String[] args){
	System.out.println("Welcome to the AddressBook application");
	application = new AddressBook();
	application.runApp();
}

public void runApp(){	
	application.connectToDatabase("jdbc:mysql://localhost/addressbook");
	myScanner = new Scanner(System.in);
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
	String name = myScanner.nextLine();
	AddressBookEntry d;
	PreparedStatement pstmt;
	try{
		pstmt = connection.prepareStatement("select * from addressbookentries where name = ?");
		pstmt.setString(1,name);
		ResultSet rs = pstmt.executeQuery();	
		if (!rs.isBeforeFirst() ) {    
			System.out.println("Record with name "+name+" not found. Do you want to create it Y/N");
			if(myScanner.nextLine().equals("Y")){
				createNewEntity(name);
			} 
		}
		else
		{
			System.out.println("Record found - Full details of "+name+":");
			rs.next();
			d = new AddressBookEntry(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
			System.out.println(d);
			String input = null;
			do{
				System.out.println("Do you want to (E)dit or (D)elete this record");
				input = myScanner.nextLine().toUpperCase();
				switch(input){
					case "E":
						System.out.println("Enter address: ");
						d.setAddress(myScanner.nextLine());
						System.out.print("Enter phone number: ");
						d.setPhoneNumber(myScanner.nextLine());
						myScanner.nextLine();
						System.out.print("Enter email addess: ");
						d.setEmail(myScanner.nextLine());
						System.out.print("Enter zipCode: ");
						d.setZipCode(myScanner.nextLine());
						System.out.println(name +" updated");
						System.out.println(input);
						updateAddressBookEntry(d);
						break;
					case "D":
						deleteAddressBookEntry(name);
						break;
					}
			}while (!(input.equals("E")||input.equals("D")));
		}
		}catch(SQLException e){
			System.out.println("Problem finding "+name);
			System.out.println(e);
		}
}
	
public void openListDialogue(){
	String input= null;
	do{
	System.out.print("Do you want to list Address Book entries by (N)ame or by (Z)ip Code: ");
	input = myScanner.nextLine().toUpperCase();
	switch (input){
		case "N":
		listOfAddressRecords = performSearch("name");
		break;
		case "Z":
		listOfAddressRecords = performSearch("zipCode");
		break;
		}
	}while(input.toLowerCase().equals("n")||input.toLowerCase().equals("z"));
}

public List<AddressBookEntry> performSearch(String columnToOrderBy){
PreparedStatement pstmt = null;
ArrayList<AddressBookEntry> recordList = new ArrayList<AddressBookEntry>();

try{
	String statement = "select * from addressbookentries order by %s";
	statement = String.format(statement,columnToOrderBy);
	pstmt = connection.prepareStatement(statement);
	ResultSet rs = pstmt.executeQuery();	
	String name = null;
	String address = null;
	String phoneNumber = null;
	String email = null;
	String zipCode = null;
	
	while(rs.next()){
		name = rs.getString(1);
		address = rs.getString(2);
		phoneNumber = rs.getString(3);
		email = rs.getString(4);
		zipCode = rs.getString(5);
		recordList.add(new AddressBookEntry(name,address,phoneNumber,email,zipCode));
	}
	System.out.println(recordList);
	}
	catch(SQLException sqlException){
		System.out.println("Error searching database with columnToSearchOn: "+columnToOrderBy );
		sqlException.printStackTrace();
		System.out.println(pstmt);
	}
return recordList;
}

private void deleteAddressBookEntry(String nameToDelete){
	
	try{
	PreparedStatement deleteStatement = connection.prepareStatement("delete from addressbookentries where name = ?");
	deleteStatement.setString(1,nameToDelete);
	deleteStatement.executeUpdate();
	System.out.println(nameToDelete +" deleted!");
	}
	catch(SQLException e){
		System.out.println("problem deleting record "+ nameToDelete);
		System.out.println(e);
	}
}

private void createNewEntity(String nameToInsert){
	AddressBookEntry d = new AddressBookEntry(nameToInsert);
	System.out.println("Enter address: ");
	d.setAddress(myScanner.nextLine());
	System.out.print("Enter phone number: ");
	d.setPhoneNumber(myScanner.nextLine());
	myScanner.nextLine();
	System.out.print("Enter email addess: ");
	d.setEmail(myScanner.nextLine());
	System.out.print("Enter zipCode: ");
	d.setZipCode(myScanner.nextLine());
	System.out.println(nameToInsert +" updated");
	
	try{
		PreparedStatement insertStatement = connection.prepareStatement("insert into addressbookentries values(?,?,?,?,?)");
		insertStatement.setString(1,nameToInsert);
		insertStatement.setString(2,d.getAddress());
		insertStatement.setString(3,d.getPhoneNumber());
		insertStatement.setString(4,d.getEmail());
		insertStatement.setString(5,d.getPhoneNumber());
		insertStatement.executeUpdate();
		System.out.println(nameToInsert + " created!");	
		}
		catch(SQLException e){
			System.out.println("problem inserting record "+ nameToInsert);
			System.out.println(e);
		}	
	}

private void updateAddressBookEntry(AddressBookEntry entryToUpdate){
	try{
		PreparedStatement updateStatement = connection.prepareStatement("update addressbookentries set address = ?,phoneNumber = ?,email=?,zipCode = ? where name = ?");
		updateStatement.setString(1,entryToUpdate.getAddress());
		updateStatement.setString(2,entryToUpdate.getPhoneNumber());
		updateStatement.setString(3, entryToUpdate.getEmail());
		updateStatement.setString(4, entryToUpdate.getZipCode());
		updateStatement.setString(5, entryToUpdate.getName());
		updateStatement.executeUpdate();
		System.out.println(entryToUpdate.getName() + " updated!");
		}
		catch(SQLException e){
			System.out.println("problem updating record "+ entryToUpdate.getName());
			System.out.println(e);
		}	
 }

public void connectToDatabase(String connectionURL){
    try{
		connection=DriverManager.getConnection(connectionURL,"root","Wisdom20");
		System.out.println("Should be connected to "+connectionURL +" now.");	
		}catch(SQLException sqlException){
			System.out.println("Error connecting to database URL: "+connectionURL);
			sqlException.printStackTrace();
			}
	}
}// end of class