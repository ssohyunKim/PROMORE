package com.promore.project.service;

import java.util.Date;
import java.util.List;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.promore.aop.HAspect;
import com.promore.customer.dto.CustomerDto;
import com.promore.project.dao.ProjectDao;
import com.promore.project.dto.ProjectDto;

@Component
public class ProjectServiceImp implements ProjectService {
	@Autowired 
	private ProjectDao projectDao;
	

	@Override
	public void projectWrite(ModelAndView mav, String aplMemId) {
	Map<String, Object> map = mav.getModelMap();
	HttpServletRequest request = (HttpServletRequest)map.get("request");
	ProjectDto projectDto = (ProjectDto) map.get("projectDto");	
	

	  projectDto.setProName(projectDto.getProName());
	  projectDto.setProContent(projectDto.getProContent());
	  projectDto.setProManager(aplMemId);
	  projectDto.setProState(0); 
	  projectDto.setProMax(projectDto.getProMax());
	  projectDto.setProCnt(1);
	  
	  int check = projectDao.projectWrite(projectDto);
	}
	
	
	@Override
	public void projectUpdateOk(ModelAndView mav, String aplMemId) {
		Map<String, Object> map = mav.getModelMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		int proNum = Integer.parseInt(request.getParameter("proNum"));
		
		ProjectDto projectDto = (ProjectDto) map.get("projectDto");
		projectDto.setProManager(aplMemId);
		
		int check = projectDao.projectUpdateOk(projectDto);
		HAspect.logger.info(HAspect.logMsg + check);
		
		mav.addObject("check", check); 
		mav.addObject("proNum", proNum);
		mav.setViewName("project/updateOk"); 
	}
	
	@Override
	public void projectDeleteOk(ModelAndView mav) {
		Map<String, Object> map = mav.getModelMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		
		int proNum = Integer.parseInt(request.getParameter("proNum"));
		int check = projectDao.projectDeleteOk(proNum);
		
		mav.addObject("check", check);
		mav.setViewName("project/deleteOk");
		
		
	}
	
	@Override
	public void projectList(ModelAndView mav) {
		int projectCount = projectDao.projectCount();
		List<ProjectDto> projectDtoArray = projectDao.projectList();
		
		mav.addObject("projectDtoArray", projectDtoArray);
		mav.addObject("projectCount", projectCount);
		
	}
	
	@Override
	public void projectApplyAdd(ModelAndView mav, String memId) {
		Map<String, Object> map = mav.getModelMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
	

		//프로젝트에 추가
		int check = projectDao.projectApplyAdd(memId);

	}

	@Override
	public void projectCnt(ModelAndView mav, String aplMemId) {
		//번호 가져오기
		Map<String, Object> map = mav.getModelMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		ProjectDto projectDto = new ProjectDto();
		
		//번호 가져오기
		 List<String> projectCnt = projectDao.projectState(aplMemId);
		 if(projectCnt==null) {
			 List<String> projectCntnull = projectDao.projectState();
			 mav.addObject("projectCntnull", projectCntnull);
		 }
		 else {
			mav.addObject("projectCnt", projectCnt);
		 }
	}
	
	@Override
	public void projectApplyOk(ModelAndView mav, String aplMemId) {
		Map<String, Object> map = mav.getModelMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		int proNum = Integer.parseInt(request.getParameter("proNum"));
		
		int check = projectDao.projectApply(proNum, aplMemId);
		//숫자 증가
		projectDao.projectApplyCnt(proNum);
		
		mav.addObject("check", check);
		mav.addObject("proNum", proNum);
		mav.setViewName("project/applyOk");
	}
	
	@Override
	public void projectStateUpdateOk(ModelAndView mav) {
		Map<String, Object> map = mav.getModelMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		ProjectDto projectDto = (ProjectDto) map.get("projectDto");	
		int proNum = Integer.parseInt(request.getParameter("proNum"));
		int proState = Integer.parseInt(request.getParameter("proState"));
				
		mav.addObject("proNum", proNum);
		mav.addObject("proState", proState);
		int check = projectDao.projectStateUpdate(proNum, proState);
		mav.addObject("check", check);
		mav.setViewName("project/projectStateUpdate");
		 
	}
	
	
	@Override
	public void projectSelect(ModelAndView mav, String aplMemId) {
		Map<String, Object> map = mav.getModelMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		//id가 신청한 프로젝트 목록 가져오기
		 List<String> projectCnt = projectDao.projectState(aplMemId);
		int proNum = Integer.parseInt(request.getParameter("proNum"));
		List<ProjectDto> projectList = projectDao.projectSelectList(proNum); 

		 mav.addObject("projectCnt",projectCnt);
		 mav.addObject("projectList",projectList);
		 mav.addObject("id",aplMemId);
		 mav.addObject("proNum", proNum);
		 mav.setViewName("project/projectSelect");
		
	}
	
	@Override
	public void projectRead(ModelAndView mav, String aplMemId) {
		Map<String, Object> map = mav.getModelMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		
		int proNum = Integer.parseInt(request.getParameter("proNum"));
		System.out.println("OK" + proNum);
		ProjectDto projectDto = projectDao.projectSelectRead(proNum); 
		
		mav.addObject("projectDto", projectDto);
		mav.addObject("id", aplMemId);
		mav.setViewName("project/projectRead");
		
		
	}
	
}
