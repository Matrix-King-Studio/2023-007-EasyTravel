package tour.base.utils;

import org.springframework.stereotype.Component;


@Component
public class UserThreadLocal {

    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public void set(Long userId) {
        threadLocal.set(userId);
    }

    public Long get() {
        return threadLocal.get();
    }

    public void remove() {
        threadLocal.remove();
    }

}
