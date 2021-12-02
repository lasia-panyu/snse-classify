package edu.neu.weibo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;

import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Test {
	public static void main(String[] args) throws Exception {
//		Pattern pattern = Pattern.compile("(|原图 )赞\\[\\d*\\] 转发\\[\\d*\\] 评论\\[\\d*\\] 收藏 \\d分钟前 来自.*");
//		Pattern patternZ = Pattern.compile("\\d{14} \\S* 转发了");
//		Pattern patternY = Pattern.compile("\\d{14} \\S*:((\\S|\\s)*)");
//		Pattern patternZR = Pattern.compile("转发理由:((\\S|\\s)*)(\\/\\/\\@){1}");
//		Pattern patternYR = Pattern.compile("\\d{14} \\S*:((\\S|\\s)*)\\s*(原图|)赞\\[\\d*\\] 转发\\[\\d*\\] 评论\\[\\d*\\] 收藏 \\d分钟前 来自.*");
//		Matcher matcher;
//		String weibo = "20160602014032 高殿L 转发了 水瓶座-小宝 的微博:水瓶座的你是这样的吗？1、手机不离身，睡觉不关机；2、对待不同的人有不同的性格；3、从小懂得很多道理，但知行往往难以合一；4、有时候很神经，有时候很镇静；5、会因为别人一句话伤心，但不会被发现；6、很会安慰别人，却不会安慰自己；7、会经常怀念从前；8、有时会笑的没心没肺，有时却很沉默。  原图 赞[3] 原文转发[2] 原文评论[1] 转发理由:精神病杀人是不是不犯法  赞[0] 转发[0] 评论[0] 收藏 1分钟前 来自iPhone 6 Plus";
//		File file = new File("C:\\Users\\Pan\\Desktop\\SNSE\\新浪微博数据\\S0602.txt");
//		/* 抽取微博 */
//		BufferedReader reader = new BufferedReader(new FileReader(file));
//		matcher = patternZ.matcher(weibo);
//		// syso
//		// while (matcher.find()) {
//		// System.out.println(1);
//		// System.out.println(matcher.group());
//		// }
//		int a=0;
//		int b=0;
//		int c=0;
//		String line = "";
//		while ((line = reader.readLine()) != null) {
//			a++;
//			matcher = patternZ.matcher(line);
//			if (matcher.find()) {
//				b++;
//				matcher = patternZR.matcher(line);
//				if(matcher.find())
//				    System.out.println(matcher.group(1).split("//@")[0]);
//			} else {
//				c++;
//				matcher = patternYR.matcher(line);
//				if (matcher.find()){
//					String tmpWeibo=matcher.group(1);
//					tmpWeibo=tmpWeibo.replaceAll("原图","");
//					tmpWeibo=tmpWeibo.replaceAll("[组图共\\d张]","");
//					tmpWeibo=tmpWeibo.replaceAll("沈阳·\\S*","");
//					tmpWeibo=tmpWeibo.replaceAll("显示地图","");
//					System.out.println(tmpWeibo);
//				}
//					
//			}
//		}
//		System.out.println(a);
//		System.out.println(b);
//		System.out.println(c);
//	}
//		SNSEDic.initDic();
//		for(String str:SNSEDic.positiveWordList){
//		//	System.out.println("表扬".equals(str));
//		}
//	List<Word> words = WordSegmenter.seg("我TM差点摔手机，Skye 被感染了，这还怎么看啊，编剧赶紧洗白");
//		//System.out.println(words);
//		for (int i = 0; i < words.size(); i++) {
//			//String str=words.get(i).getText();
//		//	System.out.println(strhashCode());
//			//System.out.println("表扬".hashCode());
//			System.out.println(words.get(i).getText()+SNSEDic.negtiveWordList.contains(words.get(i).getText()));
//		//	System.out.println("表扬".getBytes());
//			System.out.println(words.get(i).getText()+SNSEDic.positiveWordList.contains(words.get(i).getText()));f
		 InputStream naiveAn = new FileInputStream("C:\\Users\\Pan\\Desktop\\科目对应账号v1.0.xlsx");	
		 OutputStream naiveAn1 = new FileOutputStream("C:\\Users\\Pan\\Desktop\\科目对应账号v1.0 - 1.xlsx");	
		 InputStream in_mst = new FileInputStream("C:\\Users\\Pan\\Desktop\\in_mst.xlsx");	
		 InputStream dc_mst = new FileInputStream("C:\\Users\\Pan\\Desktop\\dc_mst.xlsx");	
			
          XSSFWorkbook xssfWorkbook = new XSSFWorkbook(naiveAn);
          XSSFWorkbook xssfWorkbook1 = new XSSFWorkbook(in_mst);
          XSSFWorkbook xssfWorkbook2 = new XSSFWorkbook(dc_mst);
		 //File file = new File("C:\\Users\\Pan\\Desktop\\SNSE\\dic\\" + "bankc.txt");
	     //	 File file = new File("C:\\Users\\Pan\\Desktop\\SNSE\\dic\\" + "bankq.txt");
		 WritableWorkbook wwb = Workbook.createWorkbook(naiveAn1);   
		 XSSFSheet  xssfSheet  =  xssfWorkbook.getSheetAt( 0 ); 
		 XSSFSheet  xssfSheet1  =  xssfWorkbook1.getSheetAt( 0 ); 
		 XSSFSheet  xssfSheet2  =  xssfWorkbook2.getSheetAt( 0 ); 
		 
		 WritableSheet ws = wwb.createSheet("Sheet 1",0);   
		
		 int tmp=0;
		 Double c=0.0;
		 String ssss="";
		 //BufferedWriter writer6 = new BufferedWriter(new FileWriter(file));
	     for(int i=2;i<236;i++){
	    	 tmp=0;
	    	 XSSFRow xssfRow = xssfSheet.getRow(i); 
	        String ss=xssfRow.getCell(0).toString().trim();
	    	 
	    	 if(ss.startsWith("待定")||ss.equals(""))
	    		 continue;
	    	 if(ss.contains(".")){
	    		// System.out.println(ss.split("\\.")[0]);
	    		 ss=ss.split("\\.")[0];
	    	 }
	    		;
	    	
	    	 Double a=Double.valueOf(xssfRow.getCell(0).toString().trim());
	      	 //System.out.println(i+"~"+a+"~"+"~"+xssfRow.getCell(0).toString().trim());
	    	 for(int lm1=1;lm1<24821;lm1++){
	    		 XSSFRow xssfRow1 = xssfSheet1.getRow(lm1); 
	    		// String ss1=xssfRow.getCell(6).toString();
	    		// ssss=xssfRow1.getCell(6).toString().trim();
		         c=Double.valueOf(xssfRow1.getCell(6).toString().trim());
	    		 if(c-a==0){
	    			 //System.out.println(lm1+"找到相等的了"+xssfRow1.getCell(6).toString());
	    		 ws.addCell(new Label(3,i,xssfRow1.getCell(1).toString()));
	    	    // wwb.write();   
	    		 ws.addCell(new Label(4,i,xssfRow1.getCell(0).toString())); 
	    	    // wwb.write();   
	    		 tmp=1;
	    		 break;
	    		 }
	    	 }
	    	if(tmp==1){
	    		continue;
	    	}else{
	    		 for(int aaa=1;aaa<126;aaa++){
		    		 XSSFRow xssfRow2 = xssfSheet2.getRow(aaa); 
		    		
		    		  if(xssfRow2==null)
		    			  continue;
			         c=Double.valueOf(xssfRow2.getCell(5).toString().trim());
			        if(c-a==0){
		     			 System.out.println("找到相等的了"+xssfRow2.getCell(5).toString().trim());
		    			 ws.addCell(new Label(3,i,xssfRow2.getCell(1).toString()));
		    		//     wwb.write();   
			    		 ws.addCell(new Label(4,i,xssfRow2.getCell(0).toString())); 
			    	 //    wwb.write();   
		    		 tmp=1;
		    		 break;
		    		 }
	    	}
	    	
        // xssfWorkbook.close();
		}
	    	if(tmp==0){
	    	 ws.addCell(new Label(3,i,"暂无"));
	    //     wwb.write();   
	    	 ws.addCell(new Label(4,i,"暂无"));
	    	}
	    //     wwb.write();   
	    	 }
	     wwb.write();   
	     wwb.close(); 
	     }
	}



