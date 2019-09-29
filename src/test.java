import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.String;

public class test {

	public static void main(String[] args)
	{
		String[] arr= {"����ʡ�����г��������ɽ�ֵ�ѧ԰·10�ŵ�ʮС��", //�����߼�
						"����ʡ�������������Ͻ���ѧ����·1�Ÿ���ʦ����ѧ��Է" , //�����߼�������ֵ���ͬһ��
						"����ʡ�人�к�ɽ�����пƼ���ѧ",       
						"����ʡ�������������Ͻ���ѧ԰��·2�Ÿ��ݴ�ѧ��ɽУ��",//�����߼�
						"�½�ά�������������³ľ����������������ҵ��22��",   //��������ȱ4��7
						"�Ϻ�������������·99�Ŷ���һ��С��",   //ֱϽ�У��弶    null
						"����ʡ������˼����������·61����������������",  //�߼���ȱ4
						"����ر�����������������л��ű�������۶�ֲ�﹫԰",  //�ر�������������
						"�㽭ʡ�������ϳ������·154�ſ�Ԫ��",   //�߼���ȱ4
						"����ʡ��ʩ���������������ݶ�ʩ��ʩ�ݴ��34��",};  //������
		for(int i=0;i<arr.length;i++)
		{	
			Object[] array=detailAddress2(arr[i]).toArray(); //��listֱ��תΪobject����
			for(int j=0;j<array.length;j++)
			{
				System.out.println(array[j]);
			}
			System.out.println("~~~~~~~~~~~~~~~~~");
		}

	}
	public static ArrayList<String>  detailAddress2(String address) //�Ѷ�2���߼���ַ
	{
        String regex4="(?<province>[^ʡ]+������|.*?ʡ|.*?������)(?<city>[^��]+������|.*?����|.*?������λ|.+��|��Ͻ��|.*?��|.*?��)(?<county>[^��]+��|.{2,4}��|.+��|.+��|.+����|.+��)?(?<town>.{2,4}��|.+��|.+�ֵ�)?(?<path>[^·]+·|.+��|.+��|.+��)?(?<num>[^��]+��)?(?<village>.*)";
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
