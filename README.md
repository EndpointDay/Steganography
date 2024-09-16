# Simple Steganography

A simple Java-based steganography tool for hiding and extracting messages within image files. This tool demonstrates basic steganographic techniques by modifying the least significant bits (LSBs) of the image's pixel data.

## Features

- **Hide Messages**: Embed a text message within an image file.
- **Extract Messages**: Retrieve the hidden message from an image file.

## Prerequisites

- Java Development Kit (JDK) 8 or later
- An image file in BMP or PNG format

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/endpoint1337/Simple-Steganography.git
    ```

2. Navigate to the project directory:

    ```bash
    cd Simple-Steganography/src
    ```

## Usage

### Hiding a Message

To hide a message in an image, run:

```bash
java Main hide <image-path> <message>
```

- `<image-path>`: Path to the image file where the message will be hidden.
- `<message>`: The message to be hidden in the image.

**Example:**

```bash
java Main hide example.png "This is a secret message"
```

This command will hide the message in `example.png` and save the modified image as `output_example.png`.

### Extracting a Message

To extract a hidden message from an image, run:

```bash
java Main extract <image-path>
```

- `<image-path>`: Path to the image file from which the message will be extracted.

**Example:**

```bash
java Main extract output_example.png
```

This command will extract the hidden message from `output_example.png` and print it to the console.

## Code Overview

- **`Main.java`**: The main class that contains methods for hiding and extracting messages. It reads and modifies image files to embed or retrieve text data.

## Limitations

- Currently supports only BMP and PNG image formats.
- Limited error handling and validation.

## Acknowledgments

- This project is for educational purposes and demonstrates basic steganographic techniques.
- Inspired by various steganography resources and tutorials.
