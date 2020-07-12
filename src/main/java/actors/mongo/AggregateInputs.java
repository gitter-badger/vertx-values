package actors.mongo;

import com.mongodb.client.ClientSession;
import io.vertx.core.DeploymentOptions;

public class AggregateInputs extends Inputs {

   AggregateInputs(final DeploymentOptions deploymentOptions,
                   final ClientSession clientSession) {
    super(deploymentOptions,
          clientSession
         );

   }


}
