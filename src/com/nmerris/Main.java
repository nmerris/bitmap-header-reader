package com.nmerris;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

// author: Nathan Merris
// this program reads in a bitmap file that you specify via console input
// the width and height of the file in pixels is reported back to the console
// if you enter an invalid file path, an error message is shown
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // get the file path from the user
        // example file path: /home/nmerris/IdeaProjects/bitmap header reader/src/com/nmerris/simple.bmp
        System.out.println("Enter a FULL file path: ");
        Path filePath = Paths.get(scanner.nextLine());

        try {
            // convert the whole file into an array of bytes
            byte[] data = Files.readAllBytes(filePath);

            // wrap it in a little endian byte buffer to make it easier to get at specific byte chunks
            // NOTE: numbers are stored in little-endian format in bitmap headers, so least significant digit is first
            ByteBuffer bb = ByteBuffer.wrap(data);
            bb.order(ByteOrder.LITTLE_ENDIAN);

            // get the width and height: width is 4 bytes, always starts at 18, height always starts at 22
            int width = bb.getInt(18);
            int height = bb.getInt(22);

            // display the results
            System.out.println("width: " + width + ", height: " + height);

        } catch (IOException e) {
            System.out.println("That file path did not work, please try again.");
        }

    } // main
}
