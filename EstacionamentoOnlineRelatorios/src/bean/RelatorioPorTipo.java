package bean;

public class RelatorioPorTipo {
	private String tipo;
	private double valorTotal;
	
	public RelatorioPorTipo(String tipo, double valorTotal) {
		this.tipo = tipo;
		this.valorTotal = valorTotal;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	
	
	
}
