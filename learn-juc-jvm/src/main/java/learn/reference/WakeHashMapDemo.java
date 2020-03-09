package learn.reference;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * @author xrb
 * @create 2020-03-09 21:20
 */
public class WakeHashMapDemo {
    //缓存
    public static void main(String[] args) {
        myHashMap();
        System.out.println("---------------------");
        myWakeHashMap();
    }
    private static void myHashMap(){
        HashMap<Integer,String> map = new HashMap<>();
        Integer key = new Integer(1);
        String value = "hashMap";
        map.put(key,value);
        System.out.println(map);
        key = null;
        System.out.println(map);
        System.gc();
        System.out.println(map);
    }
    private static  void myWakeHashMap(){
        WeakHashMap<Integer,String> map = new WeakHashMap<>();
        Integer key = new Integer(1);
        String value = "hashMap";
        map.put(key,value);
        System.out.println(map);
        key = null;
        System.out.println(map);
        System.gc();
        System.out.println(map);
    }
}
