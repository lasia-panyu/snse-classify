package edu.neu.weibo;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
/**
 * 
 * @author Pan
 * @deprecated 从html版本中获取数据
 */
public class WeiboNewCreate {
	static Map<String, Integer> emotionMap = new HashMap<String, Integer>();
	static Pattern pattern = Pattern.compile("\\[[^\\]]*\\]");
	static Pattern patternO = Pattern.compile("\\d{10}\\~[^~]*\\~((\\S|\\s)*)\\~(\\d*\\~){3}");
	static Pattern patternZ = Pattern.compile("\\d{14}\\s*\\S*\\s*转发了");
	static Pattern patternY = Pattern.compile("\\d{14}\\s*\\S*:((\\S|\\s)*)");
	// 转发理由:((\S|\s)*)\s*赞\[\d*\]\s转发\[\d*\]\s评论\[\d*\]\s收藏\s\d分钟前\s来自.*
	static Pattern patternZR = Pattern.compile("转发理由:((\\S|\\s)*)(\\/\\/\\@){1}");
	static Pattern patternZR1 = Pattern.compile("转发理由:((\\S|\\s)*)");
	static Pattern patternYR = Pattern.compile(
			"\\d{14}\\s*\\S*:((\\S|\\s)*)\\s*(原图|)赞\\[\\d*\\]\\s转发\\[\\d*\\]\\s评论\\[\\d*\\]\\s收藏\\s\\d分钟前\\s来自.*");

	static int tmpNumber = 0;
	static Matcher matcher;
	static Matcher matcher0;
	static Calendar cal = Calendar.getInstance();
	static SimpleDateFormat sf = new SimpleDateFormat("MMdd");
	static String path = "C://Users//Pan//Desktop//SNSE//新浪微博数据//F";
	static int a = 0;
	static int b = 0;
	static int c = 0;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("deprecation")
		Date date = new Date();
		date.setYear(116);
		;
		date.setMonth(5);
		date.setDate(1);
		cal.setTime(date);
		//40
		for (int i = 0; i < 20; i++) {
			// System.out.println(cal.getTime().getYear());
			File file = new File(path + sf.format(cal.getTime()) + ".txt");
			File file1 = new File("C:\\Users\\Pan\\Desktop\\SNSE\\微博\\F" + sf.format(cal.getTime()) + ".txt");

			/* 抽取微博 */

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1), "UTF-8"));
			cal.add(cal.DATE, 1);
			HttpResponse res = new HttpResponse(null);
			String weiboContent = "";
			String line = "";
		//	if (i < 22) {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				while ((line = reader.readLine()) != null) {
					weiboContent = getContent1(line);
					if (!weiboContent.equals("")) {
						writer.write(weiboContent);
						writer.newLine();
						writer.flush();
					}
				}
				System.out.println("一个文件完成");
				// writer.close();
				// reader.close();
//			} else {
//				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
//				System.out.println(c);
//				String content = "";
//				int temp = 0;
//				while ((line = reader.readLine()) != null) {
//					line = line.replaceAll("\\?", " ");
//					temp++;
//					if (temp % 3 != 0)
//						continue;
//					// res.
//					// res.setHtml(line);
//					Page page = new Page(null, res);
//					Document doc = Jsoup.parse(line, "www.baidu.com");
//					page.setDoc(doc);
//					Elements weibos = page.select("div.c");
//					int size = weibos.size();
//					for (int z = 1; z < size - 2; z++) {
//						content = getContent1("11111111111111 " + weibos.get(z).text());
//						if (!content.equals("")) {
//						writer.write(content);
//						writer.newLine();
//						writer.flush();
//						}
//					}
//					// reader.close();
//					// writer.close();
//					System.out.println("一个文件完成");
//				}
//
//			}
		}
	}

	private static String getContent1(String line) {
		// TODO Auto-generated method stub
		matcher = patternZ.matcher(line);
		String tmpWeibo = "";
		if (matcher.find()) {
			matcher = patternZR.matcher(line);
			if (matcher.find()){
				tmpWeibo = matcher.group(1).split("//@")[0];	
			}else{
				if(line.split("转发理由:").length==2)
				return (line.split("转发理由:")[1]).split("赞\\[")[0];	
			}
		} else {
			matcher = patternYR.matcher(line);
			//System.out.println("YR" + line);
			if (matcher.find()) {
				//System.out.println("YR" + matcher.group(1));
				tmpWeibo = matcher.group(1);
				tmpWeibo = tmpWeibo.replaceAll("原图", "");
				tmpWeibo = tmpWeibo.replaceAll("[组图共\\d张]", "");
				tmpWeibo = tmpWeibo.replaceAll("沈阳\\·\\S*", "");
				tmpWeibo = tmpWeibo.replaceAll("显示地图", "");
			}
		}

		return tmpWeibo;
	}

	private static void addEmotionDic(String weiboContent) {
		// TODO Auto-generated method stub
		if (weiboContent == null || weiboContent.equals(""))
			return;
		matcher = pattern.matcher(weiboContent);
		while (matcher.find()) {
			if (!emotionMap.containsKey(matcher.group()))
				emotionMap.put(matcher.group(), 1);
		}
	}

	private static String getContent(String line) {
		// TODO Auto-generated method stub
		matcher0 = patternO.matcher(line);
		if (matcher0.find()) {
			c++;
			String[] tmp = matcher0.group(1).split("~");
			int lenth = tmp.length;
			String a = "";
			if (lenth == 1) {
				a = matcher0.group(1).replace("阜新·\\S*", "");
				return a;
			}

			for (int i = 0; i < lenth - 4; i++) {
				a += tmp[i];
			}
			a = a.replace("阜新\\·\\S*", "");
			return a;

		} else {
			return "";
		}
	}
}
