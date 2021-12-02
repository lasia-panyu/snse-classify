package edu.neu.weibo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeiboCount {
	private final static String path = "C:\\Users\\Pan\\Desktop\\SNSE\\dic\\";
	static File file1 = new File(path + "pnWriboDic" + ".txt");
  	static File file2 = new File(path + "bankc" + ".txt");
	static File file3 = new File(path + "fpnWriboDic2" + ".txt");
  	static File file4 = new File(path + "fbankq2" + ".txt");
    public static void main(String[] args) throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader(file3));
        BufferedReader reader2 = new BufferedReader(new FileReader(file4));
  //      BufferedWriter writer1=new BufferedWriter(new FileWriter(file3));
   //     BufferedWriter writer2=new BufferedWriter(new FileWriter(file4));	
        List<Integer> list1=new ArrayList<Integer>();
        List<Integer> list2=new ArrayList<Integer>();
        String line="";
        int i=0;
        double tmpValue=0;
        double value=0;
        while ((line = reader1.readLine()) != null) {
        	System.out.println(line);
        	list1.add(Integer.valueOf(line));
        }
        i=0;
        tmpValue=0;
        value=0;
        while ((line = reader2.readLine()) != null) {
        	System.out.println(line);
        	list2.add(Integer.valueOf(line));
        }
        int temp=1;
        int count1=0;
        int count2=0;
        for(int a=1;a<list1.size();a++){
        	System.out.println((a-1)+"~"+a);
        	if(list1.get(a-1)==list2.get(a)){
        		count1++;
        	}else
        		count2++;
        	
        }
        System.out.println(count1+"~"+count2);
        System.out.println(Double.valueOf(count1/(count1+count2)));
	}
}
