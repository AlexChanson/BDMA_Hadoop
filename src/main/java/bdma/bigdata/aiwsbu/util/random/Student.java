package bdma.bigdata.aiwsbu.util.random;

import java.util.Random;

public class Student {

    private String rowKey = "";

    public Student(int serial, int year) {
        rowKey = String.format("%4d%6d", year, serial);
    }

    public String getRowKey() {
        return rowKey;
    }

    // TODO
    public String generateClass() {
        return "";
    }

    // TODO
    public String generateFirstName() {
        return "";
    }

    // TODO
    public String generateLastName() {
        return "";
    }

    // TODO
    public String generateBirthDate() {
        return "";
    }

    // TODO
    public String generateDomicileAddress() {
        return "";
    }

    // TODO
    public String generateEmailAddress() {
        return generateFirstName()+generateLastName()+"@etu.univ-tours.fr";
    }

    // TODO
    public String generatePhoneNumber() {
        return "02"+String.valueOf(0 + (int)(Math.random() * ((9 - 0) + 1)))
        +String.valueOf(0 + (int)(Math.random() * ((9 - 0) + 1)))+String.valueOf(0 + (int)(Math.random() * ((9 - 0) + 1)))
        +String.valueOf(0 + (int)(Math.random() * ((9 - 0) + 1)))+String.valueOf(0 + (int)(Math.random() * ((9 - 0) + 1)))
        +String.valueOf(0 + (int)(Math.random() * ((9 - 0) + 1)))+String.valueOf(0 + (int)(Math.random() * ((9 - 0) + 1)))
        +String.valueOf(0 + (int)(Math.random() * ((9 - 0) + 1)));
    }
}
