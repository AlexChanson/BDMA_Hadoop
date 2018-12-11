package bdma.bigdata.aiwsbu.util.random;

public class Grade {

    private String rowKey = "";

    public Grade(int year, int semester, String student, String course) {
        rowKey = String.format("%4d/%1d/%s/%s", year, semester, student, course);
    }

    public String getRowKey() {
        return rowKey;
    }

    // TODO
    public double generateGrade() {
        return 0.0;
    }
}
