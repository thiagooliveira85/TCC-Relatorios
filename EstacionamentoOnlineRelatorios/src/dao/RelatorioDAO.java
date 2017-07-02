package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Cliente;
import bean.Faturamento;
import bean.RelatorioAluguel;
import bean.RelatorioPorTipo;
import db.DB;

public class RelatorioDAO extends DB {
	
	private static final String ORDER_BY = " ORDER BY HORA_ENTRADA DESC ";
	
	private int idEstacionamento;
	
	public RelatorioDAO(int idEstacionamento){
		this.idEstacionamento = idEstacionamento;
	}
	

	public List<RelatorioAluguel> listaRelatorioAluguel() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RelatorioAluguel> relatorios = null;

		try {

			conn = getMyqslConnection();
			pstmt = conn.prepareStatement("select * from historico_aluguel where id_estacionamento = ? " + ORDER_BY);
			pstmt.setInt(1, idEstacionamento);
			rs = pstmt.executeQuery();
			relatorios = new ArrayList<RelatorioAluguel>();

			while (rs.next()) {
				montaObjRelatorioAluguel(rs, relatorios);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return relatorios;
	}
	
	public List<RelatorioPorTipo> listaRelatorioPorTipo() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RelatorioPorTipo> relatorios = null;

		try {

			conn = getMyqslConnection();
			pstmt = conn.prepareStatement("select tipo_vaga, sum(valor_cobrado) as total from historico_aluguel where id_estacionamento = ? group by tipo_vaga" + ORDER_BY);
			pstmt.setInt(1, idEstacionamento);
			rs = pstmt.executeQuery();
			relatorios = new ArrayList<RelatorioPorTipo>();

			while (rs.next())
				relatorios.add(new RelatorioPorTipo(rs.getString("tipo_vaga"), rs.getDouble("total")));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return relatorios;
	}
	
	/*public List<RelatorioPorTipo> listaRelatorioPorTempo() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RelatorioPorTipo> relatorios = null;

		try {

			conn = getMyqslConnection();
			
			pstmt = conn.prepareStatement("select distinct tipo_vaga from historico_aluguel group by  tipo_vaga, hora_entrada");
			rs = pstmt.executeQuery();
			
			List<Tipo> tipos = new ArrayList<>();			
			while (rs.next()){
				tipos.add(new Tipo(rs.getString("tipo_vaga"), ""));
			}
			
			pstmt = null;
			rs = null;
			
			pstmt = conn.prepareStatement("select min(hora_entrada) as minima, max(hora_entrada) as maxima  from historico_aluguel");
			rs = pstmt.executeQuery();
			
			Date dataIni = null;
			Date dataFim = null;
			if (rs.next()){
				dataIni = rs.getDate("minima");
				dataFim = rs.getDate("maxima");
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return relatorios;
	}*/

	public List<RelatorioAluguel> buscarInformacoesPorTipo(String tipoInformado) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RelatorioAluguel> lista = null;

		try {

			conn = getMyqslConnection();
			pstmt = conn.prepareStatement("select * from historico_aluguel where id_estacionamento = ? and upper(tipo_vaga) = ?" + ORDER_BY);
			pstmt.setInt(1, idEstacionamento);
			pstmt.setString(2, tipoInformado.toUpperCase());
			rs = pstmt.executeQuery();
			lista = new ArrayList<RelatorioAluguel>();

			while (rs.next()){
				montaObjRelatorioAluguel(rs, lista);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return lista;
	}

	public List<RelatorioAluguel> buscarInformacoesPorFiltro(Date dateIni,Date dateFim, String tipoInformado) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RelatorioAluguel> lista = null;

		try {

			conn = getMyqslConnection();
			pstmt = conn.prepareStatement("select * from historico_aluguel where hora_entrada between (?) and (?) and upper(tipo_vaga) = ? and id_estacionamento = ?" + ORDER_BY);
			pstmt.setDate(1, new java.sql.Date(dateIni.getTime()));
			pstmt.setDate(2, new java.sql.Date(dateFim.getTime()));
			pstmt.setString(3, tipoInformado.toUpperCase());
			pstmt.setInt(4, idEstacionamento);
			rs = pstmt.executeQuery();
			lista = new ArrayList<RelatorioAluguel>();

			while (rs.next()){
				montaObjRelatorioAluguel(rs, lista);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return lista;
	}

	public List<RelatorioAluguel> buscarInformacoesPorPeriodo(Date dateIni, Date dateFim) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RelatorioAluguel> lista = null;

		try {

			conn = getMyqslConnection();
			pstmt = conn.prepareStatement("select * from historico_aluguel where hora_entrada between (?) and (?) and id_estacionamento = ?" + ORDER_BY);
			pstmt.setDate(1, new java.sql.Date(dateIni.getTime()));
			pstmt.setDate(2, new java.sql.Date(dateFim.getTime()));
			pstmt.setInt(3, idEstacionamento);
			rs = pstmt.executeQuery();
			lista = new ArrayList<RelatorioAluguel>();

			while (rs.next()){
				montaObjRelatorioAluguel(rs, lista);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return lista;
	}

	public List<RelatorioAluguel> buscarInformacoesPorPlaca(String placa) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RelatorioAluguel> relatorios = null;

		try {

			conn = getMyqslConnection();
			pstmt = conn.prepareStatement("select * from historico_aluguel where id_estacionamento = ? and upper(placa) like '%" + placa.toUpperCase() + "%'" + ORDER_BY);
			pstmt.setInt(1, idEstacionamento);
			rs = pstmt.executeQuery();
			relatorios = new ArrayList<RelatorioAluguel>();

			while (rs.next()) {
				montaObjRelatorioAluguel(rs, relatorios);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return relatorios;
	}

	private void montaObjRelatorioAluguel(ResultSet rs, List<RelatorioAluguel> relatorios)
			throws SQLException {
		
		RelatorioAluguel r = new RelatorioAluguel();
		r.setCodigoVaga(rs.getString("codigo_vaga"));
		
		validaDataHora(rs, r);
		
		r.setModelo(rs.getString("modelo"));
		r.setPlaca(rs.getString("placa"));
		r.setTipoPagamento(rs.getString("tipo_pagamento"));
		r.setTipoVaga(rs.getString("tipo_vaga"));
		r.setValorCobrado(rs.getDouble("valor_cobrado"));
		relatorios.add(r);
		
	}

	private void validaDataHora(ResultSet rs, RelatorioAluguel r) throws SQLException {
		
		Timestamp timestampEntrada = rs.getTimestamp("hora_entrada");
		if (timestampEntrada != null){
			LocalDateTime dataEntrada = timestampEntrada.toLocalDateTime();
			r.setHoraEntrada(dataEntrada != null ? dataEntrada : null);
		}
		
		Timestamp timestampSaida = rs.getTimestamp("hora_saida");
		if (timestampSaida != null){
			LocalDateTime dataSaida = timestampSaida.toLocalDateTime();
			r.setHoraSaida(dataSaida != null ? dataSaida : null);
		}
	}


	/*public List<Cliente> buscaQTDClientesPorData() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Cliente> clientes = null;
		
		String sql = " select distinct(placa) from HISTORICO_ALUGUEL where id_estacionamento = ? and placa in ('KVU7464','LLK4566')";
		
		String sql2 = " select placa, concat(month(hora_entrada),'/', year(hora_entrada)) as mes_ano, count(placa) as qtd from HISTORICO_ALUGUEL " + 
					" where id_estacionamento = ?" +
					" and placa = ? and placa in ('KVU7464','LLK4566')" +
					" group by placa, month(hora_entrada), year(hora_entrada) " +
					" order by count(placa) desc ";
		
		try {

			conn = getMyqslConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idEstacionamento);
			rs = pstmt.executeQuery();
			
			clientes = new ArrayList<Cliente>();
			while (rs.next()){
				
				String placa = rs.getString("placa");
				
				List<MesAno> lstMesAno = new ArrayList<>();
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, idEstacionamento);
				pstmt.setString(2, placa);
				
				ResultSet rs2 = pstmt.executeQuery();
				while (rs2.next())
					lstMesAno.add(new MesAno(rs2.getString("mes_ano"), rs2.getInt("qtd")));
				
				rs2.close();
				rs2 = null;
				
				clientes.add(new Cliente(placa, lstMesAno));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		
		return clientes;
	}*/
	
	public List<Cliente> buscaQTDClientesPorAlugueis() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Cliente> clientes = null;
		
		String sql = " select placa, count(placa) as qtd from HISTORICO_ALUGUEL where id_estacionamento = ? " +
				" group by placa order by count(placa) desc ";
		
		try {

			conn = getMyqslConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idEstacionamento);
			rs = pstmt.executeQuery();
			
			clientes = new ArrayList<Cliente>();
			while (rs.next())
				clientes.add(new Cliente(rs.getString("placa"), rs.getInt("qtd")));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		
		return clientes;
	}
	
	public List<Faturamento> buscaFaturamentoMensal() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Faturamento> lista = null;
		
		String sql = " select concat(month(hora_entrada),'/', year(hora_entrada)) as mes_ano, sum(valor_cobrado) as valor from HISTORICO_ALUGUEL where id_estacionamento = ? " + 
			" group by month(hora_entrada), year(hora_entrada) order by count(placa) desc ";
		
		try {

			conn = getMyqslConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idEstacionamento);
			rs = pstmt.executeQuery();
			
			lista = new ArrayList<Faturamento>();
			while (rs.next())
				lista.add(new Faturamento(rs.getString("mes_ano"), rs.getInt("valor")));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		
		return lista;
	}
	
	

}
