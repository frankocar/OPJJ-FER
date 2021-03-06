package hr.fer.zemris.java.p10.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class UDPKlijent {

	public static void main(String[] args) throws SocketException, UnknownHostException {
		
		if (args.length != 4) {
			System.out.println("Očeivao sam host port broj1 broj2");
			return;
		}
		
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		
		short broj1 = Short.parseShort(args[2]);
		short broj2 = Short.parseShort(args[3]);
		
		//priprema polja podataka
		byte[] podaci = new byte[4];
		ShortUtil.shortToBytes(broj1, podaci, 0);
		ShortUtil.shortToBytes(broj2, podaci, 2);
		
		//stvori pristupnu točku klijenta
		DatagramSocket dSocket = new DatagramSocket();
		
		//umetni podatke u paket i paketu postavi adresu i port poslužitelja
		DatagramPacket packet = new DatagramPacket(podaci, podaci.length);		
		packet.setSocketAddress(new InetSocketAddress(InetAddress.getByName(hostname), port));
		
		dSocket.setSoTimeout(4000);
		
		boolean answerRecieved = false;
		while (!answerRecieved) {
			
			System.out.println("Šaljem upit");
			try {
				dSocket.send(packet);
			} catch (IOException ex) {
				System.out.println("Ne mogu poslati upit");
				break;
			}
			
			//pripremi paket i čekaj odgovor
			byte[] recvBuffer = new byte[4];
			DatagramPacket recvPacket = new DatagramPacket(recvBuffer, recvBuffer.length);
			
			try {
				dSocket.receive(recvPacket);
			} catch (SocketTimeoutException ex) {
				continue;
			} catch (IOException ex) {
				continue;
			}
			
			//analiziraj sadržaj
			if (recvPacket.getLength() != 2) {
				System.out.println("Primljen paket neočekivane duljine");
				break;
			}
			
			System.out.println("Rezultat je: " +
					ShortUtil.bytesToShort(recvPacket.getData(), recvPacket.getOffset()));
			
			answerRecieved = true;	
			
		}
		
		dSocket.close();
		
	}
	
	
}
