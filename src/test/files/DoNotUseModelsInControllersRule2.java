import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@RestController
public class MyRestController
{
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private CustomerFacade facade;

    @GetMapping
    @ResponseBody
    public CustomerModel getCustomer()
    {
        return customerDAO.getCustomerByUID("currentuid"); // Noncompliant
    }

    @GetMapping
    @ResponseBody
    public CustomerData getCustomer2()
    {
        return facade.getCurrentCustomer(); // Compliant
    }

    private interface CustomerDAO
    {
        UserModel getCustomerByUID(String uid);
        List<UserModel> getCustomersByGroup(String group);
    }

    private interface CustomerFacade
    {
        CustomerData getCurrentCustomer();
    }
}