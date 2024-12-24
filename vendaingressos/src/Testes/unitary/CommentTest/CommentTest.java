//package Testes.unitary.CommentTest;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.UUID;
//
//import aplication.facade.UserTestFacade.UserTestFacade;
//import aplication.facade.EventTestFacade.EventTestFacade;
//import aplication.facade.TicketTestFacade.TicketTestFacade;
//import aplication.facade.CommentTestFacade.CommentTestFacade;
//import aplication.model.Usuario;
//
//import static org.junit.Assert.*;
//
//public class CommentTest {
//    private UserTestFacade userTestFacade;
//    private EventTestFacade eventTestFacadeFacade;
//    private TicketTestFacade ticketTestFacade;
//    private CommentTestFacade commentTestFacade;
//    private Usuario usuario;
//
//    @Before
//    public void setUp() {
//        userTestFacade = new UserTestFacade();
//        eventTestFacadeFacade = new EventTestFacade();
//        ticketTestFacade = new TicketTestFacade();
//        commentTestFacade = new CommentTestFacade();
//    }
//
//    @After
//    public void tearDown() {
//        userTestFacade.deleteAllUsers();
//        eventTestFacadeFacade.deleteAllEvents();
//        ticketTestFacade.deleteAllTickets();
//        commentTestFacade.deleteAllComments();
//    }
//
//    @Test
//    public void testCreateComment() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2020, Calendar.SEPTEMBER, 10);
//        String name = "Show de Rock";
//        String description = "Banda XYZ";
//        Date date = calendar.getTime();
//
//        String login = "LS@";
//        String nameUser = "Lia Silva";
//        String email = "lia@example.com";
//        String password = "teste123";
//        String cpf = "987654321";
//        Boolean isAdmin = true;
//
//        boolean user = userTestFacade.create(login, password, nameUser, cpf, email, isAdmin);
//
//        if(user){
//            usuario = userTestFacade.getByEmail(email);
//        }
//
//        UUID eventId = eventTestFacadeFacade.addEventInDataBaseWithPastDate(usuario, name, description, date);
//
//        UUID commentId = commentTestFacade.create(userTestFacade.getUserIdByEmail(email), eventId, 2, "Bom evento!");
//
//        assertNotNull(commentTestFacade.getById(commentId));
//        assertEquals("Bom evento!", commentTestFacade.getContentById(commentId));
//        assertEquals(2, commentTestFacade.getRatingCommentById(commentId));
//        assertEquals(userTestFacade.getUserIdByEmail(email), commentTestFacade.getUserIdById(commentId));
//        assertEquals(eventId, commentTestFacade.getEventIdById(commentId));
//    }
//
//    @Test
//    public void testCreateCommentFutureEvent() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2025, Calendar.SEPTEMBER, 10);
//        String name = "Show de Rock";
//        String description = "Banda XYZ";
//        Date date = calendar.getTime();
//
//        String _login = "LS@";
//        String _nameUser = "Lia Silva";
//        String _email = "lia@example.com";
//        String _password = "teste123";
//        String _cpf = "987654321";
//        Boolean _isAdmin = true;
//
//        String login = "Lr@";
//        String nameUser = "Lara";
//        String email = "lr@example.com";
//        String password = "teste123";
//        String cpf = "123456789";
//        Boolean isAdmin = true;
//
//        userTestFacade.create(_login, _password, _nameUser, _cpf, _email, _isAdmin);
//        userTestFacade.create(login, password, nameUser, cpf, email, isAdmin);
//
//        UUID eventId = eventTestFacadeFacade.create(_login, name, description, date);
//
//        Exception exception = assertThrows(SecurityException.class, () -> {
//            commentTestFacade.create(userTestFacade.getUserIdByEmail(email), eventId, 4, "Bom evento!");
//        });
//
//        assertEquals("Só é possível avaliar após a realização do evento!", exception.getMessage());
//    }
//
//    @Test
//    public void readCommentByEventTest() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2020, Calendar.SEPTEMBER, 10);
//        Date date = calendar.getTime();
//
//
//        String login = "LS@";
//        String nameUser = "Lia Silva";
//        String email = "lia@example.com";
//        String password = "teste123";
//        String cpf = "987654321";
//        Boolean isAdmin = true;
//
//        boolean user = userTestFacade.create(login, password, nameUser, cpf, email, isAdmin);
//
//        if(user){
//            usuario = userTestFacade.getByEmail(email);
//        }
//
//        UUID firstEventId = eventTestFacadeFacade.addEventInDataBaseWithPastDate(usuario,"Show de Rock", "Evento musical", date);
//        UUID secondEventId = eventTestFacadeFacade.addEventInDataBaseWithPastDate(usuario, "Jogo do Bahia", "Evento esportivo", date);
//
//        UUID c1Id = commentTestFacade.create(userTestFacade.getUserIdByEmail(email), firstEventId, 4, "Bom evento!");
//        UUID c2Id = commentTestFacade.create(userTestFacade.getUserIdByEmail(email), secondEventId, 5, "Bom evento!");
//        UUID c3Id = commentTestFacade.create(userTestFacade.getUserIdByEmail(email), secondEventId, 3, "Bom evento!");
//
//        assertNotNull(commentTestFacade.getById(c1Id));
//        assertNotNull(commentTestFacade.getById(c2Id));
//        assertNotNull(commentTestFacade.getById(c3Id));
//
//        assertEquals(1, eventTestFacadeFacade.getCommentQuantityByEventId(firstEventId));
//        assertEquals(2, eventTestFacadeFacade.getCommentQuantityByEventId(secondEventId));
//    }
//
//
//    @Test
//    public void ratingAverageTest() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2020, Calendar.SEPTEMBER, 10);
//        Date date = calendar.getTime();
//
//        String login = "LS@";
//        String nameUser = "Lia Silva";
//        String email = "lia@example.com";
//        String password = "teste123";
//        String cpf = "987654321";
//        Boolean isAdmin = true;
//
//        boolean user = userTestFacade.create(login, password, nameUser, cpf, email, isAdmin);
//
//        if(user){
//            usuario = userTestFacade.getByEmail(email);
//        }
//
//        UUID eventId = eventTestFacadeFacade.addEventInDataBaseWithPastDate(usuario, "Show de Rock", "Evento musical", date);
//
//        UUID c1Id = commentTestFacade.create(userTestFacade.getUserIdByEmail(email), eventId, 4, "Bom evento, mas pode melhorar!");
//        UUID c2Id = commentTestFacade.create(userTestFacade.getUserIdByEmail(email), eventId, 5, "Bom evento!");
//
//        assertNotNull(commentTestFacade.getById(c1Id));
//        assertNotNull(commentTestFacade.getById(c2Id));
//
//        assertEquals(4.5, commentTestFacade.getEventRatingByEventId(eventId), 0.001);
//    }
//
//    @Test
//    public void deleteCommentTest() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2020, Calendar.SEPTEMBER, 10);
//        Date date = calendar.getTime();
//
//        String login = "LS@";
//        String nameUser = "Lia Silva";
//        String email = "lia@example.com";
//        String password = "teste123";
//        String cpf = "987654321";
//        Boolean isAdmin = true;
//
//        boolean user = userTestFacade.create(login, password, nameUser, cpf, email, isAdmin);
//
//        if(user){
//            usuario = userTestFacade.getByEmail(email);
//        }
//
//        UUID eventId = eventTestFacadeFacade.addEventInDataBaseWithPastDate(usuario, "Show de Rock", "Evento musical", date);
//
//        UUID c1Id = commentTestFacade.create(userTestFacade.getUserIdByEmail(email), eventId, 4, "Bom evento, mas pode melhorar!");
//        assertNotNull(commentTestFacade.getById(c1Id));
//
//        commentTestFacade.delete(c1Id);
//
//        assertNull(commentTestFacade.getById(c1Id));
//    }
//}
