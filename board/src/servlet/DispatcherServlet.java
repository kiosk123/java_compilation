package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;


@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet{
	
	private final static Logger log=LoggerFactory.getLogger(DispatcherServlet.class);

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext sc=this.getServletContext();
		log.info("Dispatcher 서블릿 시작");
		
		try {
			
			String servletPath=request.getServletPath();
			Controller controller=(Controller)sc.getAttribute(servletPath);
			log.info("요청된 서블릿 패스"+servletPath);
			
			if(controller==null){
				throw new Exception("요청 페이지를 찾을 수 없음 404 에러");
			}
			
			String viewURL=controller.execute(request, response);
			log.info("viewURL : "+viewURL);
			
			if(viewURL.startsWith("redirect:")){
				response.sendRedirect(viewURL.substring(9));
			}else{
				log.info(viewURL);
				RequestDispatcher rd=request.getRequestDispatcher(viewURL);
				rd.forward(request, response);
			}
			
		} catch (Exception e) {
		
		}
	}
}
