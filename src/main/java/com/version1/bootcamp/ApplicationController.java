package com.version1.bootcamp;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ApplicationController {

   
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/retrieve")
    public AddressBookEntry retrieve(@RequestParam(value="name", defaultValue="Michael Ryan") String name) {
		AddressBookEntry returned = new DAOMysqlImplementation().findAddressBookEntry(name);
		return returned;
    }
	
	@RequestMapping("/list")
    public List<AddressBookEntry> retrieve() {
		List<AddressBookEntry> returned = new DAOMysqlImplementation().getListOfAddressRecords("name")
		return returned;
    }
	
	
	
}