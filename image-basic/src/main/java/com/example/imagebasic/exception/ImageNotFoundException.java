package com.example.imagebasic.exception;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(String imageName) {
        super("No image found with name: " + imageName);
    }

    public ImageNotFoundException(String imageName, Throwable cause) {
        super("No image found with name: " + imageName, cause);
    }
}
