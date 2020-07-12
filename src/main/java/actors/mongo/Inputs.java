package actors.mongo;

import com.mongodb.client.ClientSession;
import io.vertx.core.DeploymentOptions;

import java.util.Objects;

public class Inputs {

  public final DeploymentOptions deploymentOptions;
  public final ClientSession clientSession;

  public Inputs(final DeploymentOptions deploymentOptions,
                final ClientSession clientSession) {
    this.deploymentOptions = Objects.requireNonNull(deploymentOptions);
    this.clientSession = clientSession;
  }
}
