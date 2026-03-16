package md.utm.libraryapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class BookTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Book getBookSample1() {
        return new Book().id(1L).isbn("isbn1").name("name1").publishYear("publishYear1").copies(1).picture("picture1");
    }

    public static Book getBookSample2() {
        return new Book().id(2L).isbn("isbn2").name("name2").publishYear("publishYear2").copies(2).picture("picture2");
    }

    public static Book getBookRandomSampleGenerator() {
        return new Book()
            .id(longCount.incrementAndGet())
            .isbn(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .publishYear(UUID.randomUUID().toString())
            .copies(intCount.incrementAndGet())
            .picture(UUID.randomUUID().toString());
    }
}
