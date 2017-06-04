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

import bean.RelatorioAluguel;
import bean.RelatorioPorTipo;
import bean.Tipo;
import db.DB;

public class RelatorioDAO extends DB {
	

	public List<RelatorioAluguel> listaRelatorioAluguel() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RelatorioAluguel> relatorios = null;

		try {

			conn = getMyqslConnection();
			pstmt = conn.prepareStatement("select * from historico_aluguel");
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
			pstmt = conn.prepareStatement("select tipo_vaga, sum(valor_cobrado) as total from historico_aluguel group by tipo_vaga");
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
			pstmt = conn.prepareStatement("select * from historico_aluguel where upper(tipo_vaga) = ?");
			pstmt.setString(1, tipoInformado.toUpperCase());
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
			pstmt = conn.prepareStatement("select * from historico_aluguel where hora_entrada between (?) and (?) and upper(tipo_vaga) = ?");
			pstmt.setDate(1, new java.sql.Date(dateIni.getTime()));
			pstmt.setDate(2, new java.sql.Date(dateFim.getTime()));
			pstmt.setString(3, tipoInformado.toUpperCase());
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
			pstmt = conn.prepareStatement("select * from historico_aluguel where hora_entrada between (?) and (?) ");
			pstmt.setDate(1, new java.sql.Date(dateIni.getTime()));
			pstmt.setDate(2, new java.sql.Date(dateFim.getTime()));
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
			pstmt = conn.prepareStatement("select * from historico_aluguel where upper(placa) like '%" + placa.toUpperCase() + "%'");
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

}
