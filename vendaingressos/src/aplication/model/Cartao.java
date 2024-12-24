package aplication.model;

import aplication.interfaces.Identificavel;
import java.util.Date;
import java.util.UUID;

/**
 * Classe que representa um cartão de pagamento.
 * Implementa a interface Identificavel para garantir o uso de um identificador único.
 */
public class Cartao implements Identificavel {
    private final UUID id; // ID único do cartão
    private final UUID idUsuario; // ID do usuário dono do cartão
    private final String nome; // Nome do cartão
    private final String numero; // Número do cartão
    private final Date dataValidade; // Data de validade do cartão
    private final String cvv; // Código de segurança do cartão
    private boolean ativo; // Estado ativo do cartão

    /**
     * Construtor para criar uma nova instância de Cartao.
     *
     * @param idUsuario    ID do usuário dono do cartão.
     * @param numero       Número do cartão.
     * @param dataValidade Data de validade do cartão.
     * @param cvv          Código de segurança do cartão.
     * @param nome         Nome do cartão.
     */
    public Cartao(UUID idUsuario, String numero, Date dataValidade, String cvv, String nome) {
        this.id = UUID.randomUUID();
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.numero = numero;
        this.dataValidade = dataValidade;
        this.cvv = cvv;
        this.ativo = true;
    }

    // Getters
    /**
     * Retorna o ID do cartão.
     *
     * @return UUID representando o ID do cartão.
     */
    @Override
    public UUID getId() {
        return id;
    }

    /**
     * Retorna o ID do usuário associado ao cartão.
     *
     * @return UUID do usuário dono do cartão.
     */
    public UUID getIdUsuario() {
        return idUsuario;
    }

    /**
     * Retorna o nome do cartão, geralmente o nome impresso no cartão.
     *
     * @return String representando o nome do cartão.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o número do cartão.
     *
     * @return String representando o número do cartão.
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Retorna a data de validade do cartão.
     *
     * @return Date representando a data de validade do cartão.
     */
    public Date getDataValidade() {
        return dataValidade;
    }

    /**
     * Retorna o código de segurança do cartão (CVV).
     *
     * @return String representando o código CVV do cartão.
     */
    public String getCvv() {
        return cvv;
    }


    /**
     * Retorna o status de ativo do cartão.
     * O cartão será desativado automaticamente se a data atual for posterior à data de validade.
     *
     * @return true se o cartão ainda é válido, false caso contrário.
     */
    public boolean getAtivo() {
        // Atualiza o status do cartão com base na validade
        Date dataAtual = new Date();
        if (dataAtual.after(dataValidade)) {
            this.ativo = false;
        }
        return ativo;
    }

    /**
     * Define o estado ativo do cartão manualmente.
     *
     * @param ativo Define se o cartão deve estar ativo ou não.
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
