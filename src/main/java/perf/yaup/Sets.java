package perf.yaup;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 */
public class Sets {

    public static <T> Set<T> getOverlap(Set<T> a, Set<T> b){
        Set<T> rtrn = new HashSet<T>(a);
        rtrn.retainAll(b);
        return rtrn;
    }
    public static <T> Set<T> of(T...t){
        LinkedHashSet<T> rtrn = new LinkedHashSet<>();
        if(t!=null && t.length > 0){
            rtrn.addAll(Arrays.asList(t));
        }
        return rtrn;
    }
    public static <T> Set<T> unique(Set<T> a, Set<T> b){
        if(b.containsAll(a)){
            return Collections.emptySet();
        }
        Set<T> rtrn = new HashSet<>();
        rtrn.addAll(a);
        rtrn.removeAll(b);
        return rtrn;
    }

}