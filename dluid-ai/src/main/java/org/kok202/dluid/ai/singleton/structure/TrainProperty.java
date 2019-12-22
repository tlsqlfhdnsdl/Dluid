package org.kok202.dluid.ai.singleton.structure;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.kok202.dluid.ai.AIConstant;
import org.kok202.dluid.ai.entity.enumerator.Optimizer;
import org.kok202.dluid.ai.entity.enumerator.WeightInitWrapper;
import org.kok202.dluid.domain.exception.CanNotFindGraphNodeException;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.ArrayList;
import java.util.List;

@Data
public class TrainProperty {
    private Optimizer optimizer;
    private WeightInitWrapper weightInit;
    private LossFunctions.LossFunction lossFunction;
    private double learningRate;
    private double learningMomentum;
    private int batchSize;
    private int totalRecordSize;
    private int epoch;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private List<DataSetManager> dataSetManagers;

    public TrainProperty() {
        optimizer = Optimizer.SGD;
        weightInit = WeightInitWrapper.ONES;
        lossFunction = LossFunctions.LossFunction.MSE;
        batchSize = AIConstant.DEFAULT_BATCH_SIZE;
        totalRecordSize = AIConstant.DEFAULT_RECORD_SIZE;
        learningRate = AIConstant.DEFAULT_LEARNING_RATE;
        epoch = AIConstant.DEFAULT_EPOCH_SIZE;
        dataSetManagers = new ArrayList<>();
    }

    public DataSetManager getDataSetManager(long inputLayerId) {
        for (DataSetManager dataSetManager : dataSetManagers) {
            if(dataSetManager.getInputLayerId() == inputLayerId)
                return dataSetManager;
        }
        throw new CanNotFindGraphNodeException(String.valueOf(inputLayerId));
    }
}
