package org.kok202.deepblock.application.content.model.test;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Data;
import org.kok202.deepblock.application.content.model.TabModelTestController;

@Data
public class ModelTestTestingController extends AbstractModelTestController {
    @FXML private Accordion accordion;
    @FXML private VBox container;

    private ModelTestTestingTaskController modelTestTestingTaskController;
    private ModelTestTestingResultTableController modelTestTestingResultTableController;

    public ModelTestTestingController(TabModelTestController tabModelTestController) {
        super(tabModelTestController);
    }

    public AnchorPane createView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/frame/content/model/test/testing.fxml"));
        fxmlLoader.setController(this);
        AnchorPane content = fxmlLoader.load();
        return content;
    }

    @Override
    protected void initialize() throws Exception {
        modelTestTestingTaskController = new ModelTestTestingTaskController(getTabModelTestController());
        modelTestTestingResultTableController = new ModelTestTestingResultTableController(getTabModelTestController());

        modelTestTestingTaskController.createView();
        modelTestTestingResultTableController.createView();
        modelTestTestingResultTableController.getTiltedPane().setExpanded(false);

        accordion.getPanes().add(modelTestTestingTaskController.getTiltedPane());
        accordion.setExpandedPane(modelTestTestingTaskController.getTiltedPane());
        container.getChildren().add(modelTestTestingResultTableController.getTiltedPane());
    }
}
