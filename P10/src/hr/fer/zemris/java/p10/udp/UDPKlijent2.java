package hr.fer.zemris.java.p10.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class UDPKlijent2 {

	public static void main(String[] args) throws SocketException, UnknownHostException {
		
		if (args.length != 3) {
			System.out.println("Očeivao sam host port string");
			return;
		}
		
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		
		String text = args[2];

		byte[] textData = text.getBytes(StandardCharsets.UTF_8);
		byte[] podaci = new byte[textData.length + 2];
		
		ShortUtil.shortToBytes((short)textData.length, podaci, 0);
		
		for (int i = 2; i < podaci.length; i++) {
			podaci[i] = textData[i - 2];
		}

		DatagramSocket dSocket = new DatagramSocket();

		DatagramPacket packet = new DatagramPacket(podaci, podaci.length);		
		packet.setSocketAddress(new InetSocketAddress(InetAddress.getByName(hostname), port));
		
		dSocket.setSoTimeout(4000);
			
		System.out.println("Šaljem upit");
		try {
			dSocket.send(packet);
		} catch (IOException ex) {
			System.out.println("Ne mogu poslati upit");
		}		
		
		dSocket.close();
	
	}
	
}
