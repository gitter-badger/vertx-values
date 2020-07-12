package actors.mongo;

import com.mongodb.client.ClientSession;
import com.mongodb.client.model.InsertOneOptions;
import io.vertx.core.DeploymentOptions;

public class InsertOneInputs extends Inputs {


  public final InsertOneOptions options;


   InsertOneInputs(final DeploymentOptions deploymentOptions,
                   final InsertOneOptions options,
                   final ClientSession clientSession) {
    super(deploymentOptions,
          clientSession
         );
    this.options = options;
  }


}
