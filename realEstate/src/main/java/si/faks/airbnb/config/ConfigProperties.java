package si.faks.airbnb.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("real-estate-config")
public class ConfigProperties {

    @ConfigValue(value = "dynamic", watch = true)
    private String dynamic;

    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }
    // get and set methods

}