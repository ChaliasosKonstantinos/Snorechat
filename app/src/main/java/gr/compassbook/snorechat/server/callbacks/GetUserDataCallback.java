package gr.compassbook.snorechat.server.callbacks;

import gr.compassbook.snorechat.user.User;

public interface GetUserDataCallback {
    void done(User returnedUser);
}
