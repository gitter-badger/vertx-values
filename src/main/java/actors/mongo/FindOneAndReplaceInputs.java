package actors.mongo;

import com.mongodb.client.ClientSession;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import io.vertx.core.DeploymentOptions;

public class FindOneAndReplaceInputs extends Inputs {

   public final FindOneAndReplaceOptions options;

   FindOneAndReplaceInputs(final DeploymentOptions deploymentOptions,
                           final ClientSession clientSession,
                           final FindOneAndReplaceOptions options) {
    super(deploymentOptions,
          clientSession
         );
       this.options = options;
   }


}
