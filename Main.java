import java.util.*;

// Перерахування для типу студента
enum StudType {
    EXCELLENT,
    NORMAL
}

// Клас студента
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

    // Реалізація Prototype
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Student ID: " + id + ", Score: " + score + ", Type: " + type;
    }
}

// Інтерфейс для створювача студента
interface StudBuilder {
    void setId(int id);
    void setScore(int score);
    Stud build();
}

// Відмінник
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

// Звичайний студент
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

// Клас Director
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

// Інтерфейс для інтерпретатора
interface Expression {
    boolean interpret(String context);
}

// Клас для інтерпретації
class TermExpression implements Expression {
    private String term;

    public TermExpression(String term) {
        this.term = term;
    }

    public boolean interpret(String context) {
        return context.contains(term);
    }
}

// Інтерфейс для друку студента
interface Printer {
    void print(Stud student);
}

// Інтерфейс для друку списку студентів
interface StudListPrinter {
    void print(List<Stud> students);
}

// Адаптер для друку списку студентів
class PrinterAdapter implements StudListPrinter {
    public void print(List<Stud> students) {
        for (Stud student : students) {
            System.out.println(student);
        }
    }
}

// Інтерфейс для декоратора
interface StudentDecorator {
    void decorate();
}

// Декоратор для додаткової інформації
class StudDecorator implements StudentDecorator {
    private Stud student;

    public StudDecorator(Stud student) {
        this.student = student;
    }

    public void decorate() {
        System.out.println("Student Details: " + student);
    }
}

// Ітератор для списку студентів
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

// Головний клас
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

        // Використання ітератора
        StudIterator iterator = new StudIterator(ST);
        while (iterator.hasNext()) {
            Stud student = iterator.next();
            StudDecorator decorator = new StudDecorator(student);
            decorator.decorate();
        }
    }
}
