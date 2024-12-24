package aplication.controller;

import aplication.model.Cartao;
import aplication.filemanagement.CartaoFileManager;

import java.util.List;
import java.util.UUID;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Classe responsável pelo controle dos cartões de pagamento.
 * Gerencia as operações de criação, recuperação, atualização e remoção de cartões.
 */
public class CartaoController {

    private final CartaoFileManager cartaoFileManager;

    /**
     * Construtor da classe CartaoController.
     * Inicializa o gerenciador de arquivos de cartões.
     */
    public CartaoController() {
        this.cartaoFileManager = new CartaoFileManager();
    }

    /**
     * Cria um novo cartão e o associa a um usuário.
     *
     * @param idUsuario    ID do usuário que será associado ao cartão.
     * @param numero       Número do cartão.
     * @param cvv          Código de verificação do cartão.
     * @param dataValidade Data de validade do cartão.
     * @param nome         Nome do cartão.
     * @return O cartão criado.
     * @throws IllegalArgumentException Se já houver um cartão com o mesmo número para o usuário.
     */
    public Cartao criarCartao(UUID idUsuario, String numero, String cvv, Date dataValidade, String nome) {
        Cartao cartao = new Cartao(idUsuario, numero, dataValidade, cvv, nome);
        if (this.getCartaoByNumeroIdUsuario(numero, idUsuario) != null) {
            throw new IllegalArgumentException("Cartão com esse número já cadastrado!");
        }
        cartaoFileManager.save(cartao);
        return cartao;
    }

    /**
     * Recupera um cartão pelo seu ID.
     *
     * @param id ID do cartão.
     * @return O cartão correspondente ao ID.
     */
    public Cartao getById(UUID id) {
        return cartaoFileManager.getById(id);
    }

    /**
     * Recupera todos os cartões cadastrados.
     *
     * @return Lista de todos os cartões.
     */
    public List<Cartao> getAll() {
        return cartaoFileManager.getAll();
    }

    public List<Cartao> getByUsuarioId(UUID idUsuario) {
        return this.getAll().stream()
                .filter(cartao -> cartao.getIdUsuario().equals(idUsuario))
                .collect(Collectors.toList());
    }

    /**
     * Recupera um cartão pelo número e pelo ID do usuário associado.
     *
     * @param numero    Número do cartão.
     * @param idUsuario ID do usuário dono do cartão.
     * @return O cartão correspondente ao número e usuário, ou null se não existir.
     */
    public Cartao getCartaoByNumeroIdUsuario(String numero, UUID idUsuario) {
        return this.getAll().stream()
                .filter(cartao -> cartao.getNumero().equals(numero) && cartao.getIdUsuario().equals(idUsuario))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retorna o cartão com o nome e ID de usuário especificados.
     *
     * @param nome     Nome do cartão a ser encontrado.
     * @param idUsuario UUID do usuário dono do cartão.
     * @return Objeto Cartao que corresponde ao nome e ID de usuário, ou null se não for encontrado.
     */
    public Cartao getCartaoByNomeIdUsuario(String nome, UUID idUsuario) {
        return this.getAll().stream()
                .filter(cartao -> cartao.getNome().equals(nome) && cartao.getIdUsuario().equals(idUsuario))
                .findFirst()
                .orElse(null);
    }


    /**
     * Desativa um cartão, marcando-o como inativo.
     *
     * @param id ID do cartão a ser desativado.
     */
    public void desativarCartao(UUID id) {
        Cartao cartao = getById(id);
        if (cartao != null) {
            cartao.setAtivo(false);
            this.update(cartao);
        }
    }

    /**
     * Atualiza as informações de um cartão.
     *
     * @param cartao Cartão com as informações atualizadas.
     */
    public void update(Cartao cartao) {
        cartaoFileManager.update(cartao);
    }

    /**
     * Remove um cartão pelo seu ID.
     *
     * @param id ID do cartão a ser removido.
     */
    public void delete(UUID id) {
        cartaoFileManager.delete(id);
    }

    /**
     * Remove todos os cartões cadastrados.
     */
    public void deleteAll() {
        cartaoFileManager.deleteAll();
    }

}
