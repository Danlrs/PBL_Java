package aplication.facade.CardTestFacade;

import aplication.controller.UsuarioController;
import aplication.controller.CartaoController;
import aplication.model.Usuario;
import aplication.model.Cartao;

import java.util.UUID;
import java.util.Date;

/**
 * Facade para facilitar os testes e interações com cartões.
 *
 * Esta classe oferece uma interface simplificada para criar, acessar e gerenciar cartões.
 */
public class CardTestFacade {
    private final UsuarioController usuarioController;
    private final CartaoController cartaoController;

    /**
     * Construtor da classe CardTestFacade.
     * Inicializa os controladores de usuários e cartões.
     */
    public CardTestFacade() {
        this.usuarioController = new UsuarioController();
        this.cartaoController = new CartaoController();
    }

    /**
     * Cria um novo cartão associado a um usuário.
     *
     * @param userEmail   O e-mail do usuário proprietário do cartão.
     * @param cardNumber  O número do cartão.
     * @param expiryDate  A data de validade do cartão.
     * @param cvv         O código de segurança do cartão.
     * @param cardName    O nome no cartão.
     * @return O ID do cartão criado.
     */
    public UUID create(String userEmail, String cardNumber, Date expiryDate, int cvv, String cardName) throws Exception {
        Usuario usuario = usuarioController.getByEmail(userEmail);
        Cartao cartao = cartaoController.criarCartao(usuario.getId(), cardNumber, String.valueOf(cvv), expiryDate, cardName);
        return cartao.getId();
    }

    /**
     * Obtém o nome do usuário associado a um cartão.
     *
     * @param cardId O ID do cartão.
     * @return O nome do usuário proprietário do cartão.
     */
    public String getUserNameByCardId(UUID cardId) {
        UUID usuarioId = cartaoController.getById(cardId).getIdUsuario();
        return usuarioController.getById(usuarioId).getNome();
    }

    /**
     * Obtém o número de um cartão pelo seu ID.
     *
     * @param cardId O ID do cartão.
     * @return O número do cartão.
     */
    public String getCardNumberByCardId(UUID cardId) {
        return cartaoController.getById(cardId).getNumero();
    }

    /**
     * Obtém o ano de validade de um cartão.
     *
     * @param cardId O ID do cartão.
     * @return O ano de validade do cartão.
     */
    public int getYearByCardId(UUID cardId) {
        return cartaoController.getById(cardId).getDataValidade().getYear();
    }

    /**
     * Obtém o mês de validade de um cartão.
     *
     * @param cardId O ID do cartão.
     * @return O mês de validade do cartão.
     */
    public int getMonthByCardId(UUID cardId) {
        return cartaoController.getById(cardId).getDataValidade().getMonth();
    }

    /**
     * Obtém o dia de validade de um cartão.
     *
     * @param cardId O ID do cartão.
     * @return O dia de validade do cartão.
     */
    public int getDayByCardId(UUID cardId) {
        return cartaoController.getById(cardId).getDataValidade().getDay();
    }

    /**
     * Desativa um cartão pelo seu ID.
     *
     * @param cardId O ID do cartão.
     */
    public void disable(UUID cardId) {
        cartaoController.desativarCartao(cardId);
    }

    /**
     * Verifica o status de ativação de um cartão.
     *
     * @param cardId O ID do cartão.
     * @return true se o cartão está ativo, caso contrário false.
     */
    public boolean getStatusByCardId(UUID cardId) {
        return cartaoController.getById(cardId).getAtivo();
    }

    /**
     * Exclui um cartão pelo seu ID.
     *
     * @param cardId O ID do cartão a ser excluído.
     */
    public void delete(UUID cardId) {
        cartaoController.delete(cardId);
    }

    /**
     * Obtém um cartão pelo seu ID.
     *
     * @param id O ID do cartão.
     * @return O objeto Cartao.
     */
    public Cartao getById(UUID id) {
        return cartaoController.getById(id);
    }

    /**
     * Exclui todos os cartões.
     */
    public void deleteAllCards() {
        cartaoController.deleteAll();
    }
}
