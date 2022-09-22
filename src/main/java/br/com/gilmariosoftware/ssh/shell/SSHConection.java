package br.com.gilmariosoftware.ssh.shell;

import br.com.gilmariosoftware.ssh.usuario.Usuario;
import br.com.gilmariosoftware.ssh.servidor.Servidor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.channel.Channel;

/**
 *
 * @author gilmario
 */
public class SSHConection {

    private static final long DEFAULT_TIMEOUT = 5;
    private final Usuario usuario;
    private final Servidor servidor;
    private ClientSession session;
    private ByteArrayOutputStream responseStream;
    private ClientChannel channel;
    private OutputStream pipedIn;
    private SshClient client;

    public SSHConection(Usuario usuario, Servidor servidor) throws IOException {
        this.usuario = usuario;
        this.servidor = servidor;

        client = SshClient.setUpDefaultClient();
        client.start();

        client.connect(usuario.getUsername(), servidor.getHostName(), this.servidor.getPort()).addListener((l) -> {
            session = l.getClientSession();
            session.addPasswordIdentity(usuario.getPassword());
            try {
                session.auth().addListener((j) -> {

                    responseStream = new ByteArrayOutputStream();

                    try {
                        channel = session.createChannel(Channel.CHANNEL_SHELL);
                        channel.setOut(responseStream);
                        channel.setErr(responseStream);
                        channel.open().addListener((t) -> {
                            pipedIn = channel.getInvertedIn();
                        });

                        channel.getAsyncOut().removeCloseFutureListener((dd) -> {
                            System.out.println("getAsyncOut");
                        });
                        channel.getAsyncErr().removeCloseFutureListener((dd) -> {
                            System.out.println("getAsyncErr");
                        });
                        channel.getAsyncIn().removeCloseFutureListener((dd) -> {
                            System.out.println("getAsyncIn");
                        });
                    } catch (IOException ex) {
                        Logger.getLogger(SSHConection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

            } catch (IOException ex) {
                Logger.getLogger(SSHConection.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

//        String responseString = new String(responseStream.toByteArray());
    }

    public void read() {
        String responseString = new String(responseStream.toByteArray());
        System.out.println(responseString);

    }

    public String write(String command) throws IOException {
        if (Objects.isNull(pipedIn)) {
            pipedIn = channel.getInvertedIn();
        }
//        pipedIn.write(command.concat("/r/n").getBytes());;
//        pipedIn.flush();
        return session.executeRemoteCommand(command, responseStream, Charset.defaultCharset());
    }

//    public static void listFolderStructure(String username, String password,
//            String host, int port, long defaultTimeoutSeconds, String command) throws IOException {
//
//        SshClient client = SshClient.setUpDefaultClient();
//        client.start();
//
//        try ( ClientSession session = client.connect(username, host, port).verify(defaultTimeoutSeconds, TimeUnit.SECONDS).getSession()) {
//            session.addPasswordIdentity(password);
//            session.auth().verify(defaultTimeoutSeconds, TimeUnit.SECONDS);
//
//            try ( ByteArrayOutputStream responseStream = new ByteArrayOutputStream();  ClientChannel channel = session.createChannel(Channel.CHANNEL_SHELL)) {
//                channel.setOut(responseStream);
//                try {
//                    channel.open().verify(defaultTimeoutSeconds, TimeUnit.SECONDS);
//                    try ( OutputStream pipedIn = channel.getInvertedIn()) {
//                        pipedIn.write(command.getBytes());
//                        pipedIn.flush();
//                    }
//
////                    channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), TimeUnit.SECONDS.toMillis(defaultTimeoutSeconds));
//                    String responseString = new String(responseStream.toByteArray());
//                    System.out.println(responseString);
//                } finally {
//                    channel.close(false);
//                }
//            }
//        } finally {
//            client.stop();
//        }
//    }
    public void terminate() {
        channel.close(false);
        client.stop();
    }
}
