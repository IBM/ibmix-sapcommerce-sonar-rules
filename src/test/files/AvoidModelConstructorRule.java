import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.model.ModelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService
{
    @Autowired
    private ModelService modelService;

    public void doSomething()
    {
        new UserModel(); // Noncompliant
    }

    public void doSomethingElse()
    {
        modelService.create(UserModel.class); // Compliant
    }
}