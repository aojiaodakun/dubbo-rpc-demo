package register;

import framework.URL;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class RemoteMapRegister {

    private static Map<String,List<URL>> REGISTER_MAP = new HashMap<>();

    private static Random random = new Random();

    private static final String FILENAME = "temp.txt";

    public static void register(String interfaceName, URL url){
        List<URL> list = REGISTER_MAP.get(url);
        if(list == null){
            list = new ArrayList<>();
        }
        list.add(url);
        REGISTER_MAP.put(interfaceName,list);

        saveFile();
    }

    public static URL random(String inferfaceName){
        REGISTER_MAP = getFile();

        List<URL> list = REGISTER_MAP.get(inferfaceName);
        int n = random.nextInt(list.size());
        return list.get(n);
    }

    private static void saveFile(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(FILENAME);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(REGISTER_MAP);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Map<String,List<URL>> getFile(){
        try {
            FileInputStream fileInputStream = new FileInputStream(FILENAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Map<String,List<URL>>)objectInputStream.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
