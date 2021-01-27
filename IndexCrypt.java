import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;

public class IndexCrypt {
	public static void main(String[] args) {
		if (args.length == 4) {
			String mode = args[0];
			String inputFilePath = args[1];
			String outputFilePath = args[2];
			String keyFilePath = args[3];
			if (mode.equalsIgnoreCase("encrypt")) encrypt(inputFilePath, outputFilePath, keyFilePath);
			else if (mode.equalsIgnoreCase("decrypt")) decrypt(inputFilePath, outputFilePath, keyFilePath);
			else System.out.println("Error: Invalid mode");
		} else System.out.println("Error: Invalid args");
	}
	
	public static void encrypt(String inputFilePath, String encryptedFilePath, String keyFilePath) {
		try {
			StringBuffer encryptedFileBuffer = new StringBuffer(); // initialize buffers, list of unique tokens
			StringBuffer keyFileBuffer = new StringBuffer();
			ArrayList<String> uniqueTokens = new ArrayList<String>();
			
			String inputFileData = Files.readString(Path.of(inputFilePath)); // scan input file to populate buffers, list of unique tokens
			Scanner inputFileScanner = new Scanner(inputFileData).useDelimiter(" ");
			while (inputFileScanner.hasNext()) {
				String token = inputFileScanner.next();
				if (!uniqueTokens.contains(token)) {
					keyFileBuffer.append(token + " "); // if we haven't seen the token yet, add it to the key buffer and the list of unique tokens
					uniqueTokens.add(token); 
				}
				int index = uniqueTokens.indexOf(token); // encrypted file consists of indexes delimited by spaces
				encryptedFileBuffer.append(index + " ");
			}
			inputFileScanner.close();
			
			Files.writeString(Path.of(encryptedFilePath), encryptedFileBuffer); // write buffers to files
			Files.writeString(Path.of(keyFilePath), keyFileBuffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void decrypt(String inputFilePath, String decryptedFilePath, String keyFilePath) {
		try {
			ArrayList<String> uniqueTokens = new ArrayList<String>(); // initialize list of unique tokens
			String keyFileData = Files.readString(Path.of(keyFilePath)); // scan key file to populate list
			Scanner keyFileScanner = new Scanner(keyFileData).useDelimiter(" ");
			while (keyFileScanner.hasNext()) uniqueTokens.add(keyFileScanner.next());
			keyFileScanner.close();
			
			StringBuffer decryptedFileBuffer = new StringBuffer(); // initialize buffer for decrypted file
			String inputFileData = Files.readString(Path.of(inputFilePath)); // scan input file to populate buffer
			Scanner inputFileScanner = new Scanner(inputFileData).useDelimiter(" ");
			while (inputFileScanner.hasNext()) {
				int index = Integer.parseInt(inputFileScanner.next()); // input file provides index
				String token = uniqueTokens.get(index); // index provides original token
				decryptedFileBuffer.append(token + " ");
			}
			inputFileScanner.close();
			
			Files.writeString(Path.of(decryptedFilePath), decryptedFileBuffer); // write buffer to file
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}