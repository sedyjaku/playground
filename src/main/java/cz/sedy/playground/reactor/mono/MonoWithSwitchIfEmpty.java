package cz.sedy.playground.reactor.mono;

import reactor.core.publisher.Mono;

public class MonoWithSwitchIfEmpty {

    public static void main(String[] args) {
        String val = Mono.just("111")
                .flatMap(MonoWithSwitchIfEmpty::processVal)
                .repeat(5)
                .switchIfEmpty(emptyStrategy())
                .defaultIfEmpty(emptyDefault())
                .blockLast();

        System.out.println(val);
    }

    public static Mono<String> processVal(String val) {
        System.out.println("---inside processVal---");
        return Mono.just(val).delayUntil(str -> {
                    System.out.println("Delayining " + str);
                    return Mono.just("DELAYED_PROCESS");
                }
        );
    }

    public static Mono<String> emptyStrategy() {
        System.out.println("---inside emptyStrategy---");
        return Mono.just("EMPTY").delayUntil(str -> {
                    System.out.println("Delaying empty " + str);
                    return Mono.just("DELAYED_EMPTY");
                }
        );

    }


    public static String emptyDefault() {
        System.out.println("---inside emptyDefault---");
        return "EMPTYDEFAULT";

    }

}
