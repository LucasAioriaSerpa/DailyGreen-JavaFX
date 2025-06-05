package org.dailygreen.dailygreen.util;

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

    public datManager() {
        for (String fileName : FILES_DATAS) {
            FileOutputStream f;
            File file = new File(PATH_FOLDER + fileName);
            if (!file.exists()) {
                file.mkdir();
                System.out.println("Arquivo .dat criado com Sucesso!\n"+fileName);
                System.out.println(file.getAbsolutePath());
            } else {
                System.out.println("Arquivo .dat existente!\n"+fileName);
            }
        }
    }
    private static boolean _checkFileName(String fileName) {
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
    public static @Nullable <T> ArrayList<T> loadArray(String fileName) {
        if (_checkFileName(fileName)) { return null; }
        ArrayList<T> arrayList = new ArrayList<>();
        try {
            File file = new File(PATH_FOLDER + fileName);
            ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(file));
            arrayList = (ArrayList<T>) OIS.readObject();
            OIS.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler o arquivo!\n" + fileName);
            e.printStackTrace();
        }
        return arrayList;
    }
    public static void addObject(Object object, String fileName) {
        ArrayList<Object> arrayList = loadArray(fileName);
        arrayList.add(object);
        saveArray(arrayList, fileName);
    }
}
