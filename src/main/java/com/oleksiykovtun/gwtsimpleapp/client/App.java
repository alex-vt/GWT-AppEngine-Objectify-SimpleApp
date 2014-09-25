package com.oleksiykovtun.gwtsimpleapp.client;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ProvidesKey;
import com.oleksiykovtun.gwtsimpleapp.shared.entities.Item;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry point class
 */
public class App implements EntryPoint {

    private CellTable dataTable = getTable();

    private List<Item> items = new ArrayList<Item>();

    private final ServerServiceAsync service
            = GWT.create(ServerService.class);

    /**
     * Entry point.
     */
    public void onModuleLoad() {
        VerticalPanel panel = new VerticalPanel();
        panel.setSpacing(10);

        panel.add(dataTable);
        panel.add(getAddButton(items));
        panel.add(getLoadButton());
        panel.add(getSaveButton(items));
        panel.add(getDeleteButton());

        RootPanel.get().add(panel);

        loadItems();
    }


    private CellTable getTable() {
        final CellTable table = new CellTable(new ProvidesKey() {

            public Object getKey(Object item) {
                return ((Item) item).id;
            }
        });

        Column nameColumn = new Column(new TextInputCell()) {
            public String getValue(Object object) {
                return ((Item) object).name;
            }
        };
        table.addColumn(nameColumn, "Item Name");
        nameColumn.setFieldUpdater(new FieldUpdater() {

            public void update(int index, Object object, Object value) {
                ((Item) object).name = (String) value;
                table.redraw();
            }
        });

        Column quantityColumn = new Column(
                new SelectionCell(Arrays.asList("1", "2", "3", "4", "5"))) {
            public String getValue(Object object) {
                return "" + ((Item) object).quantity;
            }
        };
        table.addColumn(quantityColumn, "Quantity");
        quantityColumn.setFieldUpdater(new FieldUpdater() {

            public void update(int index, Object object, Object value) {
                ((Item) object).quantity = Integer.parseInt((String) value);
                table.redraw();
            }
        });
        return table;
    }

    private void updateTable(List<Item> inputItems) {
        items.clear();
        for (Item itemLoaded : inputItems) {
            items.add(itemLoaded);
        }
        dataTable.setRowData(items);
    }


    private Button getAddButton(final List<Item> items) {
        Button button = new Button("Add Item");
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                addItem(items);
            }
        });
        return button;
    }

    private void addItem(final List<Item> items) {
        service.compute(items,
            new AsyncCallback<List<Item>>() {

                public void onFailure(Throwable caught) {
                    Logger.getLogger("").log(Level.SEVERE,
                            "Computing failed.", caught);
                }

                public void onSuccess(List<Item> itemsLoaded) {
                    Logger.getLogger("").info("New items computed.");
                    updateTable(itemsLoaded);
                }
            });
    }


    private Button getLoadButton() {
        Button button = new Button("Load from Database");
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                loadItems();
            }
        });
        return button;
    }

    private void loadItems() {
        service.get(new AsyncCallback<List<Item>>() {

            public void onFailure(Throwable caught) {
                Logger.getLogger("").log(Level.SEVERE,
                        "Loading failed.", caught);
            }

            public void onSuccess(List<Item> itemsLoaded) {
                Logger.getLogger("").info("Loaded "
                        + itemsLoaded.size() + " items.");
                updateTable(itemsLoaded);
            }
        });
    }


    private Button getSaveButton(final List<Item> items) {
        Button button = new Button("Save to Database");
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                saveItems(items);
            }
        });
        return button;
    }

    private void saveItems(List<Item> items) {
        for (int i = 0; i < items.size(); ++i) {
            final Item item = items.get(i);
            service.save(item,
                new AsyncCallback<Item>() {

                    public void onFailure(Throwable caught) {
                        Logger.getLogger("").log(Level.SEVERE,
                                "Saving " + item.getName()
                                        + " failed.", caught);
                    }

                    public void onSuccess(Item item) {
                        Logger.getLogger("").info(item.getName()
                                + " saved.");
                    }
                }
            );
        }
    }


    private Button getDeleteButton() {
        Button button = new Button("Delete Database");
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                deleteItems();
            }
        });
        return button;
    }

    private void deleteItems() {
        service.delete(new AsyncCallback<Void>() {

            public void onFailure(Throwable caught) {
                Logger.getLogger("").log(Level.SEVERE,
                        "Deleting failed.", caught);
            }

            public void onSuccess(Void aVoid) {
                Logger.getLogger("").info("All items deleted.");
                updateTable(new ArrayList<Item>());
            }

        });
    }

}