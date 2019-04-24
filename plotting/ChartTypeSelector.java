package hud.plotting;


import javafx.scene.chart.*;

import java.util.List;
import java.util.stream.Collectors;

public class ChartTypeSelector extends LabeledComboBox {
    private static final String DESCRIPTION = "Select Chart Type";
    private static final List<Class<? extends XYChart>> charts = List.of(StackedAreaChart.class,
                                                                           LineChart.class,
                                                                           ScatterChart.class,
                                                                           BubbleChart.class);

    public ChartTypeSelector() {
        super(DESCRIPTION);
    }

    @Override
    protected List<String> getItems() {
        return charts.stream().map(clazz -> getClassName(clazz)).collect(Collectors.toList());
    }

    private String getClassName(Class clazz) {
        System.out.println(clazz.toString());
        String[] splitPath = clazz.toString().split("\\.");
        return splitPath[splitPath.length - 1];
    }
}
