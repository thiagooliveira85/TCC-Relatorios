package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.BairroBean;
import bean.CidadeBean;
import bean.Coordenadas;
import bean.EnderecoBean;
import bean.EstacionamentoBean;
import bean.StatusBean;
import db.DB;

public class EstacionamentoDAO extends DB {
	
	private static final String LISTAR_TODOS_ESTACIONAMENTOS = " SELECT e.id, s.id as id_status, s.nome as status, e.nome_fantasia, "
			+ " end.nome_logradouro, "
			+ " end.numero, "
			+ " b.id as id_bairro, "
			+ " b.nome AS bairro, "
			+ " c.id AS id_cidade, "
			+ " c.nome AS cidade, "
			+ " end.latitude, "
			+ " end.longitude, "
			+ " e.cnpj, "
			+ " e.avaliacao, "
			+ " u.nome as administrador "
			+ " FROM "
			+ " estacionamento e, "
			+ " endereco end, "
			+ " bairro b, "
			+ " cidade c, "
			+ " status s, "
			+ " usuario u "
			+ " WHERE "
			+ " e.id_endereco = end.id "
			+ " AND b.id = end.id_bairro "
			+ " AND c.id = end.id_cidade "
			+ " AND s.id = e.id_status "
			+ " AND e.id_adm_estacionamento = u.id "; 
			
	
	private String BUSCA_COORDENADAS_POR_ESTACIONAMENTO = " select latitude, longitude from endereco where  id in "
			+ " ( select id_endereco from estacionamento where upper(nome_fantasia) like ";

	
	public List<EstacionamentoBean> listaTodos() {
		
		Connection conn				=	null;
		PreparedStatement pstmt		=	null;
		ResultSet rs				=	null;
		List<EstacionamentoBean> listaEstacionamentoBean =	null;

		try {

			conn	=	getMyqslConnection();
			pstmt	=	conn.prepareStatement(LISTAR_TODOS_ESTACIONAMENTOS + " ORDER BY e.avaliacao DESC");
			rs		=	pstmt.executeQuery();
			listaEstacionamentoBean 		=	new ArrayList<EstacionamentoBean>();

			while(rs.next()) {
				montaObjEstacionamento(rs, listaEstacionamentoBean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return listaEstacionamentoBean;
	}

	public Coordenadas buscaCoordenadasPorEstacionamento(String valorPesquisa) {
		
		Connection conn				=	null;
		PreparedStatement pstmt		=	null;
		ResultSet rs				=	null;
		
		try {
			conn	=	getMyqslConnection();
			
			if (valorPesquisa != null)
				valorPesquisa = valorPesquisa.toUpperCase();
			
			String sql = BUSCA_COORDENADAS_POR_ESTACIONAMENTO + "'%" + valorPesquisa + "%')";
			
			pstmt	=	conn.prepareStatement(sql);	
			rs		=	pstmt.executeQuery();

			if(rs.next())
				return new Coordenadas(rs.getString("LATITUDE"), rs.getString("LONGITUDE"));
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		
		return null;	
	}

	public boolean avaliarEstacionamento(EstacionamentoBean estac) {
		
		Connection conn				=	null;
		PreparedStatement pstmt		=	null;
		
		try {
			conn = getMyqslConnection();
			
			pstmt =	conn.prepareStatement(" update estacionamento set avaliacao = (avaliacao + ?) where id = ? ");	
			pstmt.setInt(1, estac.getAvaliacao());
			pstmt.setInt(2, estac.getId());
			if(pstmt.executeUpdate() > 0)
				return true;
			
		} catch (Exception e) {
			return false;
		} finally {
			close(conn, pstmt, null);
		}
		return false;
	}

	public List<EstacionamentoBean> listaTodosAdmin(int idAdministrador) {
		
		Connection conn				=	null;
		PreparedStatement pstmt		=	null;
		ResultSet rs				=	null;
		StringBuilder sql			=  	new StringBuilder(LISTAR_TODOS_ESTACIONAMENTOS);
		List<EstacionamentoBean> listaEstacionamentoBean =	null;

		try {

			conn	=	getMyqslConnection();
			
			sql.append(" AND u.id = ? ");
			sql.append(" ORDER BY e.avaliacao DESC ");
			
			pstmt	=	conn.prepareStatement(sql.toString());
			pstmt.setInt(1, idAdministrador);
			
			rs		=	pstmt.executeQuery();
			listaEstacionamentoBean 		=	new ArrayList<EstacionamentoBean>();

			while(rs.next()) {
				montaObjEstacionamento(rs, listaEstacionamentoBean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return listaEstacionamentoBean;
		
	}

	private void montaObjEstacionamento(ResultSet rs, List<EstacionamentoBean> listaEstacionamentoBean) throws SQLException {
		
		EstacionamentoBean estacionamentoBean		=	new EstacionamentoBean();
		
		estacionamentoBean.setId(rs.getInt("ID"));
		estacionamentoBean.setNomeFantasia(rs.getString("NOME_FANTASIA"));
		estacionamentoBean.setCnpj(rs.getString("CNPJ"));
		estacionamentoBean.setAdministrador(rs.getString("ADMINISTRADOR"));
		StatusBean statusBean = new StatusBean();
		statusBean.setId(rs.getInt("ID_STATUS"));
		statusBean.setNome(rs.getString("STATUS"));
		estacionamentoBean.setStatusBean(statusBean);
		EnderecoBean end = new EnderecoBean();
		end.setNomeLogradouro(rs.getString("NOME_LOGRADOURO"));
		end.setNumero(rs.getInt("NUMERO"));
		end.setBairroBean(new BairroBean(rs.getInt("ID_BAIRRO"), rs.getString("BAIRRO")));
		end.setCidadeBean(new CidadeBean(rs.getInt("ID_CIDADE"), rs.getString("CIDADE")));
		end.setCoordenadas(new Coordenadas(rs.getString("LATITUDE"), rs.getString("LONGITUDE")));
		estacionamentoBean.setEnderecoBean(end);
		estacionamentoBean.setAvaliacao(rs.getInt("AVALIACAO"));
		estacionamentoBean.setTiposPagamentos(new TipoPagamentoDAO().buscaTiposPorEstacionamento(rs.getInt("ID")));
		estacionamentoBean.setTiposVaga(new VagasDAO().listaInformacoes(rs.getInt("ID")));
		
		listaEstacionamentoBean.add(estacionamentoBean);
	}

}
