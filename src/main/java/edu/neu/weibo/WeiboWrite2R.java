package edu.neu.weibo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WeiboWrite2R {
	private final static String path = "C:\\Users\\Pan\\Desktop\\SNSE\\dic\\";
	static File weibo = new File(path + "pnWriboDic" + ".txt");
  	static File bankc = new File(path + "bankc" + ".txt");
 	static File bankq = new File(path + "bankq" + ".txt");
	static File file1 = new File(path + "fpnWriboDic1" + ".txt");
	static File file2 = new File(path + "fpnWriboDic2" + ".txt");
  	static File file3 = new File(path + "fbankc1" + ".txt");
  	static File file4 = new File(path + "fbankc2" + ".txt");
  	static File file5 = new File(path + "fbankq1" + ".txt");
  	static File file6 = new File(path + "fbankq2" + ".txt");
    public static void main(String[] args) throws IOException {
        BufferedReader weibo1 = new BufferedReader(new FileReader(weibo));
        BufferedReader bankc1 = new BufferedReader(new FileReader(bankc));
        BufferedReader bankq1= new BufferedReader(new FileReader(bankq));
        BufferedReader weibo2 = new BufferedReader(new FileReader(weibo));
        BufferedReader bankc2 = new BufferedReader(new FileReader(bankc));
        BufferedReader bankq2= new BufferedReader(new FileReader(bankq));
        BufferedWriter writer1=new BufferedWriter(new FileWriter(file1));
        BufferedWriter writer2=new BufferedWriter(new FileWriter(file2));		
        BufferedWriter writer3=new BufferedWriter(new FileWriter(file3));
        BufferedWriter writer4=new BufferedWriter(new FileWriter(file4));
        BufferedWriter writer5=new BufferedWriter(new FileWriter(file5));		
        BufferedWriter writer6=new BufferedWriter(new FileWriter(file6));
        String line="";
        int i=0;
        double tmpValue=0;
        double value=0;
        while ((line = weibo1.readLine()) != null) {
        	if(i==0){
        	   	i++;
        		//tmpValue=Double.valueOf(line.split("~")[0])/Double.valueOf(line.split("~")[1]);
        		tmpValue=Double.valueOf(line.split("~")[0]);
        		continue;
        	}
        	//value=Double.valueOf(line.split("~")[0])/Double.valueOf(line.split("~")[1]);
        	value=Double.valueOf(line.split("~")[0]);
        	System.out.println(value+"~"+tmpValue);
        	if(value>tmpValue){
        		writer1.write("1");
        	    writer1.flush();
        	    writer1.newLine();
        	}
        	else{
        		writer1.write("0");
        	    writer1.flush();
        	    writer1.newLine();
        	}
        	tmpValue=value;
     
        }
        i=0;
        tmpValue=0;
        value=0;
        while ((line = weibo2.readLine()) != null) {
        	if(i==0){
        	   	i++;
        		//tmpValue=Double.valueOf(line.split("~")[0])/Double.valueOf(line.split("~")[1]);
        		value=Double.valueOf(line.split("~")[1]);
        		continue;
        	}
        	//value=Double.valueOf(line.split("~")[0])/Double.valueOf(line.split("~")[1]);
        	value=Double.valueOf(line.split("~")[1]);
        	System.out.println(value+"~"+tmpValue);
        	if(value>tmpValue){
        		System.out.println("value>tmpValue"+value+"~"+tmpValue);
        		writer2.write("1");
        		writer2.flush();
        		writer2.newLine();
        	}
        	else{
        		writer2.write("0");
        		System.out.println("value<tmpValue"+value+"~"+tmpValue);
        		writer2.flush();
        		writer2.newLine();
        	}
        	tmpValue=value;
        }
        i=0;
        tmpValue=0;
        value=0;
        while ((line = bankc1.readLine()) != null) {
        	if(i==0){
        	   	i++;
        		//tmpValue=Double.valueOf(line.split("~")[0])/Double.valueOf(line.split("~")[1]);
        	  	tmpValue=Double.valueOf(line.split("~")[0]);
        		continue;
        	}
        	//value=Double.valueOf(line.split("~")[0])/Double.valueOf(line.split("~")[1]);
        	value=Double.valueOf(line.split("~")[0]);
        	System.out.println(value+"~"+tmpValue);
        	if(value>tmpValue){
        		System.out.println("value>tmpValue"+value+"~"+tmpValue);
        		writer3.write("1");
        		writer3.flush();
        		writer3.newLine();
        	}
        	else{
        		writer3.write("0");
        		System.out.println("value<tmpValue"+value+"~"+tmpValue);
        		writer3.flush();
        		writer3.newLine();
        	}
        	tmpValue=value;
        }
        i=0;
        tmpValue=0;
        value=0;
        while ((line = bankc2.readLine()) != null) {
        	if(i==0){
        	   	i++;
        		//tmpValue=Double.valueOf(line.split("~")[0])/Double.valueOf(line.split("~")[1]);
        	  	tmpValue=Double.valueOf(line.split("~")[1]);
        		continue;
        	}
        	//value=Double.valueOf(line.split("~")[0])/Double.valueOf(line.split("~")[1]);
        	value=Double.valueOf(line.split("~")[1]);
        	System.out.println(value+"~"+tmpValue);
        	if(value>tmpValue){
        		System.out.println("value>tmpValue"+value+"~"+tmpValue);
        		writer4.write("1");
        		writer4.flush();
        		writer4.newLine();
        	}
        	else{
        		writer4.write("0");
        		System.out.println("value<tmpValue"+value+"~"+tmpValue);
        		writer4.flush();
        		writer4.newLine();
        	}
        	tmpValue=value;
        }
        i=0;
        tmpValue=0;
        value=0;
        while ((line = bankq1.readLine()) != null) {
        	if(i==0){
        	   	i++;
        		//tmpValue=Double.valueOf(line.split("~")[0])/Double.valueOf(line.split("~")[1]);
        	  	tmpValue=Double.valueOf(line.split("~")[0]);
        		continue;
        	}
        	//value=Double.valueOf(line.split("~")[0])/Double.valueOf(line.split("~")[1]);
        	System.out.println(line);
        	value=Double.valueOf(line.split("~")[0]);
        	System.out.println(value+"~"+tmpValue);
        	if(value>tmpValue){
        		System.out.println("value>tmpValue"+value+"~"+tmpValue);
        		writer5.write("1");
        		writer5.flush();
        		writer5.newLine();
        	}
        	else{
        		writer5.write("0");
        		System.out.println("value<tmpValue"+value+"~"+tmpValue);
        		writer5.flush();
        		writer5.newLine();
        	}
        	tmpValue=value;
        }
        i=0;
        tmpValue=0;
        value=0;
        while ((line = bankq2.readLine()) != null) {
        	if(i==0){
        	   	i++;
        		//tmpValue=Double.valueOf(line.split("~")[0])/Double.valueOf(line.split("~")[1]);
        	  	tmpValue=Double.valueOf(line.split("~")[1]);
        		continue;
        	}
        	//value=Double.valueOf(line.split("~")[0])/Double.valueOf(line.split("~")[1]);
        	value=Double.valueOf(line.split("~")[1]);
        	System.out.println(value+"~"+tmpValue);
        	if(value>tmpValue){
        		System.out.println("value>tmpValue"+value+"~"+tmpValue);
        		writer6.write("1");
        		writer6.flush();
        		writer6.newLine();
        	}
        	else{
        		writer6.write("0");
        		System.out.println("value<tmpValue"+value+"~"+tmpValue);
        		writer6.flush();
        		writer6.newLine();
        	}
        	tmpValue=value;
        }
	}
}
