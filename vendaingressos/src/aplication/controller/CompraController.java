package aplication.controller;

import aplication.model.*;
import aplication.filemanagement.CompraFileManager;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

/**
 * Classe responsável pelo controle de compras.
 * Permite gerenciar a criação de recibos, recuperar compras e seus detalhes,
 * além de deletar compras.
 */
public class CompraController {

    private final CompraFileManager compraFileManager;
    private final UsuarioController usuarioController;
    private final IngressoController ingressoController;
    private final CartaoController cartaoController;
    private final EventoController eventoController;

    /**
     * Construtor da classe CompraController.
     * Inicializa os controladores e gerenciadores de arquivos necessários.
     */
    public CompraController() {
        this.compraFileManager = new CompraFileManager();
        this.usuarioController = new UsuarioController();
        this.ingressoController = new IngressoController();
        this.cartaoController = new CartaoController();
        this.eventoController = new EventoController();
    }

    /**
     * Cria múltiplos recibos de compra para os ingressos selecionados.
     * Para cada assento, cria-se um ingresso e um recibo, e o recibo é enviado por e-mail para o usuário.
     *
     * @param idUsuario O ID do usuário que está realizando a compra.
     * @param idEvento O ID do evento para o qual os ingressos estão sendo comprados.
     * @param assentos Uma lista de assentos selecionados para a compra.
     * @param idCartao O ID do cartão utilizado para realizar o pagamento.
     * @param preco O preço de cada ingresso.
     * @return Uma lista de UUIDs representando os IDs dos recibos gerados para as compras.
     * @throws Exception Se ocorrer algum erro ao processar a compra, criar os ingressos ou enviar o recibo.
     */
    public List<UUID> criarRecibo(UUID idUsuario, UUID idEvento, List<String> assentos, UUID idCartao, double preco) throws Exception {
        // Recupera os dados do usuário e do evento com os IDs fornecidos
        Usuario usuario = usuarioController.getById(idUsuario);
        Evento evento = eventoController.getById(idEvento);

        // Lista que armazenará os IDs dos recibos gerados
        List<UUID> idsCompras = new ArrayList<>();

        // Itera sobre cada assento selecionado para criar ingressos e recibos
        for (String assento : assentos) {
            // Cria o ingresso para o evento, usuário e assento selecionado
            Ingresso ingresso = ingressoController.criarIngresso(idEvento, idUsuario, preco, assento);

            // Cria a compra, associando o ingresso, o usuário e o cartão de pagamento
            Compra compra = new Compra(idUsuario, idEvento, ingresso.getId(), idCartao, preco);

            // Cria a string do recibo, que contém as informações da compra
            String recibo = "Ingresso de ID: " + ingresso.getId() +
                    "\nEvento: " + evento.getNome() +
                    "\nData: " + evento.getData() +
                    "\nAssento: " + assento +
                    "\nPreço: " + preco +
                    "\nMétodo de pagamento: " + compra.getMetodoPagamento() +
                    "\nID da transação: " + compra.getId();

            // Exibe no console que o comprovante foi enviado para o e-mail do usuário
            System.out.println("Comprovante de compra enviado para o email: " + usuario.getEmail() + ".\n");

            // Adiciona o recibo gerado ao usuário e o ingresso
            usuario.addRecibo(recibo);
            usuario.comprarIngresso(ingresso);

            // Salva a compra no sistema (ou banco de dados)
            compraFileManager.save(compra);

            // Adiciona o ID da compra (recibo) à lista de IDs
            idsCompras.add(compra.getId());
        }

        // Atualiza as informações do usuário, incluindo os recibos gerados
        usuarioController.update(usuario, usuario.getLogin(), usuario.getSenha(), usuario.getNome(), usuario.getEmail());

        // Retorna a lista de IDs das compras (recibos) geradas
        return idsCompras;
    }

    /**
     * Cria múltiplos recibos de compra sem associar a um cartão de pagamento, utilizando boleto.
     * Para cada assento selecionado, cria-se um ingresso e um recibo, enviando-o para o e-mail do usuário.
     *
     * @param idUsuario ID do usuário que está comprando.
     * @param idEvento  ID do evento associado à compra.
     * @param assentos  Lista de assentos selecionados no evento.
     * @param preco     Preço de cada ingresso.
     * @param boleto    ID (código) do boleto.
     * @return Uma lista de UUIDs representando os IDs das compras criadas.
     * @throws Exception Se houver algum erro na criação dos recibos ou ingressos.
     */
    public List<UUID> criarRecibo(UUID idUsuario, UUID idEvento, List<String> assentos, double preco, UUID boleto) throws Exception {
        // Recupera os dados do usuário e do evento com os IDs fornecidos
        Usuario usuario = usuarioController.getById(idUsuario);
        Evento evento = eventoController.getById(idEvento);

        // Lista que armazenará os IDs dos recibos gerados
        List<UUID> idsCompras = new ArrayList<>();

        // Itera sobre cada assento selecionado para criar ingressos e recibos
        for (String assento : assentos) {
            // Cria o ingresso para o evento, usuário e assento selecionado
            Ingresso ingresso = ingressoController.criarIngresso(idEvento, idUsuario, preco, assento);

            // Cria a compra, associando o ingresso, o usuário e o boleto de pagamento
            Compra compra = new Compra(idUsuario, idEvento, ingresso.getId(), preco, boleto);

            // Cria a string do recibo, que contém as informações da compra
            String recibo = "Ingresso de ID: " + ingresso.getId() +
                    "\nEvento: " + evento.getNome() +
                    "\nData: " + evento.getData() +
                    "\nAssento: " + assento +
                    "\nPreço: " + preco +
                    "\nMétodo de pagamento: Boleto" +
                    "\nID da transação: " + compra.getId();

            // Exibe no console que o comprovante foi enviado para o e-mail do usuário
            System.out.println("Comprovante de compra enviado para o email: " + usuario.getEmail() + ".\n");

            // Adiciona o recibo gerado ao usuário e o ingresso
            usuario.addRecibo(recibo);
            usuario.comprarIngresso(ingresso);
            usuarioController.update(usuario, usuario.getLogin(), usuario.getSenha(), usuario.getNome(), usuario.getEmail());

            // Salva a compra no sistema (ou banco de dados)
            compraFileManager.save(compra);

            // Adiciona o ID da compra (recibo) à lista de IDs
            idsCompras.add(compra.getId());
        }

        // Atualiza as informações do usuário, incluindo os recibos gerados
        usuarioController.update(usuario, usuario.getLogin(), usuario.getSenha(), usuario.getNome(), usuario.getEmail());

        // Retorna a lista de IDs das compras (recibos) geradas
        return idsCompras;
    }



    /**
     * Retorna uma compra pelo seu ID.
     *
     * @param id ID da compra.
     * @return Compra associada ao ID.
     */
    public Compra getById(UUID id) {
        return compraFileManager.getById(id);
    }

    /**
     * Retorna todas as compras registradas.
     *
     * @return Lista de compras.
     */
    public List<Compra> getAll() {
        return compraFileManager.getAll();
    }

    /**
     * Retorna o evento relacionado a uma compra.
     *
     * @param id ID da compra.
     * @return Evento associado à compra.
     */
    public Evento getEventoByIdCompra(UUID id) {
        return eventoController.getById(this.getById(id).getIdEvento());
    }

    /**
     * Retorna o login do usuário associado a uma compra.
     *
     * @param id ID da compra.
     * @return Login do usuário.
     */
    public String getLoginByIdCompra(UUID id) {
        return usuarioController.getById(this.getById(id).getIdUsuario()).getLogin();
    }

    /**
     * Retorna o ingresso associado a uma compra.
     *
     * @param id ID da compra.
     * @return Ingresso relacionado à compra.
     */
    public Ingresso getIngressoByIdCompra(UUID id) {
        return ingressoController.getById(this.getById(id).getIdIngresso());
    }

    /**
     * Retorna o número de recibos do usuário associado à compra.
     *
     * @param id ID da compra.
     * @return Quantidade de recibos do usuário.
     */
    public int getRecibosSizeById(UUID id) {
        String login = getLoginByIdCompra(id);
        Usuario usuario = usuarioController.getByLogin(login);
        return usuario.getRecibos().size();
    }

    /**
     * Retorna o cartão associado a uma compra.
     *
     * @param id ID da compra.
     * @return Cartão utilizado na compra.
     */
    public Cartao getCartaoByIdCompra(UUID id) {
        return cartaoController.getById(this.getById(id).getIdCartao());
    }

    /**
     * Deleta uma compra pelo seu ID.
     *
     * @param id ID da compra.
     */
    public void delete(UUID id) {
        compraFileManager.delete(id);
    }

    /**
     * Deleta todas as compras registradas.
     */
    public void deleteAll() {
        compraFileManager.deleteAll();
    }
}
