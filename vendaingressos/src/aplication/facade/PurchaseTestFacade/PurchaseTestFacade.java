//package aplication.facade.PurchaseTestFacade;
//
//import aplication.model.Cartao;
//import aplication.model.Compra;
//import aplication.model.Ingresso;
//
//import aplication.controller.UsuarioController;
//import aplication.controller.CartaoController;
//import aplication.controller.CompraController;
//import aplication.model.Usuario;
//
//import java.util.List;
//import java.util.UUID;
//
///**
// * Facade para facilitar os testes e interações com compras.
// *
// * Esta classe oferece uma interface simplificada para criar, acessar e gerenciar compras,
// * utilizando os controladores de compra, usuário e cartão.
// */
//public class PurchaseTestFacade {
//
//    private final UsuarioController usuarioController;
//    private final CompraController compraController;
//
//    /**
//     * Construtor da classe PurchaseTestFacade.
//     * Inicializa os controladores de usuário e compra.
//     */
//    public PurchaseTestFacade() {
//        this.usuarioController = new UsuarioController();
//        this.compraController = new CompraController();
//    }
//
//    /**
//     * Cria uma nova compra sem cartão associado.
//     *
//     * @param email O email do usuário que está realizando a compra.
//     * @param eventId O ID do evento associado à compra.
//     * @param seat O assento da compra.
//     * @param price O preço da compra.
//     * @param boleto O ID (código do boleto)
//     * @return O ID da compra criada.
//     * @throws Exception Se houver erros ao criar a compra.
//     */
//    public UUID create(String email, UUID eventId, List <String> seat, double price, UUID boleto) throws Exception {
//        Usuario usuario = usuarioController.getByEmail(email);
//        return compraController.criarRecibo(usuario.getId(), eventId, seat, price, boleto);
//    }
//
//    /**
//     * Cria uma nova compra com cartão associado.
//     *
//     * @param email O email do usuário que está realizando a compra.
//     * @param eventId O ID do evento associado à compra.
//     * @param cardId O ID do cartão utilizado na compra.
//     * @param seat O assento da compra.
//     * @param price O preço da compra.
//     * @return O ID da compra criada.
//     * @throws Exception Se houver erros ao criar a compra.
//     */
//    public UUID create(String email, UUID eventId, UUID cardId, String seat, double price) throws Exception {
//        Usuario usuario = usuarioController.getByEmail(email);
//        return compraController.criarRecibo(usuario.getId(), eventId, seat, cardId, price);
//    }
//
//    /**
//     * Retorna uma compra com base no seu ID.
//     *
//     * @param id O ID da compra.
//     * @return O objeto Compra.
//     */
//    public Compra getById(UUID id) {
//        return compraController.getById(id);
//    }
//
//    /**
//     * Obtém o ID do evento associado a uma compra.
//     *
//     * @param id O ID da compra.
//     * @return O ID do evento associado.
//     */
//    public UUID getEventByPurchaseId(UUID id) {
//        return compraController.getEventoByIdCompra(id).getId();
//    }
//
//    /**
//     * Obtém o login do usuário associado a uma compra.
//     *
//     * @param id O ID da compra.
//     * @return O login do usuário.
//     */
//    public String getUserLoginByPurchaseId(UUID id) {
//        return compraController.getLoginByIdCompra(id);
//    }
//
//    /**
//     * Obtém o ingresso associado a uma compra.
//     *
//     * @param id O ID da compra.
//     * @return O objeto Ingresso associado.
//     */
//    public Ingresso getTicketByPurchaseId(UUID id) {
//        return compraController.getIngressoByIdCompra(id);
//    }
//
//    /**
//     * Obtém o tamanho da caixa de correio do usuário com base na compra.
//     *
//     * @param id O ID da compra.
//     * @return O número de recibos no correio do usuário.
//     */
//    public int getUserMailBoxByPurchaseId(UUID id) {
//        return compraController.getRecibosSizeById(id);
//    }
//
//    /**
//     * Obtém o cartão associado a uma compra.
//     *
//     * @param id O ID da compra.
//     * @return O objeto Cartao associado à compra.
//     */
//    public Cartao getCardByPurchaseID(UUID id) {
//        return compraController.getCartaoByIdCompra(id);
//    }
//
//    /**
//     * Exclui todas as compras existentes.
//     */
//    public void deleteAllPurchases() {
//        compraController.deleteAll();
//    }
//}
