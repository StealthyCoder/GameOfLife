package ui;

import java.io.IOException;

public interface Renderer<T> {

    void clear() throws IOException;

    void render(T t) throws IOException;
}
