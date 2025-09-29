package org.dailygreen.dailygreen.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    @SuppressWarnings("unchecked")
    public static <T>List<T> carregar(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (List<T>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static <T> void salvar(String path, List<T> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
