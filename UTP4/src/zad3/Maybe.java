package zad3;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Maybe<T> {

    T t;
    boolean isEmpty;

    private Maybe(T t, boolean isEmpty){
        this.t = t;
        this.isEmpty = isEmpty;
    }

    static <T> Maybe<T> of(T t){
        if (t == null)
            return new Maybe<T>(t, true);
        else
            return new Maybe<T>(t, false);
    }

    void ifPresent(Consumer<T> c){
        if (!isEmpty)
            c.accept(t);
    }

    <R> Maybe<R> map (Function<T, R> f){
        if (!isEmpty)
            return Maybe.of(f.apply(t));
        else
            return new Maybe<R>(null, true);
    }

    T get () throws NoSuchElementException{
        if (!isEmpty)
            return t;
        else
            throw new NoSuchElementException("maybe is empty");
    }

    boolean isPresent(){
        return !isEmpty;
    }

    T orElse(T defVal){
        return  isEmpty? defVal : t;
    }

    Maybe<T> filter(Predicate<T> p){
        if (!isEmpty) {
            if (p.test(t))
                return this;
        }
        return new Maybe<T>(null, true);
    }

    @Override
    public String toString() {
        if (isEmpty)
            return "Maybe is empty";
        else
            return "Maybe has value " + t;
    }
}
