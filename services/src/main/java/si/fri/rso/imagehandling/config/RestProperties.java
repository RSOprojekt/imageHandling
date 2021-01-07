package si.fri.rso.imagehandling.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ConfigBundle("rest-properties")
@ApplicationScoped
public class RestProperties {

    @ConfigValue(watch=true)
    private Boolean devMode;
    @ConfigValue(watch=true)
    private Boolean broken;

    public Boolean getDevMode() {
        return this.devMode;
    }

    public void setDevMode(Boolean devMode) {
        this.devMode = devMode;
    }

    public Boolean getBroken() {
        return broken;
    }

    public void setBroken(Boolean broken) {
        this.broken = broken;
    }
}
