

import java.lang.reflect.Method;

import com.jfshare.finagle.thrift.manager.SlotImage;
import com.jfshare.manager.model.TbSlotImage;



public class CreateBean2Form {

	public static void main(String[] args) {
		
		//tb_order_user_10
		//tb_order_info_user_10
		
		/*String orderTableName = "tb_order_user_10";
		System.out.println(orderTableName.replaceAll("tb_order", "tb_order_info"));
		*/
		
		//System.out.println(System.getProperty("user.dir"));
		//UUID uuid = UUID.randomUUID();
		//String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        //String a = s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
		//System.out.println(a);
		int conut = 0;
		
		Class<?> clazz1 = SlotImage.class;
		String clazz1Name = "slotImage";
		Class clazz2 = TbSlotImage.class;
		String clazz2Name = "tbSlotImage";
		
		Method method1Array[] = clazz1.getDeclaredMethods();
		//Method method2Array[] = clazz2.getDeclaredMethods();
		for(int i=0; i<method1Array.length; i++){
			Method method1 = method1Array[i];
			//Method method2 = method1Array[i];
			//buyerOrderDetail.setUserId(tbOrder.getUserId());
			if(method1.getName().indexOf("set") == 0 && method1.getName().indexOf("IsSet") == -1 && !method1.getName().equals("setFieldValue")) {
				//System.out.println(clazz1Name+"." + method1.getName() + "(BeanUtils.valueToString(" + clazz2Name + "." + method1.getName().replaceFirst("set", "get") + "()));");
				
				String clazz1NameGetFunctionStr = clazz1Name + "." + method1.getName().replaceFirst("set", "get");
				String clazz2NameGetFunctionStr = clazz2Name + "." + method1.getName().replaceFirst("set", "get");
				//System.out.println(clazz1Name+"." + method1.getName() + "(" + clazz2NameGetFunctionStr + "() == null ? "+clazz1NameGetFunctionStr + "()" +" : "+clazz2NameGetFunctionStr+"() );");
				//System.out.println(clazz1Name+"." + method1.getName() + "(ConvertUtil.valueToString(" +clazz2Name+".get(\"\")));");
				//System.out.println(clazz1Name+"." + method1.getName() + "();");
				System.out.println(clazz1Name+"." + method1.getName() + "(" + clazz2NameGetFunctionStr + "());");
				conut++;
			}
		}
		
		System.out.println(conut);
	}

}
