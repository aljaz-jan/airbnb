package si.faks.airbnb;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.kumuluz.ee.discovery.annotations.RegisterService;

import javax.ws.rs.ApplicationPath;

@RegisterService
@ApplicationPath("/")
public class Application extends javax.ws.rs.core.Application {

}
