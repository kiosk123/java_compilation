package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(
	    urlPatterns="/*",
	    initParams={
	        @WebInitParam(name="encoding",value="UTF-8")
	    })
public class CharacterEncodingFilter implements Filter {

	private final static Logger log=LoggerFactory.getLogger(CharacterEncodingFilter.class);
	private FilterConfig config;

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain next) throws IOException, ServletException {
		log.info("문자 인코딩 필터 실행");
		req.setCharacterEncoding(config.getInitParameter("encoding"));
		res.setCharacterEncoding(config.getInitParameter("encoding"));
		next.doFilter(req, res);
	}

	@Override
	public void destroy() {

	}
}
