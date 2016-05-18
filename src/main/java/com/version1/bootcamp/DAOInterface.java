package com.version1.bootcamp;
import java.util.List;

public interface DAOInterface {
	
public void deleteAddressBookEntry(String entryToDelete);
public void updateAddressBookEntry(AddressBookEntry entryToUpdate);
public void insertAddressBookEntry(AddressBookEntry entryToInsert); 
public List<AddressBookEntry> getListOfAddressRecords(String orderBy);
public AddressBookEntry findAddressBookEntry(String nameToLocate);
}
