package actors.mongo;

import com.mongodb.client.ClientSession;
import com.mongodb.client.model.InsertManyOptions;
import io.vertx.core.DeploymentOptions;

public class InsertManyInputs extends Inputs {


  public final InsertManyOptions options;


   InsertManyInputs(final DeploymentOptions deploymentOptions,
                    final InsertManyOptions options,
                    final ClientSession clientSession) {
    super(deploymentOptions,
          clientSession
         );
    this.options = options;
  }


}
