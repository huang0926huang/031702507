import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.String;

public class test {

	public static void main(String[] args)
	{
		String[] arr= {"福建省莆田市城厢区凤凰山街道学园路10号第十小区", //正常七级
						"福建省福州市闽侯县上街镇学府南路1号福建师范大学榕苑" , //正常七级，镇与街道是同一级
						"湖北省武汉市洪山区华中科技大学",       
						"福建省福州市闽侯县上街镇学园南路2号福州大学旗山校区",//正常七级
						"新疆维吾尔族自治区乌鲁木齐市新市区温州商业街22号",   //自治区，缺4，7
						"上海市闵行区凯城路99号东城一号小区",   //直辖市，五级    null
						"福建省厦门市思明区湖滨北路61号厦门市人民政府",  //七级，缺4
						"香港特别行政区中西区香港中环雅宾利道香港动植物公园",  //特别行政区，道？
						"浙江省杭州市上城区解放路154号奎元馆",   //七级，缺4
						"湖北省恩施土家族苗族自治州恩施市施州大道34号",};  //自治州
		for(int i=0;i<arr.length;i++)
		{	
			Object[] array=detailAddress2(arr[i]).toArray(); //将list直接转为object数组
			for(int j=0;j<array.length;j++)
			{
				System.out.println(array[j]);
			}
			System.out.println("~~~~~~~~~~~~~~~~~");
		}

	}
	public static ArrayList<String>  detailAddress2(String address) //难度2，七级地址
	{
        String regex4="(?<province>[^省]+自治区|.*?省|.*?行政区)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.{2,4}区|.+市|.+旗|.+海域|.+岛)?(?<town>.{2,4}区|.+镇|.+街道)?(?<path>[^路]+路|.+街|.+巷|.+道)?(?<num>[^号]+号)?(?<village>.*)";
        Matcher m=Pattern.compile(regex4).matcher(address);
        String province=null,city=null,county=null,town=null,path=null,num=null,village=null;
        
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
}
