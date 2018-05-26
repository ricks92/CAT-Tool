package com.hsc.cat.controller;

import java.awt.PageAttributes.MediaType;
import java.util.List;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hsc.cat.TO.EmployeeTO;
import com.hsc.cat.TO.GetManagerDetailsResponse;
import com.hsc.cat.VO.EmployeeDetailsVO;
import com.hsc.cat.VO.VerifyManagerVO;
import com.hsc.cat.entity.EmployeeDetails;
import com.hsc.cat.service.EmployeeDetailService;
import com.hsc.cat.utilities.JSONOutputEnum;
import com.hsc.cat.utilities.JSONOutputModel;
import com.hsc.cat.utilities.RESTURLConstants;
import com.hsc.cat.utilities.StatusCode;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.hsc.cat.TO.ManagerDetails;
import com.hsc.cat.TO.ResponseTO;
import com.hsc.cat.TO.ViewTeamTO;

@RestController
public class EmployeeDetailsController {

	private static final Logger LOGGER = (Logger) LogManager.getLogger(EmployeeDetailsController.class);
	
	@Autowired
	private EmployeeDetailService employeeDetailService;
	
	//Persist an employee's details
	@ResponseBody
	@RequestMapping(value=RESTURLConstants.REGISTER_USER,method=RequestMethod.POST,produces = "application/json",consumes="application/json")
	@CrossOrigin
	public JSONOutputModel save(@RequestBody EmployeeDetailsVO e){
		JSONOutputModel output = new JSONOutputModel();
		EmployeeTO employeeTO=employeeDetailService.save(e);
		if(employeeTO!=null){
			
			if(employeeTO.getIssue()!=null) {
				output.setData(new String("Please add valid information"));
				output.setMessage(employeeTO.getIssue());
				if(employeeTO.getIssue().equalsIgnoreCase("Username already exists")) {
					output.setStatus(StatusCode.USERNAME_ALREADY_EXISTS);
				}
				else if(employeeTO.getIssue().equalsIgnoreCase("Email already exists")) {
					output.setStatus(StatusCode.EMAIL_ALREADY_EXISTS);
				}
				System.out.println(employeeTO.getIssue());
			}
			else {
			output.setData(employeeTO);
			output.setStatus(HttpStatus.CREATED.value());
			output.setMessage("Employees saved successfully");
			System.out.println("Employees saved successfully");
			
			}
			
		}
		else{
			output.setData(employeeTO);
			output.setMessage("Employees could not be saved");
			output.setStatus(JSONOutputEnum.FAILURE.getValue());
			System.out.println("Employees could not be saved");
		}
		return output;
	}
	
	
	@ResponseBody
	@RequestMapping(value=RESTURLConstants.FETCH_ALL_EMPLOYEES,method=RequestMethod.GET,produces = "application/json",consumes="application/json")
	@CrossOrigin
	public JSONOutputModel getAllEmployees() {
		JSONOutputModel output = new JSONOutputModel();
		List<EmployeeTO> employeeTOList=employeeDetailService.getAllEmployees();
		
		if(!employeeTOList.isEmpty()  && employeeTOList.size()>0) {
			output.setData(employeeTOList);
			output.setStatus(JSONOutputEnum.SUCCESS.getValue());
			output.setMessage("Employees fetched successfully");
		}
		
		else {
			output.setData(employeeTOList);
			output.setStatus(JSONOutputEnum.FAILURE.getValue());
			output.setMessage("No employee to fetch");
		}
		
		return output;
	}
	
	
	/*@ResponseBody
	@RequestMapping(value="/deleteManagers",method=RequestMethod.GET,produces = "application/json",consumes="application/json")
	@CrossOrigin
	public JSONOutputModel deleteManagers() {
		JSONOutputModel output = new JSONOutputModel();
		ResponseTO response=employeeDetailService.deleteMangers();
		output.setMessage(response.getResponseMessage());
		output.setStatus(Integer.valueOf(response.getResponseCode()));
		output.setData(new String(response.getResponseMessage()));
		return output;
	}*/
	
	@RequestMapping(value=RESTURLConstants.GET_MANAGER_DETAILS,method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getManagerDetails() {
		
		GetManagerDetailsResponse getManagerDetailsResponse=new GetManagerDetailsResponse();
		List<ManagerDetails> managerDetailsList=employeeDetailService.getAllManager();
		
		if(null!=managerDetailsList && managerDetailsList.size()>0) {
			getManagerDetailsResponse.setManagerList(managerDetailsList);
			getManagerDetailsResponse.setResponseCode("1");
			getManagerDetailsResponse.setResponseMessage("SUCCESS");
		}
		else {
			
			getManagerDetailsResponse.setResponseCode("0");
			getManagerDetailsResponse.setResponseMessage("FAILURE");
		}
		
		return new ResponseEntity(getManagerDetailsResponse, HttpStatus.ACCEPTED);
	
	}
	
	
	
	/*@RequestMapping(value="/verifyManager",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity verifyManager(@RequestBody VerifyManagerVO verifyManagerVO) {
		boolean updatedResult=Boolean.FALSE;
		ResponseTO responseTO=new ResponseTO();
		updatedResult=employeeDetailService.updateApprovalStatus(verifyManagerVO.getEmpId(),verifyManagerVO.getApprovalStatus());
		if(updatedResult) {
			responseTO.setResponseCode("1");
			responseTO.setResponseMessage("SUCCESS");
		}else {
			responseTO.setResponseCode("0");
			responseTO.setResponseMessage("FAILURE");
		}
		
		return new ResponseEntity(responseTO, HttpStatus.ACCEPTED);
	
	}*/
	
	@RequestMapping(value=RESTURLConstants.VERIFY_MANAGER,method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity verifyManager(@PathVariable("empId")String empId ) {
		boolean updatedResult=Boolean.FALSE;
		ResponseTO responseTO=new ResponseTO();
		updatedResult=employeeDetailService.updateApprovalStatus(empId);
		if(updatedResult) {
			responseTO.setResponseCode("1");
			responseTO.setResponseMessage("SUCCESS");
		}else {
			responseTO.setResponseCode("0");
			responseTO.setResponseMessage("FAILURE");
		}
		
		return new ResponseEntity(responseTO, HttpStatus.ACCEPTED);
	
	}
	
	@RequestMapping(value=RESTURLConstants.VIEW_TEAM,method=RequestMethod.GET)
	@ResponseBody
	public ViewTeamTO viewTeam(@PathVariable("id") String managerId) {
		
		ViewTeamTO viewTeamTO=null;
		viewTeamTO=employeeDetailService.getEmployeeUnderManager(managerId);
		
		return viewTeamTO;
		
	}
	
}
