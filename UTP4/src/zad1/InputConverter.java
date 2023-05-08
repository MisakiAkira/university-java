package zad1;

import java.util.function.Function;

public class InputConverter<T> {

    private T t;

    public InputConverter(T t){
        this.t = t;
    }

    public <A> A convertBy(Function<T, A> first){
        return first.apply(t);
    }

    public <A, B> B convertBy(Function<T, A> first, Function<A, B> second){
        return second.apply(first.apply(t));
    }

    public <A, B, C> C convertBy(Function<T, A> first, Function<A, B> second, Function<B, C> third){
        return third.apply(second.apply(first.apply(t)));
    }

    public <A, B, C, D> D convertBy(Function<T, A> first, Function<A, B> second, Function<B, C> third, Function<C, D> forth){
        return forth.apply(third.apply(second.apply(first.apply(t))));
    }

}
