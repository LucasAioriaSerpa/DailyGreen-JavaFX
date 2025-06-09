package org.dailygreen.dailygreen.Users.Administrador.dao;

import org.dailygreen.dailygreen.Users.Administrador.models.Denuncia;
import org.dailygreen.dailygreen.Users.Administrador.views.DenunciaView;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DenunciaDAO {
    public static final String REPORT_FILE = "denuncia.dat";

    public static void registrar(Denuncia denuncia) {
        List<Denuncia> denuncias = mostrar();
        denuncias.add(denuncia);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REPORT_FILE))){
            oos.writeObject(denuncias);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static List<Denuncia> mostrar() {
        File file = new File(REPORT_FILE);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(REPORT_FILE))){
            return (List<Denuncia>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static int updateId(){
        List<Denuncia> denuncias = mostrar();
        if (denuncias.isEmpty()) return 1;
        int maxId = denuncias.stream()
                .mapToInt(Denuncia::getId)
                .max()
                .orElse(0);
        return maxId + 1;
    }

    public static void removerPorId(Integer denuncia){
        List<Denuncia> denuncias = mostrar();
        denuncias.removeIf(d -> d.getId().equals(denuncia));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REPORT_FILE))){
            oos.writeObject(denuncias);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
