package gr.compassbook.snorechat.server.callbacks;

import java.util.List;

public interface GetFriendsCallback {
    void done(List<String> friendsList);
}
