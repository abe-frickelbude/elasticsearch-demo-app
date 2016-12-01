package de.frickelbude.es.sandbox.base;

import static org.junit.Assert.*;
import org.elasticsearch.action.admin.indices.cache.clear.*;
import org.elasticsearch.action.admin.indices.delete.*;
import org.elasticsearch.action.admin.indices.exists.indices.*;
import org.elasticsearch.client.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import de.frickelbude.es.sandbox.config.*;

/**
 * Base class for various integration tests - provided here mainly to reduce copy-paste and also to provide
 * common ES index cleanup functionality.
 * 
 * @author Ibragim Kuliev
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
    TestContextConfiguration.class
})
public abstract class BaseEsSandboxTest {

    private static final String INDEX_NAMES[] = {
        "passive", "semiconductor"
    };

    @Autowired
    private Client client;

    /**
     * This implementation can be called in the {@linkplain @Before} or {@linkplain @After} initialization methods of
     * any test class that performs persistent operations on ES indices and wishes to cleanup before or after itself.
     * 
     * <p>
     * Please note that the implementation is a little convoluted - I could have just used prepareDelete("_all") instead, and
     * not performed any post-delete checking. However, it provides a nice example for using the "administrative" client APIs
     * ;-)
     * </p>
     */
    @SuppressWarnings("unused")
    public void cleanIndices() {

        IndicesAdminClient indexAdminClient = client.admin().indices();
        for (String indexName : INDEX_NAMES) {

            // see if the targeted index actually exists
            IndicesExistsResponse existResponse = indexAdminClient.prepareExists(indexName).execute().actionGet();

            if (existResponse.isExists()) {
                // delete it
                ClearIndicesCacheResponse clearResponse = indexAdminClient.prepareClearCache(indexName).execute().actionGet();
                DeleteIndexResponse deleteResponse = indexAdminClient.prepareDelete(indexName).execute().actionGet();

                // and make sure the index has really been deleted
                existResponse = indexAdminClient.prepareExists(indexName).execute().actionGet();
                assertFalse(existResponse.isExists());
            }
        }

        // sync everything
        indexAdminClient.refresh(Requests.refreshRequest()).actionGet();
    }
}
