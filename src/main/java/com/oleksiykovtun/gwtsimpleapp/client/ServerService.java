package com.oleksiykovtun.gwtsimpleapp.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.oleksiykovtun.gwtsimpleapp.shared.entities.Item;

/**
 * RPC service client side.
 */
@RemoteServiceRelativePath("services")
public interface ServerService extends RemoteService {

    public void delete();

    public List<Item> get();

    public Item save(Item item);

    public List<Item> compute(List<Item> inputItems);

}
