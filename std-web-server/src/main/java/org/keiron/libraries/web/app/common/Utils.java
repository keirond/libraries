package org.keiron.libraries.web.app.common;

import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;

public class Utils {

  public static <T> Comparator<? super T> fromSortToComparator(Sort sort, Class<T> clazz) {

    Comparator<? super T> comparator = (T x, T y) -> {
      List<Sort.Order> orders = sort.get().toList();
      for (Sort.Order order : orders) {
        String property = order.getProperty();
        Sort.Direction direction = order.getDirection();
        try {
          Field field = clazz.getField(property);
          field.setAccessible(true);
          Object u = field.get(x);
          Object v = field.get(y);
          int curr = compare(u, v);
          if (curr == 0)
            continue;
          if (direction.isDescending())
            curr = -curr;
          return curr;
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException |
                 UnsupportedOperationException ignored) {}
      }
      return 0;
    };

    return comparator.reversed();
  }

  public static int compare(Object a, Object b)
      throws IllegalArgumentException, UnsupportedOperationException {
    if (a == null || b == null) {
      throw new IllegalArgumentException("Cannot compare null values");
    }

    if (a instanceof Comparable) {
      @SuppressWarnings("unchecked")
      var compA = (Comparable<Object>) a;
      return compA.compareTo(b);
    }

    throw new UnsupportedOperationException("Objects do not support '<' comparison");
  }

}
