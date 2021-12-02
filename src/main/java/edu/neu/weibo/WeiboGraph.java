package edu.neu.weibo;

import java.awt.BasicStroke;  
import java.awt.Color;  
import java.awt.Polygon;  
import java.awt.Shape;  
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.axis.NumberAxis;  
import org.jfree.chart.plot.CategoryPlot;  
import org.jfree.chart.plot.DefaultDrawingSupplier;  
import org.jfree.chart.plot.DrawingSupplier;  
import org.jfree.chart.plot.PlotOrientation;  
import org.jfree.chart.renderer.category.LineAndShapeRenderer;  
import org.jfree.data.category.CategoryDataset;  
import org.jfree.data.category.DefaultCategoryDataset;  
import org.jfree.ui.ApplicationFrame;  
import org.jfree.ui.RefineryUtilities;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;  
  
/**    
 * A line chart demo showing the use of a custom drawing supplier.    
 *    
 */  
public class WeiboGraph extends ApplicationFrame  
{  
  
    /**    
     * Creates a new demo.    
     *    
     * @param title the frame title.    
     * @throws IOException 
     * @throws NumberFormatException 
     * @throws BiffException 
     */  
    public WeiboGraph(final String title) throws NumberFormatException, IOException, BiffException  
    {  
        super(title);  
        final CategoryDataset dataset = createDataset();  
        final JFreeChart chart = createChart(dataset);  
        final ChartPanel chartPanel = new ChartPanel(chart);  
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));  
        setContentPane(chartPanel);  
  
    }  
  
    /**    
     * Creates a sample dataset.    
     *    
     * @return a sample dataset.    
     * @throws IOException 
     * @throws NumberFormatException 
     * @throws BiffException 
     */  
    private CategoryDataset createDataset() throws NumberFormatException, IOException, BiffException  
    {  
  
        // row keys...      
        final String series1 = "微博正负比";  
        final String series2 = "银行存款";  
        final String series3 = "Third";  
  
        // column keys...      
        final String type1 = "Type 1";  
        final String type2 = "Type 2";  
        final String type3 = "Type 3";  
        final String type4 = "Type 4";  
        final String type5 = "Type 5";  
        final String type6 = "Type 6";  
        final String type7 = "Type 7";  
        final String type8 = "Type 8";  
    	final String path = "C:\\Users\\Pan\\Desktop\\SNSE\\dic\\";
        // create the dataset...      
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
        File file=new File(path + "pnWriboDic" + ".txt");
        String line="";
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		double d1=0.0;
		double d2=0.0;
		int i=0;
		File file1=new File(path + "bankc" + ".txt");
		BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(file1), "UTF-8"));
		while ((line = reader1.readLine()) != null) {
			d1=Double.valueOf(line.split("~")[0]);
			d2=Double.valueOf(line.split("~")[1]);
			 i++;
			 dataset.addValue(d1/1000000000, series2,String.valueOf(i));
			 dataset.addValue(d2/1000000000, series3,String.valueOf(i));
			 //dataset.addValue(d1, series3,String.valueOf(i));
		}
		i=0;
		while ((line = reader.readLine()) != null) {
			d1=Double.valueOf(line.split("~")[0]);
			d2=Double.valueOf(line.split("~")[1]);
			System.out.println(d1/(d2+d1));
			 i++;
			 dataset.addValue(d1/d2, series1,String.valueOf(i));
		}
//        dataset.addValue(1.0, series1, type1);  
//        dataset.addValue(4.0, series1, type2);  
//        dataset.addValue(3.0, series1, type3);  
//        dataset.addValue(5.0, series1, type4);  
//        dataset.addValue(5.0, series1, type5);  
//        dataset.addValue(7.0, series1, type6);  
//        dataset.addValue(7.0, series1, type7);  
//        dataset.addValue(8.0, series1, type8);  
//  
//        dataset.addValue(5.0, series2, type1);  
//        dataset.addValue(7.0, series2, type2);  
//        dataset.addValue(6.0, series2, type3);  
//        dataset.addValue(8.0, series2, type4);  
//        dataset.addValue(4.0, series2, type5);  
//        dataset.addValue(4.0, series2, type6);  
//        dataset.addValue(2.0, series2, type7);  
//        dataset.addValue(1.0, series2, type8);  
//  
//        dataset.addValue(4.0, series3, type1);  
//        dataset.addValue(3.0, series3, type2);  
//        dataset.addValue(2.0, series3, type3);  
//        dataset.addValue(3.0, series3, type4);  
//        dataset.addValue(6.0, series3, type5);  
//        dataset.addValue(3.0, series3, type6);  
//        dataset.addValue(4.0, series3, type7);  
//        dataset.addValue(3.0, series3, type8);  
  
        return dataset;  
  
    }  
  
    /**    
     * Creates a sample chart.    
     *    
     * @param dataset the dataset.    
     *    
     * @return a chart.    
     */  
    private JFreeChart createChart(final CategoryDataset dataset)  
    {  
  
        final JFreeChart chart = ChartFactory.createLineChart(  
                "阜新银行 社交数据拟合银行市场",// chart title      
                "日期", // domain axis label      
                "量化值", // range axis label      
                dataset, // data      
                PlotOrientation.VERTICAL, // orientation      
                true, // include legend      
                true, // tooltips      
                false // urls      
                );  
  
        // final StandardLegend legend = (StandardLegend) chart.getLegend();      
        // legend.setDisplaySeriesShapes(true);      
  
        final Shape[] shapes = new Shape[3];  
        int[] xpoints;  
        int[] ypoints;  
  
        // right-pointing triangle      
        xpoints = new int[] { -3, 3, -3 };  
        ypoints = new int[] { -3, 0, 3 };  
        shapes[0] = new Polygon(xpoints, ypoints, 3);  
  
        // vertical rectangle      
        shapes[1] = new Rectangle2D.Double(-2, -3, 3, 6);  
  
        // left-pointing triangle      
        xpoints = new int[] { -3, 3, 3 };  
        ypoints = new int[] { 0, -3, 3 };  
        shapes[2] = new Polygon(xpoints, ypoints, 3);  
  
        final DrawingSupplier supplier = new DefaultDrawingSupplier(  
                DefaultDrawingSupplier.DEFAULT_PAINT_SEQUENCE,  
                DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,  
                DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,  
                DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, shapes);  
        final CategoryPlot plot = chart.getCategoryPlot();  
        plot.setDrawingSupplier(supplier);  
  
        chart.setBackgroundPaint(Color.green);  
  
        // set the stroke for each series...      
        plot.getRenderer().setSeriesStroke(  
                0,  
                new BasicStroke(2.0f, BasicStroke.CAP_ROUND,  
                        BasicStroke.JOIN_ROUND, 1.0f,  
                        new float[] { 10.0f, 6.0f }, 0.0f));  
        plot.getRenderer().setSeriesStroke(  
                1,  
                new BasicStroke(2.0f, BasicStroke.CAP_ROUND,  
                        BasicStroke.JOIN_ROUND, 1.0f,  
                        new float[] { 6.0f, 6.0f }, 0.0f));  
        plot.getRenderer().setSeriesStroke(  
                2,  
                new BasicStroke(2.0f, BasicStroke.CAP_ROUND,  
                        BasicStroke.JOIN_ROUND, 1.0f,  
                        new float[] { 2.0f, 6.0f }, 0.0f));  
  
        // customise the renderer...      
        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot  
                .getRenderer();  
        // renderer.setDrawShapes(true);      
        renderer.setItemLabelsVisible(true);  
        // renderer.setLabelGenerator(new StandardCategoryLabelGenerator());      
  
        // customise the range axis...      
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();  
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());  
        rangeAxis.setAutoRangeIncludesZero(false);  
        rangeAxis.setUpperMargin(0.12);  
  
        return chart;  
  
    }  
  
    // ****************************************************************************      
    // * JFREECHART DEVELOPER GUIDE *      
    // * The JFreeChart Developer Guide, written by David Gilbert, is available *      
    // * to purchase from Object Refinery Limited: *      
    // * *      
    // * http://www.object-refinery.com/jfreechart/guide.html *      
    // * *      
    // * Sales are used to provide funding for the JFreeChart project - please *      
    // * support us so that we can continue developing free software. *      
    // ****************************************************************************      
  
    /**    
     * Starting point for the demonstration application.    
     *    
     * @param args ignored.    
     * @throws IOException 
     * @throws NumberFormatException 
     * @throws BiffException 
     */  
    public static void main(final String[] args) throws NumberFormatException, IOException, BiffException  
    {  
  
        final WeiboGraph demo = new WeiboGraph("微博正负比");  
        demo.pack();  
        RefineryUtilities.centerFrameOnScreen(demo);  
        demo.setVisible(true);  
  
    }  
  
}  