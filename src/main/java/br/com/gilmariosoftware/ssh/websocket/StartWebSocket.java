package br.com.gilmariosoftware.ssh.websocket;

import br.com.gilmariosoftware.ssh.shell.SSHConection;
import br.com.gilmariosoftware.ssh.servidor.Servidor;
import br.com.gilmariosoftware.ssh.usuario.Usuario;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/start-websocket/{name}")
@ApplicationScoped
public class StartWebSocket {

    Map<String, Session> sessions = new ConcurrentHashMap<>();
    Map<String, SSHConection> sshConections = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name) throws IOException {
        System.out.println("onOpen> " + name);
        sessions.put(name, session);
        sshConections.put(name, new SSHConection(Usuario.builder().username("gilmario").nome("gilmario").password("kpslow@0909").build(), Servidor.builder().hostName("5.249.147.198").port(22).build()));
    }

    @OnClose
    public void onClose(Session session, @PathParam("name") String name) throws IOException {
        System.out.println("onClose> " + name);
        sessions.remove(name);
        broadcast("User " + name + " left");
        sshConections.get(name).write("exit");
        sshConections.get(name).terminate();
    }

    @OnError
    public void onError(Session session, @PathParam("name") String name, Throwable throwable) {
        System.out.println("onError> " + name + ": " + throwable);
        sessions.remove(name);
        broadcast("User " + name + " left on error: " + throwable);
//        sshConections.forEach((s, h) -> {
//            h.terminate();
//        });
    }

    @OnMessage
    public void onMessage(String message, @PathParam("name") String name) throws IOException {
        System.out.println("onMessage> " + name + ": " + message);
        if (message.equalsIgnoreCase("_ready_")) {
            broadcast("User " + name + " joined");
        } else {
            broadcast(sshConections.get(name).write(message));
        }
        System.out.println(sshConections.get(name).write(message));
//        sshConections.get(name).read();;
    }

    private void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result -> {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }
}
