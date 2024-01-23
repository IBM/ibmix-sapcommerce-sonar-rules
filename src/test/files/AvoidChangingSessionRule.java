import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.session.SessionExecutionBody;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService
{
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;

    public void doSomething()
    {
        sessionService.executeInLocalView(new SessionExecutionBody() // Noncompliant
        {
            @Override
            public Object execute()
            {
                //Unsecure code
            }
        }, userService.getAdminUser());
    }

    public void doSomethingElse()
    {
        sessionService.executeInLocalView(new SessionExecutionBody() // Compliant
        {
            @Override
            public Object execute()
            {
                //Secure code
            }
        });
    }
}