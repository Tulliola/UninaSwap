package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset;

import dto.Offerta;
import dto.OffertaAcquisto;
import dto.OffertaScambio;
import dto.ProfiloUtente;
import utilities.MyJLabel;
import utilities.MyJPanel; 

public class PanelVisualizzaReport extends MyJPanel {
   
	private static final long serialVersionUID = 1L;
	
	//Panels 
	private MyJPanel panelRiepilogo = new MyJPanel();
	private MyJPanel panelReportOfferteAcquisto;
	private MyJPanel panelReportOfferteScambio;
	private MyJPanel panelReportOfferteRegalo;
	private ChartPanel chartPanelPerLineChart;
	private ChartPanel chartPanelPerBarChart;

	//JFreeCharts
	private JFreeChart graficoReportOfferte;
	private JFreeChart graficoVariazioneMediaOfferte;

	private int numOfferteAcquisto;
	private int numOfferteAcquistoAccettate;
	private double minimaOfferta;
	private double massimaOfferta;
	private double mediaOfferta;
	private int numOfferteAcquistoRifiutate;
	private int numOfferteAcquistoInAttesa;
	private int numOfferteAcquistoRitirate;
	private ArrayList<Double> valoriOfferteAccettate = new ArrayList<Double>();

	private int numOfferteScambio;
	private int numOfferteScambioAccettate;
	private int numOfferteScambioRifiutate;
	private int numOfferteScambioInAttesa;
	private int numOfferteScambioRitirate;

	private int numOfferteRegalo;
	private int numOfferteRegaloAccettate;
	private int numOfferteRegaloRifiutate;
	private int numOfferteRegaloInAttesa;
	private int numOfferteRegaloRitirate;

	
	//Variabili per i grafici
	
	
	public PanelVisualizzaReport(ProfiloUtente utenteLoggato) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Color.white);
		
		this.filtraInformazioni(utenteLoggato);
		
		panelRiepilogo.setBackground(Color.white);
		panelRiepilogo.setLayout(new BoxLayout(panelRiepilogo, BoxLayout.Y_AXIS));
		panelRiepilogo.add(Box.createRigidArea(new Dimension(0, 40)));
		panelRiepilogo.add(this.creaPanelReportOfferteAcquisto());
		panelRiepilogo.add(Box.createRigidArea(new Dimension(0, 40)));
		panelRiepilogo.add(this.creaPanelReportOfferteScambio());
		panelRiepilogo.add(Box.createRigidArea(new Dimension(0, 40)));
		panelRiepilogo.add(this.creaPanelReportOfferteRegalo());
		panelRiepilogo.add(Box.createRigidArea(new Dimension(0, 40)));

		ChartPanel panelPerReport = new ChartPanel(this.creaReportGrafico());
		panelRiepilogo.add(panelPerReport);
		
		JScrollPane scrollPane = new JScrollPane(panelRiepilogo);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		
		this.add(scrollPane);
	}
	
	private MyJPanel creaPanelReportOfferteAcquisto() {
		panelReportOfferteAcquisto = new MyJPanel();
		panelReportOfferteAcquisto.setLayout(new BoxLayout(panelReportOfferteAcquisto, BoxLayout.Y_AXIS));
		panelReportOfferteAcquisto.setBackground(Color.white);
		panelReportOfferteAcquisto.setPreferredSize(new Dimension(1000, 500));
		panelReportOfferteAcquisto.setMaximumSize(new Dimension(1000, 500));
		panelReportOfferteAcquisto.setAlignmentX(CENTER_ALIGNMENT);
		
		MyJLabel lblTitoloSezione = new MyJLabel("Riepilogo delle tue offerte di acquisto");
		lblTitoloSezione.aggiungiImmagineScalata("images/iconaAnnuncioVenditaColored.png", 50, 50, false);
		lblTitoloSezione.setFont(new Font("Ubuntu Sans", Font.BOLD, 25));
		lblTitoloSezione.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJLabel lblOfferteInviate = new MyJLabel("Hai inviato un totale di " + this.numOfferteAcquisto + " offerte di acquisto!");
		lblOfferteInviate.setFont(new Font("Ubuntu Sans", Font.PLAIN, 18));
		
		MyJLabel lblOfferteAccettate = new MyJLabel("Di queste, ben " + this.numOfferteAcquistoAccettate + " ti sono state accettate!");
		lblOfferteAccettate.setFont(new Font("Ubuntu Sans", Font.PLAIN, 18));
		
		MyJLabel lblValoreMinimo = new MyJLabel("L'offerta minima che hai fatto e che ti è stata accettata è stata di " + this.minimaOfferta + " !");
		lblValoreMinimo.setFont(new Font("Ubuntu Sans", Font.PLAIN, 18));
		
		MyJLabel lblValoreMassimo = new MyJLabel("L'offerta massima che hai fatto e che ti è stata accettata è stata di " + this.massimaOfferta + " !");
		lblValoreMassimo.setFont(new Font("Ubuntu Sans", Font.PLAIN, 18));
		
		MyJLabel lblValoreMedio = new MyJLabel("La media di tutte le offerte di acquisto che hai inviato e che ti sono state accettate è pari a " + this.mediaOfferta + " !");
		lblValoreMedio.setFont(new Font("Ubuntu Sans", Font.PLAIN, 18));
		
		panelReportOfferteAcquisto.add(lblTitoloSezione);
		panelReportOfferteAcquisto.add(lblOfferteInviate);
		panelReportOfferteAcquisto.add(lblOfferteAccettate);
		panelReportOfferteAcquisto.add(lblValoreMinimo);
		panelReportOfferteAcquisto.add(lblValoreMassimo);
		panelReportOfferteAcquisto.add(lblValoreMedio);
		panelReportOfferteAcquisto.add(Box.createRigidArea(new Dimension(0, 20)));
		
		chartPanelPerLineChart = new ChartPanel(this.creaReportGraficoOfferteAcquisto());
		panelReportOfferteAcquisto.add(chartPanelPerLineChart);
		
		return panelReportOfferteAcquisto;
	}
	
	private JFreeChart creaReportGraficoOfferteAcquisto() {
		graficoVariazioneMediaOfferte = ChartFactory.createLineChart("Evoluzione della media delle tue offerte di acquisto accettate", 
				"Offerte di acquisto accettate",
				"Valore", 
				creaDataSetPerLineChart(), 
				PlotOrientation.VERTICAL, 
				true, false, false);
		
		this.impostaSettingsPerLineChart();
		
		return graficoVariazioneMediaOfferte;
	}
	
	private CategoryDataset creaDataSetPerLineChart() {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
			
		for(Double valoreAttuale : valoriOfferteAccettate)
			dataSet.addValue(valoreAttuale, "Offerte", String.valueOf(valoreAttuale));
		
		return dataSet;
	}
	
	private void impostaSettingsPerLineChart() {
		CategoryPlot plotLineChart = graficoVariazioneMediaOfferte.getCategoryPlot();
		plotLineChart.getDomainAxis().setTickLabelsVisible(false);
		plotLineChart.getDomainAxis().setTickMarksVisible(false);
		plotLineChart.setBackgroundPaint(uninaLightColor);
	
		LineAndShapeRenderer lineChartRenderer = (LineAndShapeRenderer) plotLineChart.getRenderer();
		lineChartRenderer.setSeriesPaint(0, uninaColor);
		lineChartRenderer.setSeriesShapesVisible(0, true);
		lineChartRenderer.setSeriesLinesVisible(0, true);
		lineChartRenderer.setSeriesShape(0, new Ellipse2D.Double(-3, -3, 6, 6));

	}
	
	private MyJPanel creaPanelReportOfferteScambio() {
		panelReportOfferteScambio = new MyJPanel();
		panelReportOfferteScambio.setLayout(new BoxLayout(panelReportOfferteScambio, BoxLayout.Y_AXIS));
		panelReportOfferteScambio.setBackground(Color.white);
		panelReportOfferteScambio.setPreferredSize(new Dimension(1000, 100));
		panelReportOfferteScambio.setMaximumSize(new Dimension(1000, 100));
		panelReportOfferteScambio.setAlignmentX(CENTER_ALIGNMENT);

		MyJLabel lblTitoloSezione = new MyJLabel("Riepilogo delle tue offerte di scambio");
		lblTitoloSezione.aggiungiImmagineScalata("images/iconaAnnuncioScambioColored.png", 50, 50, false);
		lblTitoloSezione.setFont(new Font("Ubuntu Sans", Font.BOLD, 25));
		lblTitoloSezione.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJLabel lblOfferteInviate = new MyJLabel("Hai inviato un totale di " + this.numOfferteScambio + " offerte di scambio!");
		lblOfferteInviate.setFont(new Font("Ubuntu Sans", Font.PLAIN, 18));
		
		MyJLabel lblOfferteAccettate = new MyJLabel("Di queste, ben " + this.numOfferteScambioAccettate + " sono state accettate!");
		lblOfferteAccettate.setFont(new Font("Ubuntu Sans", Font.PLAIN, 18));
		
		panelReportOfferteScambio.add(lblTitoloSezione);
		panelReportOfferteScambio.add(lblOfferteInviate);
		panelReportOfferteScambio.add(lblOfferteAccettate);
		
		return panelReportOfferteScambio;
	}
	
	private MyJPanel creaPanelReportOfferteRegalo() {
		panelReportOfferteRegalo = new MyJPanel();
		panelReportOfferteRegalo.setLayout(new BoxLayout(panelReportOfferteRegalo, BoxLayout.Y_AXIS));
		panelReportOfferteRegalo.setBackground(Color.white);
		panelReportOfferteRegalo.setPreferredSize(new Dimension(1000, 100));
		panelReportOfferteRegalo.setMaximumSize(new Dimension(1000, 100));
		panelReportOfferteRegalo.setAlignmentX(CENTER_ALIGNMENT);

		MyJLabel lblTitoloSezione = new MyJLabel("Riepilogo delle tue offerte di Regalo");
		lblTitoloSezione.aggiungiImmagineScalata("images/iconaAnnuncioRegaloColored.png", 50, 50, false);
		lblTitoloSezione.setFont(new Font("Ubuntu Sans", Font.BOLD, 25));
		lblTitoloSezione.setAlignmentX(LEFT_ALIGNMENT);
		
		MyJLabel lblOfferteInviate = new MyJLabel("Hai inviato un totale di " + this.numOfferteRegalo + " offerte di regalo!");
		lblOfferteInviate.setFont(new Font("Ubuntu Sans", Font.PLAIN, 18));
		
		MyJLabel lblOfferteAccettate = new MyJLabel("Di queste, ben " + this.numOfferteRegaloAccettate + " ti sono state accettate!");
		lblOfferteAccettate.setFont(new Font("Ubuntu Sans", Font.PLAIN, 18));
		
		panelReportOfferteRegalo.add(lblTitoloSezione);
		panelReportOfferteRegalo.add(lblOfferteInviate);
		panelReportOfferteRegalo.add(lblOfferteAccettate);
		
		return panelReportOfferteRegalo;
	}
	
	private JFreeChart creaReportGrafico() {
		graficoReportOfferte = ChartFactory.createBarChart("Un report grafico delle tue offerte",
				"Tipo offerta",
				"Numero di offerte",
				this.creaDataSetPerBarChart(),
				PlotOrientation.VERTICAL,
				true, false, false);
		
		this.impostaSettingsPerBarChart();
		
		return graficoReportOfferte;
	}
	
	private CategoryDataset creaDataSetPerBarChart() {
		//ColumnKeys
		String colKeyOfferteScambio = "Offerte di scambio";
		String colKeyOfferteVendita = "Offerte di vendita";
		String colKeyOfferteRegalo = "Offerte di regalo";
		
		//RowKeys
		String rowKeyOfferteAccettate = "Offerte accettate";
		String rowKeyOfferteInAttesa = "Offerte in attesa";
		String rowKeyOfferteRifiutate = "Offerte rifiutate";
		String rowKeyOfferteRitirate = "Offerte ritirate";

	    DefaultCategoryDataset dataSet = new DefaultCategoryDataset( );  
	    
        dataSet.addValue(this.numOfferteScambioAccettate , rowKeyOfferteAccettate , colKeyOfferteScambio );        
        dataSet.addValue(this.numOfferteAcquistoAccettate, rowKeyOfferteAccettate , colKeyOfferteVendita );        
        dataSet.addValue(this.numOfferteRegaloAccettate, rowKeyOfferteAccettate , colKeyOfferteRegalo ); 

        dataSet.addValue(this.numOfferteScambioInAttesa, rowKeyOfferteInAttesa , colKeyOfferteScambio );        
        dataSet.addValue(this.numOfferteAcquistoInAttesa, rowKeyOfferteInAttesa , colKeyOfferteVendita );       
        dataSet.addValue(this.numOfferteRegaloInAttesa, rowKeyOfferteInAttesa , colKeyOfferteRegalo );        	
      
        dataSet.addValue(this.numOfferteScambioRifiutate, rowKeyOfferteRifiutate , colKeyOfferteScambio );        
        dataSet.addValue(this.numOfferteAcquistoRifiutate, rowKeyOfferteRifiutate, colKeyOfferteVendita );        
        dataSet.addValue(this.numOfferteRegaloRifiutate, rowKeyOfferteRifiutate , colKeyOfferteRegalo ); 
      
        dataSet.addValue(this.numOfferteScambioRitirate, rowKeyOfferteRitirate, colKeyOfferteScambio );        
        dataSet.addValue(this.numOfferteAcquistoRitirate, rowKeyOfferteRitirate, colKeyOfferteVendita );        
        dataSet.addValue(this.numOfferteRegaloRitirate, rowKeyOfferteRitirate , colKeyOfferteRegalo );  
	
	    return dataSet;
	}
	
	private void impostaSettingsPerBarChart() {
		CategoryPlot plotBarChart = graficoReportOfferte.getCategoryPlot();
		plotBarChart.setBackgroundPaint(uninaLightColor);

		BarRenderer barRenderedBarChart = (BarRenderer) plotBarChart.getRenderer();
		barRenderedBarChart.setShadowVisible(true);
	    barRenderedBarChart.setBarPainter(new StandardBarPainter());
	    //Nell'ordine: offerte accettate, in attesa, rifiutate e rimosse
	    barRenderedBarChart.setSeriesPaint(0, new Color(76, 175, 80));   
	    barRenderedBarChart.setSeriesPaint(1, new Color(255, 193, 7));  
	    barRenderedBarChart.setSeriesPaint(2, new Color(244, 67, 54));  
		barRenderedBarChart.setSeriesPaint(3, new Color(158, 158, 158));
	}
	
	private void filtraInformazioni(ProfiloUtente utenteLoggato) {
		minimaOfferta = 0;
		massimaOfferta = 0;
		double prezzoTotaleAccettato = 0;
		
		for(Offerta offerta : utenteLoggato.getOfferteUtente()) {
			if(offerta instanceof OffertaAcquisto) {
				numOfferteAcquisto++;
				if(offerta.isAccettata()) {
					numOfferteAcquistoAccettate++;
					
					double prezzoOfferto = offerta.getPrezzoOfferto();
					valoriOfferteAccettate.add(prezzoOfferto);
					
					if(prezzoOfferto < minimaOfferta || minimaOfferta == 0)
						minimaOfferta = prezzoOfferto;
					if(prezzoOfferto > massimaOfferta)
						massimaOfferta = prezzoOfferto;
					prezzoTotaleAccettato += prezzoOfferto;
				}
				else if(offerta.isRifiutata())
					numOfferteAcquistoRifiutate++;
				else if(offerta.isInAttesa())
					numOfferteAcquistoInAttesa++;
				else if(offerta.isRitirata())
					numOfferteAcquistoRitirate++;
			}
			else if(offerta instanceof OffertaScambio) {
				numOfferteScambio++;
				if(offerta.isAccettata())
					numOfferteScambioAccettate++;
				else if(offerta.isRifiutata())
					numOfferteScambioRifiutate++;
				else if(offerta.isInAttesa())
					numOfferteScambioInAttesa++;
				else if(offerta.isRitirata())
					numOfferteScambioRitirate++;
			}
			else {
				numOfferteRegalo++;
				if(offerta.isAccettata())
					numOfferteRegaloAccettate++;
				else if(offerta.isRifiutata())
					numOfferteRegaloRifiutate++;
				else if(offerta.isInAttesa())
					numOfferteRegaloInAttesa++;
				else if(offerta.isRitirata())
					numOfferteRegaloRitirate++;
			}
		}
		
		mediaOfferta = prezzoTotaleAccettato / numOfferteAcquistoAccettate;
	}
}

