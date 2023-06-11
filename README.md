# Cryptography

This repository contains implementations of various cryptographic algorithms using Python. The implemented algorithms include RSA encryption, hybrid encryption, DSA (Digital Signature Algorithm), and Diffie-Hellman key exchange.

## Table of Contents

- [Introduction](#introduction)
- [Algorithms](#algorithms)
  - [RSA Encryption](#rsa-encryption)
  - [Hybrid Encryption](#hybrid-encryption)
  - [DSA (Digital Signature Algorithm)](#dsa-digital-signature-algorithm)
  - [Diffie-Hellman Key Exchange](#diffie-hellman-key-exchange)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Introduction

Cryptography is the practice of securing communication from unauthorized access or modification by converting data into a format that is unreadable to anyone except the intended recipient. This repository provides Python implementations of several fundamental cryptographic algorithms, including RSA encryption, hybrid encryption, DSA (Digital Signature Algorithm), and Diffie-Hellman key exchange.

## Algorithms

### RSA Encryption

[RSA](https://en.wikipedia.org/wiki/RSA_(cryptosystem)) is an asymmetric encryption algorithm widely used for secure data transmission. It relies on the mathematical difficulty of factoring large integers to provide security. The RSA implementation in this repository allows you to generate key pairs, encrypt messages using the public key, and decrypt them using the private key.

### Hybrid Encryption

Hybrid encryption combines symmetric and asymmetric encryption to provide the benefits of both approaches. In this repository, a hybrid encryption scheme is implemented by combining the RSA algorithm for key exchange and AES (Advanced Encryption Standard) symmetric encryption for secure message transmission. The RSA algorithm is used to encrypt the AES key, which is then used to encrypt the actual message.

### DSA (Digital Signature Algorithm)

[DSA](https://en.wikipedia.org/wiki/Digital_Signature_Algorithm) is a widely-used digital signature algorithm that allows the recipient of a message to verify its authenticity and integrity. It provides a mechanism for the sender to sign a message using their private key, and the recipient can verify the signature using the corresponding public key. The DSA implementation in this repository supports message signing and verification.

### Diffie-Hellman Key Exchange

[Diffie-Hellman](https://en.wikipedia.org/wiki/Diffie%E2%80%93Hellman_key_exchange) is a key exchange protocol that enables two parties to establish a shared secret over an insecure communication channel. It allows the parties to agree upon a shared secret key without transmitting the key directly. The Diffie-Hellman implementation in this repository allows you to generate public-private key pairs and compute the shared secret key.
