package com.parfait.reactorstudy.flux;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class FluxTest {

    @Test
    public void logging() {
//        Flux.just("Hello", "World", "I am", "a Programmer!").subscribe(log::info);
        Flux.just("Hello", "World", "I am", "a Programmer!").subscribe((word) -> log.info(word));
    }

    @Test
    public void parallel() {
        Flux.range(1, 10)
            .parallel(2)
            .runOn(Schedulers.parallel())
            .map(String::valueOf)
            .subscribe(log::info);
    }

    @Test
    public void delay() throws Exception {
        Flux.just("Hello", "World", "I am", "a Programmer!")
            .delayElements(Duration.ofSeconds(1L))
            .subscribe(log::info);

        Thread.sleep(5000L);
    }

    @Test
    public void subscribe() throws Exception {
        Flux.just("1")
            .map(numberString -> {
                Integer number = Integer.valueOf(numberString);
                log.info("parsed: {}", number);
                return number;
            }).subscribe(number -> log.info("result: {}", number));
    }

    @Test
    public void notSubscribe() throws Exception {
        Flux.just("1")
            .map(numberString -> {
                Integer number = Integer.valueOf(numberString);
                log.info("parsed: {}", number);
                return number;
            });
    }

    @Test
    public void map() throws Exception {
        Flux.just(-1, -2, -3, -4)
            .map(Math::abs)
            .map(String::valueOf)
            .subscribe(log::info);
    }

    @Test
    public void filter() throws Exception {
        Flux.just("Hello", "World", "I am", "a Programmer!")
            .filter(word -> word.length() > 4)
            .subscribe(log::info);
    }

    @Test
    public void buffer() throws Exception {
        Flux.range(0, 20)
            .map(i -> i + 1)
            .filter(i -> i % 2 == 0)
            .buffer(3)
            .subscribe(list -> log.info("list: {}", list));
    }

    @Test
    public void zip() {
        Flux<String> alphabets = Flux.just("a", "b", "c");
        Flux<Integer> numbers = Flux.just(1, 2, 3);

        Flux.zip(alphabets, numbers, (a, n) -> a + n).subscribe(log::info);
    }

    @Test
    public void errorHandling() throws InterruptedException {
        Flux<String> name = getName();
        name.timeout(Duration.ofMillis(10L))
            .doOnError(e -> log.error(e.getMessage(), e))
            .onErrorReturn("error occurred")
            .subscribe(log::info);

        Thread.sleep(1000L);
    }

    private Flux<String> getName() {
        return Flux.just("a", "b", "c")
                   .delayElements(Duration.ofMillis(100L));
    }
}
