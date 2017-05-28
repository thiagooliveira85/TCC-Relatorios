package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.AutenticacaoBean;
import bean.PerfilEnum;
import bean.UsuarioBean;
import db.DB;

public class AutenticacaoDAO extends DB {

	private static final String BUSCA_USUARIO_POR_LOGIN_E_SENHA_INFORMADOS	=	"SELECT ID, NOME, LOGIN, ID_PERFIL FROM USUARIO WHERE LOGIN = ? AND SENHA = ? AND STATUS = 'A' AND ID_PERFIL = ?";
	private static final String BUSCA_USUARIO_POR_LOGIN						=	"SELECT 1 FROM USUARIO WHERE LOGIN = ? AND STATUS = 'A' AND ID_PERFIL = ?";
	private static final String ALTERA_SENHA_USUARIO						=	"UPDATE USUARIO SET SENHA = ? WHERE LOGIN = ? AND STATUS = 'A'";
	
	public UsuarioBean existeUsuarioPorLoginESenhaInformados(AutenticacaoBean entidade) 
	{		
		Connection conn				=	null;
		PreparedStatement pstmt		=	null;
		ResultSet rs				=	null;
		
		UsuarioBean usuarioBean =	null;
		
		try {
			conn	=	DB.getMyqslConnection();
			pstmt	=	conn.prepareStatement(BUSCA_USUARIO_POR_LOGIN_E_SENHA_INFORMADOS);
			pstmt.setString(1, entidade.getLogin());
			pstmt.setString(2, entidade.getSenha());
			pstmt.setInt(3, PerfilEnum.ADMINISTRADOR_ESTACIONAMENTO.getCodigo());
			rs		=	pstmt.executeQuery();
			
			if(rs.next()) {
				usuarioBean		=	new UsuarioBean();
				usuarioBean.setId(rs.getInt("ID"));
				usuarioBean.setNome(rs.getString("NOME"));
				usuarioBean.setSenha(entidade.getSenha());
				usuarioBean.setPerfil(rs.getInt("ID_PERFIL"));
				usuarioBean.setLogin(rs.getString("LOGIN"));
			}
			
		} catch (Exception e) {
			System.out.println("Erro no metodo existeUsuarioPorLoginESenhaInformados. Pilha: " + e.getMessage());
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		
		return usuarioBean;
	}
	
	public boolean redefineSenhaPorLogin(UsuarioBean usuarioBean) {
		Connection conn				=	null;
		PreparedStatement pstmt		=	null;
		PreparedStatement pstmt2	=	null;
		ResultSet rs				=	null;
		
		try {

			conn	=	DB.getMyqslConnection();
			pstmt	=	conn.prepareStatement(BUSCA_USUARIO_POR_LOGIN);
			pstmt.setString(1, usuarioBean.getLogin());
			pstmt.setInt(2, PerfilEnum.ADMINISTRADOR_ESTACIONAMENTO.getCodigo());
			rs		=	pstmt.executeQuery();
			
			if(rs.next()) {
				pstmt2	=	conn.prepareStatement(ALTERA_SENHA_USUARIO);
				pstmt2.setString(1, usuarioBean.getSenha());
				pstmt2.setString(2, usuarioBean.getLogin());
				pstmt2.executeUpdate();
				pstmt2.close();
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e) {
			System.out.println("Erro no metodo redefineSenhaPorLogin. Pilha: " + e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			DB.close(conn, pstmt, rs);
		}
	}
	
}