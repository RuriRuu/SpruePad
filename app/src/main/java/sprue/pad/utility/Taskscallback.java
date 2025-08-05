package sprue.pad.utility;

import java.util.Map;

public interface Taskscallback {

    void onSuccess(Map<String, String> tasks);

    void onFailure(Exception e);
}
