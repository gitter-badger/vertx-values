package actors.mongo;
import com.mongodb.client.ClientSession;
import io.vertx.core.DeploymentOptions;
import static java.util.Objects.requireNonNull;

public class FindInputsBuilder {

  private final static DeploymentOptions DEFAULT_DEPLOYMENT_OPTIONS = new DeploymentOptions();

  private DeploymentOptions deploymentOptions = DEFAULT_DEPLOYMENT_OPTIONS;
  private ClientSession clientSession;

  public FindInputsBuilder deploymentOptions(final DeploymentOptions deploymentOptions) {
    this.deploymentOptions = requireNonNull(deploymentOptions);
    return this;
  }

  public FindInputsBuilder session(final ClientSession clientSession) {
    this.clientSession = requireNonNull(clientSession);
    return this;
  }

  public FindInputs create() {
    return new FindInputs(deploymentOptions,
                          clientSession
    );
  }
}
