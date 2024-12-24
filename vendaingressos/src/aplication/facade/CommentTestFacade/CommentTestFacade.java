package aplication.facade.CommentTestFacade;

import aplication.controller.AvaliacaoController;
import aplication.model.Avaliacao;

import java.util.Date;
import java.util.UUID;

/**
 * Facade para facilitar os testes e interações com comentários (avaliações).
 *
 * Esta classe oferece uma interface simplificada para criar, acessar e gerenciar avaliações.
 */
public class CommentTestFacade {

    private final AvaliacaoController avaliacaoController;

    /**
     * Construtor da classe CommentTestFacade.
     * Inicializa o controlador de avaliações.
     */
    public CommentTestFacade() {
        this.avaliacaoController = new AvaliacaoController();
    }

    /**
     * Cria uma nova avaliação para um evento.
     *
     * @param userID   O ID do usuário que fez a avaliação.
     * @param eventID  O ID do evento avaliado.
     * @param rating   A nota dada ao evento.
     * @param content  O conteúdo do comentário.
     * @return O ID da avaliação criada.
     */
    public UUID create(UUID userID, UUID eventID, int rating, String content) {
        Avaliacao avaliacao = avaliacaoController.criarAvaliacao(content, rating, userID, eventID);
        return avaliacao.getId();
    }

    /**
     * Obtém uma avaliação pelo seu ID.
     *
     * @param id O ID da avaliação.
     * @return O objeto Avaliacao.
     */
    public Avaliacao getById(UUID id) {
        return avaliacaoController.getById(id);
    }

    /**
     * Retorna o conteúdo do comentário de uma avaliação.
     *
     * @param id O ID da avaliação.
     * @return O conteúdo do comentário.
     */
    public String getContentById(UUID id) {
        return avaliacaoController.getById(id).getComentarios();
    }

    /**
     * Obtém a nota de uma avaliação.
     *
     * @param id O ID da avaliação.
     * @return A nota da avaliação.
     */
    public int getRatingCommentById(UUID id) {
        return avaliacaoController.getById(id).getRating();
    }

    /**
     * Obtém o ID do usuário que fez a avaliação.
     *
     * @param id O ID da avaliação.
     * @return O ID do usuário.
     */
    public UUID getUserIdById(UUID id) {
        return avaliacaoController.getById(id).getIdUsuario();
    }

    /**
     * Obtém o ID do evento avaliado.
     *
     * @param id O ID da avaliação.
     * @return O ID do evento.
     */
    public UUID getEventIdById(UUID id) {
        return avaliacaoController.getById(id).getIdEvento();
    }

    /**
     * Obtém a nota média de todas as avaliações para um determinado evento.
     *
     * @param id O ID do evento.
     * @return A nota média do evento.
     */
    public float getEventRatingByEventId(UUID id) {
        return avaliacaoController.getRatingEvento(id);
    }

    /**
     * Exclui uma avaliação pelo seu ID.
     *
     * @param id O ID da avaliação a ser excluída.
     */
    public void delete(UUID id) {
        avaliacaoController.delete(id);
    }

    /**
     * Exclui todas as avaliações.
     */
    public void deleteAllComments() {
        avaliacaoController.deleteAll();
    }
}
