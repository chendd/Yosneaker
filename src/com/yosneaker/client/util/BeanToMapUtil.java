package com.yosneaker.client.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class BeanToMapUtil {
    
	public static Map getValue(Object obj)  
	  {  
		Map<String,Object> map = new LinkedHashMap<String,Object>();  
        //key值 应该是 Person类中的属性名，利用反射机制  
        Field[] fields = obj.getClass().getDeclaredFields();  
        for(int i=0; i<fields.length; i++){  
            String field = fields[i].toString();  
            String[] keys = field.split("\\.");  
            String key = keys[keys.length-1];  
            char toUpperCase = (char)(key.charAt(0)-32);  
            String keyUpper = key.replace(key.charAt(0),toUpperCase);  
            Method getMethod;  
            try {  
                getMethod = obj.getClass().getDeclaredMethod("get"+keyUpper);//根据 field得到对应的get方法  
                Object value = getMethod.invoke(obj);  
                map.put(key, value);  
            } catch (NoSuchMethodException e) {  
                e.printStackTrace();  
            } catch (SecurityException e) {  
                e.printStackTrace();  
            }catch (IllegalAccessException e) {  
                e.printStackTrace();  
            } catch (IllegalArgumentException e) {  
                e.printStackTrace();  
            } catch (InvocationTargetException e) {  
                e.printStackTrace();  
            }         
        }  
        return map;  
	  }  
}
