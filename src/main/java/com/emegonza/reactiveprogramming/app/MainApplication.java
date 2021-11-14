package com.emegonza.reactiveprogramming.app;

import com.emegonza.reactiveprogramming.app.model.Comments;
import com.emegonza.reactiveprogramming.app.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainApplication {

    private static final Logger logger = Logger.getLogger(MainApplication.class.toString());

    public static void main(String[] args) {
        //workWithMap();
        //workWithFlatMap();
        //workWithZip();
        workBackpressure();
    }

    private static void workBackpressure() {
        Flux.range(1, 10)
                .log()
                .limitRate(5)
                .subscribe();
    }

    private static void workWithZip() {
        buildUsers()
                .zipWith(buildComments("anónimo"))
                .map(tuple -> {
                    var user = tuple.getT1();
                    var comments = tuple.getT2();
                    return getCommentWithUser(user, comments);
                })
                .subscribe(logger::info);
    }

    private static void workWithFlatMap() {
        buildUsers().flatMap(user -> buildComments(user.getName())
                .map(comments -> getCommentWithUser(user, comments))
        ).subscribe(logger::info);
    }

    private static void workWithMap() {
        Flux.fromIterable(users())
                .doOnNext(System.out::println)
                .map(String::toUpperCase)
                .map(name -> name.split(" "))
                .map(splitName -> User.builder().name(splitName[0]).lastName(splitName[1]).build())
                .filter(user -> user.getCompleteName().contains("G"))
                .subscribe(user -> logger.info(user.getCompleteName()),
                        throwable -> logger.severe(throwable.getMessage()),
                        () -> logger.info("La ejecución a finalizado!!!"));
    }

    private static String getCommentWithUser(User user, Comments comments) {
        return user.getCompleteName().concat(":\n").concat(comments.getComments().toString());
    }

    private static Flux<User> buildUsers() {
        return Flux.fromIterable(users())
                .map(name -> name.split(" "))
                .map(splitName -> User.builder().name(splitName[0]).lastName(splitName[1]).build());
    }

    private static Mono<Comments> buildComments(String name) {
        return Mono.just(comments(name));
    }

    private static List<String> users() {
        var users = new ArrayList<String>();
        users.add("Emerson Gonzalez");
        users.add("Ana Lozano");
        users.add("Pablo Martinez");
        users.add("Luis Gonzalez");
        users.add("Pedro Mengano");
        return users;
    }

    private static Comments comments(String name) {
        var comments = new Comments();
        comments.addComment("primer comentario de ".concat(name));
        comments.addComment("segundo comentario de ".concat(name));
        comments.addComment("tercer comentario de ".concat(name));
        return comments;
    }
}
