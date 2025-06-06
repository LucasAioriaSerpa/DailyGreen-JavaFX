package org.dailygreen.dailygreen.util;

import org.dailygreen.dailygreen.Users.Administrador.models.Administrador;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;

public class datManager {
    private static final String PATH_FOLDER = "src/main/resources/db_dailygreen/";
    private static final String[] FILES_DATAS = {
            "participante.dat",
            "organizacao.dat",
            "administrador.dat",
            "post.dat",
            "evento.dat",
            "denuncia.dat",
            "banido.dat",
            "suspenso.dat"
    };

    public datManager() throws IOException {
        for (String fileName : FILES_DATAS) {
            FileOutputStream f;
            File file = new File(PATH_FOLDER + fileName);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Arquivo .dat criado com Sucesso!\n"+fileName);
                System.out.println(file.getAbsolutePath());
            } else {
                System.out.println("Arquivo .dat existente!\n"+fileName);
            }
        }
    }
    public static String getPathFolder() {
        return PATH_FOLDER;
    }
    public static String[] getFiles() {
        return FILES_DATAS;
    }
    public static boolean _checkFileName(String fileName) {
        File file = new File(PATH_FOLDER + fileName);
        if (!file.exists()) {
            System.out.println("Arquivo .dat invalido!\n"+fileName);
            return true;
        }
        System.out.println("Arquivo .dat valido!\n"+fileName);
        return false;
    }
    public static <T> void saveArray(ArrayList<T> arrayList, String fileName) {
        if (_checkFileName(fileName)) { return; }
        FileOutputStream f; try {
            File file = new File(PATH_FOLDER + fileName);
            if (file.exists()) {
                ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(file));
                OOS.writeObject(arrayList);
                OOS.close();
                System.out.println("Arquivo .dat salvo com Sucesso!\n"+fileName);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo!\n"+fileName);
        }
    }
    public static <T> ArrayList<T> loadArray(String fileName) {
        if (_checkFileName(fileName)) { return new ArrayList<>(); }
        ArrayList<T> arrayList = new ArrayList<>();
        File file = new File(PATH_FOLDER + fileName);
        if (!file.exists()) { return arrayList; }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?>) {arrayList = (ArrayList<T>) obj;}
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }
        return arrayList;
    }
    public static void addObject(Object object, String fileName) {
        ArrayList<Object> arrayList = loadArray(fileName);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            arrayList.add(object);
        }
        arrayList.add(object);
        saveArray(arrayList, fileName);
    }
}
