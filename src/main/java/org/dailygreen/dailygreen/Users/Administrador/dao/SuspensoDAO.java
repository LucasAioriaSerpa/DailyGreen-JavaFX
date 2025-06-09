package org.dailygreen.dailygreen.Users.Administrador.dao;

import org.dailygreen.dailygreen.Users.Administrador.models.Denuncia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SuspensoDAO {
    public static final String SUSPEND_FILE = "suspend.dat";

    public static void registrar(Denuncia denuncia) {
        List<Denuncia> denuncias = mostrar();
        denuncias.add(denuncia);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SUSPEND_FILE))){
            oos.writeObject(denuncias);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static List<Denuncia> mostrar() {
        File file = new File(SUSPEND_FILE);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SUSPEND_FILE))){
            return (List<Denuncia>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}

