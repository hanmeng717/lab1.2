import java.awt.image.DataBufferDouble;
import java.beans.Expression;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.Scanner;
import java.util.function.DoubleToLongFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.text.DateFormat;  
import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Calendar;  
import java.util.Date; 

import javax.imageio.stream.ImageInputStream;

import org.omg.CORBA.PUBLIC_MEMBER;

/*
 1.ʵ����С���Ĺ��ܣ�����С��һ�㲻����Ϊ�������ָ����
 2.ʵ����������Ĺ��ܣ����������ڱ�����ϵ���Ĵη�Ҳ���Լ����ʮ���ƣ���
 3.�����ڱ���֮���*���Ժ��Բ��������룻
 4.���԰����ո�tab���ַ��� 
 5.����ʽ�еı�������ʹ�ó���>1 ���ַ�����ʾ�� 
 6.�ڳ��֡�+����λ�ã�����ʹ�� ��-���� 
 ����������
//2*x*z+3*x*z^3-z^2+2
//!simplify x=2 
//!d/dz
 */
public class lab 
{
	public static ArrayList<Double> xishu =  new ArrayList<Double>() ;
	public static ArrayList<String> bianliang =new ArrayList<String>();
	public static ArrayList<String> jiajian =new ArrayList<String>();
	public static ArrayList<Double> xishu2 =new ArrayList<Double>();
	public static ArrayList<String> bianliang2 =new ArrayList<String>();
	public static ArrayList<String> bianliang3 =new ArrayList<String>();
	public static ArrayList<Double> xishu3 =new ArrayList<Double>();
	public static ArrayList<Double> xishunew =new ArrayList<Double>();
	public static ArrayList<String> bianliangnew =new ArrayList<String>();
	public static ArrayList<Integer> index=new ArrayList<Integer>();
	public static void main(String[] args)
	{	
		
		String s = null;
		Date d = new Date();
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        s = sdf.format(d);  
        System.out.println("��ǰʱ��Ϊ��"+s); 
		
		String duoxiangshi="";
		String order1="";
		String order2="";
		while (!duoxiangshi.equals("exit"))
		{
			duoxiangshi="";
			duoxiangshi=In();//Ϊ�˱���������󣬹涨�����ϵ������^�����������ϵ�����������
			
			Calendar c = Calendar.getInstance();
			
			if (duoxiangshi.equals("exit"))
			{
				break;
			}
			duoxiangshi=Check(duoxiangshi);
			
			Calendar c1 = Calendar.getInstance();
			System.out.println("�������ʽ�㷨����ʱ��" + (c1.getTimeInMillis()-c.getTimeInMillis())+"����"); 
			
			xishu.clear();
			bianliang.clear();
			jiajian.clear();
			xishu2.clear();
			bianliang2.clear();
			bianliang3.clear();
			xishu3.clear();
			xishunew.clear();
			bianliangnew.clear();
			
			Expression1 (duoxiangshi);
			Expression2 (duoxiangshi);
			Expression3 (duoxiangshi);
			order1=In();
			Calendar c2 = Calendar.getInstance();
			Simplify(order1);
			Calendar c3 = Calendar.getInstance();
			System.out.println("��ֵ�㷨����ʱ��" + (c3.getTimeInMillis()-c2.getTimeInMillis())+"����");
			
			xishu.clear();
			bianliang.clear();
			jiajian.clear();
			xishu2.clear();
			bianliang2.clear();
			bianliang3.clear();
			xishu3.clear();
			xishunew.clear();
			bianliangnew.clear();
			
			Expression1 (duoxiangshi);
			Expression2 (duoxiangshi);
			Expression3 (duoxiangshi);
			
			order2=In();
			Calendar c4 = Calendar.getInstance();
			Derivative(order2,duoxiangshi);
			Calendar c5 = Calendar.getInstance();
			System.out.println("���㷨����ʱ��" + (c5.getTimeInMillis()-c4.getTimeInMillis())+"����");
			
		}	
		String s1 = null;
		Date d1 = new Date();
		DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        s1 = sdf1.format(d1);  
        System.out.println("��ǰʱ��Ϊ��"+s1); 
	}
	public static String In()//���뺯��
	{
		Scanner input=new Scanner(System.in);
		String duoxiangshi=input.nextLine();//�������ʽ�����ַ������浽�ַ���duoxiangshi��
		return duoxiangshi;
	}
	public static String Check (String str)//���������Ƿ��зǷ��ַ�
	{
		String regx0="[^	a-zA-Z0-9\\+\\-\\*\\^\\. ]";
		Pattern p = Pattern.compile(regx0);
		Matcher m = p.matcher(str);
		boolean flag=m.find();//���ҷǷ�������ַ���������ַ����ܰ������˺Ϸ�������ĸ�Լ������֮����κ��ַ�
		if(flag)
		{
			System.out.println("�Ƿ����룡���������룺");
		}//������鵽�Ƿ��ַ��������쳣
		else
		{
			str=str.replaceAll(" *", "");//����ո񣬽����пո��滻�ɿ�
			str=str.replaceAll("	*", "");//����tab�������пո��滻�ɿ�
			System.out.println(str);
			
		}
		return str;
	}
	public static void Expression1 (String str)//���ڴ����ַ����е�ϵ����������ʽϵ��������ڲ��������ݽṹ

	{
		ArrayList<String> xishuf  =  new ArrayList<String>();
		String regx1="\\+|\\-";//����+���߼���
		String[] arryinshi=str.split(regx1);//����������ʽ�ֽ�ɶ����ʽ
		for(String s:arryinshi)//��ÿ����ʽ���в���
		{
			xishuf.clear();
			boolean flag=true;
			//String regx2="([0-9]+\\.[0-9]+\\^?[1-9]*[0-9]*)|([1-9][0-9]*\\^?[1-9]*[0-9]*)";//�ҳ�ϵ��
			String regx2="[1-9]+[0-9]*\\.?[0-9]*\\^?[1-9]*[0-9]*";//�ҳ�ϵ��
			Pattern p=Pattern.compile(regx2);
			Matcher m=p.matcher(s);//ȡ��ÿ����ʽ�е��������ϵ�����������ϵ�����дη�����2^3,���Ȳ����㵥������
			int index0=-1;
			while (m.find())
			{
				//index0++;
				index0=s.indexOf(m.group(),index0+1);
				if (index0>=1)
				{
					if(s.charAt(index0-1)!='^' & s.charAt(index0-1)!='.')
					{
						xishuf.add(m.group());
					}
				}
				else
				{
					xishuf.add(m.group());
				}
			}
			for(String s1:xishuf)//�ҳ��˷���ʽ��ϵ��,��������
			{
				String regx3="([0-9]+\\.[0-9]+\\^[1-9]*[0-9]*)|([1-9][0-9]*\\^[1-9]*[0-9]*)";
				Pattern pattern = Pattern.compile(regx3);
				Matcher matcher = pattern.matcher(s1);
				boolean b= matcher.matches();
				if(b)
				{
					flag=false;
				}
				if(!flag)//��ʱs1��a^b����ʽ
				{
					double t1=0;
					double t2=0;
					int index1;
					String regx4="\\^";
					String[] arrtemp=s1.split(regx4);
					t1=Double.parseDouble(arrtemp[0]);//��������ָ���ֱ����t1��t2
					t2=Double.parseDouble(arrtemp[1]);
					t1=Math.pow(t1,t2);//����˷���ֵ
					index1=xishuf.indexOf(s1);
					xishuf.set(index1, String.valueOf(t1));//�ü������滻��ԭ����a^b��ʽ
				}
			}
			double num=1.0;
			for(String s2:xishuf)
			{
				num=num*Double.parseDouble(s2);
			}
			xishu.add(num);
		}
		//System.out.print(xishu);
		xishunew=xishu;
		//System.out.print('\n');
	}
	public static void Expression2 (String str)//���ڴ����ַ����еı�����������ʽϵ��������ڲ��������ݽṹ

	{
		String regx1="\\+|\\-";//����+���߼���
		String[] arryinshi=str.split(regx1);//����������ʽ�ֽ�ɶ����ʽ
		bianliang.clear();
		ArrayList<String>chengfang=new ArrayList<String>();
		ArrayList<String>bianliangf=new ArrayList<String>();
		String regx2="\\*?[a-z]+\\^[1-9]+[0-9]*";//����Ѱ��������
		String regx3="\\*?[a-z]+";//����Ѱ��x ���� *x
		for(String s:arryinshi)
		{
			chengfang.clear();
			bianliangf.clear();
			Pattern p=Pattern.compile(regx2);
			Matcher m=p.matcher(s);
			while (m.find())
			{
				chengfang.add(m.group());
			}
			s=s.replaceAll(regx2, "");
			Pattern p1=Pattern.compile(regx3);
			Matcher m1=p1.matcher(s);
			while (m1.find())
			{
				bianliangf.add(m1.group());
			}
			String temp="";
			for(String s1:bianliangf)
			{
				temp=temp+s1;
			}
			for(String s2:chengfang)
			{
				temp=temp+s2;
			}
			bianliang.add(temp);
		}
		bianliangnew=bianliang;
		//System.out.println(bianliang);
		
	}
	public static void Expression3(String str)//���ڴ����ַ����еļӼ���

	{
		String regx="[+-]";
		Pattern p=Pattern.compile(regx);
		Matcher m=p.matcher(str);
		while (m.find())
		{
			jiajian.add(m.group());
		}
		//System.out.println(jiajian);
	}
	public static void Expression4(String str)

	{
		ArrayList<String >xishuf=new ArrayList<String >();
		boolean flag=true;
		//String regx2="([0-9]+\\.[0-9]+\\^?[1-9]*[0-9]*)|([1-9][0-9]*\\^?[1-9]*[0-9]*)";//�ҳ�ϵ��
		String regx2="[1-9]+[0-9]*\\.?[0-9]*\\^?[1-9]*[0-9]*";//�ҳ�ϵ��
		Pattern p=Pattern.compile(regx2);
		Matcher m=p.matcher(str);//ȡ��ÿ����ʽ�е��������ϵ�����������ϵ�����дη�����2^3,���Ȳ����㵥������
		int index0=-1;
		while (m.find())
		{
			//index0++;
			index0=str.indexOf(m.group(),index0+1);
			if (index0>=1)
			{
				if(str.charAt(index0-1)!='^' & str.charAt(index0-1)!='.')
				{
					xishuf.add(m.group());
				}
			}
			else
			{
				xishuf.add(m.group());
			}
		}
		for(String s1:xishuf)//�ҳ��˷���ʽ��ϵ��,��������
		{
			String regx3="([0-9]+\\.[0-9]+\\^[1-9]*[0-9]*)|([1-9][0-9]*\\^[1-9]*[0-9]*)";
			Pattern pattern = Pattern.compile(regx3);
			Matcher matcher = pattern.matcher(s1);
			boolean b= matcher.matches();
			if(b)
			{
				flag=false;
			}
			if(!flag)//��ʱs1��a^b����ʽ
			{
				double t1=0;
				double t2=0;
				int index1;
				String regx4="\\^";
				String[] arrtemp=s1.split(regx4);
				t1=Double.parseDouble(arrtemp[0]);//��������ָ���ֱ����t1��t2
				t2=Double.parseDouble(arrtemp[1]);
				t1=Math.pow(t1,t2);//����˷���ֵ
				index1=xishuf.indexOf(s1);
				xishuf.set(index1, String.valueOf(t1));//�ü������滻��ԭ����a^b��ʽ
			}
		}
		double num=1.0;
		for(String s2:xishuf)
		{
			num=num*Double.parseDouble(s2);
		}
		xishu2.add(num);
	}
	public static void Expression5(String str)

	{
		ArrayList<String>chengfang=new ArrayList<String>();
		ArrayList<String>bianliangf=new ArrayList<String>();
		String regx2="\\*?[a-z]+\\^[1-9]+[0-9]*";//����Ѱ��������
		String regx3="\\*?[a-z]+";//����Ѱ��x ���� *x
		
			chengfang.clear();
			bianliangf.clear();
			Pattern p=Pattern.compile(regx2);
			Matcher m=p.matcher(str);
			while (m.find())
			{
				chengfang.add(m.group());
			}
			str=str.replaceAll(regx2, "");
			Pattern p1=Pattern.compile(regx3);
			Matcher m1=p1.matcher(str);
			while (m1.find())
			{
				bianliangf.add(m1.group());
			}
			String temp="";
			for(String s1:bianliangf)
			{
				temp=temp+s1;
			}
			for(String s2:chengfang)
			{
				temp=temp+s2;
			}
			bianliang2.add(temp);
		
	}
	public static void Output()
	{
		jiajian.add("");
		String temp2="";
		for (int j=0 ;j< bianliang2.size() & j< xishu2.size();j++ )
		{
			String temp1="";
			temp1=xishu2.get(j)+bianliang2.get(j)+jiajian.get(j);
			temp2=temp2+temp1;
		}
		System.out.print(temp2);
		System.out.print("\n");
	}
	public static void Output2()
	{
		String temp2="";
		String temp4="";
		String temp5="";
		jiajian.add("");
		for (int j=0 ;j< bianliang3.size() & j< xishu3.size();j++ )
		{
			
				int falg=1;
				for(int i=0;i<index.size();i++)
				{
					if(index.get(i)==j)
					{
						falg=0;
					}
				}
				if(falg==1)
				{
					String temp1="";
					temp1=xishu3.get(j)+bianliang3.get(j)+jiajian.get(j);
					temp2=temp2+temp1;
				}			
		}
		Pattern p=Pattern.compile("[0-9]+\\.[0-9]+[a-z]+");
		Matcher m=p.matcher(temp2);
		while (m.find())
		{
			String temp3=m.group();
			Pattern p1=Pattern.compile("[0-9]+\\.[0-9]*");
			Matcher m1=p1.matcher(temp3);
			while (m1.find())
			{
				temp4=m1.group();
			}
			temp5= temp4 + "*";
		}
		temp2=temp2.replaceAll(temp4, temp5);
		temp2=temp2.replaceAll("\\*\\+", "\\+");
		temp2=temp2.replaceAll("\\*\\-", "-");
		temp2=temp2.replaceAll("\\*$", "");
		temp2=temp2.replaceAll("\\*\\*", "\\*");
		temp2=temp2.replaceAll("\\+$|\\-$", "");
		System.out.print(temp2);
		System.out.print("\n");
	}
	public static void Simplify(String order)


	{
		ArrayList<String >bianliangx=new ArrayList<String>();
		bianliangx=bianliangnew;
		ArrayList<String >fuzhi=new ArrayList<String>();
		ArrayList<String >result=new ArrayList<String >();
		order=order.replaceAll("!simplify", "");
		order=order.replaceAll(" +", "");
		order=order.replaceAll("	+", "");
		String regx="[a-z]+=[0-9]+";
		Pattern p=Pattern.compile(regx);
		Matcher m=p.matcher(order);//ȡ��ÿ����ʽ�е��������ϵ�����������ϵ�����дη�����2^3,���Ȳ����㵥������
		while (m.find())
		{
			fuzhi.add(m.group());
		}
		for (String s1 :fuzhi)
		{
			String regx2="=";
			String[] arrtemp=s1.split(regx2);
			String s2;
			for(int i=0;i<bianliangx.size();i++)
			{
				s2=bianliangx.get(i);
				s2=s2.replaceAll(arrtemp[0], arrtemp[1]);
				bianliangx.remove(i);
				bianliangx.add(i, s2);
			}
		}
		for (int j=0 ;j< bianliangx.size() & j< xishu.size();j++ )
		{
			String temp1="";
			temp1=xishu.get(j)+"*"+bianliangx.get(j);
			String regx7="\\*{2}";
			temp1=temp1.replaceAll(regx7, "\\*");
			result.add(temp1);
		}
		for(String s2: result)
		{
			Expression4(s2);
		}
		for(String s3: result)
		{
			Expression5(s3);
		}
		Output();
	}
	public static void Derivative(String order,String duoxiangshi)
	{
		order=order.replaceAll("!d/d", "");
		order=order.replaceAll(" +", "");
		order=order.replaceAll("	+", "");
		for(int m=0;m<bianliangnew.size();m++)
		{
			String temp=bianliangnew.get(m);
			bianliang3.add(temp);
		}
		xishu3=xishunew;
		for(int i=0;i<bianliang3.size();i++)
		{
			Pattern p=Pattern.compile(order);
			Matcher m=p.matcher(bianliang3.get(i));
			String temp="";
			String regx1="\\*?"+order+"\\^[0-9]+";
			
			while (m.find())
			{
				String temp3=m.group();
				int index0=bianliang3.get(i).indexOf(temp3);
				if( index0+1<bianliang3.get(i).length())
				{	
					if( bianliang3.get(i).charAt(index0+1)=='^')
					{
						Pattern p1=Pattern.compile(regx1);
						Matcher m1=p1.matcher(bianliang3.get(i));
						while (m1.find())
						{
							temp=m1.group();
						}
						String[] temp1 = temp.split("\\^");
						int temp2=Integer.parseInt(temp1[1]);
						if (temp2==2)
						{
							double num=xishu3.get(i);
							num=num*2.0;
							xishu3.remove(i);
							xishu3.add(i,num);
							String s1=bianliang3.get(i);
							s1=s1.replaceAll(order+"\\^2", order);
							bianliang3.remove(i);
							bianliang3.add(i,s1);
						}
						else 
						{
							double num=xishu3.get(i);
							num=num*(temp2);
							xishu3.remove(i);
							xishu3.add(i,num);
							String s2=bianliang3.get(i);
							s2=s2.replaceAll(order+"\\^"+temp1[1], order+"\\^"+String.valueOf(temp2-1));
							bianliang3.remove(i);
							bianliang3.add(i,s2);
						}
					}
										
				}
				else
				{
					String s2=bianliang3.get(i);
					s2=s2.replaceAll("\\*?"+order, "");
					bianliang3.remove(i);
					bianliang3.add(i, s2);
				}
			}
						
		}
		for(int k=0;k<bianliang3.size();k++)
		{
			Pattern p3=Pattern.compile(order);
			Matcher m3=p3.matcher(bianliangnew.get(k));
			if (!m3.find())
			{
				index.add(k);
			}
		}
		Output2();
	}
}
