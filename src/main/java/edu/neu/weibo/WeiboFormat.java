package edu.neu.weibo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 潘 on 2016/6/3.
 */

public class WeiboFormat {
	static Map<String, Integer> emotionMap = new HashMap<String, Integer>();
	static Pattern pattern = Pattern.compile("\\[([^\\]]+)\\]");
	static Pattern patternZ = Pattern.compile("\\d{14} \\S* 转发了");
	static Pattern patternY = Pattern.compile("\\d{14} \\S*:((\\S|\\s)*)");
	static Pattern patternZR = Pattern.compile("转发理由:((\\S|\\s)*)(\\/\\/\\@){1}");
	static Pattern patternYR = Pattern
			.compile("\\d{14} \\S*:((\\S|\\s)*)\\s*(原图|)赞\\[\\d*\\] 转发\\[\\d*\\] 评论\\[\\d*\\] 收藏 \\d分钟前 来自.*");

	static int tmpNumber = 0;
	static Matcher matcher;
	static Calendar cal = Calendar.getInstance();
	static Calendar cal1 = Calendar.getInstance();
	static SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
	static SimpleDateFormat sf1 = new SimpleDateFormat("MMdd");
	static String path = "C:\\Users\\Pan\\Desktop\\SNSE\\微博\\";
	static List<String> indexPool=new ArrayList<String>(50);
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("deprecation")
		Date date = new Date();
		date.setYear(116);
		date.setMonth(3);
		date.setDate(21);
		cal.setTime(date);
		Date date1 = new Date();
		date1.setYear(116);
		date1.setMonth(5);
		date1.setDate(1);
		cal1.setTime(date1);
		int weiboCount=0;
		File file1 = new File("C:\\Users\\Pan\\Desktop\\SNSE\\dic" + "emotion_dic" + ".txt");
		File file2 = new File("C:\\Users\\Pan\\Desktop\\SNSE\\dic" + "emotion_weibo" + ".txt");
		BufferedWriter writer1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1), "UTF-8"));
		BufferedWriter writer2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2), "UTF-8"));
		for (int i = 0; i < 60; i++) {
			File file ;
			if(i<=39){
			 file = new File(path + sf.format(cal.getTime()) + ".txt");
            System.out.println(i+file.getName());
			cal.add(cal.DATE, 1);
			/* 抽取微博 */
		
			}else{
				
				 file = new File(path +"F"+ sf1.format(cal1.getTime()) + ".txt");
				 System.out.println(i+file.getName());
				cal1.add(cal1.DATE, 1);
			}
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String weiboContent = "";
			String line = "";
			while ((line = reader.readLine()) != null) {
				if(indexPool.contains(line)){
					//System.out.println("重复微博"+line);
					updatePool(line);
					continue;
				}
				updatePool(line);	
				matcher = pattern.matcher(line);
				if(matcher.find()){
					System.out.println(i+line);
				addEmotionDic(line);
				writer2.write(line);
				writer2.newLine();
				writer2.flush();
				}
			}
		}
			
			for(Entry entry:emotionMap.entrySet()){
				//System.out.println(entry.getKey()+"~"+entry.getValue());
				writer1.write(entry.getKey()+"~"+entry.getValue());
				writer1.newLine();
				writer1.flush();
			}
		
	}
	private static void updatePool(String line) {
		if(indexPool.size()<50){
			indexPool.add(line);
		}else if(indexPool.size()==50){
			indexPool.remove(0);
			indexPool.add(line);
		}	
	}
	private static String getContent1(String line) {
		// TODO Auto-generated method stub
		matcher = patternZ.matcher(line);
		String tmpWeibo = "";
		if (matcher.find()) {
			matcher = patternZR.matcher(line);
			if (matcher.find())
				tmpWeibo = matcher.group(1).split("//@")[0];
		} else {
			matcher = patternYR.matcher(line);
			if (matcher.find()) {
				tmpWeibo = matcher.group(1);
				tmpWeibo = tmpWeibo.replaceAll("原图", "");
				tmpWeibo = tmpWeibo.replaceAll("[组图共\\d张]", "");
				tmpWeibo = tmpWeibo.replaceAll("沈阳·\\S*", "");
				tmpWeibo = tmpWeibo.replaceAll("显示地图", "");
			}
		}
		//System.out.println(tmpWeibo);
		return tmpWeibo;
	}

	private static void addEmotionDic(String weiboContent) {
		// TODO Auto-generated method stub
		if (weiboContent == null || weiboContent.equals(""))
			return;
		matcher = pattern.matcher(weiboContent);
		List<String> tmpList=new ArrayList<String>();
		//int dic=0;
		while (matcher.find()) {
			//System.out.println(matcher.group());
			if(!tmpList.contains(matcher.group()))
			tmpList.add(matcher.group());
			//dic++;
		}
		for(String str:tmpList){
			if (!emotionMap.containsKey(str))
				emotionMap.put(str, 1);
			else
				emotionMap.put(str,emotionMap.get(str)+1);
		}
	}

	private static String getContent(String line) {
		// TODO Auto-generated method stub
		String[] tmp = line.split("~");
		int lenth = tmp.length;
		String a = "";
		// System.out.println(lenth-7);
		for (int i = 2; i < lenth - 8; i++) {
			a += tmp[i];
		}
		;
		a = a.replaceAll("原图", "");
		a = a.replaceAll("[组图共\\d张]", "");
		a = a.replaceAll("沈阳·\\S*", "");
		a = a.replaceAll("显示地图", "");
		return a;
	}
}
