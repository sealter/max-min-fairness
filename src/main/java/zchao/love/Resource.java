package zchao.love;

import java.util.Map;


public interface Resource<T> {

    T getResource(String name);

    T getMaxResource();

    T getMinResource();

    Map<String, T> getResources();


}
