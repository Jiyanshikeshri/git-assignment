class Student {
    private int marks;   // private variable

    // setter
    public void setMarks(int m) {
        marks = m;
    }

    // getter
    public int getMarks() {
        return marks;
    }
}

public class Encapsulation{
    public static void main(String[] args) {
        Student s = new Student();

        s.setMarks(100);   // setting value
        System.out.println(s.getMarks()); // getting value
    }
}