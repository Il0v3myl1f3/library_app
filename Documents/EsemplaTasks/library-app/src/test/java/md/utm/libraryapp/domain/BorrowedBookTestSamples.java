package md.utm.libraryapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BorrowedBookTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BorrowedBook getBorrowedBookSample1() {
        return new BorrowedBook().id(1L).bookIsbn("bookIsbn1");
    }

    public static BorrowedBook getBorrowedBookSample2() {
        return new BorrowedBook().id(2L).bookIsbn("bookIsbn2");
    }

    public static BorrowedBook getBorrowedBookRandomSampleGenerator() {
        return new BorrowedBook().id(longCount.incrementAndGet()).bookIsbn(UUID.randomUUID().toString());
    }
}
