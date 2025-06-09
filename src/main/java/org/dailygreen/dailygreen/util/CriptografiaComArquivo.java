package org.dailygreen.dailygreen.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * <h1>CriptografiaComArquivo</h1>
 * <p>Esta classe verifica se existe um arquivo `key.aes`.</p> <br>
 * <p>- Para criptografar, recebe um texto do tipo String e chave em SecretKey.</p> <br>
 * <p>- Para descriptografar, recebe a criptografia em String e chave em SecretKey.</p>
 */
public class CriptografiaComArquivo {
    private static final String ARQUIVO_CHAVE = "src/main/resources/key.aes";
    // ? GETTER ARQUIVO_CHAVE
    public String getARQUIVO_CHAVE() {return ARQUIVO_CHAVE;}
    // ? Gera uma nova chave AES
    public static SecretKey gerarChave() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }
    // ? Salva a chave no arquivo
    public static void salvarChaveEmArquivo(SecretKey chave, String caminho) throws IOException {
        String chaveBase64 = Base64.getEncoder().encodeToString(chave.getEncoded());
        try (FileWriter writer = new FileWriter(caminho)) {writer.write(chaveBase64);}
    }
    // ? Lê a chave do arquivo
    public static SecretKey lerChaveDeArquivo(String caminho) throws IOException {
        StringBuilder conteudo = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {conteudo.append(linha);}
        }
        byte[] chaveBytes = Base64.getDecoder().decode(conteudo.toString());
        return new SecretKeySpec(chaveBytes, "AES");
    }
    // ? Gera IV aleatório
    public static byte[] gerarIV() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return iv;
    }
    // ? Criptografa string
    public static String criptografar(String texto, SecretKey chave) throws Exception {
        byte[] iv = gerarIV();
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, chave, ivSpec);
        byte[] criptografado = cipher.doFinal(texto.getBytes("UTF-8"));
        byte[] combinado = new byte[iv.length + criptografado.length];
        System.arraycopy(iv, 0, combinado, 0, iv.length);
        System.arraycopy(criptografado, 0, combinado, iv.length, criptografado.length);
        return Base64.getEncoder().encodeToString(combinado);
    }
    // ? Descriptografa string
    public static String descriptografar(String criptografadoBase64, SecretKey chave) throws Exception {
        byte[] combinado = Base64.getDecoder().decode(criptografadoBase64);
        byte[] iv = new byte[16];
        byte[] dados = new byte[combinado.length - 16];
        System.arraycopy(combinado, 0, iv, 0, 16);
        System.arraycopy(combinado, 16, dados, 0, dados.length);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, chave, ivSpec);
        byte[] texto = cipher.doFinal(dados);
        return new String(texto, "UTF-8");
    }

    // * teste *
//    public static void main(String[] args) {
//        try {
//            SecretKey chave;
//            File arquivoChave = new File(ARQUIVO_CHAVE);
//            if (arquivoChave.exists()) {
//                chave = lerChaveDeArquivo(ARQUIVO_CHAVE);
//                System.out.println("Chave lida do arquivo.");
//            } else {
//                chave = gerarChave();
//                salvarChaveEmArquivo(chave, ARQUIVO_CHAVE);
//                System.out.println("Chave gerada e salva no arquivo.");
//            }
//            String mensagem = "Informação confidencial!";
//            String criptografado = criptografar(mensagem, chave);
//            String descriptografado = descriptografar(criptografado, chave);
//            System.out.println("\nMensagem original:       " + mensagem);
//            System.out.println("Mensagem criptografada: " + criptografado);
//            System.out.println("Mensagem descriptografada: " + descriptografado);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

