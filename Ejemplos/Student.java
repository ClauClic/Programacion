// Student.java
public class Student {
    private String name;
    private double score;

    public Student(String name, double score) {
        this.name = name;
        setScore(score);
    }

    public String getName() { return name; }
    public double getScore() { return score; }

    public void setScore(double score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be 0-100");
        }
        this.score = score;
    }

    @Override
    public String toString() {
        return name + " (" + score + ")";
    }
}
