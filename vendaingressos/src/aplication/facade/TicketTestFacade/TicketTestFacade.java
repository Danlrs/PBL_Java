//package aplication.facade.TicketTestFacade;
//
//import aplication.controller.IngressoController;
//import aplication.model.Ingresso;
//
//import java.util.UUID;
//
///**
// * Facade para facilitar os testes e interações com ingressos.
// *
// * Esta classe oferece uma interface simplificada para criar, acessar e gerenciar ingressos,
// * utilizando o controlador de ingressos.
// */
//public class TicketTestFacade {
//
//    // Controlador de Ingresso utilizado para gerenciar as operações.
//    private final IngressoController ingressoController;
//
//    /**
//     * Construtor da classe TicketTestFacade.
//     * Inicializa o controlador de ingresso.
//     */
//    public TicketTestFacade() {
//        this.ingressoController = new IngressoController();
//    }
//
//    /**
//     * Cria um novo ingresso.
//     *
//     * @param eventId O ID do evento associado ao ingresso.
//     * @param userID O ID do usuário associado ao ingresso.
//     * @param price O preço do ingresso.
//     * @param seat O assento associado ao ingresso.
//     * @return O ID do ingresso criado.
//     */
//    public UUID create(UUID eventId, UUID userID, Double price, String seat) {
//        Ingresso ingresso = ingressoController.criarIngresso(eventId, userID, price, seat);
//        return ingresso.getId();
//    }
//
//    /**
//     * Obtém o ID do evento associado a um ingresso.
//     *
//     * @param id O ID do ingresso.
//     * @return O ID do evento associado ao ingresso.
//     */
//    public UUID getEventByTicketId(UUID id) {
//        return ingressoController.getById(id).getEventoId();
//    }
//
//    /**
//     * Obtém o preço de um ingresso com base no seu ID.
//     *
//     * @param id O ID do ingresso.
//     * @return O preço do ingresso.
//     */
//    public Double getPriceByTicketId(UUID id) {
//        return ingressoController.getById(id).getPreco();
//    }
//
//    /**
//     * Cancela um ingresso com base no seu ID, desativando-o.
//     *
//     * @param id O ID do ingresso.
//     */
//    public void cancelByTicketId(UUID id) {
//        ingressoController.desativarIngresso(id);
//    }
//
//    /**
//     * Obtém o assento associado a um ingresso com base no seu ID.
//     *
//     * @param id O ID do ingresso.
//     * @return O assento do ingresso.
//     */
//    public String getSeatByTicketId(UUID id) {
//        return ingressoController.getById(id).getAssento();
//    }
//
//    /**
//     * Reativa um ingresso com base no seu ID.
//     *
//     * @param id O ID do ingresso.
//     */
//    public void reactiveById(UUID id) {
//        ingressoController.reativarIngresso(id);
//    }
//
//    /**
//     * Obtém um ingresso com base no seu ID.
//     *
//     * @param id O ID do ingresso.
//     * @return O objeto Ingresso.
//     */
//    public Ingresso getById(UUID id) {
//        return ingressoController.getById(id);
//    }
//
//    /**
//     * Verifica se um ingresso está ativo com base no seu ID.
//     *
//     * @param id O ID do ingresso.
//     * @return true se o ingresso estiver ativo, false caso contrário.
//     */
//    public boolean getIsActive(UUID id) {
//        return ingressoController.getById(id).isAtivo();
//    }
//
//    /**
//     * Exclui todos os ingressos existentes.
//     */
//    public void deleteAllTickets() {
//        ingressoController.deleteAll();
//    }
//}
