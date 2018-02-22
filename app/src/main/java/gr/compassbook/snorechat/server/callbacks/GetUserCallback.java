package gr.compassbook.snorechat.server.callbacks;

import gr.compassbook.snorechat.user.User;

public interface GetUserCallback {

    void done(User returnedUser);


}
