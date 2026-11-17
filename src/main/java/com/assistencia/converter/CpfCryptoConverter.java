package com.assistencia.converter;

// Importações das interfaces do JPA para conversão automática de atributos
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

// Importações das bibliotecas nativas de criptografia do Java (JCA) e manipulação de texto/Base64
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
- Atende à Regra de Negócio 03 (RN03):
- O CPF dos clientes deve ser armazenado de forma criptografada no banco de dados.
- Converte de forma transparente os dados ao persistir e ao recuperar do banco.
 */
// Indica ao JPA que esta classe atua como um conversor de tipos entre a entidade Java e a coluna do banco
@Converter
public class CpfCryptoConverter implements AttributeConverter<String, String> {

    // Define o algoritmo AES com modo de operação ECB e preenchimento PKCS5Padding
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    // Chave de 128-bit (16 bytes) para criptografia AES simétrica
    private static final byte[] SECRET_KEY = "MinhaChaveSec123".getBytes(StandardCharsets.UTF_8);

    // Executado automaticamente pelo JPA antes de salvar ou atualizar o CPF no banco de dados
    @Override
    public String convertToDatabaseColumn(String cpf) {
        // Retorna nulo caso o CPF não tenha sido informado ou contenha apenas espaços
        if (cpf == null || cpf.isBlank()) {
            return null;
        }
        try {
            // Instancia o motor de criptografia com o algoritmo configurado
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // Cria a especificação da chave simétrica AES a partir dos bytes da chave secreta
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY, "AES");
            // Inicializa o cifrador no modo de encriptação
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            // Remove espaços das extremidades, converte o CPF para bytes UTF-8 e aplica a criptografia
            byte[] encryptedBytes = cipher.doFinal(cpf.trim().getBytes(StandardCharsets.UTF_8));
            // Converte os bytes criptografados para texto Base64 para armazenamento seguro na coluna
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            // Interrompe a persistência caso ocorra alguma falha técnica na criptografia
            throw new IllegalStateException("Falha ao criptografar CPF para persistência", e);
        }
    }

    // Executado automaticamente pelo JPA ao carregar o registro do banco de dados para a entidade
    @Override
    public String convertToEntityAttribute(String dbData) {
        // Retorna nulo caso o valor vindo do banco esteja nulo ou em branco
        if (dbData == null || dbData.isBlank()) {
            return null;
        }
        try {
            // Instancia o motor de criptografia com o mesmo algoritmo utilizado na gravação
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // Recria a especificação da chave simétrica AES
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY, "AES");
            // Inicializa o cifrador no modo de descriptografia
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            // Decodifica a string Base64 do banco de volta para o array de bytes cifrados
            byte[] decodedBytes = Base64.getDecoder().decode(dbData.trim());
            // Descriptografa os bytes e reconstrói a String original do CPF em UTF-8
            return new String(cipher.doFinal(decodedBytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            // Se o dado não estiver cifrado (por exemplo, migração legada), retorna o dado original
            return dbData;
        }
    }
}