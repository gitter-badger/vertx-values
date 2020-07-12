package actors.mongo;

import actors.mongo.Inputs;
import com.mongodb.client.ClientSession;
import com.mongodb.client.model.UpdateOptions;
import io.vertx.core.DeploymentOptions;

import java.util.Objects;

public class UpdateInputs extends Inputs {

  public final UpdateOptions options;

  UpdateInputs(final DeploymentOptions deploymentOptions,
               final ClientSession clientSession,
               final UpdateOptions options) {
    super(deploymentOptions,
          clientSession
         );
    this.options = Objects.requireNonNull(options);
  }
}
