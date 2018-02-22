package gr.compassbook.snorechat.server.callbacks;

import java.util.List;

public interface GetAllUsersCallback {
    void done(List<String> returnedUsers);
}
