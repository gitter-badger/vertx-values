package actors.mongo;

import com.mongodb.client.ClientSession;
import com.mongodb.client.model.ReplaceOptions;
import io.vertx.core.DeploymentOptions;

import java.util.Objects;

public class ReplaceInputs extends Inputs {

  public final ReplaceOptions options;

  ReplaceInputs(final DeploymentOptions deploymentOptions,
                final ClientSession clientSession,
                final ReplaceOptions options) {
    super(deploymentOptions,
          clientSession
         );
    this.options = Objects.requireNonNull(options);
  }
}
