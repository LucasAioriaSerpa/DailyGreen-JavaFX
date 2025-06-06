package org.dailygreen.dailygreen.Users.Administrador.utils;

import org.dailygreen.dailygreen.Users.Administrador.models.Administrador;
import org.dailygreen.dailygreen.util.datManager;

import java.io.*;
import java.util.ArrayList;

public class datAdm extends datManager {
    public datAdm() throws IOException {super();}
    public static boolean saveArray(ArrayList<Administrador> list) {
        System.err.println("func: saveArray");
        FileOutputStream f; try {
            File file = new File(getPathFolder()+getFiles()[2]);
            if (file.exists()) {
                ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(file));
                OOS.writeObject(list);
                OOS.close();
                System.out.println("Arquivo .dat salvo com Sucesso!\n"+getFiles()[2]);
                return true;
            }
        } catch (Exception e) {
            System.err.println("Erro ao gravar arquivo: "+e.getMessage());
            return false;
        }
        return false;
    }
    public static ArrayList<Administrador> loadArray() {
        System.err.println("func: loadArray");
        ArrayList<Administrador> list = new ArrayList<>();
        File file = new File(getPathFolder() + getFiles()[2]);
        if (!file.exists()) {return list;}
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?>) {list = (ArrayList<Administrador>) obj;}
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }
        return list;
    }
    public static boolean addObject(Administrador obj) {
        ArrayList<Administrador> list = loadArray();
        if (list == null) {
            list = new ArrayList<>();
            list.add(obj);
        }
        list.add(obj);
        saveArray(list);
        return true;
    }
}
