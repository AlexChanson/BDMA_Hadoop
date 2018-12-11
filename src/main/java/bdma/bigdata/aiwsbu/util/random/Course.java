package bdma.bigdata.aiwsbu.util.random;

public class Course {

    private String rowKey = "";

    public Course(int semester, char type, int serial, int year) {
        rowKey = String.format("S%1d%s%3d/%4d", semester, type, serial, 9999 - year);
    }

    public String getRowKey() {
        return rowKey;
    }

    // TODO
    public String generateName() {
        return "";
    }

    // TODO
    public String[] generateInstructors() {
        return new String[1];
    }
}
