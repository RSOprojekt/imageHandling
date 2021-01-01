package si.fri.rso.imagehandling.api.v1.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import si.fri.rso.imagehandling.config.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Liveness
@ApplicationScoped
public class CustomHealthCheck implements HealthCheck {
    @Inject
    private RestProperties restProperties;


    @Override
    public HealthCheckResponse call() {
        if (restProperties.getBroken()){
            return HealthCheckResponse.down(CustomHealthCheck.class.getSimpleName());
        } else return HealthCheckResponse.up(CustomHealthCheck.class.getSimpleName());
    }
}
