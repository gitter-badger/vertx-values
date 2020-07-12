package actors.mongo;

import com.mongodb.client.ClientSession;
import com.mongodb.client.model.FindOneAndDeleteOptions;
import io.vertx.core.DeploymentOptions;

public class FindOneAndDeleteInputs extends Inputs {

   public final FindOneAndDeleteOptions options;

   FindOneAndDeleteInputs(final DeploymentOptions deploymentOptions,
                          final ClientSession clientSession,
                          final FindOneAndDeleteOptions options) {
    super(deploymentOptions,
          clientSession
         );
       this.options = options;
   }


}
