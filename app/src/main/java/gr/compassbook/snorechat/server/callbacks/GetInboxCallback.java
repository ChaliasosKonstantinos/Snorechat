package gr.compassbook.snorechat.server.callbacks;

import java.util.List;

public interface GetInboxCallback {
    void done(List<String> returnedList);
}
