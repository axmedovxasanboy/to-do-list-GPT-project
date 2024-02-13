import java.io.Serializable;

public class BookTest implements Serializable {
    String author;
    String title;

    public BookTest(String author, String title) {
        this.author = author;
        this.title = title;
    }

    @Override
    public String toString() {
        return "BookTest{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
