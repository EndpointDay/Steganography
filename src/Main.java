import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Main <hide|extract> <image-path> [<message>]");
            return;
        }

        String action = args[0];
        String imagePath = args[1];

        if (action.equals("hide")) {
            if (args.length < 3) {
                System.out.println("Usage for hiding: java Main hide <image-path> <message>");
                return;
            }
            String message = args[2];
            hideMessage(imagePath, message);
        } else if (action.equals("extract")) {
            extractMessage(imagePath);
        } else {
            System.out.println("Invalid action. Use 'hide' or 'extract'.");
        }
    }

    private static void hideMessage(String imagePath, String message) {
        try {
            File file = new File(imagePath);
            BufferedImage image = ImageIO.read(file);

            String binaryMessage = toBinary(message) + "0000000000000000";

            int messageIndex = 0;
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    if (messageIndex >= binaryMessage.length()) {
                        ImageIO.write(image, "png", new File("output_" + file.getName()));
                        System.out.println("Message hidden and image saved as output_" + file.getName());
                        return;
                    }
                    int rgb = image.getRGB(x, y);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = rgb & 0xFF;

                    b = (b & 0xFE) | (binaryMessage.charAt(messageIndex) - '0');
                    messageIndex++;

                    rgb = (r << 16) | (g << 8) | b;
                    image.setRGB(x, y, rgb);
                }
            }
            System.out.println("Message hidden successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void extractMessage(String imagePath) {
        try {
            File file = new File(imagePath);
            BufferedImage image = ImageIO.read(file);

            StringBuilder binaryMessage = new StringBuilder();
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    int b = rgb & 0xFF;

                    binaryMessage.append(b & 1);
                }
            }

            String binaryString = binaryMessage.toString();
            String message = fromBinary(binaryString);

            int endIndex = message.indexOf("\u0000\u0000");
            if (endIndex != -1) {
                message = message.substring(0, endIndex);
            }

            System.out.println("Extracted Message: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String toBinary(String message) {
        StringBuilder binary = new StringBuilder();
        for (char c : message.toCharArray()) {
            String bin = Integer.toBinaryString(c);
            while (bin.length() < 8) {
                bin = "0" + bin;
            }
            binary.append(bin);
        }
        return binary.toString();
    }

    private static String fromBinary(String binary) {
        StringBuilder message = new StringBuilder();
        int length = binary.length();

        for (int i = 0; i < length; i += 8) {
            if (i + 8 <= length) {
                String byteString = binary.substring(i, i + 8);
                char c = (char) Integer.parseInt(byteString, 2);
                message.append(c);
            }
        }
        return message.toString();
    }
}
