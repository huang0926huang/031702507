import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.String;

import com.alibaba.fastjson.JSONArray;

public class Main{
	static Map<String,Object> map=null; 

	public static String nameResolution(String peoplename) //获取名字
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
	
	public static String phoneNumberResolution(String phoneNumber) //获取电话号码
	{
		String regex1="(\\d{11})";
		Matcher p=Pattern.compile(regex1).matcher(phoneNumber);
		String number=null;
		while(p.find()){
            number=p.group();
        }
		return number;
	}
	
	public static String addressResolution(String address)  //获取一整串地址
	{
		String regex3="(,.+\\.)";
		String reg=phoneNumberResolution(address); //address和phonenumber本质都是line
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
	
	public static ArrayList<String>  detailaArress1(String address) //难度1，五级地址
	{
		String regex4="(?<province>[^省]+自治区|.*?省|.*?行政区)?(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇|.+街道)?(?<village>.*)";
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
		ArrayList<String> arrayList = new ArrayList<String>();     //arraylist动态数组
		arrayList.add(province);
		arrayList.add(city);
		arrayList.add(county);
		arrayList.add(town);
		arrayList.add(village);
		return arrayList;
	}
	public static ArrayList<String>  detailAddress2(String address) //难度2，七级地址
	{
        String regex4="(?<province>[^省]+自治区|.*?省|.*?行政区)?(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇|.+街道)?(?<path>[^路]+路|.+街|.+巷)?(?<num>[^号]+号)?(?<village>.*)";
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
		map.put("姓名", name.toString());
		map.put("手机", phone.toString());
		map.put("地址", array);
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
                	//一次操作
                    //String json=net.sf.json.JSONArray.fromObject(addressResolution1(temp)).toString();
                    String temp1=nameResolution(line);
                    String temp2=phoneNumberResolution(line);
                    String temp3=addressResolution(line);
                              	
                    String reg="(^[0-9])";  //根据难度来选择五级地址还是七级地址
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
        	System.out.println("读取文件内容操作出错");
			e.printStackTrace();
		} 
        catch(IOException e)
        {
        	System.out.println("写入文件内容操作出错");
			e.printStackTrace();
		}  
	}
}
