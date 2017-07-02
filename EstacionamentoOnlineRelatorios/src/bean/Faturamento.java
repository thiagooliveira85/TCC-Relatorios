package bean;

public class Faturamento {
	
	private String mesAno;
	private double valor;
	
	public Faturamento(String mesAno, double valor) {
		this.mesAno = mesAno;
		this.valor = valor;
	}

	public String getMesAno() {
		return mesAno;
	}

	public void setMesAno(String mesAno) {
		this.mesAno = mesAno;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
}
