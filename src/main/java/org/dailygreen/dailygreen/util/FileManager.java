package org.dailygreen.dailygreen.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    @SuppressWarnings("unchecked")
    public static <T>List<T> carregar(String path) {
        try (ObjectInputStream OIS = new ObjectInputStream(new FileInputStream(path))) { return (List<T>) OIS.readObject(); }
        catch (Exception e) { return new ArrayList<>(); }
    }

    public static <T> void salvar(String path, List<T> lista) {
        try (ObjectOutputStream OOS = new ObjectOutputStream(new FileOutputStream(path))) { OOS.writeObject(lista); }
        catch (Exception e) { e.printStackTrace(); }
    }
}
