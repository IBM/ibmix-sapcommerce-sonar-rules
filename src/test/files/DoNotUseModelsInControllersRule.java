import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class MyController
{
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private CustomerFacade facade;

    @GetMapping
    public String profilePage(final Model model) throws CMSItemNotFoundException
    {
        model.addAttribute("user", customerDAO.getCustomerByUID("currentuid")); // Noncompliant
        return getViewForPage(model);
    }

    @GetMapping
    public String profilePage2(final Model model) throws CMSItemNotFoundException
    {
        model.addAttribute("users", customerDAO.getCustomersByGroup("group")); // Noncompliant
        return getViewForPage(model);
    }

    @GetMapping
    public String profilePage3(final Model model) throws CMSItemNotFoundException
    {
        model.addAttribute("user", facade.getCurrentCustomer()); // Compliant
        return getViewForPage(model);
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