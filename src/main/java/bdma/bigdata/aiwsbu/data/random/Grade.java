package bdma.bigdata.aiwsbu.data.random;

public class Grade {

    private String rowKey = "";

    public Grade(int year, int semester, String student, String course) {
        rowKey = String.format("%4d/%2d/%s/%s", year, semester, student, course);
    }

    public String getRowKey() {
        return rowKey;
    }

    // TODO
    public double generateGrade() {
        return 0.0;
    }
}
