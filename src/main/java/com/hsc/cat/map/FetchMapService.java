package com.hsc.cat.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsc.cat.entity.EmployeeSkillEntity;
import com.hsc.cat.entity.Skill;
import com.hsc.cat.enums.LevelsEnum;
import com.hsc.cat.repository.EmployeeSkillRepository;
import com.hsc.cat.repository.SkillRepository;

import ch.qos.logback.classic.Logger;

@Service
public class FetchMapService {
	

	private final Logger LOGGER = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EmployeeSkillRepository employeeSkillRepository;
	@Autowired
	private SkillRepository skillRepository;
	
	
	public FetchMapTO fetchMap(NewFetchMapVO newFetchMapVO) {
		FetchMapTO fetchMapTO = new FetchMapTO();
		String empId=newFetchMapVO.getEmpId();
		int quarter=newFetchMapVO.getQuarter();
		
		
		SkillMapResponse skillMapResponse = new SkillMapResponse();
		
		Map<String,String> mapTemporary = new HashMap<>(); 
		
		Map<String,String> map = new HashMap<>();//Key=value1 , SkillName:
		
		 int start=0;
		   int end=0;
		   
		   switch(quarter) {
		   case 1:{
			   start=1;
			   end=start+12;
			   break;
		   }
		   case 2:{
			   start=14;
			   end=start+12;
			   break;
		   }
		   case 3:{
			   start=27;
			   end=start+12;
			   break;
		   }
		   case 4:{
			   start=40;
			   end=start+12;
			   break;
		   }
		   default:System.out.println("Illegal");
		   }
		
		List<EmployeeSkillEntity> employeeSkillEntityList=employeeSkillRepository.getAllRatedSkillsCustom(empId, start, end);
	
		System.out.println("\n\n\n\n%%%%%%%%"+employeeSkillEntityList);
		
		
		for(EmployeeSkillEntity e:employeeSkillEntityList) {
			Skill skill=skillRepository.findOne(e.getSkillId());
			//map.put("value"+countOfRatedSkills, skill.getSkillName());
			mapTemporary.put(skill.getSkillName(), "value"+skill.getSkillId());
			
		}
		
		for(Entry<String,String> entry:mapTemporary.entrySet()) {
			map.put(entry.getValue(), entry.getKey());
		}
		
		System.out.println("\n\n\n\n\n"+map);
		
		
		skillMapResponse.setMap(map);
		
		fetchMapTO.setSkillMapResponse(skillMapResponse);
		
		
		
		List<Map<String,String>> listOfselfReviews=new ArrayList<>();
		
		
		List<Map<String,String>> listOfpeerReviews=new ArrayList<>();
		
		List<EmployeeSkillEntity> getSelfReviewRowList=employeeSkillRepository.getReviewSelf(empId, start, end);
		
		List<Integer> weekNumbersSelf=employeeSkillRepository.getDistinctWeekMumber(empId, start, end, "Self");
		System.out.println(getSelfReviewRowList);
		System.out.println("Distinct count:"+weekNumbersSelf);
		
		for(int i=0;i<weekNumbersSelf.size();i++) {
			Map<String,String> selfreviewmap=new LinkedHashMap<>();
			selfreviewmap.put("Category", "week-"+weekNumbersSelf.get(i));
		    
			
			for(Entry<String,String> entry:map.entrySet()) {
		    	//int skillId=Integer.valueOf(entry.getKey().substring(5,5));
		    	
		    	//System.out.println("\n\n\n\n&&&&&&"+skillRepository.findSkillIdBySkillNameCustom(entry.getValue()));
		    	int skillId=skillRepository.findSkillIdBySkillName(entry.getValue());
		    	String rating=employeeSkillRepository.getSpecificRating(empId, weekNumbersSelf.get(i), skillId, "Self");
		    	selfreviewmap.put("value"+skillId, ""+LevelsEnum.getLevelFromName(rating));
		    }
		    listOfselfReviews.add(selfreviewmap);
		}
		
		
		fetchMapTO.setListOfselfReviews(listOfselfReviews);
		
		//Peer review
		List<EmployeeSkillEntity> getPeerReviewRowList=employeeSkillRepository.getReviewPeer(empId, start, end);
		
		List<Integer> weekNumbersPeer=employeeSkillRepository.getDistinctWeekMumber(empId, start, end, "Peer");
		
		
		for(int i=0;i<weekNumbersPeer.size();i++) {
			Map<String,String> peerreviewmap=new LinkedHashMap<>();
			peerreviewmap.put("Category", "week-"+weekNumbersPeer.get(i));
		    
			
			for(Entry<String,String> entry:map.entrySet()) {
		    	//int skillId=Integer.valueOf(entry.getKey().substring(5,5));
		    	
		    	//System.out.println("\n\n\n\n&&&&&&"+skillRepository.findSkillIdBySkillNameCustom(entry.getValue()));
		    	int skillId=skillRepository.findSkillIdBySkillName(entry.getValue());
		    	String rating=employeeSkillRepository.getSpecificRating(empId, weekNumbersSelf.get(i), skillId, "Peer");
		    	peerreviewmap.put("value"+skillId, ""+LevelsEnum.getLevelFromName(rating));
		    }
			listOfpeerReviews.add(peerreviewmap);
		}
		
		
		fetchMapTO.setListOfpeerReviews(listOfpeerReviews);
		
		System.out.println(getPeerReviewRowList);
		return fetchMapTO;
	}
}
