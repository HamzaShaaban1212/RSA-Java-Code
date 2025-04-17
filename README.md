# 🔐 RSA Encryption GUI (Java Swing)
This Java application demonstrates the RSA encryption and decryption algorithm with a graphical user interface built using Swing. The program allows users to either manually enter prime numbers or let the system generate them automatically to carry out RSA encryption.

# 💡 Features Enter or auto-generate prime numbers for RSA key generation.

Input plaintext to encrypt using the generated public key.

View all RSA parameters including:

Prime numbers p and q

Modulus n

Public key e

Private key components k and d

Encrypted cipher output

Decrypted text using the private key

Clean and interactive UI with styled components

# 🛠️ How It Works Prime Selection:

If the user enters 1, they'll be prompted to manually enter two prime numbers.

Any other input will result in the program generating two random primes in the specified ranges.

RSA Key Generation:

Calculates modulus n = p * q

Computes Euler's totient phi = (p - 1) * (q - 1)

Generates public key e such that gcd(e, phi) = 1

Computes private key d satisfying e * d ≡ 1 mod phi

Encryption & Decryption:

Converts plaintext characters to ASCII and encrypts them using modular exponentiation.

Decrypts the ciphertext using the private key to recover original text.

# 📦 Technologies Used Java SE

Swing for GUI

BigInteger for prime checking and GCD computation
