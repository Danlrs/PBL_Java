package aplication.model;

import java.util.Date;
import java.util.UUID;
import aplication.interfaces.Identificavel;

/**
 * Classe que representa uma compra realizada por um usuário para um evento.
 * A compra pode ser realizada via cartão ou boleto.
 */
public class Compra implements Identificavel {
    // Atributos
    private final UUID id; // ID único da compra
    private final UUID idUsuario; // ID do usuário que fez a compra
    private final UUID idEvento; // ID do evento relacionado à compra
    private final UUID idIngresso; // ID do ingresso comprado
    private final UUID idCartao; // Pode ser nulo se o pagamento for via boleto
    private final UUID boleto; // Pode ser nulo se o pagamento for via cartáp
    private final Date dataCompra; // Data da compra
    private final double preco; // Preço da compra
    private final String metodoPagamento; // Metodo de pagamento (Cartão ou Boleto)

    /**
     * Construtor para a compra realizada via cartão.
     *
     * @param idUsuario   ID do usuário que realizou a compra.
     * @param idEvento    ID do evento.
     * @param idIngresso  ID do ingresso comprado.
     * @param idCartao    ID do cartão utilizado para pagamento.
     * @param preco       Preço total da compra.
     */
    public Compra(UUID idUsuario, UUID idEvento, UUID idIngresso, UUID idCartao, double preco) {
        this.id = UUID.randomUUID(); // Gerar um novo UUID para a compra
        this.idUsuario = idUsuario;
        this.idEvento = idEvento;
        this.idIngresso = idIngresso;
        this.idCartao = idCartao;
        this.boleto = null;
        this.dataCompra = new Date(); // Data atual da compra
        this.preco = preco;
        this.metodoPagamento = "Cartão"; // Metodo de pagamento específico
    }

    /**
     * Construtor para a compra realizada via boleto.
     *
     * @param idUsuario   ID do usuário que realizou a compra.
     * @param idEvento    ID do evento.
     * @param idIngresso  ID do ingresso comprado.
     * @param preco       Preço total da compra.
     * @param boleto ID (código) do boleto
     */
    public Compra(UUID idUsuario, UUID idEvento, UUID idIngresso, double preco, UUID boleto) {
        this.id = UUID.randomUUID(); // Gerar um novo UUID para a compra
        this.idUsuario = idUsuario;
        this.idEvento = idEvento;
        this.idIngresso = idIngresso;
        this.idCartao = null; // Não aplicável se o pagamento é por boleto
        this.boleto = boleto;
        this.dataCompra = new Date(); // Data atual da compra
        this.preco = preco;
        this.metodoPagamento = "Boleto"; // Metodo de pagamento específico
    }

    /**
     * Retorna o ID da compra.
     *
     * @return UUID da compra.
     */
    @Override
    public UUID getId() {
        return id;
    }

    /**
     * Retorna o ID do usuário que realizou a compra.
     *
     * @return UUID do usuário.
     */
    public UUID getIdUsuario() {
        return idUsuario;
    }

    /**
     * Retorna o ID do evento associado à compra.
     *
     * @return UUID do evento.
     */
    public UUID getIdEvento() {
        return idEvento;
    }

    /**
     * Retorna o ID do ingresso associado à compra.
     *
     * @return UUID do ingresso.
     */
    public UUID getIdIngresso() {
        return idIngresso;
    }

    /**
     * Retorna o ID do cartão utilizado no pagamento (pode ser nulo).
     *
     * @return UUID do cartão, ou null se o pagamento foi via boleto.
     */
    public UUID getIdCartao() {
        return idCartao;
    }

    /**
     * Retorna a data da compra.
     *
     * @return Data da compra.
     */
    public Date getDataCompra() {
        return dataCompra;
    }

    /**
     * Retorna o preço da compra.
     *
     * @return Preço da compra.
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Retorna o metodo de pagamento utilizado na compra.
     *
     * @return String com o metodo de pagamento (Cartão ou Boleto).
     */
    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    /**
     * Sobrescreve o metodo toString para exibir informações detalhadas da compra.
     *
     * @return String com os detalhes da compra.
     */
    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idEvento=" + idEvento +
                ", idIngresso=" + idIngresso +
                ", idCartao=" + idCartao +
                ", dataCompra=" + dataCompra +
                ", preco=" + preco +
                ", metodoPagamento='" + metodoPagamento + '\'' +
                '}';
    }
}
