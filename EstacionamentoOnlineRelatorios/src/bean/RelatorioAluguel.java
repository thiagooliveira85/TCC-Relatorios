package bean;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RelatorioAluguel {
	
	private String tipoVaga;
	private String modelo;
	private String placa;
	private String codigoVaga;
	private LocalDateTime horaEntrada;
	private LocalDateTime horaSaida;
	private double valorCobrado;
	private String tipoPagamento;
	private String precoFormatado;
	private String dtEntradaFormatada;
	private String dtSaidaFormatada;
	
	public String getTipoVaga() {
		return tipoVaga;
	}
	public void setTipoVaga(String tipoVaga) {
		this.tipoVaga = tipoVaga;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getCodigoVaga() {
		return codigoVaga;
	}
	public void setCodigoVaga(String codigoVaga) {
		this.codigoVaga = codigoVaga;
	}
	public double getValorCobrado() {
		return valorCobrado;
	}
	public void setValorCobrado(double valorCobrado) {
		this.setPrecoFormatado(NumberFormat.getCurrencyInstance().format(valorCobrado));
		this.valorCobrado = valorCobrado;
	}
	public String getTipoPagamento() {
		return tipoPagamento;
	}
	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
	public String getPrecoFormatado() {
		return precoFormatado;
	}
	public void setPrecoFormatado(String precoFormatado) {
		this.precoFormatado = precoFormatado;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codigoVaga == null) ? 0 : codigoVaga.hashCode());
		result = prime * result
				+ ((getHoraEntrada() == null) ? 0 : getHoraEntrada().hashCode());
		result = prime * result
				+ ((getHoraSaida() == null) ? 0 : getHoraSaida().hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
		result = prime * result + ((placa == null) ? 0 : placa.hashCode());
		result = prime * result
				+ ((precoFormatado == null) ? 0 : precoFormatado.hashCode());
		result = prime * result
				+ ((tipoPagamento == null) ? 0 : tipoPagamento.hashCode());
		result = prime * result
				+ ((tipoVaga == null) ? 0 : tipoVaga.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valorCobrado);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		RelatorioAluguel other = (RelatorioAluguel) obj;
		if (codigoVaga == null) {
			if (other.codigoVaga != null)
				return false;
		} else if (!codigoVaga.equals(other.codigoVaga))
			return false;
		if (getHoraEntrada() == null) {
			if (other.getHoraEntrada() != null)
				return false;
		} else if (!getHoraEntrada().equals(other.getHoraEntrada()))
			return false;
		if (getHoraSaida() == null) {
			if (other.getHoraSaida() != null)
				return false;
		} else if (!getHoraSaida().equals(other.getHoraSaida()))
			return false;
		if (modelo == null) {
			if (other.modelo != null)
				return false;
		} else if (!modelo.equals(other.modelo))
			return false;
		if (placa == null) {
			if (other.placa != null)
				return false;
		} else if (!placa.equals(other.placa))
			return false;
		if (precoFormatado == null) {
			if (other.precoFormatado != null)
				return false;
		} else if (!precoFormatado.equals(other.precoFormatado))
			return false;
		if (tipoPagamento == null) {
			if (other.tipoPagamento != null)
				return false;
		} else if (!tipoPagamento.equals(other.tipoPagamento))
			return false;
		if (tipoVaga == null) {
			if (other.tipoVaga != null)
				return false;
		} else if (!tipoVaga.equals(other.tipoVaga))
			return false;
		if (Double.doubleToLongBits(valorCobrado) != Double
				.doubleToLongBits(other.valorCobrado))
			return false;
		return true;
	}
	public String getDtEntradaFormatada() {
		return dtEntradaFormatada;
	}
	public void setDtEntradaFormatada(String dtEntradaFormatada) {
		this.dtEntradaFormatada = dtEntradaFormatada;
	}
	public String getDtSaidaFormatada() {
		return dtSaidaFormatada;
	}
	public void setDtSaidaFormatada(String dtSaidaFormatada) {
		this.dtSaidaFormatada = dtSaidaFormatada;
	}
	public LocalDateTime getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(LocalDateTime horaEntrada) {
		
		if(horaEntrada == null)
			return;

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		setDtEntradaFormatada(horaEntrada.format(fmt));

		this.horaEntrada = horaEntrada;
	}
	public LocalDateTime getHoraSaida() {
		return horaSaida;
	}
	public void setHoraSaida(LocalDateTime horaSaida) {
		
		if (horaSaida == null)
			return;
		
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		setDtSaidaFormatada(horaSaida.format(fmt));
		
		this.horaSaida = horaSaida;
	}
	
	
	
}
