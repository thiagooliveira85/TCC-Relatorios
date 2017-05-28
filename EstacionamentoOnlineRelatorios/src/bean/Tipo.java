package bean;

import java.util.List;

public class Tipo {
	
	private String nome;
	private String desc;
	
	List<Mes> meses;
	
	public Tipo(){}
	
	public Tipo(String nome, String desc) {
		this.nome = nome;
		this.desc = desc;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Mes> getMeses() {
		return meses;
	}

	public void setMeses(List<Mes> meses) {
		this.meses = meses;
	}
	
	

}