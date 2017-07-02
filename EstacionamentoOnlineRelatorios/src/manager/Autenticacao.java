package manager;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AutenticacaoDAO;
import bean.AutenticacaoBean;
import bean.UsuarioBean;

@ManagedBean
public class Autenticacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String senha;
	
	@PostConstruct
	public void init(){
		
	}
	
	public String autentica(){
		
		try {
			
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			
			AutenticacaoBean entidade = new AutenticacaoBean(login, senha);
			UsuarioBean usuario = new AutenticacaoDAO().existeUsuarioPorLoginESenhaInformados(entidade);
			
			if (usuario != null){
				session.setAttribute("usuario", usuario);
				return "/home.jsf";
			}
			
			throw new Exception("login ou senha inválidos!");
		
		} catch (Exception e) {
			try {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage());		
	    		FacesContext.getCurrentInstance().addMessage(null, message);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		}
	}
	
	public void logout(){
		try {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.removeAttribute("usuario");
			session.invalidate();
			HttpServletResponse res = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
			res.sendRedirect("/EstacionamentoOnlineRelatorios");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void entrada(){
		try {
			HttpServletResponse res = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
			res.sendRedirect("/EstacionamentoOnlineRelatorios");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
