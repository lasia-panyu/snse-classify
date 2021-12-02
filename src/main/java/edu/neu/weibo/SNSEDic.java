package edu.neu.weibo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 潘 on 2016/6/3.
 * 2016/06/03 首先训练带表情的词典
 */
public class SNSEDic {
    public static Map<String, Integer> emontionDic = new HashMap<String, Integer>();
    public static Map<String, Integer> positiveDic = new HashMap<String, Integer>();
    public static Map<String, Integer> negtiveDic = new HashMap<String, Integer>();
    public static List<String> positiveWordList = new ArrayList<String>();
    public static List<String> negtiveWordList = new ArrayList<String>();
    public static Map<String, Integer> advWordList = new HashMap<String, Integer>();
    public static Map<String, Integer> pBayes = new HashMap<String, Integer>();
    public static Map<String, Integer> nBayes = new HashMap<String, Integer>();
    private final static String path = "C:\\Users\\Pan\\Desktop\\SNSE\\dic\\";
 //   private static File file = new File(path + "emotion_dic.txt");
	static File file2 = new File(path + "nemotion_dic" + ".txt");
	static File file1 = new File(path + "pemotion_dic" + ".txt");
	static File file3 = new File(path + "正面情感词语（中文）" + ".txt");
	static File file4 = new File(path + "负面情感词语（中文）" + ".txt");
	static File file5 = new File(path + "程度级别词语（中文）" + ".txt");
	static File file6 = new File(path + "pbayes" + ".txt");
	static File file7 = new File(path + "nbayes" + ".txt");
    public static void initDic() throws Exception {
 //       BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedReader reader1 = new BufferedReader(new FileReader(file1));
        BufferedReader reader2 = new BufferedReader(new FileReader(file2));
        BufferedReader reader3 = new BufferedReader(new FileReader(file3));
        BufferedReader reader4 = new BufferedReader(new FileReader(file4));
        BufferedReader reader5 = new BufferedReader(new FileReader(file5));
        BufferedReader reader6 = new BufferedReader(new FileReader(file6));
        BufferedReader reader7 = new BufferedReader(new FileReader(file7));
        String line = "";
//        while ((line = reader.readLine()) != null) {
//            emontionDic.put(line.split("~")[0], Integer.valueOf(line.split("~")[1]));
//        }
        while ((line = reader1.readLine()) != null) {
        	positiveDic.put(line.split("~")[0], Integer.valueOf(line.split("~")[1]));
        }
        while ((line = reader2.readLine()) != null) {
        	negtiveDic.put(line.split("~")[0], Integer.valueOf(line.split("~")[1]));
        }
        while ((line = reader3.readLine()) != null) {
        	positiveWordList.add(line.trim());
        }
        while ((line = reader4.readLine()) != null) {
        	negtiveWordList.add((line).trim());
        }
        int i=6;
        while ((line = reader5.readLine()) != null) {
        	if(line.trim().startsWith("1|2|3|4|5")){
        		i=i-1;
        		continue;
        	}else if(line.trim().startsWith("6")){
        		i=5;
        		continue;
        	}else{
        	advWordList.put(line.trim(),i-1);
        	}
       
        }
        while ((line = reader6.readLine()) != null) {
        	System.out.println(line);
        	
        	pBayes.put(line.split("~+")[0], Integer.valueOf(line.split("~+")[1]));
        }
        while ((line = reader7.readLine()) != null) {
        	nBayes.put(line.split("~+")[0], Integer.valueOf(line.split("~+")[1]));
        }

    }
}