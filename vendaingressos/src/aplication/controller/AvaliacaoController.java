package aplication.controller;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;
import aplication.model.Avaliacao;
import aplication.model.Usuario;
import aplication.model.Evento;
import aplication.filemanagement.AvaliacaoFileManager;

/**
 * Classe responsável pelo controle das avaliações.
 * Gerencia a criação, recuperação, atualização e exclusão de avaliações feitas por usuários em eventos.
 */
public class AvaliacaoController {

    private final AvaliacaoFileManager avaliacaoFileManager;
    private final UsuarioController usuarioController;
    private final EventoController eventoController;

    /**
     * Construtor da classe AvaliacaoController.
     * Inicializa o gerenciador de arquivos de avaliações e controla dependências de usuários e eventos.
     */
    public AvaliacaoController() {
        this.avaliacaoFileManager = new AvaliacaoFileManager();
        this.usuarioController = new UsuarioController();
        this.eventoController = new EventoController();
    }

    /**
     * Cria uma nova avaliação para um evento.
     *
     * @param comentarios Comentários do usuário sobre o evento.
     * @param rating      Nota atribuída ao evento (de 1 a 5).
     * @param idUsuario   ID do usuário que está realizando a avaliação.
     * @param idEvento    ID do evento que está sendo avaliado.
     * @return A avaliação criada, ou null se o evento ou usuário não forem encontrados.
     * @throws SecurityException Se o evento ainda estiver ativo, impedindo a avaliação antes de sua realização.
     */
    public Avaliacao criarAvaliacao(String comentarios, int rating, UUID idUsuario, UUID idEvento) {
        Evento evento = eventoController.getById(idEvento);
        Usuario usuario = usuarioController.getById(idUsuario);
        Avaliacao avaliacao = new Avaliacao(comentarios, rating, idUsuario, idEvento);
        if (evento == null || usuario == null) {
            return null;
        }
        if (!evento.isAtivo()) {
            avaliacaoFileManager.save(avaliacao);
            return avaliacao;
        }
        throw new SecurityException("Só é possível avaliar após a realização do evento!");
    }

    /**
     * Recupera uma avaliação pelo seu ID.
     *
     * @param id ID da avaliação.
     * @return A avaliação correspondente ao ID.
     */
    public Avaliacao getById(UUID id) {
        return avaliacaoFileManager.getById(id);
    }

    /**
     * Recupera todas as avaliações cadastradas.
     *
     * @return Lista com todas as avaliações.
     */
    public List<Avaliacao> getAll() {
        return avaliacaoFileManager.getAll();
    }

    /**
     * Recupera todas as avaliações de um evento específico.
     *
     * @param idEvento ID do evento cujas avaliações serão retornadas.
     * @return Lista de avaliações associadas ao evento.
     */
    public List<Avaliacao> getAvaliacoesEvento(UUID idEvento) {
        return this.getAll().stream()
                .filter(avaliacao -> avaliacao.getIdEvento().equals(idEvento))
                .collect(Collectors.toList());
    }

    /**
     * Obtém a avaliação de um evento feita por um usuário específico.
     *
     * @param idEvento o identificador único do evento a ser pesquisado.
     * @param idUsuario o identificador único do usuário que realizou a avaliação.
     * @return a instância de {@link Avaliacao} correspondente ao evento e usuário fornecidos,
     *         ou {@code null} se nenhuma avaliação correspondente for encontrada.
     */
    public Avaliacao getAvaliacaoEventoUsuario(UUID idEvento, UUID idUsuario) {
        return this.getAll().stream()
                .filter(avaliacao -> avaliacao.getIdEvento().equals(idEvento) && avaliacao.getIdUsuario().equals(idUsuario))
                .findFirst()
                .orElse(null); // Retorna null se nenhuma avaliação for encontrada
    }



    /**
     * Recupera todas as avaliações feitas por um usuário específico.
     *
     * @param idUsuario ID do usuário cujas avaliações serão retornadas.
     * @return Lista de avaliações feitas pelo usuário.
     */
    public List<Avaliacao> getAvaliacoesUsuario(UUID idUsuario) {
        return this.getAll().stream()
                .filter(avaliacao -> avaliacao.getIdUsuario().equals(idUsuario))
                .collect(Collectors.toList());
    }

    /**
     * Calcula a nota média das avaliações de um evento.
     *
     * @param idEvento ID do evento.
     * @return Nota média das avaliações do evento. Se não houver avaliações, retorna 0.
     */
    public float getRatingEvento(UUID idEvento) {
        return (float) this.getAvaliacoesEvento(idEvento).stream()
                .mapToInt(Avaliacao::getRating)  // Mapeia as notas
                .average()                       // Calcula a média
                .orElse(0.0);                    // Retorna 0 se não houver avaliações
    }

    /**
     * Atualiza os comentários e a nota de uma avaliação.
     *
     * @param id          ID da avaliação a ser atualizada.
     * @param comentarios Novos comentários para a avaliação.
     * @param rating      Nova nota atribuída ao evento.
     */
    public void update(UUID id, String comentarios, int rating) {
        Avaliacao avaliacao = avaliacaoFileManager.getById(id);
        avaliacao.setComentarios(comentarios);
        avaliacao.setRating(rating);
        avaliacaoFileManager.update(avaliacao);
    }

    /**
     * Remove uma avaliação pelo seu ID.
     *
     * @param id ID da avaliação a ser removida.
     */
    public void delete(UUID id) {
        avaliacaoFileManager.delete(id);
    }

    /**
     * Remove todas as avaliações cadastradas.
     */
    public void deleteAll() {
        avaliacaoFileManager.deleteAll();
    }
}
