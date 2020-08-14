package com.promore.project.service;

import java.util.List;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.promore.aop.HAspect;
import com.promore.project.dao.ProjectDao;
import com.promore.project.dto.ProjectDto;

@Component
public class ProjectServiceImp implements ProjectService {
	@Autowired 
	private ProjectDao projectDao;
	 

	@Override
	public void projectWrite(ModelAndView mav, String memid) {
	Map<String, Object> map = mav.getModelMap();
	HttpServletRequest request = (HttpServletRequest)map.get("request");
	ProjectDto projectDto = (ProjectDto) map.get("projectDto");	
	System.out.println("@@@@@@@" + memid);
	  projectDto.setProName(projectDto.getProName());
	  projectDto.setProContent(projectDto.getProContent());
	  projectDto.setProManager(memid);
	  projectDto.setProState(0); 
	  projectDto.setProMax(projectDto.getProCnt());
	  projectDto.setProCnt(0);
		  
	 
	  int check =  projectDao.projectWrite(projectDto);
	  mav.addObject("check", check);
	  mav.setViewName("project/writeOk");
	}
	
	@Override
	public void projectList(ModelAndView mav) {
		int projectCount = projectDao.projectCount();
		List<ProjectDto> projectDtoArray = projectDao.projectList();
		
		mav.addObject("projectDtoArray", projectDtoArray);
		mav.addObject("projectCount", projectCount);
		
	}
	

	@Override
	public void projectCnt(ModelAndView mav, String id) {
		//번호 가져오기
		Map<String, Object> map = mav.getModelMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		ProjectDto projectDto = new ProjectDto();
		
		 HAspect.logger.info(HAspect.logMsg+ "state id check" + id); 
		//번호 가져오기
		 List<String> projectCnt = projectDao.projectState(id);
		 mav.addObject("projectCnt", projectCnt);
		
	}
	
	
}
