package org.dailygreen.dailygreen.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * <h1>CriptografiaComArquivo</h1>
 * <p>Esta classe verifica se existe um arquivo `key.aes`.</p> <br>
 * <p>- Para criptografar, recebe um texto do tipo String e chave em SecretKey.</p> <br>
 * <p>- Para descriptografar, recebe a criptografia em String e chave em SecretKey.</p>
 */
public class Cryptography {
    private static final String ARQUIVO_CHAVE = "src/main/resources/key.aes";

    // ? GETTER ARQUIVO_CHAVE
    public static String getARQUIVO_CHAVE() {
        return ARQUIVO_CHAVE;
    }

    // ? Gera uma nova chave AES
    public static SecretKey gerarChave() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

    // ? Salva a chave no arquivo
    public static void salvarChaveEmArquivo(SecretKey chave, String caminho) throws IOException {
        String chaveBase64 = Base64.getEncoder().encodeToString(chave.getEncoded());
        try (FileWriter writer = new FileWriter(caminho)) {
            writer.write(chaveBase64);
        }
    }

    // ? Lê a chave do arquivo
    public static SecretKey lerChaveDeArquivo(String caminho) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String conteudo = reader.readLine();
            byte[] chaveBytes = Base64.getDecoder().decode(conteudo);
            return new SecretKeySpec(chaveBytes, "AES");
        }
    }

    // ? Gera IV aleatório
    private static final int IV_LENGTH = 16;

    private static byte[] gerarIV() {
        byte[] iv = new byte[IV_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        return iv;
    }

    // ? Criptografa string
    public static String criptografar(String texto, SecretKey chave) throws Exception {
        byte[] iv = gerarIV();
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, chave, ivSpec);
        byte[] textoCriptografado = cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));
        byte[] ivETexto = new byte[iv.length + textoCriptografado.length];
        System.arraycopy(iv, 0, ivETexto, 0, iv.length);
        System.arraycopy(textoCriptografado, 0, ivETexto, iv.length, textoCriptografado.length);
        return Base64.getEncoder().encodeToString(ivETexto);
    }

    // ? Descriptografa string
    public static String descriptografar(String textoCriptografado, SecretKey chave) throws Exception {
        try {
            byte[] ivETexto = Base64.getDecoder().decode(textoCriptografado.trim());
            if (ivETexto.length < IV_LENGTH) {
                throw new IllegalArgumentException("Dados criptografados inválidos");
            }
            byte[] iv = new byte[IV_LENGTH];
            byte[] textoCifrado = new byte[ivETexto.length - IV_LENGTH];
            System.arraycopy(ivETexto, 0, iv, 0, IV_LENGTH);
            System.arraycopy(ivETexto, IV_LENGTH, textoCifrado, 0, textoCifrado.length);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, chave, new IvParameterSpec(iv));
            byte[] textoDescriptografado = cipher.doFinal(textoCifrado);
            return new String(textoDescriptografado, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            throw new Exception("Formato de dados criptografados inválido");
        } catch (Exception e) {
            throw new Exception("Erro na descriptografia: " + e.getMessage());
        }
    }
}

