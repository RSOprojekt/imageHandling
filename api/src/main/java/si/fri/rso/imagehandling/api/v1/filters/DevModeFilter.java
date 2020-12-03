package si.fri.rso.imagehandling.api.v1.filters;

import si.fri.rso.imagehandling.config.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class DevModeFilter implements ContainerRequestFilter {

    @Inject
    private RestProperties restProperties;

    @Override
    public void filter(ContainerRequestContext containerRequestContext)  {
        if (restProperties.getDevMode()){
            containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity("{\"message\" : \"The site is undergoing maintenance\"}").build());
        }
    }
}
