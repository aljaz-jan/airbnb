package si.faks.airbnb.healthcheck;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Health
@ApplicationScoped
public class DependenciesAvailableHealthCheck implements HealthCheck {

	@Inject
	@DiscoverService(value = "realEstate")
	private Optional<String> realEstateServiceUrl;

	@Inject
	@DiscoverService(value = "rentRealEstateService")
	private Optional<String> rentRealEstateServiceUrl;

	@Override
	public HealthCheckResponse call() {
		if(isRealEstateServiceAvailable() && isRentRealEstateServiceAvailable()) {
			return HealthCheckResponse.named(DependenciesAvailableHealthCheck.class.getSimpleName()).up().build();
		}
		return HealthCheckResponse.named(DependenciesAvailableHealthCheck.class.getSimpleName()).down().build();
	}

	private boolean isRealEstateServiceAvailable() {
		return isServiceAvailable(realEstateServiceUrl);
	}

	private boolean isRentRealEstateServiceAvailable() {
		return isServiceAvailable(rentRealEstateServiceUrl);
	}

	private boolean isServiceAvailable(final Optional<String> serviceUrl) {
		if(serviceUrl.isPresent()) {
			try {
				HttpURLConnection connection = (HttpURLConnection) new URL(serviceUrl.get() + "/health").openConnection();
				connection.setRequestMethod("GET");

				if (connection.getResponseCode() == 200) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
