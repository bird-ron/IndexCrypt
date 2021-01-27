Description:	
	Java program for encrypting and decrypting text files using indexing.
Compilation:
	javac IndexCrypt.java
Encryption:
	java IndexCrypt encrypt <inputFile> <encryptedFile> <keyFile>
	Reads from <inputFile> and produces <encryptedFile>, <keyFile> (will overwrite).
Decryption:
	java IndexCrypt decrypt <inputFile> <decryptedFile> <keyFile>
	Reads from <inputFile> and <keyFile> and produces <decryptedFile> (will overwrite).