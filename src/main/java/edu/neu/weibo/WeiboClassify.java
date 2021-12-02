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

public class WeiboClassify {
	static int nEmotionMap = 4443;
	static int pEmotionMap = 12661;
	static Pattern pattern = Pattern.compile("\\[([^\\]]+)\\]");
	static Matcher matcher;
	private final static String path = "C:\\Users\\Pan\\Desktop\\SNSE\\dic\\";
	static Map<String, Integer> wordMap = new HashMap<String, Integer>();
	static Map<String, Integer> nBayes = new HashMap<String, Integer>();
	static Map<String, Integer> pBayes = new HashMap<String, Integer>();
	static Calendar cal = Calendar.getInstance();
	static Calendar cal1 = Calendar.getInstance();
	static SimpleDateFormat sf  = new SimpleDateFormat("yyyyMMdd");
	static SimpleDateFormat sf1 = new SimpleDateFormat("MMdd");
	static int i = 0;
	static int nCount=0;
	static int pCount=0;
	public static void main(String[] args) throws Exception {
		String line = "";
		SNSEDic.initDic();
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
		BufferedWriter writer1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path + "pweibo" + ".txt")), "UTF-8"));
		BufferedWriter writer2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path + "nweibo" + ".txt")), "UTF-8"));
		BufferedWriter writer3 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path + "zweibo" + ".txt")), "UTF-8"));
		BufferedWriter writer4 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path + "pBayes1" + ".txt")), "UTF-8"));
		BufferedWriter writer5 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path + "nBayes1" + ".txt")), "UTF-8"));
		BufferedWriter writer6 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path + "pnWriboDic" + ".txt")), "UTF-8"));
    
		for (int i = 0; i < 83; i++) {
				File file ;
				if(i<41){
				 file = new File("C:\\Users\\Pan\\Desktop\\SNSE\\微博\\" + sf.format(cal.getTime()) + ".txt");
	            System.out.println(i+file.getName());
				cal.add(cal.DATE, 1);
				/* 抽取微博 */
			
				}else{
					
					 file = new File("C:\\Users\\Pan\\Desktop\\SNSE\\微博\\" +"F"+ sf1.format(cal1.getTime()) + ".txt");
					 System.out.println(i+file.getName());
					cal1.add(cal1.DATE, 1);
				}
			/* 抽取微博 */
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			while ((line = reader.readLine()) != null) {
				WeiboClassify.i++;
				if (line.contains("【") || line.contains("】")||line.contains("《")||line.contains("》"))
					continue;
				if (line.contains("#") || line.contains("[钱]"))
					continue;
				if (line.contains("微博等级") || line.contains("秒拍") || line.contains("分享图片") || line.contains("分享视频"))
					continue;
				if (line.contains("http"))
					continue;
				tmpWord = line.replaceAll("/*", "");
				getWord(tmpWord,writer1,writer2,writer3);
			}
			writer6.write(pCount+"~"+nCount);
			writer6.newLine();
			writer6.flush();
			pCount=0;
			nCount=0;
		}
		ArrayList<Map.Entry<String, Integer>> treemap1 = new ArrayList<Map.Entry<String, Integer>>(pBayes.entrySet());
		Collections.sort(treemap1, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});
		for (Entry<String, Integer> entry : treemap1) {
			 writer4.write(entry.getKey() + "~" + entry.getValue());
			 writer4.newLine();
			 writer4.flush();
		}
		ArrayList<Map.Entry<String, Integer>> treemap2 = new ArrayList<Map.Entry<String, Integer>>(nBayes.entrySet());
		Collections.sort(treemap2, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});
		for (Entry<String, Integer> entry : treemap2) {
			 writer5.write(entry.getKey() + "~" + entry.getValue());
			 writer5.newLine();
			 writer5.flush();
		}
		//System.out.println(i);
	}

	public static List<String> getWord(String line, BufferedWriter writer1, BufferedWriter writer2, BufferedWriter writer3) throws IOException {
		String tmpWord = "";
		// System.out.println(line);
		line = line.trim();
		matcher = pattern.matcher(line);
		double score = 0;
		double score1 = 0;
		double score2 = 0;
		double scoret = 0;
		while (matcher.find()) {
			if (SNSEDic.positiveDic.containsKey(matcher.group())) 
				score += SNSEDic.positiveDic.get(matcher.group());
			if (SNSEDic.negtiveDic.containsKey(matcher.group())) 
				score -= SNSEDic.negtiveDic.get(matcher.group());
		}
		if (score > 0)
			score = 0.5;
		else if(score < 0)
			score=-0.5;
		System.out.println(line);
		tmpWord = line.trim();
		tmpWord = tmpWord.replaceAll("\\[([^\\]]*)\\]", "");
		tmpWord = tmpWord.replaceAll("阜新\\·\\S*", "");
		tmpWord = tmpWord.replaceAll("@\\S*", "");
		tmpWord = tmpWord.replaceAll("显示地图", "");
		tmpWord = tmpWord.replaceAll("显示地", "");
		tmpWord = tmpWord.replaceAll("(\\?)+", "");
		//System.out.println(tmpWord);
		if (tmpWord.equals(""))
			return null;
		List<Word> words = WordSegmenter.seg(tmpWord);
		// System.out.println("未标注词性："+words);
		// 词性标注
		PartOfSpeechTagging.process(words);
		System.out.println(words);
		// System.out.println("标注词性："+words);
		if (words.size() == 0)
			return null;
		for (int i = 0; i < words.size(); i++) {
			if (i < words.size() - 1) {
				if (!words.get(i).toString().split("\\/")[0].equals("") && !words.get(i+1).toString().split("\\/")[0].equals("")) {
					if (SNSEDic.positiveWordList.contains(words.get(i).toString().split("\\/")[0])) 
						score1++;
					if (SNSEDic.negtiveWordList.contains(words.get(i).toString().split("\\/")[0])) 
						score2++;
					if (SNSEDic.advWordList.containsKey(words.get(i).toString().split("\\/")[0])
							&& SNSEDic.positiveWordList.contains(words.get(i + 1).toString().split("\\/")[0])) {
						score1 += SNSEDic.advWordList.get(words.get(i).toString().split("\\/")[0]);
						i++;
						continue;
					}
					System.out.println(words.get(i).toString());
					if (SNSEDic.advWordList.containsKey(words.get(i).toString().split("\\/")[0])
							&& SNSEDic.negtiveWordList.contains(words.get(i + 1).toString().split("\\/")[0])) {
						score2 += SNSEDic.advWordList.get(words.get(i).toString().split("\\/")[0]) ;
						i++;
						continue;
				
					}				
				}
			}
			if (i == words.size() - 1) 
				if (SNSEDic.positiveWordList.contains(words.get(i).toString().split("\\/")[0])) 
					score1++;
				if (SNSEDic.negtiveWordList.contains(words.get(i).toString().split("\\/")[0])) 
					score2++;
		}
		if (score1 != 0 || score2 != 0) {
			System.out.println(score1+"~"+score2);
			scoret = (score1 - score2) / (score1 + score2);
		}
		score = scoret + score;
		score1 = 0;
		score2 = 0;
		for (int i = 0; i < words.size(); i++) {
		if(SNSEDic.pBayes.containsKey(words.get(i).toString().split("\\/")[0]))	
			score1++;
		if(SNSEDic.nBayes.containsKey(words.get(i).toString().split("\\/")[0]))
			score2++;
		}
		scoret = (score1 - score2) / (score1 + score2);
		score = scoret + score;
		if(score>0){
			writer1.write(line+"~|~"+score);;
			writer1.newLine();
			writer1.flush();
			for(Word word:words){
				if(pBayes.containsKey(word.toString().split("\\/")[0]))
				pBayes.put(word.toString().split("\\/")[0], pBayes.get(word.toString().split("\\/")[0])+1);
				else
				pBayes.put(word.toString().split("\\/")[0],1);
			}
			pCount++;
		}else if(score<0){
		writer2.write(line+"~|~"+score);;
		writer2.newLine();
		writer2.flush();
		for(Word word:words){
			if(nBayes.containsKey(word.toString().split("\\/")[0]))
				nBayes.put(word.toString().split("\\/")[0], nBayes.get(word.toString().split("\\/")[0])+1);
			else
				nBayes.put(word.toString().split("\\/")[0],1);
		}
		nCount++;
		}else{
		writer3.write(line+"~|~"+score);;
		writer3.newLine();
		writer3.flush();
		}
		return null;
	}
}
