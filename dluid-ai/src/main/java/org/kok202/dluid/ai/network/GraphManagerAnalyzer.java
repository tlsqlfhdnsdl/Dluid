package org.kok202.dluid.ai.network;

import lombok.Getter;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.domain.structure.GraphManager;
import org.kok202.dluid.domain.structure.GraphNode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class GraphManagerAnalyzer {

    private List<Long> inputLayerIds;
    private Map<Long, Map<Long, Layer>> multiLayerManager;

    public GraphManagerAnalyzer() {
        multiLayerManager = new LinkedHashMap<>();
    }

    public void analyze(GraphManager<Layer> layerGraphManager){
        inputLayerIds = layerGraphManager.getDataNodes()
                .stream()
                .filter(layer -> layer.getType().isInputLayerType())
                .map(Layer::getId)
                .collect(Collectors.toList());

        for (long inputLayerId : inputLayerIds) {
            Map<Long, Layer> multiLayersFromInputLayer = new LinkedHashMap<>();
            GraphNode<Layer> inputLayerGraphNode = layerGraphManager.findGraphNode(layerGraphNode -> ((Layer)layerGraphNode).getId() == inputLayerId);
            List<GraphNode<Layer>> reachableLayerGraphNodes = layerGraphManager.findAllReachableNode(inputLayerGraphNode);

            reachableLayerGraphNodes.forEach(reachableLayerGraphNode -> {
                if(reachableLayerGraphNode.getData().getType().isAssistLayerType())
                    return;

                multiLayersFromInputLayer.put(
                        reachableLayerGraphNode.getData().getId(),
                        reachableLayerGraphNode.getData());
            });
            multiLayerManager.put(inputLayerId, multiLayersFromInputLayer);
        }

    }
}