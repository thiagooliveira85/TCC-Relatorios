package business;

import java.util.Date;
import java.util.List;

import dao.RelatorioDAO;
import manager.RelatorioAluguel;

public class RelatorioBusiness {
	
	private static RelatorioBusiness instance;
	
	private RelatorioBusiness(){}

	public static RelatorioBusiness getInstance() {
		if (instance == null)
			return new RelatorioBusiness();
		return instance;
	}

	public List<RelatorioAluguel> buscarInformacoesPor(Date dateIni, Date dateFim, String tipoInformado) {
		
		if (dateIni != null && dateFim != null && tipoInformado != null && !tipoInformado.equals(""))
			return new RelatorioDAO().buscarInformacoesPorFiltro(dateIni, dateFim, tipoInformado);
		
		else if (tipoInformado == null || tipoInformado.equals(""))
			return new RelatorioDAO().buscarInformacoesPorPeriodo(dateIni, dateFim);
		
		return new RelatorioDAO().buscarInformacoesPorTipo(tipoInformado);
	}


}
