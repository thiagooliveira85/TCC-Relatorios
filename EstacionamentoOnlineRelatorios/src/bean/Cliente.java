package bean;


public class Cliente {

	private String placa;
	private int qtdAlugueis;
	
	public Cliente(String placa, int qtdAlugueis) {
		this.placa = placa;
		this.qtdAlugueis = qtdAlugueis;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public int getQtdAlugueis() {
		return qtdAlugueis;
	}

	public void setQtdAlugueis(int qtdAlugueis) {
		this.qtdAlugueis = qtdAlugueis;
	}

}
