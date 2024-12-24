package aplication.model;

import aplication.interfaces.Identificavel;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Classe que representa um usuário do sistema.
 * Um usuário possui um ID único, login, senha, nome, CPF, e-mail e pode ser um administrador.
 * O usuário também possui listas de ingressos, cartões e recibos, além de métodos para gerenciar seu estado de login.
 */
public class Usuario implements Identificavel {
    private final UUID id; // ID aleatório e imutável
    private String login;
    private String senha;
    private String nome;
    private String cpf;
    private String email;
    private boolean admin;
    private boolean logado;
    public List<Ingresso> ingressos;
    public List<Cartao> cartoes;
    public List<String> recibos;

    /**
     * Construtor da classe Usuario.
     *
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @param nome O nome do usuário.
     * @param cpf O CPF do usuário.
     * @param email O e-mail do usuário.
     * @param admin Indica se o usuário tem privilégios de administrador.
     */
    public Usuario(String login, String senha, String nome, String cpf, String email, boolean admin) {
        this.id = UUID.randomUUID(); // Geração do ID único
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.admin = admin;
        this.logado = false;
        this.ingressos = new ArrayList<>();
        this.cartoes = new ArrayList<>();
        this.recibos = new ArrayList<>();
    }

    /**
     * Retorna o ID do usuário.
     *
     * @return O UUID do usuário.
     */
    @Override
    public UUID getId() {
        return id;
    }

    // Getters e Setters

    public String getLogin() {
        return login;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getSenha() {
        return senha;
    }

    public List<String> getRecibos() {
        return recibos;
    }

    public List<Ingresso> getIngressos() {
        return ingressos;
    }

    public boolean getLogado() {
        return logado;
    }

    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String novaSenha) {
        this.senha = novaSenha;
    }

    /**
     * Adiciona um recibo à lista de recibos do usuário.
     *
     * @param recibo O recibo a ser adicionado.
     */
    public void addRecibo(String recibo) {
        this.recibos.add(recibo);
    }

    /**
     * Realiza o login do usuário com base em login e senha fornecidos.
     *
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @return true se o login for bem-sucedido, false caso contrário.
     */
    public boolean login(String login, String senha) {
        this.logado = this.login.equals(login) && this.senha.equals(senha);
        return this.logado;
    }

    /**
     * Desloga o usuário, definindo seu status como não logado.
     */
    public void deslogar() {
        this.logado = false;
    }

    /**
     * Adiciona um ingresso à lista de ingressos do usuário.
     *
     * @param ingresso O ingresso a ser adicionado.
     */
    public void comprarIngresso(Ingresso ingresso) {
        this.ingressos.add(ingresso);
    }

    public void setIngressos(List<Ingresso> ingressos) {
        this.ingressos = ingressos;
    }

    /**
     * Remove um ingresso da lista de ingressos do usuário.
     *
     * @param ingresso O ingresso a ser removido.
     */
    public void removerIngresso(Ingresso ingresso) {
        this.ingressos.remove(ingresso);
    }

    /**
     * Adiciona um cartão à lista de cartões do usuário.
     *
     * @param cartao O cartão a ser adicionado.
     */
    public void addCartao(Cartao cartao) {
        this.cartoes.add(cartao);
    }

    /**
     * Remove um cartão da lista de cartões do usuário.
     *
     * @param cartao O cartão a ser removido.
     */
    public void removerCartao(Cartao cartao) {
        this.cartoes.remove(cartao);
    }

    @Override
    public String toString() {
        return "Usuário{" +
                "ID: " + id +
                ", Nome: " + nome +
                ", E-mail: " + email +
                ", CPF: " + cpf +
                ", login=" + login +
                '}';
    }

    /**
     * Sobrescreve o metodo equals para comparar usuários com base no ID.
     *
     * @param o O objeto a ser comparado.
     * @return true se os IDs forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id); // Comparação usando o ID
    }

    /**
     * Sobrescreve o metodo hashCode para gerar um hash com base no ID do usuário.
     *
     * @return O hash gerado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id); // Hash baseado no ID
    }
}
