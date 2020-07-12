package actors.mongo;

import com.mongodb.client.ClientSession;
import com.mongodb.client.model.UpdateOptions;
import io.vertx.core.DeploymentOptions;

import java.util.Objects;

public class UpdateInputsBuilder {
    private final static DeploymentOptions DEFAULT_DEPLOYMENT_OPTIONS = new DeploymentOptions();
    private final static UpdateOptions DEFAULT_UPDATE_OPTIONS = new UpdateOptions();
    private ClientSession clientSession;
    private DeploymentOptions deploymentOptions = DEFAULT_DEPLOYMENT_OPTIONS;
    private UpdateOptions options = DEFAULT_UPDATE_OPTIONS;

    public UpdateInputsBuilder setDeploymentOptions(final DeploymentOptions deploymentOptions) {
        this.deploymentOptions = Objects.requireNonNull(deploymentOptions);
        return this;
    }

    public UpdateInputsBuilder session(final ClientSession clientSession) {
        this.clientSession = Objects.requireNonNull(clientSession);
        return this;
    }

    public UpdateInputsBuilder options(final UpdateOptions options) {
      this.options = Objects.requireNonNull(options);
        return this;
    }

    public UpdateInputs create() {
        return new UpdateInputs(deploymentOptions,
                                clientSession,
                                options
        );
    }
}
