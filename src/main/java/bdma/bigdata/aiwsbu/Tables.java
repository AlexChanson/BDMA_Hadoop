package bdma.bigdata.aiwsbu;

import org.apache.hadoop.hbase.TableName;

public class Tables {

    private static final String course = "C";
    private static final String courseFamilies = "# I";
    private static final String grade = "G"; // TODO
    private static final String gradeFamilies = "#"; // TODO
    private static final String instructor = "I"; //TODO
    private static final String instructorFamilies = "#"; // TODO
    private static final String student = "S"; // TODO
    private static final String studentFamilies = "# C"; // TODO

    public static String getCourseFamilies() {
        return courseFamilies;
    }

    public static String getCourseTable() {
        return course;
    }

    public static TableName getCourseTableName() {
        return TableName.valueOf(Namespace.get(), course);
    }

    public static String getGradeFamilies() {
        return gradeFamilies;
    }

    public static String getGradeTable() {
        return grade;
    }

    public static TableName getGradeTableName() {
        return TableName.valueOf(Namespace.get(), grade);
    }

    public static String getInstructorFamilies() {
        return instructorFamilies;
    }

    public static String getInstructorTable() {
        return instructor;
    }

    public static TableName getInstructorTableName() {
        return TableName.valueOf(Namespace.get(), instructor);
    }

    public static String getStudentFamilies() {
        return studentFamilies;
    }

    public static String getStudentTable() {
        return student;
    }

    public static TableName getStudentTableName() {
        return TableName.valueOf(Namespace.get(), student);
    }

    public static TableName getTableName(String table) {
        return TableName.valueOf(Namespace.get(), table);
    }
}
