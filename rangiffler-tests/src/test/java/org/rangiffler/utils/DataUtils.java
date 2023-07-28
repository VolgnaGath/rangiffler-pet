package org.rangiffler.utils;

import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;


public class DataUtils {

    private static final Faker faker = new Faker();
    private final String photoPath = "testdata/cat.png";


    private final String testPhoto = fileToBase64StringConversion(photoPath);
    public String getTestPhoto() {
        return testPhoto;
    }

    public static String generateRandomUsername() {
        return faker.name().username();
    }

    public static String generateRandomPassword() {
        return faker.bothify("????####");
    }

    public static String generateRandomName() {
        return faker.name().firstName();
    }

    public static String generateRandomSurname() {
        return faker.name().lastName();
    }

    public static String generateNewDescription() {
        return faker.book().title();
    }

    public static String generateRandomSentence(int wordsCount) {
        return faker.lorem().sentence(wordsCount);
    }
    public static String fileToBase64StringConversion(String pathToPhotoFile) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader().getClass().getClassLoader();
        File inputFile = new File(Objects.requireNonNull(classLoader
                        .getResource(pathToPhotoFile))
                .getFile());
        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(inputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String encodedString = Base64
                .getEncoder()
                .encodeToString(fileContent);
        return encodedString;
    }
}
