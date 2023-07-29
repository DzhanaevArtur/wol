import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Slf4j
public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        byte[] macBytes = getMacBytes();
        byte[] bytes = new byte[102];

        for (int i = 0; i < 6; i++) bytes[i] = (byte) 0xff;
        for (int i = 6; i < bytes.length; i += 6) System.arraycopy(macBytes, 0, bytes, i, 6);

        DatagramSocket socket = new DatagramSocket();
        socket.send(new DatagramPacket(bytes, bytes.length, InetAddress.getByName(args[0]), 9));
        socket.close();

        log.info("Wake-on-LAN packet sent");
    }

    private static byte @NotNull [] getMacBytes() throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        for (int i = 0; i < 6; i++) bytes[i] = (byte) Integer.parseInt("C8-7F-54-03-20-31".split("-")[i], 16);
        return bytes;
    }
}
