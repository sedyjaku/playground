package cz.sedy.playground.reactor.flux;

import cz.sedy.playground.reactor.mono.MonoWithSwitchIfEmpty;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class FluxWithSwitchIfEmpty {

    public static void main(String[] args) {
        List<String> values = Flux.just("111", "222", "333")
                .flatMap(MonoWithSwitchIfEmpty::processVal)
                .switchIfEmpty(emptyStrategy())
                .defaultIfEmpty(emptyDefault())
                .collectList()
                .block();

        System.out.println(values);
    }

    public static Mono<String> processVal(String val) {
        System.out.println("---inside processVal---" + val);
        return Mono.just(val).delayUntil(str -> {
                    System.out.println("Delayining " + str);
                    return Mono.just("DELAYED_PROCESS" + str);
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
