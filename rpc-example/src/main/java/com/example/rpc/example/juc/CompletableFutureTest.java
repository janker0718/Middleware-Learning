package com.example.rpc.example.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureTest {
    public static void main(String[] args) {
//        normalAsync();
//        thenRun();
//        thenAcceptAsync();
//        thenApplyAsync();
//        thenComposeAsync();
//        runAfterBothAsync();
//        thenAcceptBothAsync();
//        thenCombineAsync();
    }


    private static void thenCombineAsync() {
        CompletableFuture<String> first = CompletableFuture.completedFuture("First");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> "hello janker", executorService)
                .thenCombineAsync(first, (s, w) -> {
                    System.out.println(w +" "+ s);
                    return "End";
                }, executorService);
        System.out.println(stringCompletableFuture.join());
        executorService.shutdown();
    }

    private static void thenAcceptBothAsync() {
        CompletableFuture<String> first = CompletableFuture.completedFuture("First");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture.supplyAsync(() -> "hello janker", executorService)
                .thenAcceptBothAsync(first, (s,w) -> System.out.println(w + s), executorService);
        executorService.shutdown();
    }

    private static void runAfterBothAsync() {
        CompletableFuture<String> first = CompletableFuture.completedFuture("First");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture.supplyAsync(() -> "hello janker", executorService)
                .runAfterBothAsync(first, () -> System.out.println("End"), executorService);
        executorService.shutdown();
    }

    private static void thenComposeAsync() {
        CompletableFuture<String> f = CompletableFuture.completedFuture("Start");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<String> thenComposeAsync = CompletableFuture.supplyAsync(() -> "hello janker", executorService)
                .thenComposeAsync(result -> {
                    System.out.println("hello janker");
                    return f;
                }, executorService);
        System.out.println(thenComposeAsync.join());
        executorService.shutdown();
    }

    private static void thenApplyAsync() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<String> thenApplyAsync = CompletableFuture.supplyAsync(() -> "hello janker", executorService)
                .thenApplyAsync(result -> {
                    System.out.println("hello janker");
                    return "ApplyAsync End";
                }, executorService);
        System.out.println(thenApplyAsync.join());
        executorService.shutdown();
    }

    private static void thenAcceptAsync() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture.supplyAsync(() -> "hello janker", executorService)
                .thenAcceptAsync(System.out::println, executorService);
        executorService.shutdown();
    }

    private static void thenRun() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture.supplyAsync(() -> "hello janker", executorService)
                .thenRunAsync(() -> System.out.println("End"), executorService);
        executorService.shutdown();
    }

    private static void normalAsync() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(() -> System.out.println("hello janker"), executorService);

        CompletableFuture<String> supplyAsyncFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("hello supplyAsync");
            return "supplyAsync";
        }, executorService);
        System.out.println(runAsyncFuture.join());
        String name = supplyAsyncFuture.join();
        System.out.println(name);
        executorService.shutdown();
    }
}
