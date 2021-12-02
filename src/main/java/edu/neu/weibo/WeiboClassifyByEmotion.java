package edu.neu.weibo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.Word;
import org.apdplat.word.tagging.PartOfSpeechTagging;

import code.NlpirTest;

public class WeiboClassifyByEmotion {
	static Pattern pattern = Pattern.compile("\\[([^\\]]+)\\]");
	static Matcher matcher;
	private final static String path = "C:\\Users\\Pan\\Desktop\\SNSE\\微博\\";
	private static File file = new File(path + "emotion_weibo.txt");
	private static File file3 = new File(path + "emotion_weibo3.txt");
	static File file2 = new File(path + "emotion_weibo2" + ".txt");
	static Map<String,Integer> wordMap=new HashMap<String,Integer>();
	static Calendar cal = Calendar.getInstance();
	static Calendar cal1 = Calendar.getInstance();
	static SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
	static SimpleDateFormat sf1 = new SimpleDateFormat("MMdd");
	static int i=0;
	public static void main(String[] args) throws Exception {
		String line = "";
		SNSEDic.initDic();
		String weibo = "";
		String classify = "";
		NlpirTest nlp = new NlpirTest();
		String tmpWord = "";
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
		BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		// BufferedWriter writer1 = new BufferedWriter(new
		// OutputStreamWriter(new FileOutputStream(file1), "UTF-8"));
		BufferedWriter writer1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file3), "UTF-8"));
		BufferedWriter writer2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2), "UTF-8"));
		BufferedWriter writer3 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path + "wordsDic" + ".txt")), "UTF-8"));
		
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
	

			/* 抽取微博 */
			BufferedReader reader = new BufferedReader(new FileReader(file));
		while ((line = reader.readLine()) != null) {
			WeiboClassifyByEmotion.i++;
			if(line.contains("【")||line.contains("】"))
				continue;
			if(line.contains("#")||line.contains("[钱]"))
				continue;
			if(line.contains("微博等级")||line.contains("秒拍")||line.contains("分享图片")||line.contains("分享视频"))
				continue;
			if(line.contains("http"))
				continue;
		//	System.out.println("line"+line);
		//	tmpWord = line.replaceAll("/*", "");
		//	getWord(tmpWord);
			// tmpWord=tmpWord.replaceAll("[]", "");
			// System.out.println("line"+line);
			//nlp.seg(tmpWord);
//			System.out.println(nlp.seg(tmpWord));
//			getWord(nlp.seg(tmpWord));

			 matcher = pattern.matcher(line);
			 if (matcher.find()) {
			 addEmotionDic(line,writer1, writer2);
			 }
		}
		
	//	}
		ArrayList<Map.Entry<String, Integer>> treemap1 = new ArrayList<Map.Entry<String, Integer>>(
				wordMap.entrySet());
		Collections.sort(treemap1, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});
		for (Entry entry : treemap1) {
			// System.out.println(entry.getKey()+"~"+entry.getValue());
			writer3.write(entry.getKey() + "~" + entry.getValue());
			writer3.newLine();
			writer3.flush();
		}
		ArrayList<Map.Entry<String, Integer>> treemap2 = new ArrayList<Map.Entry<String, Integer>>(
				wordMap.entrySet());
		Collections.sort(treemap2, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});
		for (Entry entry : treemap2) {
			// System.out.println(entry.getKey()+"~"+entry.getValue());
			writer3.write(entry.getKey() + "~" + entry.getValue());
			writer3.newLine();
			writer3.flush();
		}
		}
		System.out.println(i);
	}

	private static void addEmotionDic(String weiboContent, BufferedWriter writer1, BufferedWriter writer2)
			throws IOException {
		// TODO Auto-generated method stub
		if (weiboContent == null || weiboContent.equals(""))
			return;
		matcher = pattern.matcher(weiboContent);
		int score = 0;
		while (matcher.find()) {
			System.out.println(matcher.group());
			if (SNSEDic.positiveDic.containsKey(matcher.group())) {
				score += SNSEDic.positiveDic.get(matcher.group());
			}
			if (SNSEDic.negtiveDic.containsKey(matcher.group())) {
				score -= SNSEDic.negtiveDic.get(matcher.group());
			}
		}
		// dic++;
		System.out.println(score);
		if (score > 0) {
			writer2.write(weiboContent + " ~|~0");
			writer2.newLine();
			writer2.flush();
		} else if (score < 0) {
			writer2.write(weiboContent + " ~|~1");
			writer2.newLine();
			writer2.flush();
		} else {
			writer1.write(weiboContent);
			writer1.newLine();
			writer1.flush();
		}
	}
//	public static List<String> getWord(String line) {
//		String tmpWord = "";
//		line = line.trim();
//		tmpWord = line;
//		tmpWord = tmpWord.replaceAll("\\[([^\\]]*)\\]", "");
//		tmpWord = tmpWord.replaceAll("阜新·\\S*", "");
//		tmpWord = tmpWord.replaceAll("@\\S*", "");
//		tmpWord = tmpWord.replaceAll("显示地图", "");
//		tmpWord = tmpWord.replaceAll("显示地", "");
//		tmpWord = tmpWord.replaceAll("(\\?)+", "");
//		if (tmpWord.equals(""))
//			return null;
//		List<String> tmpList = new ArrayList<String>();
//		List<Word> words = WordSegmenter.seg(tmpWord);
//		System.out.println("未标注词性："+words);
//		//词性标注
//		PartOfSpeechTagging.process(words);
//		System.out.println("标注词性："+words);
//		if (words.size() == 0)
//			return null;
//		for (int i = 0; i < words.size() - 1; i++) {
//			if (i < words.size() - 1) {		
//				if (!words.get(i).equals("")&&!words.get(i).equals("") ){
//					//System.out.println("words[i]"+words[i]);
////					if (words.get(i).toString().split("\\/")[1].equals("d")&&words.get(i).toString().split("\\/")[1].equals("v"))
////						tmpList.add(words.get(i).toString().split("\\/")[0] +words.get(i).toString().split("\\/")[0]);
//				//	if (words.get(i).toString().split("\\/")[1].equals(words.get(i+1).toString().split("\\/")[1]))
//						tmpList.add(words.get(i).toString().split("\\/")[0] + words.get(i+1).toString().split("\\/")[0]);
//				}
//				if (i == words.size() - 1) {
//					tmpList.add(words.get(i).toString().split("\\/")[0]);
//				}
//			}
//		}
//		for (String str : tmpList){
//			if(wordMap.containsKey(str))
//				wordMap.replace(str, wordMap.get(str)+1);
//			else
//				wordMap.put(str, 1);
//		}
//		return null;
//	}

}
