package org.dailygreen.dailygreen.Users.Administrador.dao;

import org.dailygreen.dailygreen.Users.Administrador.models.Denuncia;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DenunciaDAO {
    public static final String REPORT_FILE = "src/main/resources/db_dailygreen/denuncia.dat";

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

    public static void removerPorEmail(String denuncia){
        List<Denuncia> denuncias = mostrar();
        denuncias.removeIf(d -> d.getParticipante().equalsIgnoreCase(denuncia));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REPORT_FILE))){
            oos.writeObject(denuncias);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static List<Denuncia> filtrar(String tipo, String termo){
        final String finalTermo = termo.toLowerCase();
        return mostrar().stream().filter(d -> {
            switch (tipo) {
                case "ID": return d.getId().toString().contains(finalTermo);
                case "Participante": return d.getParticipante().toLowerCase().contains(finalTermo);
                case "Titulo": return d.getTitulo().toLowerCase().contains(finalTermo);
                case "Motivo": return d.getMotivo().toLowerCase().contains(finalTermo);
                case "Data": return d.getData().toString().contains(finalTermo);
                case "Status": return d.getStatus().toLowerCase().contains(finalTermo);
                default: return true;
            }
        }).collect(Collectors.toList());
    }

}
