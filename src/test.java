import java.io.*;

public class test {
    public static void main(String[] args) throws IOException {
//        LocalDate now = LocalDate.now();
//        LocalDate parse = LocalDate.parse("2524-12-12");
//        System.out.println(parse);
//        System.out.println(LocalDate.parse("2024-12-50"));
//        System.out.println("now.getDayOfMonth() = " + now.getDayOfMonth());
//        System.out.println("now.getMonthValue() = " + now.getMonthValue());
//        System.out.println("now.getYear() = " + now.getYear());
//        LocalDate date2 = LocalDate.parse("09-02-2024", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//        System.out.println(date2.isAfter(now));
//        System.out.println("\"\".isEmpty() = " + "".isEmpty());
//        System.out.println("\"\".isBlank() = " + "".isBlank());
//        System.out.println("\" \".isEmpty() = " + " ".isEmpty());
//        System.out.println("\" \".isBlank() = " + " ".isBlank());
//        newidiot();

        BookTest bookTest = new BookTest("Java in action1", " Jochua Bloch");
//        System.out.println(bookTest);
        BookTest bookTest1 = new BookTest("Java in action2", " Jochua Bloch");

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("book.ser"));
        out.writeObject(bookTest);
        out.writeObject(bookTest1);

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("book.ser"));

        BookTest book = null;
        while (true) {
            try {
                book = (BookTest) in.readObject();
            } catch (ClassNotFoundException | EOFException e) {
                System.out.println(e.getMessage());
                break;
            }
            System.out.println(book);
        }

    }

    private static void newidiot() {

    }
}
