//package Testes.unitary.TicketTest;
//
//import aplication.facade.EventTestFacade.EventTestFacade;
//import aplication.facade.TicketTestFacade.TicketTestFacade;
//import aplication.facade.UserTestFacade.UserTestFacade;
//import aplication.model.Usuario;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.UUID;
//
//import static org.junit.Assert.*;
//import static org.junit.Assert.assertEquals;
//
//public class TicketTest {
//    private UserTestFacade userFacade;
//    private EventTestFacade eventFacade;
//    private TicketTestFacade ticketFacade;
//    private Usuario usuario;
//
//    @Before
//    public void setUp() {
//        userFacade = new UserTestFacade();
//        eventFacade = new EventTestFacade();
//        ticketFacade = new TicketTestFacade();
//    }
//
//    @After
//    public void tearDown() {
//        userFacade.deleteAllUsers();
//        eventFacade.deleteAllEvents();
//        ticketFacade.deleteAllTickets();
//    }
//
//    @Test
//    public void ticketCreateTest() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2025, Calendar.SEPTEMBER, 10);
//        String name = "Show de Rock";
//        String description = "Banda XYZ";
//        Date date = calendar.getTime();
//
//        String login = "john";
//        String nameUser = "john souza";
//        String email = "john@example.com";
//        String password = "teste123";
//        String cpf = "123456789";
//        Boolean isAdmin = true;
//
//        boolean user = userFacade.create(login, password, nameUser, cpf, email, isAdmin);
//
//        if(user){
//            usuario = userFacade.getByEmail(email);
//        }
//
//        UUID eventId = eventFacade.create(login, name, description, date);
//
//        assertNotNull(eventFacade.getById(eventId));
//
//        eventFacade.addSeatByEventId("A1", eventId);
//
//        UUID ticketId = ticketFacade.create(eventId, usuario.getId(), 100.0, "A1");
//
//        assertNotNull(ticketFacade.getById(ticketId));
//        assertEquals(eventId, ticketFacade.getEventByTicketId(ticketId));
//        assertEquals(100.0, ticketFacade.getPriceByTicketId(ticketId), 0.0001);
//        assertEquals("A1", ticketFacade.getSeatByTicketId(ticketId));
//        assertTrue(ticketFacade.getIsActive(ticketId));
//    }
//
//    @Test
//    public void ticketCancelTest() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2025, Calendar.SEPTEMBER, 10);
//        String name = "Show de Rock";
//        String description = "Banda XYZ";
//        Date date = calendar.getTime();
//
//        String login = "john";
//        String nameUser = "john souza";
//        String email = "john@example.com";
//        String password = "teste123";
//        String cpf = "123456789";
//        Boolean isAdmin = true;
//
//        boolean user = userFacade.create(login, password, nameUser, cpf, email, isAdmin);
//
//        if(user){
//            usuario = userFacade.getByEmail(email);
//        }
//
//        UUID eventId = eventFacade.create(login, name, description, date);
//
//        eventFacade.addSeatByEventId("A1", eventId);
//
//        UUID ticketId = ticketFacade.create(eventId, usuario.getId(), 100.0, "A1");
//
//        ticketFacade.cancelByTicketId(ticketId);
//
//        assertFalse(ticketFacade.getIsActive(ticketId));
//    }
//
//
//    @Test
//    public void testReactivateTicket() throws Exception {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2025, Calendar.SEPTEMBER, 10);
//        String name = "Show de Rock";
//        String description = "Banda XYZ";
//        Date date = calendar.getTime();
//
//        String login = "john";
//        String nameUser = "john souza";
//        String email = "john@example.com";
//        String password = "teste123";
//        String cpf = "123456789";
//        Boolean isAdmin = true;
//
//        boolean user = userFacade.create(login, password, nameUser, cpf, email, isAdmin);
//
//        if(user){
//            usuario = userFacade.getByEmail(email);
//        }
//
//        UUID eventId = eventFacade.create(login, name, description, date);
//
//        eventFacade.addSeatByEventId("A1", eventId);
//
//        UUID ticketId = ticketFacade.create(eventId, usuario.getId(), 100.0, "A1");
//
//        ticketFacade.cancelByTicketId(ticketId);
//        assertFalse(ticketFacade.getIsActive(ticketId));
//
//        ticketFacade.reactiveById(ticketId);
//        assertTrue(ticketFacade.getIsActive(ticketId));
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void duplicateTicketTest() throws Exception {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2025, Calendar.SEPTEMBER, 10);
//        String name = "Show de Rock";
//        String description = "Banda XYZ";
//        Date date = calendar.getTime();
//
//        String login = "john";
//        String nameUser = "john souza";
//        String email = "john@example.com";
//        String password = "teste123";
//        String cpf = "123456789";
//        Boolean isAdmin = true;
//
//        boolean user = userFacade.create(login, password, nameUser, cpf, email, isAdmin);
//
//        if(user){
//            usuario = userFacade.getByEmail(email);
//        }
//
//        UUID eventId = eventFacade.create(login, name, description, date);
//
//        ticketFacade.create(eventId, usuario.getId(), 100.0, "A1");
//        ticketFacade.create(eventId, usuario.getId(), 100.0, "A1");
//    }
//}