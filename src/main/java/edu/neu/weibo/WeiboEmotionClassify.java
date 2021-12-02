package edu.neu.weibo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Pan 先找到Emotion词典 词典中有每个标签的数目 读取EmotionWeibo分词，通过表情构建
 */
public class WeiboEmotionClassify {
	public static Map<String, Integer> nEmotionMap = new HashMap<String, Integer>();
	public static Map<String, Integer> pEmotionMap = new HashMap<String, Integer>();
	static Pattern pattern = Pattern.compile("\\[([^\\]]+)\\]");
	static Matcher matcher;
	private final static String path = "C:\\Users\\Pan\\Desktop\\SNSE\\dic\\";
	private static File file = new File("C:\\Users\\Pan\\Desktop\\SNSE\\微博\\" + "emotion_weibo.txt");

	public static void main(String[] args) throws Exception {
		String line = "";
		SNSEDic.initDic();
		String weibo = "";
		String classify = "";
		File file1 = new File(path + "nemotion_dic" + ".txt");
		File file2 = new File(path + "pemotion_dic" + ".txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		BufferedWriter writer1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1), "UTF-8"));
		BufferedWriter writer2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2), "UTF-8"));
		while ((line = reader.readLine()) != null) {
			// System.out.println(line);
			if (line.split("~\\|~").length == 1) {
				System.out.println("size是1" + line);
			} else {
				weibo = line.split("~\\|~")[0];
				classify = line.split("~\\|~")[1];
				addEmotionDic(line, classify);
			}
		}
		ArrayList<Map.Entry<String, Integer>> treemap1 = new ArrayList<Map.Entry<String, Integer>>(
				nEmotionMap.entrySet());
		ArrayList<Map.Entry<String, Integer>> treemap2 = new ArrayList<Map.Entry<String, Integer>>(
				pEmotionMap.entrySet());
		Collections.sort(treemap1, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});

		Collections.sort(treemap2, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});
		int nEmotion=0;
		int pEmotion=0;
		for (Entry<String,Integer> entry : treemap1) {
			// System.out.println(entry.getKey()+"~"+entry.getValue());
			nEmotion=nEmotion+ entry.getValue();
			writer1.write(entry.getKey() + "~" + entry.getValue());
			writer1.newLine();
			writer1.flush();
		}
		for (Entry<String,Integer> entry : treemap2) {
			pEmotion=pEmotion+ entry.getValue();
			// System.out.println(entry.getKey()+"~"+entry.getValue());
			writer2.write(entry.getKey() + "~" + entry.getValue());
			writer2.newLine();
			writer2.flush();
		}
        System.out.println("nEmotionMap"+nEmotion);
        System.out.println("pEmotionMap"+pEmotion);

	}

	private static void addEmotionDic(String line, String classify) {
		// TODO Auto-generated method stub
		matcher = pattern.matcher(line);
		List<String> tmpList = new ArrayList<String>();
		// int dic=0;
		while (matcher.find()) {
			// System.out.println(matcher.group());
			if (!tmpList.contains(matcher.group()))
				tmpList.add(matcher.group());
		}
		if (classify.equals("1")) {
			for (String str : tmpList) {
				if (!nEmotionMap.containsKey(str))
					nEmotionMap.put(str, 1);
				else
					nEmotionMap.put(str, nEmotionMap.get(str) + 1);
			}
		}
		if (classify.equals("0")) {
			for (String str : tmpList) {
				if (!pEmotionMap.containsKey(str))
					pEmotionMap.put(str, 1);
				else
					pEmotionMap.put(str, pEmotionMap.get(str) + 1);
			}
		}
	}
}
