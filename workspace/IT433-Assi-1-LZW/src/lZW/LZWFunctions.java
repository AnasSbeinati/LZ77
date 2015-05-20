package lZW;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.awt.List;
import java.io.*;

import javax.swing.JOptionPane;

public class LZWFunctions {
	static long sizeBefore=0;
	static long sizeAfter=0;
	public static long getSizeBefore() {
		return sizeBefore;
	}
	public static void setSizeBefore(long sizeBefore) {
		LZWFunctions.sizeBefore = sizeBefore;
	}
	public static long getSizeAfter() {
		return sizeAfter;
	}
	public static void setSizeAfter(long sizeAfter) {
		LZWFunctions.sizeAfter = sizeAfter;
	}
	static public String compressLZW(String buffer)
	{
		Hashtable<String,Integer> dictionary=new Hashtable<>();
		for(int i=0;i<128;i++)
		{
			char c=(char) i;
			String temp="";
			temp+=c;
			dictionary.put(temp, i);
		}
		int current=0,bufferSize=buffer.length();
		String tags="";
		if(!buffer.equals(""))
		{
			int ite=127;
			while(current<bufferSize)
			{
				ite++;
				String strToCompress="";
				strToCompress+=buffer.charAt(current);
				int i=1;
				while((dictionary.get(strToCompress)!=null)&&(current+i<bufferSize))
				{
					strToCompress+=buffer.charAt(current+i);
					i++;
				}
				if(dictionary.get(strToCompress)==null)
				{
					String r=strToCompress.substring(0,strToCompress.length()-1);
					tags+=dictionary.get(r);
					 current+=i-1;
				}
				else
				{
					tags+=dictionary.get(strToCompress);
					current=bufferSize;
				}
				tags+='|';
				dictionary.put(strToCompress,ite);
			}
		}
		return tags;
	}
	static public File compressFile(String path,String des)
	{
		try
		{
			FileReader file=new FileReader(path);
    	    BufferedReader buffer=new BufferedReader(file);
    	    FileWriter file2=new FileWriter(des);
    	    BufferedWriter buffer2=new BufferedWriter(file2);
    	    long fileSize=new File(path).length();
    	    long size=fileSize%1000;
    	    if(fileSize<1000)
    	    {
    	    	char[] text1=new char[(int)size];
    	    	buffer.read(text1,0,(int)size);
    	    	String temp=compressLZW(String.copyValueOf(text1));
		    	buffer2.write(temp);
    	    }
    	    else
    	    {
    	    	long index=new File(path).length();
    	        char[] text=new char[1000];
		        while(buffer.read(text,0, 1000)!=-1)
		        {
		    	     //System.out.print(String.copyValueOf(text));
		    	     //String text = String.valueOf(data)
		    	     String temp=compressLZW(String.copyValueOf(text));
		    	     buffer2.write(temp);
		    	     index-=1000;
		    	     if(index<1000)
		     	     {
		     	    	char[] text1=new char[(int)index];
		     	    	buffer.read(text1,0,(int)index);
		     	    	String temp1=compressLZW(String.copyValueOf(text1));
		 		    	buffer2.write(temp1);
		     	     }
		        }
    	    }
		    buffer.close();
		    buffer2.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"Something wrong");
		}
		return new File(path);
	}
    static public String deCompressLZW(String tags)
    {
    	String[]dictionary=new String[1000];
    	for (int i = 0; i < dictionary.length; i++) 
    	{
    		dictionary[i]="";
    		if(i<128)
    		{
			  dictionary[i]+=(char)i;
			  //System.out.println(dictionary[i]);
    		}	
		}
    	String buffer="",currentStr="",lastStr="",strCurrent="";
    	int currentTag=0,currentStream=0,tagNum=0;
    	//first tag in tags
    	int i=0;
    	i=currentStream;
    	strCurrent="";
    	while(tags.charAt(i)!='|')
    	{
    	     strCurrent+=tags.charAt(i);
    	     i++;
    	}
    	currentStream=i+1;
    	currentTag=Integer.parseInt(strCurrent);
    	currentStr=dictionary[currentTag];
    	buffer+=currentStr;
    	//all tags
    	while(currentStream<tags.length())
    	{
    		lastStr=currentStr;
    		i=currentStream;
        	strCurrent="";
        	while(tags.charAt(i)!='|')
        	{
        	     strCurrent+=tags.charAt(i);
        	     i++;
        	}
       	    currentStream=i+1;
         	currentTag=Integer.parseInt(strCurrent);
    		if(dictionary[currentTag]=="")
    			currentStr=lastStr+lastStr.charAt(0);
    		else
    			currentStr=dictionary[currentTag];
    		tagNum++;
    		dictionary[tagNum+127]=lastStr+currentStr.charAt(0);
    		buffer+=currentStr;
    	}
    	return buffer;
    }
    static public File deCompressFile(String path,String des)
	{
		try
		{
			FileReader file=new FileReader(path);
    	    BufferedReader buffer=new BufferedReader(file);
    	    Scanner test=new Scanner(path);
    	    FileWriter file2=new FileWriter(des);
    	    BufferedWriter buffer2=new BufferedWriter(file2);
    	    long fileSize=new File(path).length();
    	    long size=fileSize%1000;
    	    if(fileSize<1000)
    	    {
    	    	char[] text1=new char[(int)size];
    	    	buffer.read(text1,0,(int)size);
    	    	String temp=deCompressLZW(String.copyValueOf(text1));
		    	buffer2.write(temp);
    	    }
    	    else
    	    {
    	    	long index=new File(path).length();
    	        char[] text=new char[1000];
		        while(buffer.read(text,0, 1000)!=-1)
		        {
		    	     //System.out.print(String.copyValueOf(text));
		    	     //String text = String.valueOf(data)
		    	     String temp=compressLZW(String.copyValueOf(text));
		    	     buffer2.write(temp);
		    	     index-=1000;
		    	     if(index<1000)
		     	     {
		     	    	char[] text1=new char[(int)index];
		     	    	buffer.read(text1,0,(int)index);
		     	    	String temp1=compressLZW(String.copyValueOf(text1));
		 		    	buffer2.write(temp1);
		     	     }
		        }
    	    }
		    buffer.close();
		    buffer2.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"Something wrong");
		}
		return new File(path);
	}
}
