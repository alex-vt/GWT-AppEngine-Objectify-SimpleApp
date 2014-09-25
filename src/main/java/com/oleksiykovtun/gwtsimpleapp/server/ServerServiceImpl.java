package com.oleksiykovtun.gwtsimpleapp.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.ObjectifyService;
import com.oleksiykovtun.gwtsimpleapp.client.ServerService;
import com.oleksiykovtun.gwtsimpleapp.shared.entities.Item;

/**
 * RPC service server side.
 */
public class ServerServiceImpl extends RemoteServiceServlet
        implements ServerService {

    static {
        ObjectifyService.register(Item.class);
    }

    public List<Item> compute(List<Item> inputItems) {
        List<Item> outputItems = new ArrayList<Item>(inputItems);
        outputItems.add(new Item("(noname)", 1));
        return outputItems;
    }

    public void delete() {
        ObjectifyService.ofy().delete().keys(ObjectifyService.ofy()
                .load().type(Item.class).keys());
    }

    public List<Item> get() {
        return new ArrayList<Item>(ObjectifyService.ofy()
                .load().type(Item.class).list());
    }

    public Item save(Item item) {
        ObjectifyService.ofy().save().entity(item).now();
        return item;
    }

}
