package aplication.controller;

import aplication.filemanagement.IngressoFileManager;
import aplication.model.Ingresso;
import aplication.model.Usuario;
import aplication.filemanagement.EventoFileManager;
import aplication.model.Evento;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável pelo controle das operações relacionadas aos ingressos.
 * Utiliza o gerenciador de arquivos para salvar, editar e buscar ingressos.
 */
public class IngressoController {

    private final IngressoFileManager ingressoFileManager;
    private final EventoController eventoController;
    private final UsuarioController usuarioController;

    /**
     * Construtor da classe IngressoController.
     * Inicializa o gerenciador de arquivos de ingressos e o controlador de eventos.
     */
    public IngressoController() {
        ingressoFileManager = new IngressoFileManager();
        eventoController = new EventoController();
        usuarioController = new UsuarioController();
    }

    /**
     * Cria um novo ingresso para um evento.
     * Verifica se o assento ainda está disponível e o remove da lista de disponíveis.
     *
     * @param eventoId ID do evento.
     * @param usuarioId ID do usuário.
     * @param preco Preço do ingresso.
     * @param assento Assento escolhido.
     * @return O objeto ingresso criado.
     * @throws IllegalArgumentException Caso o assento não esteja disponível.
     */
    public Ingresso criarIngresso(UUID eventoId, UUID usuarioId, double preco, String assento) throws Exception {
        Ingresso ingresso = new Ingresso(eventoId, usuarioId, preco, assento);
        EventoController eventoController = new EventoController();
        Evento evento = eventoController.getById(eventoId);
        if(!evento.getAssentosDisponiveis().contains(assento)) {
            throw new IllegalArgumentException("Esse assento não está mais disponível!");
        }
        evento.removerAssento(assento);
        eventoController.update(evento);
        ingressoFileManager.save(ingresso);
        return ingresso;
    }

    /**
     * Retorna o ingresso pelo ID.
     *
     * @param id ID do ingresso.
     * @return O objeto ingresso correspondente ao ID.
     */
    public Ingresso getById(UUID id) {
        return ingressoFileManager.getById(id);
    }

    /**
     * Retorna todos os ingressos cadastrados.
     *
     * @return Lista de todos os ingressos.
     */
    public List<Ingresso> getAll() {
        return ingressoFileManager.getAll();
    }

    /**
     * Retorna uma lista de ingressos associados ao usuário fornecido.
     *
     * @param usuarioId UUID do usuário para o qual os ingressos devem ser recuperados.
     * @return Lista de objetos Ingresso que pertencem ao usuário especificado.
     */
    public List<Ingresso> getByUsuarioId(UUID usuarioId) {
        return ingressoFileManager.getAll().stream()
                .filter(ingresso -> ingresso.getUsuarioId().equals(usuarioId))
                .collect(Collectors.toList());
    }


    /**
     * Retorna o evento associado a um ingresso específico.
     *
     * @param id UUID do ingresso cujo evento será recuperado.
     * @return Objeto Evento associado ao ingresso.
     */
    public Evento getEventoById(UUID id) {
        Ingresso ingresso = this.getById(id);
        return eventoController.getById(ingresso.getEventoId());
    }


    /**
     * Desativa um ingresso, devolvendo o assento ao evento, se o evento ainda estiver ativo.
     *
     * @param id ID do ingresso a ser desativado.
     * @return True se o ingresso foi desativado com sucesso, false caso contrário.
     */
    public boolean desativarIngresso(UUID id) throws Exception {
        Ingresso ingresso = this.getById(id);
        Evento evento = eventoController.getById(ingresso.getEventoId());
        Usuario usuario = usuarioController.getById(ingresso.getUsuarioId());
        if(evento.isAtivo()){
            evento.adicionarAssento(ingresso.getAssento());
            ingresso.setAtivo(false);
            this.update(ingresso);
            eventoController.update(evento);
            for (Ingresso ingressoUsuario : usuario.getIngressos()) {
                if (ingressoUsuario.getId().equals(id)) {
                    ingressoUsuario.setAtivo(false);
                    break;
                }
            }
            usuarioController.update(usuario, usuario.getLogin(), usuario.getSenha(), usuario.getNome(), usuario.getEmail());
            return true;
        }
        return false;
    }

    /**
     * Reativa um ingresso, removendo o assento da lista de disponíveis do evento.
     *
     * @param id ID do ingresso a ser reativado.
     */
    public void reativarIngresso(UUID id) {
        Ingresso ingresso = this.getById(id);
        Evento evento = eventoController.getById(ingresso.getEventoId());
        if(evento.isAtivo()){
            evento.removerAssento(ingresso.getAssento());
            ingresso.setAtivo(true);
            this.update(ingresso);
        }
    }

    /**
     * Atualiza um ingresso.
     *
     * @param ingresso Ingresso a ser atualizado.
     */
    public void update(Ingresso ingresso) {
        ingressoFileManager.update(ingresso);
    }

    /**
     * Remove um ingresso pelo ID.
     *
     * @param id ID do ingresso a ser removido.
     */
    public void delete(UUID id) {
        ingressoFileManager.delete(id);
    }

    /**
     * Remove todos os ingressos cadastrados.
     */
    public void deleteAll(){
        ingressoFileManager.deleteAll();
    }
}
