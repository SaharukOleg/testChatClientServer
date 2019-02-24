package server.packServer;

import pack.PacketMessage;
import server.OPacket;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PacketManager {

        private final static Map<Short, Class<? extends OPacket>> packets = new HashMap<Short, Class<? extends OPacket>>();

        static {
            packets.put((short) 1, PacketAuthorize.class);

        }

        public static OPacket getPacket(short id) throws IllegalAccessException, InstantiationException {
            return packets.get(id).newInstance();
        }

        public static void read(short id, DataInputStream dis) throws IllegalAccessException, InstantiationException, IOException {
            OPacket packet = packets.get(id).newInstance();
            packet.read(dis);
        }
}
