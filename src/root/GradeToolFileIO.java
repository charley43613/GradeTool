package root;

import root.controllers.StdntController;

import java.io.*;

public class GradeToolFileIO {

    private static GradeToolFileIO gradeToolFileIO = new GradeToolFileIO();
    private GradeToolFileIO(){}
    public static GradeToolFileIO getGradeToolFileIO(){
        return gradeToolFileIO;
    }

    public void writeFile(StdntController stdntController, String pathname) {
        try {
            FileOutputStream f = new FileOutputStream(new File(pathname));
            ObjectOutputStream o = new ObjectOutputStream(f);
            // Write objects to file
            o.writeObject(stdntController);
            o.close();
            f.close();



        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }
    public StdntController openFile(String pathname){
        StdntController readinCtrler = null;
        try {
            FileInputStream fi = new FileInputStream(new File(pathname));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            readinCtrler = (StdntController) oi.readObject();
            oi.close();
            fi.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return readinCtrler;

}

}
