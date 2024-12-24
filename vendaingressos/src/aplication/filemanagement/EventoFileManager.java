package aplication.filemanagement;

import aplication.model.Evento;
import com.google.gson.reflect.TypeToken;
import java.util.List;

/**
 * Gerenciador de arquivos para o tipo Evento.
 * Esta classe herda de FileManager e é especializada para manipular dados do tipo Evento.
 */
public class EventoFileManager extends FileManager<Evento> {

    /**
     * Construtor da classe EventoFileManager.
     * Define o caminho do arquivo de eventos e o tipo concreto de lista de eventos.
     */
    public EventoFileManager() {
        // Passa o tipo específico para o construtor da classe base
        super("src/aplication/jsonfiles/eventos.json", new TypeToken<List<Evento>>() {}.getType());
    }
}
