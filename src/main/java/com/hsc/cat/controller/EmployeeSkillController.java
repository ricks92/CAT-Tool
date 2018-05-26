package com.hsc.cat.controller;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hsc.cat.TO.MapTO;
import com.hsc.cat.TO.SkillTO;
import com.hsc.cat.TO.UpdateSkillResponse;
import com.hsc.cat.TO.UpdateSkillTO;
import com.hsc.cat.TO.ViewSkillListTO;
import com.hsc.cat.VO.MapVO;
import com.hsc.cat.VO.UpdateSkillsListVO;
import com.hsc.cat.service.EmployeeSkillService;
import com.hsc.cat.utilities.JSONOutputEnum;
import com.hsc.cat.utilities.JSONOutputModel;
import com.hsc.cat.utilities.RESTURLConstants;


@RestController
public class EmployeeSkillController {

	private static final Logger LOGGER = (Logger) LogManager.getLogger(EmployeeSkillController.class);
	
	@Autowired
	private EmployeeSkillService updateSkillService;
	
	@ResponseBody
	@RequestMapping(value=RESTURLConstants.UPDATE_SKILL,method=RequestMethod.POST,produces = "application/json",consumes="application/json")
	@CrossOrigin
	public JSONOutputModel updateSkill(@RequestBody UpdateSkillsListVO updateSkillsListVO) {
		JSONOutputModel output = new JSONOutputModel();
		
		
		if(updateSkillsListVO.getListOfEmployeeSkills()==null) {
			output.setStatus(JSONOutputEnum.FAILURE.getValue());
			output.setMessage("Invalid request");
			return output;
		}
		else if(updateSkillsListVO.getListOfEmployeeSkills().isEmpty()) {
			output.setStatus(JSONOutputEnum.FAILURE.getValue());
			output.setMessage("Invalid request");
			return output;
		}
		UpdateSkillResponse response=updateSkillService.updateSkill(updateSkillsListVO);
		 List<UpdateSkillTO> updateSkillTOList=response.getUpdateSkillTOList();
		
		if(response!=null && response.getUpdateSkillTOList()!=null && !response.getUpdateSkillTOList().isEmpty() && response.getUpdateSkillTOList().size()!=0 ) {
			output.setData(response.getUpdateSkillTOList());
			output.setStatus(JSONOutputEnum.SUCCESS.getValue());
			output.setMessage("Skills updated successfully");
		}
		
		else {
			
		
//				output.setData(response.getUpdateSkillTOList());
				output.setStatus(JSONOutputEnum.FAILURE.getValue());
				output.setMessage("Skills could not be updated");
			
		}
		
		
		return output;
		
	}
	
	
	@ResponseBody
	@RequestMapping(value=RESTURLConstants.VIEW_SKILL,method=RequestMethod.GET,produces = "application/json",consumes="application/json")
	@CrossOrigin
	public JSONOutputModel viewSkills(@PathVariable ("empId") String empid,@PathVariable("viewHistory")boolean flag) {
		JSONOutputModel output = new JSONOutputModel();
		
		ViewSkillListTO viewSkillListTO= new ViewSkillListTO();
		
		
		if(flag)
		{
			 viewSkillListTO=updateSkillService.getViewHistory(empid);	
		}
		else {
		
		 viewSkillListTO = updateSkillService.viewSkills(empid);
		}
		
		
		System.out.println(viewSkillListTO);
		//System.out.println(viewSkillListTO.getListOfEmployeeSkills().size());
		if(viewSkillListTO!=null && viewSkillListTO.getListOfEmployeeSkills()!=null && !viewSkillListTO.getListOfEmployeeSkills().isEmpty() &&viewSkillListTO.getListOfSkillId()!=null &&  !viewSkillListTO.getListOfSkillId().isEmpty()) {
			output.setData(viewSkillListTO);
			output.setStatus(JSONOutputEnum.SUCCESS.getValue());
			output.setMessage("Employee skills fetched successfully");
		}
		else {
			output.setData(viewSkillListTO);
			output.setStatus(JSONOutputEnum.FAILURE.getValue());
			output.setMessage("Employee skills entry not there");
		}
		
		
		
		return output;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/fetchMap",method=RequestMethod.POST,produces = "application/json",consumes="application/json")
	@CrossOrigin
	public JSONOutputModel fetchMapDetails(@RequestBody MapVO mapVO) {
		JSONOutputModel output = new JSONOutputModel();
		MapTO map=updateSkillService.fetchMapDetails(mapVO);
		if(map!=null && !map.getMap().isEmpty()) {
			output.setData(map);
			output.setMessage("Map details fetched successfuly");
			output.setStatus(JSONOutputEnum.SUCCESS.getValue());
		}
		else {
			output.setData(map);
			output.setMessage("No data found for this quarter");
			output.setStatus(JSONOutputEnum.FAILURE.getValue());
		}
		
		
		
		return output;
		
	}
	
	
	@ResponseBody
	@RequestMapping(value=RESTURLConstants.GET_ALL_SELF_RATED_SKILLS,method=RequestMethod.GET,produces = "application/json",consumes="application/json")
	@CrossOrigin
	public JSONOutputModel getAllSelfRatedSkills(@PathVariable("empId") String empId) {
		JSONOutputModel output = new JSONOutputModel();
		List<SkillTO> list=updateSkillService.getAllSelfRatedSkills(empId);
		
		System.out.println(list.size());
		if(list!=null && !list.isEmpty()) {
			output.setData(list);
			output.setMessage("Rated skill details fetched successfully");
			output.setStatus(JSONOutputEnum.SUCCESS.getValue());
		}
		
		else {
			output.setData(list);
			output.setMessage("No rated skill details fetched");
			output.setStatus(JSONOutputEnum.SUCCESS.getValue());
		}
		
		return output;
	}
}
