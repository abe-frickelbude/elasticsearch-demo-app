package de.frickelbude.es.sandbox.service;

import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.get.*;
import org.elasticsearch.client.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import de.frickelbude.es.sandbox.entity.Component;
import de.frickelbude.es.sandbox.service.generator.*;
import de.frickelbude.es.sandbox.service.io.*;

@Service
public class SampleDataService {

    @Autowired
    private Client client;

    @Autowired
    private RandomComponentFactory componentFactory;

    @Autowired
    private ComponentJsonConverter converter;

    public BulkResponse fillSampleData(final Integer numSamples) {

        // insert some documents into indices via bulk API
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();

        for (int i = 0; i < numSamples; i++) {
            Component component = componentFactory.makeComponent();
            String document = converter.toJson(component);
            bulkRequestBuilder.add(client.prepareIndex(
                component.getCategory(),
                component.getType(),
                String.valueOf(component.getId())).setSource(document));
        }

        BulkResponse response = bulkRequestBuilder.execute().actionGet();
        return response;
    }

    public GetResponse getById(final String indexName, final String type, final Long id) {
        GetResponse response = client.prepareGet(indexName, type, id.toString()).execute().actionGet();
        return response;
    }

    public GetResponse getById(final String indexName, final Long id) {
        GetResponse response = client.prepareGet(indexName, null, id.toString()).execute().actionGet();
        return response;
    }

}
