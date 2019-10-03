import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.String;

import com.alibaba.fastjson.JSONArray;

public class Main{
	static Map<String,Object> map=null; 

	public static String nameResolution(String peoplename) //��ȡ����
	{
		String regex2="(!.+,)";
		Matcher n=Pattern.compile(regex2).matcher(peoplename);
		String name=null;
		while(n.find())
		{
			name=n.group();
		}
		name=name.substring(1,name.length()-1);
		return name;
	}
	
	public static String phoneNumberResolution(String phoneNumber) //��ȡ�绰����
	{
		String regex1="(\\d{11})";
		Matcher p=Pattern.compile(regex1).matcher(phoneNumber);
		String number=null;
		while(p.find()){
            number=p.group();
        }
		return number;
	}
	
	public static String addressResolution(String address)  //��ȡһ������ַ
	{
		String regex3="(,.+\\.)";
		String reg=phoneNumberResolution(address); //address��phonenumber���ʶ���line
		Matcher a=Pattern.compile(regex3).matcher(address);
		String add=null;
		while(a.find())
		{
			add=a.group();
		}
		add = add.replaceAll(reg, "");
		add=add.substring(1,add.length()-1);
		return add;
	}
	
	public static ArrayList<String>  detailaArress1(String address) //�Ѷ�1���弶��ַ
	{
		String regex4="(?<province>[^ʡ]+������|.*?ʡ|.*?������)?(?<city>[^��]+������|.*?����|.*?������λ|.+��|��Ͻ��|.*?��|.*?��)(?<county>[^��]+��|.+��|.+��|.+��|.+����|.+��)?(?<town>[^��]+��|.+��|.+�ֵ�)?(?<village>.*)";
		Matcher m=Pattern.compile(regex4).matcher(address);
		String province="",city="",county="",town="",village="";
		
		while(m.find())
		{
			province = m.group(1)== null?"":m.group(1);
			city = m.group(2)== null?"":m.group(2);
			county=m.group(3)== null?"":m.group(3);
			town=m.group(4)==null?"":m.group(4);
			village=m.group(5)==null?"":m.group(5);
		}   
		ArrayList<String> arrayList = new ArrayList<String>();     //arraylist��̬����
		arrayList.add(province);
		arrayList.add(city);
		arrayList.add(county);
		arrayList.add(town);
		arrayList.add(village);
		return arrayList;
	}
	public static ArrayList<String>  detailAddress2(String address) //�Ѷ�2���߼���ַ
	{
        String regex4="(?<province>[^ʡ]+������|.*?ʡ|.*?������)?(?<city>[^��]+������|.*?����|.*?������λ|.+��|��Ͻ��|.*?��|.*?��)(?<county>[^��]+��|.+��|.+��|.+��|.+����|.+��)?(?<town>[^��]+��|.+��|.+�ֵ�)?(?<path>[^·]+·|.+��|.+��)?(?<num>[^��]+��)?(?<village>.*)";
        Matcher m=Pattern.compile(regex4).matcher(address);
        String province="",city="",county="",town="",path="",num="",village="";
        
		while(m.find())
		{
			province = m.group(1)== null?"":m.group(1);
			city = m.group(2)== null?"":m.group(2);
			county=m.group(3)== null?"":m.group(3);
			town=m.group(4)==null?"":m.group(4);
			path=m.group(5)==null?"":m.group(5);
			num=m.group(6)==null?"":m.group(6);
			village=m.group(7)==null?"":m.group(7);
		}   
		//System.out.println(province);
		//System.out.println(city);
		//System.out.println(county);
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(province);
		arrayList.add(city);
		arrayList.add(county);
		arrayList.add(town);
		arrayList.add(path);
		arrayList.add(num);
		arrayList.add(village);
		return arrayList;
	}
    
    public static Map<String, Object> getJson(String name,String phone,ArrayList<String> arrayList)
    {

		Object[] array =  arrayList.toArray();
		map = new LinkedHashMap<String,Object>();
		map.put("����", name.toString());
		map.put("�ֻ�", phone.toString());
		map.put("��ַ", array);
		return map;
	}


	
	public static void main(String[] args) {
	
		try {  
			StringBuffer stringBuffer = new StringBuffer("");
			FileInputStream fis = new FileInputStream(args[0]);
			InputStreamReader read = new InputStreamReader(fis, "utf-8");
			BufferedReader bufReader = new BufferedReader(read);
  
                String line = new String();  
                JSONArray jsonArray = new JSONArray();
                while ((line = bufReader.readLine()) != null) 
                {  
                	//һ�β���
                    //String json=net.sf.json.JSONArray.fromObject(addressResolution1(temp)).toString();
                    String temp1=nameResolution(line);
                    String temp2=phoneNumberResolution(line);
                    String temp3=addressResolution(line);
                              	
                    String reg="(^[0-9])";  //�����Ѷ���ѡ���弶��ַ�����߼���ַ
            		Matcher r=Pattern.compile(reg).matcher(line);          		
            		String level=null;
            		while(r.find())
            		{
            			level=r.group();
            		}
            		
                	if(level.equals("1"))
                	{
                		ArrayList<String> arrayList = detailaArress1(temp3);
    					Map<String, Object> JsonMap = getJson(temp1,temp2,arrayList);
    					//System.out.println(arrayList);
    					jsonArray.add(JsonMap);
                	}
                	else
                	{
                		ArrayList<String> arrayList =detailAddress2(temp3);
                		Map<String, Object> JsonMap = getJson(temp1,temp2,arrayList);
                		//System.out.println(arrayList);
                		 jsonArray.add(JsonMap);
                	}                          	
                }
                stringBuffer.append(jsonArray.toJSONString()).append("\n");
    			bufReader.close();
    			read.close();
    			
    			FileOutputStream fos = new FileOutputStream(args[1]);
    			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
    			osw.write(jsonArray.toString());
    			osw.flush(); 

    			 
        } catch(FileNotFoundException e)
        {
        	System.out.println("��ȡ�ļ����ݲ�������");
			e.printStackTrace();
		} 
        catch(IOException e)
        {
        	System.out.println("д���ļ����ݲ�������");
			e.printStackTrace();
		}  
	}
}
