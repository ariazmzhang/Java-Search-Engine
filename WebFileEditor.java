
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WebFileEditor {
    static Path dataPath  = Paths.get(Paths.get(System.getProperty("user.dir")).getParent().toString(),"crawlerData");


    public WebFileEditor() {
    }

    public static void deleteFiles(String dirName) {
        File file = new File(dirName);
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            String[] pathNames = file.list();
            for (String pathName : pathNames) {
                String curPath = Paths.get(dirName, pathName).toString();
                deleteFiles(curPath);
            }
            file.delete();
        }
    }
    public static void createFile(int dirName, String fileName, String content){
        try {
            Path curPath = Paths.get(dataPath.toString(), String.valueOf(dirName));
            //dirname should be a path
            File parentDir = new File(curPath.toString());
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            File file = new File(parentDir,fileName);
            file.createNewFile();

            BufferedWriter out = new BufferedWriter(new FileWriter(file.getAbsolutePath(),true));
            out.write(content);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void createObject(int dirName, String fileName, Object object){
        //dirname is number
        Path curPath = Paths.get(dataPath.toString(), String.valueOf(dirName));
        try {
            //dirname should be a path
            File parentDir = new File(curPath.toString());
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            File file = new File(parentDir,fileName);
            file.createNewFile();

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath()));
            out.writeObject(object);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public  static Object readObject(int dirName, String fileName) {
        Path curPath = Paths.get(dataPath.toString(), String.valueOf(dirName), fileName);
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(curPath.toString()));
            Object object = in.readObject();
            in.close();
            return object;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public  static List<String> readFile(int dirName, String fileName) {
        List<String> page = new ArrayList<>();
        Path curPath = Paths.get(dataPath.toString(), String.valueOf(dirName),fileName);
        try {
            BufferedReader in = new BufferedReader(new FileReader(curPath.toString()));
            String s = in.readLine();
            while (s != null){
                page.add(s);
                s = in.readLine();
            }
            in.close();
            return page;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
