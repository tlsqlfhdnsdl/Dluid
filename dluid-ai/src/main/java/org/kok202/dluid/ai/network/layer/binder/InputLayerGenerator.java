package org.kok202.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;

import java.util.List;

public class InputLayerGenerator extends AbstractLayerGenerator {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.INPUT_LAYER;
    }

    @Override
    public void generate(Layer layer, List<Layer> layerFroms, ComputationGraphConfiguration.GraphBuilder graphBuilder) {
        graphBuilder.addInputs(layer.getId());
    }

}
