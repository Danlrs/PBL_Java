//package aplication.facade.EventTestFacade;
//
//import aplication.controller.EventoController;
//import aplication.controller.UsuarioController;
//import aplication.controller.AvaliacaoController;
//import aplication.model.Evento;
//import aplication.model.Usuario;
//import aplication.model.Avaliacao;
//
//import java.util.List;
//import java.util.Date;
//import java.util.UUID;
//
///**
// * Facade para facilitar os testes e interações com eventos.
// *
// * Esta classe oferece uma interface simplificada para criar, acessar e gerenciar eventos,
// * além de realizar operações relacionadas a assentos e avaliações de eventos.
// */
//public class EventTestFacade {
//
//    private final EventoController eventoController;
//    private final UsuarioController usuarioController;
//    private final AvaliacaoController avaliacaoController;
//
//    /**
//     * Construtor da classe EventTestFacade.
//     * Inicializa os controladores de evento, usuário e avaliação.
//     */
//    public EventTestFacade() {
//        this.eventoController = new EventoController();
//        this.usuarioController = new UsuarioController();
//        this.avaliacaoController = new AvaliacaoController();
//    }
//
//    /**
//     * Remove um assento disponível de um evento.
//     *
//     * @param seat O assento a ser removido.
//     * @param id O ID do evento.
//     */
//    public void removeSeatByEventId(String seat, UUID id) {
//        Evento evento = eventoController.getById(id);
//        evento.removerAssento(seat);
//        eventoController.update(evento);
//    }
//
//    /**
//     * Adiciona um assento disponível a um evento.
//     *
//     * @param seat O assento a ser adicionado.
//     * @param id O ID do evento.
//     */
//    public void addSeatByEventId(String seat, UUID id) {
//        Evento evento = eventoController.getById(id);
//        evento.adicionarAssento(seat);
//        eventoController.update(evento);
//    }
//
//    /**
//     * Cria um novo evento.
//     *
//     * @param loginAdmin O login do administrador que cria o evento.
//     * @param name O nome do evento.
//     * @param description A descrição do evento.
//     * @param date A data do evento.
//     * @return O ID do evento criado.
//     */
//    public UUID create(String loginAdmin, String name, String description, Date date) {
//        Usuario usuario = usuarioController.getByLogin(loginAdmin);
//        Evento evento = eventoController.cadastrar(usuario, name, description, date);
//        return evento.getId();
//    }
//
//    /**
//     * Obtém um evento pelo seu ID.
//     *
//     * @param id O ID do evento.
//     * @return O objeto Evento.
//     */
//    public Evento getById(UUID id) {
//        return eventoController.getById(id);
//    }
//
//    /**
//     * Retorna o nome de um evento pelo seu ID.
//     *
//     * @param id O ID do evento.
//     * @return O nome do evento.
//     */
//    public String getNameByEventId(UUID id) {
//        return eventoController.getById(id).getNome();
//    }
//
//    /**
//     * Obtém a lista de assentos disponíveis de um evento.
//     *
//     * @param id O ID do evento.
//     * @return Uma lista de assentos disponíveis.
//     */
//    public List<String> getSeatsByEventId(UUID id) {
//        return eventoController.getById(id).getAssentosDisponiveis();
//    }
//
//    /**
//     * Obtém a descrição de um evento pelo seu ID.
//     *
//     * @param id O ID do evento.
//     * @return A descrição do evento.
//     */
//    public String getDescriptionByEventId(UUID id) {
//        return eventoController.getById(id).getDescricao();
//    }
//
//    /**
//     * Retorna o ano de um evento com base no seu ID.
//     *
//     * @param id O ID do evento.
//     * @return O ano do evento.
//     */
//    public int getYearByEventId(UUID id) {
//        return eventoController.getById(id).getData().getYear();
//    }
//
//    /**
//     * Retorna o mês de um evento com base no seu ID.
//     *
//     * @param id O ID do evento.
//     * @return O mês do evento.
//     */
//    public int getMonthByEventId(UUID id) {
//        return eventoController.getById(id).getData().getMonth();
//    }
//
//    /**
//     * Retorna o dia de um evento com base no seu ID.
//     *
//     * @param id O ID do evento.
//     * @return O dia do evento.
//     */
//    public int getDayByEventId(UUID id) {
//        return eventoController.getById(id).getData().getDay();
//    }
//
//    /**
//     * Verifica se um evento está ativo com base no seu ID.
//     *
//     * @param id O ID do evento.
//     * @return true se o evento estiver ativo, caso contrário false.
//     */
//    public boolean getIsActiveByEventId(UUID id) {
//        return eventoController.getById(id).isAtivo();
//    }
//
//    /**
//     * Adiciona um evento no banco de dados com uma data passada.
//     *
//     * @param usuario O usuário que adiciona o evento.
//     * @param name O nome do evento.
//     * @param description A descrição do evento.
//     * @param date A data do evento (passada).
//     * @return O ID do evento criado.
//     */
//    public UUID addEventInDataBaseWithPastDate(Usuario usuario, String name, String description, Date date) {
//        Evento evento = new Evento(name, description, date);
//        Evento event = eventoController.salvar(evento);
//        return evento.getId();
//    }
//
//    /**
//     * Obtém a quantidade de avaliações associadas a um evento.
//     *
//     * @param id O ID do evento.
//     * @return O número de avaliações.
//     */
//    public int getCommentQuantityByEventId(UUID id) {
//        List<Avaliacao> avaliacoes = avaliacaoController.getAvaliacoesEvento(id);
//        return avaliacoes.size();
//    }
//
//    /**
//     * Exclui todos os eventos.
//     */
//    public void deleteAllEvents() {
//        eventoController.deleteAll();
//    }
//}
