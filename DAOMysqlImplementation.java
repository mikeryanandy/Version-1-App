package version1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOMysqlImplementation implements DAOInterface{
	private Connection connection;
	
	public DAOMysqlImplementation(){
		connectToDatabase("jdbc:mysql://localhost/addressbook");
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
	
	public AddressBookEntry findAddressBookEntry(String nameToLocate){
		AddressBookEntry locatedEntry = null;
		PreparedStatement pstmt;
		ResultSet rs= null;
		try{
			pstmt = connection.prepareStatement("select * from addressbookentries where name = ?");
			pstmt.setString(1,nameToLocate);
			rs = pstmt.executeQuery();
			if (rs.isBeforeFirst() ) {
				rs.next();
				locatedEntry = new AddressBookEntry(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
			}
			}catch(SQLException e){
				System.out.println("Problem finding "+nameToLocate);
				System.out.println(e);
			}
		
		return locatedEntry;
	}

	
	
	public void updateAddressBookEntry(AddressBookEntry entryToUpdate){
		try{
			PreparedStatement updateStatement = connection.prepareStatement("update addressbookentries set address = ?,phoneNumber = ?,email=?,zipCode = ? where name = ?");
			updateStatement.setString(1, entryToUpdate.getAddress());
			updateStatement.setString(2, entryToUpdate.getPhoneNumber());
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
	
	public void deleteAddressBookEntry(String nameToDelete){
		
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

	public void insertAddressBookEntry(AddressBookEntry entryToInsert){
		
		
		try{
			PreparedStatement insertStatement = connection.prepareStatement("insert into addressbookentries values(?,?,?,?,?)");
			insertStatement.setString(1,entryToInsert.getName());
			insertStatement.setString(2,entryToInsert.getAddress());
			insertStatement.setString(3,entryToInsert.getPhoneNumber());
			System.out.println(entryToInsert.getEmail());
			insertStatement.setString(4,entryToInsert.getEmail());
			insertStatement.setString(5,entryToInsert.getPhoneNumber());
			insertStatement.executeUpdate();
			System.out.println(entryToInsert.getName() + " created!");	
			}
			catch(SQLException e){
				System.out.println("problem inserting record "+ entryToInsert.getName());
				System.out.println(e);
			}	
		}
	
	public List<AddressBookEntry> getListOfAddressRecords(String columnToOrderBy){
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
	
	
	
	
	
	

}
