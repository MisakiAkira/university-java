package src.zad1;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListCreator<T> {

    private final List<T> list = new ArrayList<T>();

    public static <T> ListCreator<T> collectFrom(List<T> srcList) {
        ListCreator<T> lc = new ListCreator<T>();
        lc.list.addAll(srcList);
        return lc;
    }

    public ListCreator<T> when(Predicate<T> p) {
        ListCreator<T> lc = new ListCreator<T>();
        for (T t : list)
            if (p.test(t))
                lc.list.add(t);
        return lc;
    }

    public <R> List<R> mapEvery(Function<T, R> f) {
        List<R> result = new ArrayList<R>();
        for (T t : list)
            result.add(f.apply(t));
        return result;
    }

}
