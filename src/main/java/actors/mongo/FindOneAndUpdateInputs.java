package actors.mongo;

import com.mongodb.client.ClientSession;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import io.vertx.core.DeploymentOptions;

public class FindOneAndUpdateInputs extends Inputs {

   public final FindOneAndUpdateOptions options;

   FindOneAndUpdateInputs(final DeploymentOptions deploymentOptions,
                          final ClientSession clientSession,
                          final FindOneAndUpdateOptions options) {
    super(deploymentOptions,
          clientSession
         );
       this.options = options;
   }


}
