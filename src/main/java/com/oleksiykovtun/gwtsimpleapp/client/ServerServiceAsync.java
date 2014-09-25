package com.oleksiykovtun.gwtsimpleapp.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.oleksiykovtun.gwtsimpleapp.shared.entities.Item;

/**
 * Async ServerService RPC.
 */
public interface ServerServiceAsync {

    void delete(AsyncCallback<Void> callback);

    void get(AsyncCallback<List<Item>> callback);

    void save(Item item, AsyncCallback<Item> callback);

    void compute(List<Item> items, AsyncCallback<List<Item>> callback);

}
