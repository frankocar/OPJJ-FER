package hr.fer.zemris.java.p10.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UDPPosluzitelj {

	public static void main(String[] args) throws SocketException{
		if (args.length != 1) {
			System.out.println("Očekivao sam port");
			return;
		}
		
		System.out.println("test");
		
		int port = Integer.parseInt(args[0]);
		
		@SuppressWarnings("resource")
		DatagramSocket dSocket = new DatagramSocket(null);
		dSocket.bind(new InetSocketAddress((InetAddress)null, port));
		
		while (true) {
			byte[] recvBuffer = new byte[40];
			DatagramPacket recvPacket = new DatagramPacket(recvBuffer, recvBuffer.length);
			
			try {
				dSocket.receive(recvPacket);
			} catch (IOException e) {
				continue;
			}
			
			
			if (recvPacket.getLength() != 4) {
				System.out.println("Nesipravan upit od " + recvPacket.getSocketAddress());
				continue;
			}
			
			short broj1 = ShortUtil.bytesToShort(recvPacket.getData(), recvPacket.getOffset());
			short broj2 = ShortUtil.bytesToShort(recvPacket.getData(), recvPacket.getOffset() + 2);
			
			short result = (short) (broj1 + broj2);
			System.out.println(broj1 + " + " + broj2 + " = " + result);
			
			byte[] sendBuffer = new byte[2];
			ShortUtil.shortToBytes(result, sendBuffer, 0);
			
			DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length);
			
			sendPacket.setSocketAddress(recvPacket.getSocketAddress());
			
			try {
				dSocket.send(sendPacket);
			} catch (IOException ex) {
				System.out.println("Greška pri slanju odgovora");
			}
		}
		
		
		
		
	}
	
}
