package actors.mongo;

import com.mongodb.client.ClientSession;
import com.mongodb.client.model.DeleteOptions;
import io.vertx.core.DeploymentOptions;

public class DeleteInputs extends Inputs {

  public final DeleteOptions options;
  public DeleteInputs(final DeploymentOptions deploymentOptions,
                      final ClientSession clientSession,
                      final DeleteOptions options) {
    super(deploymentOptions,
          clientSession
         );
    this.options = options;
  }
}
