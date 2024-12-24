package aplication.controller;

import aplication.model.Evento;
import aplication.filemanagement.EventoFileManager;
import aplication.model.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.List;

/**
 * Classe responsável pelo controle dos eventos.
 * Esta classe permite criar, salvar, atualizar e deletar eventos,
 * bem como obter informações sobre eventos existentes.
 */
public class EventoController {
    private final EventoFileManager eventoFileManager;

    /**
     * Construtor da classe EventoController.
     * Inicializa o gerenciador de arquivos responsável pelos eventos.
     */
    public EventoController() {
        this.eventoFileManager = new EventoFileManager();
    }

    /**
     * Cadastra um novo evento.
     * Apenas administradores podem cadastrar eventos, e a data do evento não pode ser no passado.
     *
     * @param usuario    Usuário que está cadastrando o evento (deve ser administrador).
     * @param nome       Nome do evento.
     * @param descricao  Descrição do evento.
     * @param data       Data do evento.
     * @return           O evento criado.
     * @throws SecurityException Se o usuário não for administrador ou a data for inválida.
     */
    public Evento cadastrar(Usuario usuario, String nome, String descricao, Date data, double preco) {
        Evento evento = new Evento(nome, descricao, data, preco);
        Date hoje = new Date();
        if (!usuario.isAdmin()) {
            throw new SecurityException("Somente administradores podem cadastrar eventos.");
        }
        if (hoje.after(data)){
            throw new SecurityException("Impossível cadastrar eventos em datas passadas.");
        }
        eventoFileManager.save(evento);
        return evento;
    }

    /**
     * Salva ou atualiza um evento no sistema.
     *
     * @param evento O evento a ser salvo.
     * @return O evento salvo.
     */
    public Evento salvar(Evento evento) {
        eventoFileManager.save(evento);
        return evento;
    }

    /**
     * Busca um evento pelo seu ID.
     *
     * @param id ID do evento.
     * @return Evento correspondente ao ID.
     */
    public Evento getById(UUID id) {
        return eventoFileManager.getById(id);
    }

    /**
     * Busca um evento pelo nome.
     *
     * @param nome Nome do evento.
     * @return Evento correspondente ao nome, ou null se não encontrado.
     */
    public Evento getByNome(String nome) {
        List<Evento> eventos = eventoFileManager.getAll();
        return eventos.stream()
                .filter(evento -> evento.getNome().equals(nome))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retorna a lista de todos os eventos.
     *
     * @return Lista de eventos.
     */
    public List<Evento> getAll() {
        return eventoFileManager.getAll();
    }

    public List<Evento> getDisponiveis() {
        List<Evento> eventos = eventoFileManager.getAll();
        List<Evento> disponiveis = new ArrayList<>();
        Date hoje = new Date();
        for (Evento evento : eventos) {
            if(hoje.before(evento.getData())){
                disponiveis.add(evento);
            }
        }
        return disponiveis;
    }

    /**
     * Atualiza um evento existente.
     *
     * @param evento Evento a ser atualizado.
     */
    public void update(Evento evento) {
        eventoFileManager.update(evento);
    }

    /**
     * Deleta um evento pelo seu ID.
     * Apenas administradores podem deletar eventos.
     *
     * @param usuario Usuário que está tentando deletar (deve ser administrador).
     * @param id      ID do evento a ser deletado.
     * @throws SecurityException Se o usuário não for administrador.
     */
    public void delete(Usuario usuario, UUID id) {
        if (!usuario.isAdmin()) {
            throw new SecurityException("Somente administradores podem remover eventos.");
        }
        eventoFileManager.delete(id);
    }

    /**
     * Deleta todos os eventos do sistema.
     */
    public void deleteAll() {
        eventoFileManager.deleteAll();
    }
}
