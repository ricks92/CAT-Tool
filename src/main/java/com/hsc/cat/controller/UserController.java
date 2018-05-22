package com.hsc.cat.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hsc.cat.TO.ResponseTO;
import com.hsc.cat.TO.UserTO;
import com.hsc.cat.VO.ChangePasswordVO;
import com.hsc.cat.VO.ForgetPasswordVO;
import com.hsc.cat.VO.ValidateSecurityQuestionVO;
import com.hsc.cat.VO.ValidateUserVO;
import com.hsc.cat.entity.UserDetails;
import com.hsc.cat.service.UserDetailService;
import com.hsc.cat.utilities.JSONOutputEnum;
import com.hsc.cat.utilities.JSONOutputModel;

import ch.qos.logback.classic.Logger;

@RestController
public class UserController {
	
	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserDetailService userDetailService;
/*	
	
	//Persist a user's details
		@ResponseBody
		@RequestMapping(value="/users",method=RequestMethod.POST)
	public JSONOutputModel registerUser(@RequestBody UserDetails userDetails){
		JSONOutputModel output= new JSONOutputModel();
		UserTO user=userDetailService.registerUser(userDetails);
		if(user!=null){
			output.setData(user);
			output.setMessage("User registered successfully");
			output.setStatus(JSONOutputEnum.SUCCESS.getValue());
		}else{
			output.setData(user);
			output.setMessage("User could not be registered ");
			output.setStatus(JSONOutputEnum.FAILURE.getValue());
		}
		
		return output;
	}*/
	
	
	@ResponseBody
	@RequestMapping(value="/validateSecurityQuestion",method=RequestMethod.POST,produces = "application/json",consumes="application/json")
	@CrossOrigin
	public JSONOutputModel validateSecurityQuestion(@RequestBody ValidateSecurityQuestionVO validateSecurityQuestionVO) {
		JSONOutputModel output= new JSONOutputModel();
		
		boolean matches=userDetailService.validateSecurityQuestion(validateSecurityQuestionVO);
		if(matches)
		{
			output.setData(new String("match found"));
			output.setStatus(JSONOutputEnum.SUCCESS.getValue());
			output.setMessage("Security question validated");
		}
		
		else {
			output.setData(new String("match not found"));
			output.setStatus(JSONOutputEnum.FAILURE.getValue());
			output.setMessage("Please enter correct answer!");
		}
		return output;
	}
	
	
	
	
	@RequestMapping(value="validateUser",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseTO validateUser(@RequestBody ValidateUserVO validateUserVO) {
		boolean validUser=Boolean.FALSE;
		ResponseTO responseTO;
		responseTO=userDetailService.checkValidUser(validateUserVO.getUserName(),validateUserVO.getPassword(),validateUserVO.getRole());
		if(null==responseTO) {
			ResponseTO response=new ResponseTO();
			response.setResponseCode("0");
			response.setResponseMessage("FAILURE");
			return response;
		}
		return responseTO;
	}
	
	
	
	@RequestMapping(value="changePassword",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseTO changePassword(@RequestBody ChangePasswordVO changePasswordVO) {
		
		ResponseTO responseTO=null;
		responseTO=userDetailService.updatePassword(changePasswordVO);
		
		
		return responseTO;
		
	}
	
	@RequestMapping(value="forgetPassword",method=RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public ResponseTO forgetPassword(@RequestBody ForgetPasswordVO forgetPasswordVO) {
		
		ResponseTO responseTO=null;
		responseTO=userDetailService.forgetPasswordForAll(forgetPasswordVO);
		
		
		return responseTO;
	}
	
}
