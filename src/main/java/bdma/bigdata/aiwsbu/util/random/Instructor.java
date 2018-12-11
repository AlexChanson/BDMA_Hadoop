package bdma.bigdata.aiwsbu.util.random;

public class Instructor {

    private String rowKey = "";

    public Instructor(int year) {
        rowKey = String.format("%s/%4d", generateName(), year);
    }

    public String getRowKey() {
        return rowKey;
    }

    // TODO
    public String generateName() {
        return "";
    }

    // TODO
    public String[] generateCourses() {
        return new String[1];
    }
}
