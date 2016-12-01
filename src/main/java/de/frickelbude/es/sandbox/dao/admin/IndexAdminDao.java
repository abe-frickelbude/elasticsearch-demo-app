package de.frickelbude.es.sandbox.dao.admin;

import org.elasticsearch.action.admin.indices.cache.clear.*;
import org.elasticsearch.action.admin.indices.delete.*;
import org.elasticsearch.action.admin.indices.exists.indices.*;
import org.elasticsearch.client.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import de.frickelbude.es.sandbox.common.*;

@Component
public class IndexAdminDao {

    @Autowired
    private Client client;

    public void dropIndices() {

        IndicesAdminClient indexAdminClient = client.admin().indices();
        for (String indexName : Constants.INDEX_NAMES) {

            // see if the targeted index actually exists
            IndicesExistsResponse existResponse = indexAdminClient.prepareExists(indexName).execute().actionGet();

            if (existResponse.isExists()) {
                // delete it
                ClearIndicesCacheResponse clearResponse = indexAdminClient.prepareClearCache(indexName).execute().actionGet();
                DeleteIndexResponse deleteResponse = indexAdminClient.prepareDelete(indexName).execute().actionGet();
            }
        }

        // sync everything
        indexAdminClient.refresh(Requests.refreshRequest()).actionGet();
    }
}
