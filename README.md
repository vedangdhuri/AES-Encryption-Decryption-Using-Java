<h1 align="center">AES Encryption and Decryption in Java</h1>

<p align="left" style="font-size: 18.72px;">A simple yet powerful Java application that implements the <strong>Advanced Encryption Standard (AES)</strong> algorithm to perform secure text encryption and decryption.  
This project demonstrates the practical use of AES symmetric key cryptography for protecting sensitive information in Java.<p>

<h1 align="left">📘 Description</h1>

<p align="left" style="font-size: 18.72px;"><strong>AES (Advanced Encryption Standard)</strong> is a symmetric encryption algorithm widely used for securing data.  
This project — <strong>EDcrypt</strong> — provides an educational and functional example of how AES can be implemented in Java using the built-in `javax.crypto` package.</p>

<h1 align="left">🚀 Features</h1>
<ul>
    <li>AES 128-bit encryption and decryption</li>
    <li>Text-based encryption using a user-defined secret key</li>
    <li>Command-line interface (CLI) for user input</li>
    <li>Uses Base64 encoding for readable encrypted output</li>
    <li>Lightweight and dependency-free</li>
</ul>

<h1 align="left">⚙️ Installation & Setup</h1>

<h3 align="left"> Prerequirements</h3>
<ul>
    <li>Java JDK 8 or higher installed on your system</li>
    <li>Basic command-line knowledge</li>
</ul>

1. Clone the repository:

   ```bash
       git clone https://github.com/vedangdhuri/AES-Encryption-Decryption-Using-Java.git
   ```

2. Navigate to project folder
   ```bash
       cd AES-Encryption-Decryption-Using-Java
   ```
3. Compile the Java file

   ```bash
       javac EDcrypt.java
   ```

4. Run the program
   ```bash
       java EDcrypt
   ```

<h1 align="left">💻 Usage Example</h1>

<h3 align="left">   Encrypting a Message</h3>
<pre><code>
Enter text to encrypt: Hello Vedang
Enter secret key: mySecretKey123
Encrypted text: z1G7b8x+Nlq90O2ZTYbKAw==
</code></pre>

<h3 align="left">   Decrypting a Message</h3>
<pre><code>
Enter text to decrypt: z1G7b8x+Nlq90O2ZTYbKAw==
Enter secret key: mySecretKey123
Decrypted text: Hello Vedang
</code></pre>

<h1 align="left">🧠 How It Works</h1>
<ol>
    <li>The user provides text and a secret key.</li>
    <li>The key is converted into a secure AES key.</li>
    <li>The text is encrypted using AES and encoded with Base64.</li>
    <li>During decryption, the process is reversed to reveal the original message.</li>
</ol>

<h1 align="left">🚀 Future Enhancements</h1>
<ul>
  <li>File encryption and decryption</li>
  <li>GUI implementation (Swing/JavaFX)</li>
  <li>Support for AES-256</li>
  <li>Key management improvements</li>
</ul>

<h1>📜 License</h1>
<p>This project is licensed under the <a href="./LICENSE" target="_blank"><strong>MIT License</strong></a>.</p>