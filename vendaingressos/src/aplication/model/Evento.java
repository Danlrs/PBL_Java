package aplication.model;

import aplication.interfaces.Identificavel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Classe que representa um evento.
 * Cada evento possui um ID único, nome, descrição, data, assentos disponíveis e avaliações.
 */
public class Evento implements Identificavel {
    private final UUID id; // ID aleatório e imutável
    private String nome;
    private String descricao;
    private Date data;
    private double preco;
    private final List<String> assentosDisponiveis;

    /**
     * Construtor da classe Evento.
     *
     * @param nome       O nome do evento.
     * @param descricao  A descrição do evento.
     * @param data       A data do evento.
     */
    public Evento(String nome, String descricao, Date data, double preco) {
        this.id = UUID.randomUUID(); // Geração do ID único
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.preco = preco;
        this.assentosDisponiveis = new ArrayList<>();
    }

    /**
     * Retorna o ID do evento.
     *
     * @return O UUID do evento.
     */
    @Override
    public UUID getId() {
        return id;
    }

    /**
     * Retorna o nome do evento.
     *
     * @return O nome do evento.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o preço do evento.
     *
     * @return O preço do evento.
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Define o nome do evento.
     *
     * @param nome O novo nome do evento.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a descrição do evento.
     *
     * @return A descrição do evento.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do evento.
     *
     * @param descricao A nova descrição do evento.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a data do evento.
     *
     * @return A data do evento.
     */
    public Date getData() {
        return data;
    }

    /**
     * Define a data do evento.
     *
     * @param data A nova data do evento.
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * Retorna a lista de assentos disponíveis para o evento.
     *
     * @return Uma lista de assentos disponíveis.
     */
    public List<String> getAssentosDisponiveis() {
        return assentosDisponiveis;
    }

    /**
     * Adiciona um assento à lista de assentos disponíveis.
     *
     * @param assento O assento a ser adicionado.
     */
    public void adicionarAssento(String assento) {
        assentosDisponiveis.add(assento);
    }

    /**
     * Remove um assento da lista de assentos disponíveis.
     *
     * @param assento O assento a ser removido.
     */
    public void removerAssento(String assento) {
        assentosDisponiveis.remove(assento);
    }

    /**
     * Verifica se o evento está ativo, ou seja, se a data do evento ainda não passou.
     *
     * @return true se o evento estiver ativo, false caso contrário.
     */
    public boolean isAtivo() {
        Date dataAtual = new Date();
        return data.after(dataAtual);
    }

    /**
     * Sobrescreve o metodo toString para exibir informações legíveis do evento.
     *
     * @return Uma string com as informações do evento.
     */
    @Override
    public String toString() {
        return "\nEvento: " + nome + ", Descrição: " + descricao + ", Data: " + data + "\n";
    }

    /**
     * Sobrescreve o metodo equals para comparar eventos pelo ID.
     *
     * @param o O objeto a ser comparado.
     * @return true se os eventos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return id.equals(evento.id);
    }

    /**
     * Sobrescreve o metodo hashCode para gerar o hash com base no ID do evento.
     *
     * @return O hash gerado.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
