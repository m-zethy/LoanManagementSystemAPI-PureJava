
package loanmanagementsystemapi;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EncodingExperiment {

    public static void main(String[] args) {

        String message = "Hello 😊";

        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);

        System.out.println("Original text: " + message);

        System.out.println("Bytes: " + Arrays.toString(bytes));

        System.out.println("Byte count: " + bytes.length);

        System.out.println(
            "Decoded text: " +
            new String(bytes, StandardCharsets.UTF_8)
        );
        
        for (byte b : bytes) {
        
            System.out.printf("%02X", Byte.toUnsignedInt(b));
        }
    }
}
