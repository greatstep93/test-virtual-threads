package ru.greatstep.testvirutalthreads.controllers;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private static final String RESPONSE_BODY = """
            {
            "test":"test"
            }
            """;
    private final ScheduledExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();

    @GetMapping("/test")
    public String findAll() {
        System.out.println(Thread.currentThread());
        return sleep(() -> RESPONSE_BODY);
    }

    private String sleep(Callable<String> function) {
        String val;
        try {
            val = threadPool.schedule(function, 1, TimeUnit.SECONDS).get();
        } catch (ExecutionException e) {
            val = e.getMessage();
        } catch (InterruptedException e) {
            val = e.getMessage();
            Thread.currentThread().interrupt();
        }
        return val;
    }

}
