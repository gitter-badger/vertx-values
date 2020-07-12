package actors.mongo;

import com.mongodb.client.ClientSession;
import io.vertx.core.DeploymentOptions;

public class FindInputs extends Inputs {

   FindInputs(final DeploymentOptions deploymentOptions,
              final ClientSession clientSession) {
    super(deploymentOptions,
          clientSession
         );
  }


}
