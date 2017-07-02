package business;

import java.util.Date;
import java.util.List;

import bean.RelatorioAluguel;
import dao.RelatorioDAO;

public class RelatorioBusiness {
	
	private static RelatorioBusiness instance;
	
	private RelatorioBusiness(){}

	public static RelatorioBusiness getInstance() {
		if (instance == null)
			return new RelatorioBusiness();
		return instance;
	}

	public List<RelatorioAluguel> buscarInformacoesPor(Date dateIni, Date dateFim, String tipoInformado, int idEstacionamento) {
		
		if (dateIni != null && dateFim != null && tipoInformado != null && !tipoInformado.equals(""))
			return new RelatorioDAO(idEstacionamento).buscarInformacoesPorFiltro(dateIni, dateFim, tipoInformado);
		
		else if (tipoInformado == null || tipoInformado.equals(""))
			return new RelatorioDAO(idEstacionamento).buscarInformacoesPorPeriodo(dateIni, dateFim);
		
		return new RelatorioDAO(idEstacionamento).buscarInformacoesPorTipo(tipoInformado);
	}


}
