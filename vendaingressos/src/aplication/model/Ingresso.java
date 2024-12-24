package aplication.model;

import aplication.interfaces.Identificavel;
import java.util.Objects;
import java.util.UUID;

/**
 * Classe que representa um ingresso de evento.
 * Cada ingresso é identificado de forma única e vinculado a um usuário e a um evento.
 */
public class Ingresso implements Identificavel {
    private final UUID id; // ID aleatório e imutável
    private final UUID usuarioId;
    private UUID eventoId;
    private double preco;
    private String assento;
    private boolean ativo;

    /**
     * Construtor da classe Ingresso.
     *
     * @param eventoId  O ID do evento ao qual o ingresso está associado.
     * @param usuarioId O ID do usuário que comprou o ingresso.
     * @param preco     O preço do ingresso.
     * @param assento   O assento reservado para o ingresso.
     */
    public Ingresso(UUID eventoId, UUID usuarioId, double preco, String assento) {
        this.id = UUID.randomUUID(); // Geração do ID único
        this.usuarioId = usuarioId;
        this.eventoId = eventoId;
        this.preco = preco;
        this.assento = assento;
        this.ativo = true;
    }

    /**
     * Retorna o ID do ingresso.
     *
     * @return O UUID do ingresso.
     */
    @Override
    public UUID getId() {
        return id;
    }

    /**
     * Retorna o ID do usuário que comprou o ingresso.
     *
     * @return O UUID do usuário.
     */
    public UUID getUsuarioId() {
        return usuarioId;
    }

    /**
     * Retorna o ID do evento associado ao ingresso.
     *
     * @return O UUID do evento.
     */
    public UUID getEventoId() {
        return eventoId;
    }

    /**
     * Retorna o preço do ingresso.
     *
     * @return O preço do ingresso.
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Retorna o assento reservado para o ingresso.
     *
     * @return O número do assento.
     */
    public String getAssento() {
        return assento;
    }

    /**
     * Retorna se o ingresso está ativo.
     *
     * @return true se o ingresso estiver ativo, false caso contrário.
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * Define o status de ativação do ingresso.
     *
     * @param ativo true para ativar o ingresso, false para desativá-lo.
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * Sobrescreve o metodo equals para verificar se dois ingressos são iguais com base no ID.
     *
     * @param o O objeto a ser comparado.
     * @return true se os IDs forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingresso ingresso = (Ingresso) o;
        return id.equals(ingresso.id); // Comparação pelo ID
    }

    /**
     * Sobrescreve o metodo hashCode para gerar o hash com base no ID do ingresso.
     *
     * @return O hash gerado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id); // Hash baseado no ID
    }

    /**
     * Sobrescreve o metodo toString para exibir as informações do ingresso de forma legível.
     *
     * @return Uma string com as informações do ingresso.
     */
    @Override
    public String toString() {
        return "Ingresso{" +
                "ID=" + id +
                ", Evento=" + eventoId +
                ", Preço=" + preco +
                ", Assento='" + assento + '\'' +
                ", Ativo=" + ativo +
                '}';
    }
}
