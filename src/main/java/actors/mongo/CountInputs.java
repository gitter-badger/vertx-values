package actors.mongo;

import com.mongodb.client.ClientSession;
import com.mongodb.client.model.CountOptions;
import io.vertx.core.DeploymentOptions;

public class CountInputs extends Inputs {

    final CountOptions options;
   CountInputs(final DeploymentOptions deploymentOptions,
               final ClientSession clientSession,
               final CountOptions options) {
    super(deploymentOptions,
          clientSession
         );
       this.options = options;
   }


}
