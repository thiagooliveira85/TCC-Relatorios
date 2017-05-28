package bean;

import java.io.Serializable;

import util.Util;

public class AutenticacaoBean implements Serializable {

	private static final long serialVersionUID = -5091928272630162737L;

	private String login;
	private String senha;
	
	public AutenticacaoBean(String login, String senha) throws Exception {
		this.login = login;
		this.senha = Util.criptografa(senha.trim());
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AutenticacaoBean other = (AutenticacaoBean) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "AutenticacaoBean [login=" + login + ", senha=" + senha + "]";
	}
	
}
