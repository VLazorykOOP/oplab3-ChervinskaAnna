import java.util.*;

// Enumeration for student type
enum StudType {
    EXCELLENT,
    NORMAL
}

// Student class
class Stud implements Cloneable {
    private int id;
    private int score;
    private StudType type;

    public Stud(int id, int score, StudType type) {
        this.id = id;
        this.score = score;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public StudType getType() {
        return type;
    }

    // Implementing Prototype
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Student ID: " + id + ", Score: " + score + ", Type: " + type;
    }
}

// Interface for student builder
interface StudBuilder {
    void setId(int id);

    void setScore(int score);

    Stud build();
}

// Excellent student builder
class AStudBuilder implements StudBuilder {
    private int id;
    private int score;

    public void setId(int id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Stud build() {
        return new Stud(id, score, StudType.EXCELLENT);
    }
}

// Normal student builder
class NormalStudBuilder implements StudBuilder {
    private int id;
    private int score;

    public void setId(int id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Stud build() {
        return new Stud(id, score, StudType.NORMAL);
    }
}

// Director class
class Director {
    private StudBuilder builder;

    public void setBuilder(StudBuilder builder) {
        this.builder = builder;
    }

    public Stud buildStud(int id, int score) {
        builder.setId(id);
        builder.setScore(score);
        return builder.build();
    }
}

// Interface for expression
interface Expression {
    boolean interpret(String context);
}

// Class for interpreting
class TermExpression implements Expression {
    private String term;

    public TermExpression(String term) {
        this.term = term;
    }

    public boolean interpret(String context) {
        return context.contains(term);
    }
}

// Interface for student printer
interface Printer {
    void print(Stud student);
}

// Interface for list printer
interface StudListPrinter {
    void print(List<Stud> students);
}

// Adapter for printing list of students
class PrinterAdapter implements StudListPrinter {
    public void print(List<Stud> students) {
        for (Stud student : students) {
            System.out.println(student);
        }
    }
}

// Interface for decorator
interface StudentDecorator {
    void decorate();
}

// Decorator for additional information
class StudDecorator implements StudentDecorator {
    private Stud student;

    public StudDecorator(Stud student) {
        this.student = student;
    }

    public void decorate() {
        System.out.println("Student Details: " + student);
    }
}

// Iterator for student list
class StudIterator implements Iterator<Stud> {
    private List<Stud> students;
    private int position;

    public StudIterator(List<Stud> students) {
        this.students = students;
    }

    public boolean hasNext() {
        return position < students.size();
    }

    public Stud next() {
        return students.get(position++);
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        List<Stud> ST = new ArrayList<>();

        Director director = new Director();
        StudBuilder a = new AStudBuilder();
        StudBuilder n = new NormalStudBuilder();
        Expression isA = new TermExpression("5");

        for (int i = 0; i < 100; ++i) {
            boolean A = isA.interpret(Integer.toString(i));
            Stud stud;
            if (A) {
                director.setBuilder(a);
                stud = director.buildStud(i, 1500);
            } else {
                director.setBuilder(n);
                stud = director.buildStud(i, 1000);
            }
            ST.add(stud);
        }

        PrinterAdapter printerAdapter = new PrinterAdapter();
        printerAdapter.print(ST);

        // Using iterator
        StudIterator iterator = new StudIterator(ST);
        while (iterator.hasNext()) {
            Stud student = iterator.next();
            StudDecorator decorator = new StudDecorator(student);
            decorator.decorate();
        }
    }
}
