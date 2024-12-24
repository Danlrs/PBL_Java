package aplication.model;

import aplication.interfaces.Identificavel;
import java.util.UUID;

/**
 * Representa uma avaliação de um evento por um usuário, contendo um comentário,
 * uma nota (rating) e os identificadores do usuário e do evento associados.
 */
public class Avaliacao implements Identificavel {
    private final UUID id; // Identificador único da avaliação
    private String comentarios; // Comentário textual sobre o evento
    private int rating; // Nota da avaliação, variando de 1 a 5
    private final UUID idUsuario; // Identificador do usuário que fez a avaliação
    private final UUID idEvento; // Identificador do evento avaliado

    /**
     * Construtor para criar uma nova instância de Avaliacao.
     *
     * @param comentarios Texto do comentário associado à avaliação.
     * @param rating Nota da avaliação.
     * @param idUsuario Identificador único do usuário que fez a avaliação.
     * @param idEvento Identificador único do evento que está sendo avaliado.
     */
    public Avaliacao(String comentarios, int rating, UUID idUsuario, UUID idEvento) {
        this.id = UUID.randomUUID();
        this.comentarios = comentarios;
        this.rating = rating;
        this.idUsuario = idUsuario;
        this.idEvento = idEvento;
    }

    /**
     * Obtém o identificador único da avaliação.
     *
     * @return ID da avaliação.
     */
    @Override
    public UUID getId() {
        return id;
    }

    /**
     * Obtém o comentário associado à avaliação.
     *
     * @return Comentário textual da avaliação.
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * Obtém a nota da avaliação.
     *
     * @return Nota da avaliação.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Obtém o identificador do usuário que realizou a avaliação.
     *
     * @return ID do usuário.
     */
    public UUID getIdUsuario() {
        return idUsuario;
    }

    /**
     * Obtém o identificador do evento que foi avaliado.
     *
     * @return ID do evento.
     */
    public UUID getIdEvento() {
        return idEvento;
    }

    /**
     * Define o comentário associado à avaliação.
     *
     * @param comentarios Novo comentário textual da avaliação.
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * Define a nota da avaliação.
     *
     * @param rating Nota da avaliação.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }
}
