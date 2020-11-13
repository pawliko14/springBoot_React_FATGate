
package com.fat.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fat.access.AccessRepository;
import com.fat.access.accessRepositoryimpl;
import com.fat.repository.access;

import sqlEnquiry.SqlEnquiry;
import sqlObjects.GeneralTable;
import sqlObjects.UserHistory;
import sqlObjects.WorkersAndID;

import java.util.List;

 @CrossOrigin(origins = "http://192.168.90.66:3000")  // <- for production purpose, on tests not necessary
@RestController
public class AccessControler {
	@Autowired
	AccessRepository accessRepository;
	@Autowired
	accessRepositoryimpl impl;
	
	private SqlEnquiry obj = new SqlEnquiry();
	
	public AccessControler (AccessRepository repo,accessRepositoryimpl i)
	{
		accessRepository = repo;
		impl = i;
	}
	
	
	@GetMapping("/access")
	public long getALlAccess()
	{
		return accessRepository.count();
	}
	

	@GetMapping("/test")
	public String returnTest()
	{
		return "Alive";
	}
	
	
	@GetMapping("/testowy")
	public List<access>  getAll()
	{
		return impl.findAllWorkersSInce30days();
	}
	
	@GetMapping("tescik")
	public List<access> tescik()
	{
		return impl.tescik();
	}
	
	@GetMapping("lastinout/{count}")
	public List<GeneralTable> lastInOut(@PathVariable("count") int count)
	{

		return obj.mainEnguiry(count);
	}
	
	
	@GetMapping("/activeworkers")
	public List<GeneralTable> StateOFworkersSinceDate()
	{
		return obj.peopleInFATlist();
	}
	
	
	@GetMapping("/currentstate")
	public List<WorkersAndID> CurrentStateOfPeopleInFAT()
	{
		return obj.getCurrentStateOfPeopleInFat();
	}
	
	@GetMapping("workerinfo/{workerid}")
	public UserHistory WorkerHistory(@PathVariable("workerid") int workerid) {
		return obj.getUserHistory(workerid);
	}
	
}
