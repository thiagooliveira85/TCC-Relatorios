package manager;
 
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

import bean.EstacionamentoBean;
import bean.RelatorioAluguel;
import bean.RelatorioPorTipo;
import bean.TipoVaga;
import business.RelatorioBusiness;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

import dao.EstacionamentoDAO;
import dao.RelatorioDAO;
import dao.VagasDAO;
 
 
@ManagedBean
@ViewScoped
public class DataExporterView implements Serializable {
     
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<RelatorioAluguel> relatorios;
	
	private PieChartModel pieModel1;	
	private BarChartModel barModel;	
	private LineChartModel dateModel;
	
	private Date dateIni;
	private Date dateFim;
	
	private String tipoInformado;
	private String placa;
	
	private List<EstacionamentoBean> estacionamentosAdmin;
	private List<EstacionamentoBean> estacionamentos;
	
	private List<TipoVaga> listaTiposVaga;
         
    @PostConstruct
    public void init() {
    	createPieModel1();
    	// TO CAGANDO PRA PEFORMANCE, NECESSÁRIO CONTROLE DE SESSAO E LIMPEZA
    	relatorios = new RelatorioDAO().listaRelatorioAluguel();
    	
    	// BUSCAR O ID DO ADM LOGADO
    	int idAdministrador = 1;
    	estacionamentos			= new EstacionamentoDAO().listaTodos();
    	estacionamentosAdmin	= new EstacionamentoDAO().listaTodosAdmin(idAdministrador);
    	listaTiposVaga			= new VagasDAO().listarTipoVaga();
    }
    
    public void buscarInformacoes(){
    	
    	if ( (dateIni != null && dateFim == null) || (dateFim != null && dateIni == null)){
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Favor, informar o período completo!");		
    		FacesContext.getCurrentInstance().addMessage(null, message);
    		return;
    	}
    	
    	if (dateIni == null && dateFim == null && tipoInformado == null)
    		return;
    	
    	relatorios = RelatorioBusiness.getInstance().buscarInformacoesPor(dateIni, dateFim, tipoInformado);
    }
    
    public void buscarInformacoesPorPlaca(){
    	
    	if ( placa == null){
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Favor, informar a placa do automóvel!", "Favor, informar a placa do automóvel!");		
    		FacesContext.getCurrentInstance().addMessage(null, message);
    		return;
    	}
    	
    	relatorios = new RelatorioDAO().buscarInformacoesPorPlaca(placa);
    }
    
    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "img" + File.separator + "logotipo.png";
        pdf.add(Image.getInstance(logo));
    }

	public List<RelatorioAluguel> getRelatorios() {
		return relatorios;
	}

	public void setRelatorios(List<RelatorioAluguel> relatorios) {
		this.relatorios = relatorios;
	}

	public Date getDateIni() {
		return dateIni;
	}

	public void setDateIni(Date dateIni) {
		this.dateIni = dateIni;
	}

	public Date getDateFim() {
		return dateFim;
	}

	public void setDateFim(Date dateFim) {
		this.dateFim = dateFim;
	}

	public String getTipoInformado() {
		return tipoInformado;
	}

	public void setTipoInformado(String tipoInformado) {
		this.tipoInformado = tipoInformado;
	}

	public List<EstacionamentoBean> getEstacionamentosAdmin() {
		return estacionamentosAdmin;
	}

	public void setEstacionamentosAdmin(List<EstacionamentoBean> estacionamentosAdmin) {
		this.estacionamentosAdmin = estacionamentosAdmin;
	}

	public List<EstacionamentoBean> getEstacionamentos() {
		return estacionamentos;
	}

	public void setEstacionamentos(List<EstacionamentoBean> estacionamentos) {
		this.estacionamentos = estacionamentos;
	}
	
	public PieChartModel getPieModel1() {
	return pieModel1;
	}
	
	public void setPieModel1(PieChartModel pieModel1) {
		this.pieModel1 = pieModel1;
	}
	
	public BarChartModel getBarModel() {
		return barModel;
	}
	
	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}
	
	public LineChartModel getDateModel() {
		return dateModel;
	}
	
	public void setDateModel(LineChartModel dateModel) {
		this.dateModel = dateModel;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public List<TipoVaga> getListaTiposVaga() {
		return listaTiposVaga;
	}

	public void setListaTiposVaga(List<TipoVaga> listaTiposVaga) {
		this.listaTiposVaga = listaTiposVaga;
	}
	
	private void createPieModel1() {
		pieModel1 = new PieChartModel();

		for (RelatorioPorTipo rel : new RelatorioDAO().listaRelatorioPorTipo())
			pieModel1.set(rel.getTipo(), rel.getValorTotal());

		pieModel1.setTitle("Gráfico tipo de vaga por preço");
		pieModel1.setLegendPosition("w");
	}
/*
private void createBarModel() {
    barModel = initBarModel();
     
    barModel.setTitle("Gráfico tipo de vaga por tempo");
    barModel.setLegendPosition("ne");
     
    Axis xAxis = barModel.getAxis(AxisType.X);
    xAxis.setLabel("Tipo de vagas");
     
    Axis yAxis = barModel.getAxis(AxisType.Y);
    yAxis.setLabel("Preços");
    yAxis.setMin(0);
    yAxis.setMax(50);
}

private BarChartModel initBarModel() {
    BarChartModel model = new BarChartModel();
    
    String[][] valores = new String[3][4];
    
    // PASSEIO
    valores[0][0] = "Passeio";
    valores[0][1] = "25";
    valores[0][2] = "10";
    valores[0][3] = "0";
    
    // MOTO
    valores[1][0] = "Moto";
    valores[1][1] = "0";
    valores[1][2] = "0";
    valores[1][3] = "13";
    
    // CAMINHÃO
    valores[2][0] = "Caminhão";
    valores[2][1] = "0";
    valores[2][2] = "0";
    valores[2][3] = "25";

    ChartSeries passeio = new ChartSeries();
    passeio.setLabel(valores[0][0]);
    passeio.set("JAN", Double.parseDouble(valores[0][1]));
    passeio.set("FEV", Double.parseDouble(valores[0][2]));
    passeio.set("MAR", Double.parseDouble(valores[0][3]));
    
    ChartSeries moto = new ChartSeries();
    moto.setLabel(valores[1][0]);
    moto.set("JAN", Double.parseDouble(valores[1][1]));
    moto.set("FEV", Double.parseDouble(valores[1][2]));
    moto.set("MAR", Double.parseDouble(valores[1][3]));
    
    ChartSeries cam = new ChartSeries();
    cam.setLabel(valores[2][0]);
    cam.set("JAN", Double.parseDouble(valores[2][1]));
    cam.set("FEV", Double.parseDouble(valores[2][2]));
    cam.set("MAR", Double.parseDouble(valores[2][3]));

    List<Tipo> lst = new ArrayList<Tipo>();
    
    Tipo tipo1 = new Tipo();
    tipo1.setNome("PASSEIO");
    tipo1.setMeses(Arrays.asList(new Mes("JAN", 25.0), new Mes("FEV", 10.0), new Mes("MAR", 0.0)));
    
    Tipo tipo2 = new Tipo();
    tipo2.setNome("MOTO");
    tipo2.setMeses(Arrays.asList(new Mes("JAN", 0.0), new Mes("FEV", 0.0), new Mes("MAR", 13.0)));
    
    Tipo tipo3 = new Tipo();
    tipo3.setNome("CAMINHAO");
    tipo3.setMeses(Arrays.asList(new Mes("JAN", 0.0), new Mes("FEV", 0.0), new Mes("MAR", 25.0)));
	
    lst.add(tipo1);
    lst.add(tipo2);
    lst.add(tipo3);
	
	for (Tipo t : lst) {
		ChartSeries tipoChar = new ChartSeries();
		tipoChar.setLabel(t.getNome());
		
		for (Mes m : t.getMeses()) {
			tipoChar.set(m.getNome(), m.getValor());
		}
		
		model.addSeries(tipoChar);
	}
     
    return model;
}

private void createDateModel() {
    dateModel = new LineChartModel();
   
    List<RelatorioAluguel> lst = new RelatorioDAO().listaRelatorioAluguel();
    
    LineChartSeries series1 = null;
    series1 = new LineChartSeries();
    series1.setLabel("Tudão");
    for (RelatorioAluguel r : lst) {
    	series1.set(r.getHoraEntrada(), r.getValorCobrado());
	}
    

     series1.set("2017-01-20", 15);
    series1.set("2017-01-20", 10);
    series1.set("2017-01-20", 10);
    series1.set("2017-01-20", 5);
    series1.set("2017-01-20", 8);
    series1.set("2017-01-20", 25);
    
    LineChartSeries series2 = new LineChartSeries();
    series2.setLabel("Series 2");

    series2.set("2014-01-01", 32);
    series2.set("2014-01-06", 73);
    series2.set("2014-01-12", 24);
    series2.set("2014-01-18", 12);
    series2.set("2014-01-24", 74);
    series2.set("2014-01-30", 62);
    
    LineChartSeries series3 = new LineChartSeries();
    series2.setLabel("Series 2");

    series3.set("2014-01-01", 46);
    series3.set("2014-01-06", 50);
    series3.set("2014-01-12", 88);
    

    dateModel.addSeries(series1);
    //dateModel.addSeries(series2);
    //dateModel.addSeries(series3);
     
    dateModel.setTitle("Gráfico");
    dateModel.setZoom(true);
    dateModel.getAxis(AxisType.Y).setLabel("Preços");
    DateAxis axis = new DateAxis("Tempo");
    axis.setTickAngle(-100);
    axis.setMin("2017-01-20");
    axis.setMax("2017-03-26");
    axis.setTickFormat("%b %#d, %y");
     
    dateModel.getAxes().put(AxisType.X, axis);
}*/

}