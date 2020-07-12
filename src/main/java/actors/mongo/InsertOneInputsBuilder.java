package actors.mongo;

import com.mongodb.client.ClientSession;
import com.mongodb.client.model.InsertOneOptions;
import io.vertx.core.DeploymentOptions;

import static java.util.Objects.requireNonNull;

public class InsertOneInputsBuilder {
  private final static DeploymentOptions DEFAULT_DEPLOYMENT_OPTIONS = new DeploymentOptions();
  private final static InsertOneOptions DEFAULT_INSERT_OPTIONS = new InsertOneOptions();
  private DeploymentOptions deploymentOptions = DEFAULT_DEPLOYMENT_OPTIONS;
  private InsertOneOptions options = DEFAULT_INSERT_OPTIONS;
  private ClientSession clientSession;

  public InsertOneInputsBuilder deployOptions(final DeploymentOptions deploymentOptions) {
    this.deploymentOptions = requireNonNull(deploymentOptions);
    return this;
  }

  public InsertOneInputsBuilder options(final InsertOneOptions options) {
    this.options = requireNonNull(options);
    return this;
  }

  public InsertOneInputsBuilder session(final ClientSession clientSession) {
    this.clientSession = requireNonNull(clientSession);
    return this;
  }

  public InsertOneInputs create() {
    return new InsertOneInputs(deploymentOptions,
                               options,
                               clientSession
    );
  }
}
