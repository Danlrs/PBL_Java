package aplication.filemanagement;

import aplication.interfaces.Identificavel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

/**
 * Classe genérica para gerenciar operações de leitura e escrita em arquivos JSON para itens que implementam a interface Identificavel.
 *
 * @param <T> O tipo de item que será gerenciado pela classe, deve implementar a interface Identificavel.
 */
public abstract class FileManager<T extends Identificavel> {
    protected final String filePath;
    protected final Gson gson;
    protected List<T> items;
    protected final Type typeOfT;

    /**
     * Construtor da classe FileManager.
     *
     * @param filePath Caminho do arquivo onde os dados serão armazenados.
     * @param typeOfT  Tipo concreto dos itens gerenciados.
     */
    public FileManager(String filePath, Type typeOfT) {
        this.filePath = filePath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.items = new ArrayList<>();
        this.typeOfT = typeOfT;
    }

    /**
     * Lê os dados do arquivo JSON e retorna como uma string.
     *
     * @return Dados lidos do arquivo como string JSON.
     */
    protected String readFromFile() {
        StringBuilder jsonData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData.toString();
    }

    /**
     * Escreve a lista de itens no arquivo JSON.
     *
     * @param items Lista de itens a serem salvos no arquivo.
     */
    protected void writeToFile(List<T> items) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String json = gson.toJson(items);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera um item pelo seu ID.
     *
     * @param id ID do item a ser recuperado.
     * @return O item correspondente ao ID ou null se não for encontrado.
     */
    public T getById(UUID id) {
        items = getAll();  // Carrega os itens do arquivo
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Recupera todos os itens armazenados no arquivo.
     *
     * @return Lista de todos os itens do arquivo.
     */
    public List<T> getAll() {
        String jsonData = readFromFile();  // Lê os dados do arquivo JSON
        List<T> items = new ArrayList<>();  // Cria uma nova lista para armazenar os itens

        if (jsonData != null && !jsonData.isEmpty()) {
            items = gson.fromJson(jsonData, typeOfT);  // Desserializa o JSON diretamente na lista de T
        }
        return items;  // Retorna a lista de itens
    }

    /**
     * Salva um novo item no arquivo.
     *
     * @param item Item a ser salvo.
     */
    public void save(T item) {
        items = getAll();  // Carrega os itens existentes
        items.add(item);   // Adiciona o novo item
        writeToFile(items); // Escreve no arquivo
    }

    /**
     * Remove um item pelo seu ID.
     *
     * @param id ID do item a ser removido.
     */
    public void delete(UUID id) {
        items.removeIf(item -> item.getId().equals(id));
        writeToFile(items); // Atualiza o arquivo
    }

    /**
     * Remove todos os itens do arquivo.
     */
    public void deleteAll() {
        items.clear();  // Limpa a lista
        writeToFile(items);  // Atualiza o arquivo
    }

    /**
     * Atualiza um item existente.
     *
     * @param item Item a ser atualizado.
     */
    public void update(T item) {
        // Carrega a lista de itens do arquivo
        items = getAll();
        // Procura pelo item na lista para atualizar
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(item.getId())) {
                items.set(i, item);  // Atualiza o item na lista
                break;
            }
        }
        // Salva todos os itens no arquivo
        writeToFile(items);
    }


}
