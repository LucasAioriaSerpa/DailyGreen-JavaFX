package org.dailygreen.dailygreen.util.DAT;

import org.dailygreen.dailygreen.Users.User;

import java.io.*;

public class DATuser {
    private static final String FILEuser = "src/main/resources/user.dat";
    public static String getFile() {return FILEuser;}
    public DATuser() {check();}
    public static boolean check() {
        File file = new File(FILEuser);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("User.dat Created!");
                return false;
            } catch (Exception e) {
                throw new RuntimeException("Error creating User.dat", e);
            }
        }
        return true;
    }

    public static User getUser() {
        check();
        try (FileInputStream fis = new FileInputStream(FILEuser);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            System.out.println("User.dat Read!");
            return (User) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("Error reading User.dat!", e);
        }
    }

    public static void setUser(User user) {
        check();
        try (FileOutputStream FOS = new FileOutputStream(FILEuser);
             ObjectOutputStream OOS = new ObjectOutputStream(FOS)) {
            OOS.writeObject(user);
            System.out.println("User.dat Updated!");
        } catch (Exception e) {
            throw new RuntimeException("Error writing User.dat!", e);
        }
    }
}

