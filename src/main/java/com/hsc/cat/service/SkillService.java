package com.hsc.cat.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsc.cat.TO.SearchSkillTOList;
import com.hsc.cat.TO.SkillTO;
import com.hsc.cat.VO.AddSkillVO;
import com.hsc.cat.entity.Skill;
import com.hsc.cat.repository.SkillRepository;




@Service
public class SkillService {
	
	private static final Logger LOGGER = (Logger) LogManager.getLogger(SkillService.class);

	@Autowired
	private SkillRepository skillRepository;
	
	public SkillTO addSkill(AddSkillVO svo) {
		if(svo.getSkillName()==null ||svo.getDescription()==null) {
			SkillTO skillTO = new SkillTO();
			skillTO.setIssue("Null not allowed");
			LOGGER.error("Null not allowed");
			return skillTO;
		}
		
		Skill recordExists=skillRepository.findBySkillName(svo.getSkillName());
		
		if(recordExists!=null) {
			SkillTO skillTO = new SkillTO();
			skillTO.setIssue("Record already exists");
			LOGGER.error("Skill "+svo.getSkillName()+" already exists in the database");
			return skillTO;
		}
		Skill skill = new Skill();
		skill.setSkillName(svo.getSkillName());
		skill.setDescription(svo.getDescription());
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Date d1 = new Date();
		Date d2 = new Date();
		
		skill.setCreationDate(d1);
		skill.setUpdationDate(d2);
		
		Skill saved=skillRepository.save(skill);
		SkillTO skillTO=null;
		
		if(saved!=null) {
			skillTO=modelConversion(skill);
		}
			
		return skillTO;
	}
	
	
	//Fetch all skills in the database
	public List<SkillTO> fetchAllSkills() {
		List<Skill> skills=skillRepository.findAll();
		List<SkillTO> skillTOList=new ArrayList<>();
		for(Skill s:skills) {
			SkillTO skillTO=modelConversion(s);
			skillTOList.add(skillTO);
		}
			
		return skillTOList;
	}
	
	
	
	public SkillTO modelConversion(Skill skill) {
		SkillTO skillTO = new SkillTO();
		skillTO.setSkillId(skill.getSkillId());
		skillTO.setSkillName(skill.getSkillName());
		skillTO.setDescription(skill.getDescription());
		skillTO.setSkillId(skill.getSkillId());
		skillTO.setCreationDate(skill.getCreationDate());
		skillTO.setUpdationDate(skill.getUpdationDate());
		
		return skillTO;
	}
	
	
	
	public SearchSkillTOList skillExists(String skillName) {
		SearchSkillTOList searchSkillTO = new SearchSkillTOList();
		
//		Skill skill=skillRepository.findBySkillName(skillName);
//		
//		if( skill!=null)
//		{
//			searchSkillTO.setData(skillRepository.findBySkillName(skillName));
//			searchSkillTO.setExists(true);
//		}
//		else
//			searchSkillTO.setExists(false);
		
		List<Skill> skills=skillRepository.findSkillsByName(skillName);
	
		if(skills!=null && !skills.isEmpty()) {
			searchSkillTO.setSkills(skills);
			searchSkillTO.setExists(true);
			LOGGER.debug("Skill found in the database");
		}
		else
		{
			searchSkillTO.setExists(false);
			LOGGER.debug("Skill not found in the database");
		}
		
		return searchSkillTO;
	}
}
