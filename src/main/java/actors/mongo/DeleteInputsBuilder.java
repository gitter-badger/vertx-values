package actors.mongo;

import com.mongodb.client.ClientSession;
import com.mongodb.client.model.DeleteOptions;
import io.vertx.core.DeploymentOptions;

import static java.util.Objects.requireNonNull;

public class DeleteInputsBuilder {
  private final static DeploymentOptions DEFAULT_DEPLOYMENT_OPTIONS = new DeploymentOptions();
  private final static DeleteOptions DEFAULT_DELETE_OPTIONS = new DeleteOptions();

  private DeploymentOptions deploymentOptions = DEFAULT_DEPLOYMENT_OPTIONS;
  private ClientSession clientSession;
  private DeleteOptions options = DEFAULT_DELETE_OPTIONS;

  public DeleteInputsBuilder deploymentOptions(final DeploymentOptions deploymentOptions) {
    this.deploymentOptions = requireNonNull(deploymentOptions);
    return this;
  }

  public DeleteInputsBuilder session(final ClientSession clientSession) {
    this.clientSession = requireNonNull(clientSession);
    return this;
  }

  public DeleteInputsBuilder deleteOptions(final DeleteOptions options) {
    this.options = requireNonNull(options);
    return this;
  }

  public DeleteInputs create() {
    return new DeleteInputs(deploymentOptions,
                            clientSession,
                            options
    );
  }
}
