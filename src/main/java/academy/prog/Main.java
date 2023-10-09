package academy.prog;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		String to = null;
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Enter your login: ");
			String login = scanner.nextLine();
	
			Thread th = new Thread(new GetThread());
			th.setDaemon(true);
			th.start();

			Thread thPrivate = new Thread(new GetThreadPrv(login));
			thPrivate.setDaemon(true);
			thPrivate.start();

			System.out.println("Enter your message: ");
			while (true) {
				String text = scanner.nextLine();
				if (text.isEmpty()) break;

				String[] words = text.split(" ");
				if (words[0].startsWith("@")) {
					to = words[0].substring(1);
					text = text.replaceAll(words[0], "" );
				}

				Message m = new Message(login, to, text);
				int res = m.send(Utils.getURL() + "/add");
				to = null;

				if (res != 200) { // 200 OK
					System.out.println("HTTP error occurred: " + res);
					return;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}
