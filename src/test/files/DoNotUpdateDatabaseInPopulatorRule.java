import de.hybris.platform.converters.Populator;

import java.util.Arrays;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.commercefacades.user.data.CustomerData;

@Component
public class CustomerPopulator implements Populator<UserModel, CustomerData>
{
    @Autowired
    private ModelService modelService;

    @Override
    public void populate(final UserModel source, final CustomerData target)
    {

        modelService.save(source); // Noncompliant
        modelService.saveAll(Arrays.asList(source)); // Noncompliant
        modelService.remove(source); // Noncompliant
        modelService.removeAll(Arrays.asList(source)); // Noncompliant
    }
}