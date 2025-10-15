// Main.java
public class Main {
    public static void main(String[] args) {
        Student[] students = {
            new Student("Ana", 78.5),
            new Student("Beto", 92.0),
            new Student("Carla", 85.0),
            new Student("Diego", 60.3),
            new Student("Elena", 92.0),
            new Student("Fabi", 74.2)
        };
        System.out.println("Antes:");
        for (Student s : students) System.out.println(s);

        Sorter.insertionSort(students);

        System.out.println("\nDespués:");
        for (Student s : students) System.out.println(s);
    }
}
