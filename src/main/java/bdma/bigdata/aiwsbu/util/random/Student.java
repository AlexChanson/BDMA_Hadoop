package bdma.bigdata.aiwsbu.util.random;

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
        return "";
    }

    // TODO
    public String generatePhoneNumber() {
        return "";
    }
}
