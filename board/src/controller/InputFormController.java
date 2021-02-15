package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InputFormController implements Controller{
	@Override
	public String execute(HttpServletRequest request,HttpServletResponse response) 
			throws Exception {
		return "/WEB-INF/views/inputForm.jsp";
	}	
}
