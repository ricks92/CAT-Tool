package com.hsc.cat.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsc.cat.TO.MapTO;
import com.hsc.cat.TO.SkillTO;
import com.hsc.cat.TO.SubjectTO;
import com.hsc.cat.TO.UpdateSkillResponse;
import com.hsc.cat.TO.UpdateSkillTO;
import com.hsc.cat.TO.ViewSkillListTO;
import com.hsc.cat.TO.ViewSkillTO;
import com.hsc.cat.VO.MapVO;
import com.hsc.cat.VO.UpdateSkillVO;
import com.hsc.cat.VO.UpdateSkillsListVO;
import com.hsc.cat.entity.EmployeeSkillEntity;
import com.hsc.cat.entity.Skill;
import com.hsc.cat.enums.LevelsEnum;
import com.hsc.cat.repository.EmployeeDetailRepository;
import com.hsc.cat.repository.EmployeeSkillRepository;
import com.hsc.cat.repository.SkillRepository;



@Service
public class EmployeeSkillService {
	
	private static final Logger LOGGER = (Logger) LogManager.getLogger(EmployeeSkillService.class);
	

	@Autowired
	private EmployeeSkillRepository employeeSkillRepository;
	@Autowired
	private EmployeeDetailRepository employeeDetailRepository;
	@Autowired
	private SkillRepository skillRepository;
	@Autowired
	private SkillService skillService;
	
	public UpdateSkillResponse updateSkill(UpdateSkillsListVO updateSkillsListVO) {
		
		UpdateSkillResponse response = new UpdateSkillResponse();
		
		/*List<EmployeeSkillEntity> updateSkillEntityList= new ArrayList<>();
		List<UpdateSkillVO> updateSkillVOList=updateSkillsListVO.getListOfEmployeeSkills();
		for(UpdateSkillVO updateSkillVO:updateSkillVOList) {
		EmployeeSkillEntity updateSkillEntity= new EmployeeSkillEntity();
		
		List<EmployeeSkillEntity> records=employeeSkillRepository.findAll();
		int count=records.size();
		
		System.out.println("\n\n\n"+count);
		EmployeeSkillEntity recordExists=employeeSkillRepository.findByEmpIdAndSkillIdAndWeekNumberAndRatingDoneBy(updateSkillVO.getEmpId(), updateSkillVO.getSkillId(), updateSkillVO.getWeekNumber(), updateSkillVO.getRatingDoneBy());
		
		if(recordExists!=null && !updateSkillVO.getRatingDoneBy().equalsIgnoreCase(RatingDoneByEnums.SELF.getType())) {
			UpdateSkillTO updateSkillTO= new UpdateSkillTO();
			updateSkillTO.setProblem("Record already exists!!");
		}
		updateSkillEntity.setEmpId(updateSkillVO.getEmpId());
		updateSkillEntity.setSkillId(updateSkillVO.getSkillId());
		updateSkillEntity.setRating(LevelsEnum.getLevelNameFromLevel(updateSkillVO.getRating()));
		updateSkillEntity.setWeekNumber(updateSkillVO.getWeekNumber());
		updateSkillEntity.setComment(updateSkillVO.getComment());
		updateSkillEntity.setRatingDoneBy(updateSkillVO.getRatingDoneBy());
		updateSkillEntity.setRatingDoneByEmpId(updateSkillVO.getRatingDoneByEmpId());
 //Check if self data is correct
			if (updateSkillVO.getRatingDoneBy().equalsIgnoreCase(RatingDoneByEnums.SELF.getType())
					&& !updateSkillVO.getEmpId().equals(updateSkillVO.getRatingDoneByEmpId())) {
				// do nothing when self data incorrect
				UpdateSkillTO updateSkillTO= new UpdateSkillTO();
				updateSkillTO.setProblem("Self data incorrect");
				System.out.println("Self data incorrect");
			}
 //Check if manager exists 
  else if(updateSkillVO.getRatingDoneBy().equalsIgnoreCase(RatingDoneByEnums.MANAGER.getType())) {
	  EmployeeDetails manager=employeeDetailRepository.findOne(updateSkillVO.getRatingDoneByEmpId());
	  if(manager==null ) {
		  //do nothing when manager does not exist in the table
//		  System.out.println(manager.getManagerId()!=null);
		  UpdateSkillTO updateSkillTO= new UpdateSkillTO();
		  updateSkillTO.setProblem("manager does not exist in the table");
		  System.out.println("manager does not exist in the table");
	  }
	  //Allow update for only Approved managers
	  else if(recordExists==null && manager!=null && manager.getManagerId().length()<2 && !(manager.getApprovalStatus().equals(ApprovalStatusEnum.APPROVED.getValue()))) {
		   employeeSkillRepository.save(updateSkillEntity);
			updateSkillEntityList.add(updateSkillEntity);
	  }
  }
  
			//Check if peer data i scorrect
  else if(updateSkillVO.getRatingDoneBy().equalsIgnoreCase(RatingDoneByEnums.PEER.getType())) {
	  EmployeeDetails peer=employeeDetailRepository.findOne(updateSkillVO.getRatingDoneByEmpId());
	  if(!employeeDetailRepository.exists(peer.getEmpid())) {
		  //do nothing when peer does not exist in the table
		  UpdateSkillTO updateSkillTO= new UpdateSkillTO();
		  updateSkillTO.setProblem(" peer does not exist in the table");
		  System.out.println(" peer does not exist in the table");
	  }
	  else {
	if(recordExists==null)
	{ employeeSkillRepository.save(updateSkillEntity);
			updateSkillEntityList.add(updateSkillEntity);
	}
	///
	
	  }
  }
else
			{
				if (recordExists == null)

				{
					employeeSkillRepository.save(updateSkillEntity);
					updateSkillEntityList.add(updateSkillEntity);

				}
				else if(recordExists!=null && updateSkillVO.getRatingDoneBy().equalsIgnoreCase(RatingDoneByEnums.SELF.getType())) {
					if(!updateSkillEntity.getRating().equals(updateSkillVO.getRating()))
					{
					employeeSkillRepository.save(updateSkillEntity);
					updateSkillEntityList.add(updateSkillEntity);
					}
					else {
						UpdateSkillTO updateSkillTO= new UpdateSkillTO();
						 updateSkillTO.setProblem("Nothing to update");
						
					}
				}
				
			}
		}
		List<UpdateSkillTO> updateSkillTOList=null;
		if(!updateSkillEntityList.isEmpty() && updateSkillEntityList.size()!=0)
			{
			updateSkillTOList=modelConversion(updateSkillEntityList);
			}
		
		response.setUpdateSkillTOList(updateSkillTOList);*/
		
		List<EmployeeSkillEntity> updateSkillEntityList= new ArrayList<>();
		List<UpdateSkillVO> updateSkillVOList=updateSkillsListVO.getListOfEmployeeSkills();
		for(UpdateSkillVO updateSkillVO:updateSkillVOList) {
			
		//	if(employeeSkillRepository.exists(updateSkillVO.getSkillId())) {
				EmployeeSkillEntity recordExists=employeeSkillRepository.findByEmpIdAndSkillIdAndWeekNumberAndRatingDoneByAndRatingDoneByEmpId(updateSkillVO.getEmpId(), updateSkillVO.getSkillId(), updateSkillVO.getWeekNumber(), updateSkillVO.getRatingDoneBy(),updateSkillVO.getRatingDoneByEmpId());
				
				if(recordExists==null) {
					LOGGER.debug("Creating new row for rating and comments..Rating \"+updateSkillVO.getEmpId()+\" Feedback is being given by \"+updateSkillVO.getRatingDoneByEmpId()");
					EmployeeSkillEntity updateSkillEntity= new EmployeeSkillEntity();
					updateSkillEntity.setEmpId(updateSkillVO.getEmpId());
					updateSkillEntity.setSkillId(updateSkillVO.getSkillId());
					updateSkillEntity.setRating(LevelsEnum.getLevelNameFromLevel(updateSkillVO.getRating()));
					updateSkillEntity.setWeekNumber(updateSkillVO.getWeekNumber());
					updateSkillEntity.setComment(updateSkillVO.getComment());
					updateSkillEntity.setRatingDoneBy(updateSkillVO.getRatingDoneBy());
					updateSkillEntity.setRatingDoneByEmpId(updateSkillVO.getRatingDoneByEmpId());
					updateSkillEntity.setCreationDate(new Date());
					updateSkillEntity.setComment(updateSkillVO.getComment());
					System.out.println("\n\n\n\nDate is:"+new Date());
					employeeSkillRepository.save(updateSkillEntity);
					
					updateSkillEntityList.add(updateSkillEntity);
				}
				
				else if(recordExists!=null) {
					LOGGER.debug("Updating already existing rating and comments..Rating "+updateSkillVO.getEmpId()+" Feedback is being given by "+updateSkillVO.getRatingDoneByEmpId());
					
					Date date = new Date();
				employeeSkillRepository.updateEmployeeSkill(updateSkillVO.getSkillId(), LevelsEnum.getLevelNameFromLevel(updateSkillVO.getRating()),updateSkillVO.getComment(),updateSkillVO.getWeekNumber(),updateSkillVO.getRatingDoneBy(),date,updateSkillVO.getEmpId(),updateSkillVO.getRatingDoneByEmpId());
				
				
				
				EmployeeSkillEntity updateSkillEntity=employeeSkillRepository.findByEmpIdAndSkillIdAndWeekNumberAndRatingDoneByAndRatingDoneByEmpId(updateSkillVO.getEmpId(), updateSkillVO.getSkillId(), updateSkillVO.getWeekNumber(), updateSkillVO.getRatingDoneBy(),updateSkillVO.getRatingDoneByEmpId());
				
				
				System.out.println("\n\n\n"+updateSkillEntity.toString());
				updateSkillEntityList.add(updateSkillEntity);
				}
		//	}//
			/*else {
			EmployeeSkillEntity updateSkillEntity= new EmployeeSkillEntity();
			updateSkillEntity.setEmpId(updateSkillVO.getEmpId());
			updateSkillEntity.setSkillId(updateSkillVO.getSkillId());
			updateSkillEntity.setRating(LevelsEnum.getLevelNameFromLevel(updateSkillVO.getRating()));
			updateSkillEntity.setWeekNumber(updateSkillVO.getWeekNumber());
			updateSkillEntity.setComment(updateSkillVO.getComment());
			updateSkillEntity.setRatingDoneBy(updateSkillVO.getRatingDoneBy());
			updateSkillEntity.setRatingDoneByEmpId(updateSkillVO.getRatingDoneByEmpId());
			updateSkillEntity.setCreationDate(new Date());
			System.out.println("\n\n\n\nDate is:"+new Date());
			employeeSkillRepository.save(updateSkillEntity);
			
			updateSkillEntityList.add(updateSkillEntity);
			}*/
		}
		
		
		List<UpdateSkillTO> updateSkillTOList=null;
		if(!updateSkillEntityList.isEmpty() && updateSkillEntityList.size()!=0)
		{
		updateSkillTOList=modelConversion(updateSkillEntityList);
		}
		response.setUpdateSkillTOList(updateSkillTOList);
		return response;
	}
	
	
	
/*	public ViewSkillListTO viewSkills(String empid) {
		ViewSkillListTO skillsList= new ViewSkillListTO();
		
		List<ViewSkillTO> viewSkillTOList=new ArrayList<>();
		List<EmployeeSkillEntity> employeeSkillEntityList=employeeSkillRepository.findAll();
		
		for(EmployeeSkillEntity employeeSkillEntity : employeeSkillEntityList) {
			if(employeeSkillEntity.getEmpId().equals(empid) && employeeSkillEntity.getRating()!=LevelsEnum.CANNOT_ASSESS.getLevelName()) {
				ViewSkillTO viewSkillTO = new ViewSkillTO();
				viewSkillTO.setEmpId(employeeSkillEntity.getEmpId());
				viewSkillTO.setSkillId(employeeSkillEntity.getSkillId());
				viewSkillTO.setRating(LevelsEnum.getLevelFromName(employeeSkillEntity.getRating()));
				viewSkillTO.setRatingDoneBy(employeeSkillEntity.getRatingDoneBy());
				viewSkillTO.setWeekNumber(employeeSkillEntity.getWeekNumber());
				Skill skill=skillRepository.findOne(employeeSkillEntity.getSkillId());
				viewSkillTO.setSkillName(skill.getSkillName());
				viewSkillTO.setRatingDoneByEmpId(employeeSkillEntity.getRatingDoneByEmpId());
				viewSkillTO.setDescription(skill.getDescription());
				viewSkillTO.setUpdationDate(skill.getUpdationDate());
				viewSkillTOList.add(viewSkillTO);
			}
			
			
			skillsList.setListOfEmployeeSkills(viewSkillTOList);
		}
		return skillsList;
	}*/
	
	
	public ViewSkillListTO viewSkills(String empid) {
		ViewSkillListTO skillsList= new ViewSkillListTO();
		List<ViewSkillTO> viewSkillTOList=new ArrayList<>();
		List<EmployeeSkillEntity> employeeSkillEntityList=new ArrayList<>();
		Set<Integer> listOfSkillId=new HashSet<Integer>();
	/*	if(flag=="true")
		{
			 employeeSkillEntityList=employeeSkillRepository.getViewHistory(empid);
			 
			 HashMap<Integer,EmployeeSkillEntity> selfReviewsMap=new HashMap<>();
			 HashMap<Integer,EmployeeSkillEntity> peerReviewsMap=new HashMap<>();
			 for(EmployeeSkillEntity employeeSkillEntity : employeeSkillEntityList) {
				 
				 if(employeeSkillEntity.getRatingDoneBy().equalsIgnoreCase("Self")) {
					 LOGGER.debug("View history self data putting in map");
					 if(selfReviewsMap.containsKey(employeeSkillEntity.getSkillId())) {
						 //do nothing
					 }
					 else
						 {
						 selfReviewsMap.put(employeeSkillEntity.getSkillId(), employeeSkillEntity);
						 ViewSkillTO viewSkillTO=modelConversion(employeeSkillEntity);
							viewSkillTOList.add(viewSkillTO);
								listOfSkillId.add(employeeSkillEntity.getSkillId());
						 }
				 }
				 else if(employeeSkillEntity.getRatingDoneBy().equalsIgnoreCase("Peer"))
				 {
					 LOGGER.debug("View history peer data putting in map");
					 if(peerReviewsMap.containsKey(employeeSkillEntity.getSkillId())) {
						 //do nothing
					 }
					 else
						 {
						 peerReviewsMap.put(employeeSkillEntity.getSkillId(), employeeSkillEntity);
						 ViewSkillTO viewSkillTO=modelConversion(employeeSkillEntity);
							viewSkillTOList.add(viewSkillTO);
								listOfSkillId.add(employeeSkillEntity.getSkillId());
						 }
				 }
				 
				 skillsList.setListOfSkillId(listOfSkillId);
					skillsList.setListOfEmployeeSkills(viewSkillTOList);
				 
				 //All latest data inserted
				 
				 
				 
				 
			 }
			 
			 
			 
		}*/
		//else {
		 employeeSkillEntityList=employeeSkillRepository.findByEmpIdSkill(empid);
		
		System.out.println("\n\n\n\n66666666666666666666"+employeeSkillEntityList+" "+" Employeeskill is not null "+employeeSkillEntityList!=null+" List is empty  "+employeeSkillEntityList.isEmpty());
		if((employeeSkillEntityList!=null && employeeSkillEntityList.isEmpty())){
			LOGGER.debug("There is no skills record in table for employee id:"+empid);
			LOGGER.info("There is no skills record in table for employee id:"+empid);
			return skillsList;
		}
		
		 listOfSkillId=new HashSet<Integer>();
		
		for(EmployeeSkillEntity employeeSkillEntity : employeeSkillEntityList) {
			
				/*ViewSkillTO viewSkillTO = new ViewSkillTO();
				viewSkillTO.setEmpId(employeeSkillEntity.getEmpId());
				viewSkillTO.setSkillId(employeeSkillEntity.getSkillId());
				viewSkillTO.setRating(LevelsEnum.getLevelFromName(employeeSkillEntity.getRating()));
				viewSkillTO.setRatingDoneBy(employeeSkillEntity.getRatingDoneBy());
				viewSkillTO.setWeekNumber("week-"+employeeSkillEntity.getWeekNumber());
				viewSkillTO.setCreationDate(employeeSkillEntity.getCreationDate());
				Skill skill=skillRepository.findOne(employeeSkillEntity.getSkillId());
				viewSkillTO.setSkillName(skill.getSkillName());
				viewSkillTO.setRatingDoneByEmpId(employeeSkillEntity.getRatingDoneByEmpId());
				viewSkillTO.setDescription(skill.getDescription());
				viewSkillTO.setCreationDateString(getDateForResponse(employeeSkillEntity.getCreationDate()));
				///viewSkillTO.setComment(employeeSkillEntity.getComment());
				 * 
*/				
			ViewSkillTO viewSkillTO=modelConversion(employeeSkillEntity);
			viewSkillTOList.add(viewSkillTO);
				listOfSkillId.add(employeeSkillEntity.getSkillId());
			
		}
			skillsList.setListOfSkillId(listOfSkillId);
			skillsList.setListOfEmployeeSkills(viewSkillTOList);
			
		
			
			String selfComment=null;
			String peerComment=null;
//			String selfComment=employeeSkillRepository.getLatestSelfComment(empid).get(0);
//			String peerComment=employeeSkillRepository.getLatestPeerComment(empid).get(0);
			List<String> selfCommentList=employeeSkillRepository.getLatestSelfComment(empid);
			if(selfCommentList!=null && !selfCommentList.isEmpty())
				{
				LOGGER.debug("Latest self comment fetched for: "+empid);
				selfComment=selfCommentList.get(0);
				}
			
			List<String> peerCommentList=employeeSkillRepository.getLatestPeerComment(empid);
			if(peerCommentList!=null && !peerCommentList.isEmpty())
				{
				LOGGER.debug("Latest peer comment fetched for: "+empid);
				peerComment=peerCommentList.get(0);
				}
			
			
		  skillsList.setSelfComment(selfComment);
		  skillsList.setPeerComment(peerComment);
		return skillsList;
	}
	
	
	
	public String getDateForResponse(Date d) {
		if(d==null) return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString =sdf.format(d);
		return dateString;
	}
	
   public MapTO fetchMapDetails(MapVO mapVO) {
	   String empId=mapVO.getEmpId();
	   
	   int quarter=mapVO.getQuarterNumber();
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
		List<EmployeeSkillEntity> employeeSkillEntityList=employeeSkillRepository.findByEmpIdCustom(empId,start,end);
		
		String ratingDoneBy=mapVO.getRatingDoneBy();
		List<String> skillNameList=new ArrayList<>();
		MapTO mapTO=new MapTO();
		HashMap<String, List<SubjectTO>> map=new HashMap<>();
		for(EmployeeSkillEntity e:employeeSkillEntityList) {
			if(e.getRatingDoneBy().equalsIgnoreCase(ratingDoneBy)) {
				int skillId=e.getSkillId();
				Skill skill =skillRepository.findOne(skillId);
				String skillName=skill.getSkillName();
               skillNameList.add(skill.getSkillName());
			}
		}
		
		for(int i=0;i<skillNameList.size();i++) {
			System.out.print(skillNameList.get(i)+" ");
		}
		
		//Fetched all unique skills
		int totalSkills=skillNameList.size();
		int c=0;
		
		while(c<totalSkills) {
		Skill skill=skillRepository.findBySkillName(skillNameList.get(c));
		int skillId=skill.getSkillId();
	List<EmployeeSkillEntity> employeeSkillsList= employeeSkillRepository.findByEmpIdAndSkillIdAndRatingDoneByOrderByWeekNumber(empId, skillId, ratingDoneBy);
	List<SubjectTO> subjectsList= new ArrayList<>();
	for(EmployeeSkillEntity e: employeeSkillsList) {
		SubjectTO subjectTO=new SubjectTO();
		subjectTO.setRating(LevelsEnum.getLevelFromName(e.getRating()));
		subjectTO.setWeekNumber(e.getWeekNumber());
		subjectsList.add(subjectTO);
		}
	map.put(skillNameList.get(c), subjectsList);
	++c;
	
		
	
		}//while
		mapTO.setMap(map);
		  return mapTO;
		
	}
   
   
   
   
   public List<SkillTO> getAllSelfRatedSkills(String empId){
	 //  List<String> list=new ArrayList<>();
	   List<SkillTO> skillTOList=new ArrayList<>();
	   List<EmployeeSkillEntity> employeeSkillEntityList=employeeSkillRepository.getAllSelfRatedSkillsCustom(empId);
	   
	   HashMap<Skill,Object> map=new HashMap<>();
	   for(EmployeeSkillEntity e:employeeSkillEntityList ) {
		   int skillId=e.getSkillId();
		   Skill skill=skillRepository.findOne(skillId);
		  // String skillName=skill.getSkillName();
		   map.put(skill, null);
	   }
	   
	   System.out.println("MAP:"+map);
	   
	  for(Entry<Skill, Object> entry:map.entrySet()) {
		 SkillTO skillTO=skillService.modelConversion(entry.getKey());
		 skillTOList.add(skillTO);
	  }
	   return skillTOList;
   }
   
   
   
   
  public ViewSkillListTO getViewHistory(String empid) {
	  ViewSkillListTO skillsList= new ViewSkillListTO();
		List<ViewSkillTO> viewSkillTOList=new ArrayList<>();
		List<EmployeeSkillEntity> employeeSkillEntityList=new ArrayList<>();
		Set<Integer> listOfSkillId=new HashSet<Integer>();
		
			 employeeSkillEntityList=employeeSkillRepository.getViewHistory(empid);
			 
			 HashMap<Integer,EmployeeSkillEntity> selfReviewsMap=new HashMap<>();
			 HashMap<Integer,EmployeeSkillEntity> peerReviewsMap=new HashMap<>();
			 for(EmployeeSkillEntity employeeSkillEntity : employeeSkillEntityList) {
				 
				 if(employeeSkillEntity.getRatingDoneBy().equalsIgnoreCase("Self")) {
					 LOGGER.debug("View history self data putting in map");
					 if(selfReviewsMap.containsKey(employeeSkillEntity.getSkillId())) {
						 //do nothing
					 }
					 else
						 {
						 selfReviewsMap.put(employeeSkillEntity.getSkillId(), employeeSkillEntity);
						 ViewSkillTO viewSkillTO=modelConversion(employeeSkillEntity);
							viewSkillTOList.add(viewSkillTO);
								listOfSkillId.add(employeeSkillEntity.getSkillId());
						 }
				 }
				 else if(employeeSkillEntity.getRatingDoneBy().equalsIgnoreCase("Peer"))
				 {
					 LOGGER.debug("View history peer data putting in map");
					 if(peerReviewsMap.containsKey(employeeSkillEntity.getSkillId())) {
						 //do nothing
					 }
					 else
						 {
						 peerReviewsMap.put(employeeSkillEntity.getSkillId(), employeeSkillEntity);
						 ViewSkillTO viewSkillTO=modelConversion(employeeSkillEntity);
							viewSkillTOList.add(viewSkillTO);
								listOfSkillId.add(employeeSkillEntity.getSkillId());
						 }
				 }
				 
				 skillsList.setListOfSkillId(listOfSkillId);
					skillsList.setListOfEmployeeSkills(viewSkillTOList);
					
			 }
			 
			 String selfComment=null;
				String peerComment=null;
//				String selfComment=employeeSkillRepository.getLatestSelfComment(empid).get(0);
//				String peerComment=employeeSkillRepository.getLatestPeerComment(empid).get(0);
				List<String> selfCommentList=employeeSkillRepository.getLatestSelfComment(empid);
				if(selfCommentList!=null && !selfCommentList.isEmpty())
					{
					LOGGER.debug("Latest self comment fetched for: "+empid);
					selfComment=selfCommentList.get(0);
					}
				
				List<String> peerCommentList=employeeSkillRepository.getLatestPeerComment(empid);
				if(peerCommentList!=null && !peerCommentList.isEmpty())
					{
					LOGGER.debug("Latest peer comment fetched for: "+empid);
					peerComment=peerCommentList.get(0);
					}
				
				
			  skillsList.setSelfComment(selfComment);
			  skillsList.setPeerComment(peerComment);
			return skillsList;
		
  }

	public List<UpdateSkillTO> modelConversion(List<EmployeeSkillEntity> updateSkillEntityList) {
		List<UpdateSkillTO> updateSkillTOList= new ArrayList<>();
		for(EmployeeSkillEntity saved: updateSkillEntityList) {
		UpdateSkillTO updateSkillTO = new UpdateSkillTO();
		updateSkillTO.setId(saved.getId());
		updateSkillTO.setEmpId(saved.getEmpId());
		updateSkillTO.setSkillId(saved.getSkillId());
		updateSkillTO.setComment(saved.getComment());
		updateSkillTO.setRating(LevelsEnum.getLevelFromName(saved.getRating()));
		updateSkillTO.setRatingDoneBy(saved.getRatingDoneBy());
		updateSkillTO.setRatingDoneByEmpId(saved.getRatingDoneByEmpId());
		updateSkillTO.setWeekNumber(saved.getWeekNumber());
		updateSkillTO.setCreationDate(saved.getCreationDate());
		updateSkillTO.setCreationDateString(getDateForResponse(updateSkillTO.getCreationDate()));
		updateSkillTOList.add(updateSkillTO);
		
		}
		
		return updateSkillTOList;
	}
	
	
	
	public ViewSkillTO modelConversion(EmployeeSkillEntity employeeSkillEntity) {
		ViewSkillTO viewSkillTO = new ViewSkillTO();
		viewSkillTO.setEmpId(employeeSkillEntity.getEmpId());
		viewSkillTO.setSkillId(employeeSkillEntity.getSkillId());
		viewSkillTO.setRating(LevelsEnum.getLevelFromName(employeeSkillEntity.getRating()));
		viewSkillTO.setRatingDoneBy(employeeSkillEntity.getRatingDoneBy());
		viewSkillTO.setWeekNumber("week-"+employeeSkillEntity.getWeekNumber());
		viewSkillTO.setCreationDate(employeeSkillEntity.getCreationDate());
		Skill skill=skillRepository.findOne(employeeSkillEntity.getSkillId());
		viewSkillTO.setSkillName(skill.getSkillName());
		viewSkillTO.setRatingDoneByEmpId(employeeSkillEntity.getRatingDoneByEmpId());
		viewSkillTO.setDescription(skill.getDescription());
		viewSkillTO.setCreationDateString(getDateForResponse(employeeSkillEntity.getCreationDate()));
		///viewSkillTO.setComment(employeeSkillEntity.getComment());
		
		return viewSkillTO;
	}
}
