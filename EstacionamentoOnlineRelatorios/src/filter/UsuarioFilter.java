package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UsuarioBean;

@WebFilter(servletNames = {"Faces Servlet"})
public class UsuarioFilter implements Filter{


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		HttpSession session = req.getSession();
		
		UsuarioBean usuario = (UsuarioBean)session.getAttribute("usuario");
		
		String url = req.getRequestURI();
		if (usuario == null && !url.endsWith("login.jsf") && byPassArquivosEstaticos(url))
			res.sendRedirect("login.jsf");
		else
			chain.doFilter(request, response);
	}
	
	private boolean byPassArquivosEstaticos(String url) {
		return !url.endsWith(".css") && !url.endsWith(".js") && !url.endsWith(".jpg") && !url.endsWith(".png")
				&& !url.endsWith(".gif") && !url.contains("javax.faces.resource");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	@Override
	public void destroy() {
		
	}
}
