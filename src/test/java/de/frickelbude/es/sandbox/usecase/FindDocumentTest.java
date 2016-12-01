package de.frickelbude.es.sandbox.usecase;

import static org.junit.Assert.*;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.*;
import org.elasticsearch.client.*;
import org.junit.*;
import org.junit.rules.*;
import org.junit.runners.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import de.frickelbude.es.sandbox.base.*;
import de.frickelbude.es.sandbox.common.*;
import de.frickelbude.es.sandbox.entity.*;
import de.frickelbude.es.sandbox.rules.*;
import de.frickelbude.es.sandbox.service.builder.*;
import de.frickelbude.es.sandbox.service.generator.*;
import de.frickelbude.es.sandbox.service.io.*;

/**
 * The following are basic tests for the use case "find documents" using the ES java client API.
 * 
 * <p>
 * The breakdown is as follows:
 * </p>
 * 
 * <ul>
 * <strike>
 * <li>Although the official ES API documentation (I'm referring to <a
 * href="https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/index_.html">here</a>) doesn't state it
 * explicitly, in the most basic form indexing a document <strong>requires</strong> the "index name" and "type" parameters,
 * whereas the document ID is optional, and will be auto-generated if it is not provided. This is more clear from the Javadoc
 * of e.g. {@linkplain IndexRequest}, where the index name and type are clearly labeled as required.</li>
 * <li>Failure to provide either of the required parameters produces an exception, as outlined in the tests below.</li>
 * <li>Auto-generated IDs are <em>alphanumeric</em> strings and not integers!</li>
 * <li>The index API can also be used to update existing documents. The difference is in the returned
 * {@linkplain IndexResponse}: the isCreated() flag is set to <code>false</code>, as opposed to <code>true</code> when
 * creating an index entry for the first time.</li>
 * </strike>
 * </ul>
 * 
 * <p>
 * Notes:
 * </p>
 * 
 * <ul>
 * <li>This test class as well as some others "abuse" the @FixMethodOrder mechanism provided by the JUnit framework to
 * artificially impose order on the test case execution. This, in conjunction with aSetUp() and xTearDown() methods below
 * simulates a once-per-test class setup and teardown scaffold that <em>doesn't require</em> static methods. Be aware that
 * this solution is rather fragile, because it depends on lexicographic ordering of method names, and furthermore hijacks
 * actual test cases to provide non-test functionality! Alas, the aforementioned functionality is not natively available in
 * JUnit (also not via {@linkplain TestRule} or {@linkplain ExternalResource}), and this is the simplest way to attain it
 * without resorting to TestNG....</li>
 * <li>The initialization of sample index data uses the Bulk API to substantially improve performance when inserting a large
 * number of documents. However, the setup method below should not be considered a general demonstration of the said bulk API
 * - this is handled elsewhere.</li>
 * </ul>
 * 
 * @author Ibragim Kuliev
 * 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FindDocumentTest extends BaseEsSandboxTest {

    private static final int NUM_DOCUMENTS = 50000;

    @Log
    private static Logger log;

    @Rule
    public TimingWatcher testWatcher = new TimingWatcher();

    @Autowired
    private ResistorBuilder resistorBuilder;

    @Autowired
    private RandomComponentFactory componentFactory;

    @Autowired
    private ComponentJsonConverter converter;

    @Autowired
    private Client client;

    @Test
    public void aSetUp() {

        // insert some documents into indices via bulk API
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();

        for (int i = 0; i < NUM_DOCUMENTS; i++) {
            Component component = componentFactory.makeComponent();
            String document = converter.toJson(component);
            bulkRequestBuilder.add(client.prepareIndex(
                component.getCategory(),
                component.getType(),
                String.valueOf(component.getId())).
                setSource(document));
        }

        BulkResponse response = bulkRequestBuilder.execute().actionGet();
        assertNotNull(response);
        assertFalse(response.hasFailures());
    }

    // @Test
    public void xTearDown() {
        cleanIndices();
    }

    /**
     * Simplest case: find a single document by known index, type and ID. This can be efficiently handled by the
     * GET API, which differs from the usual Search API is that it requires the document ID to be known a-priori, and
     * implicitly returns the single retrieved document. Note that the "type" argument is optional and can be
     * omitted (read: set to <code>null</code>), however this may have a certain negative impact on performance when
     * operating on a large index.
     */
    @Test
    public void testFindSingleDocument() {

        /*
         * since we don't know the distribution and specific IDs of the components created by the setup phase,
         * we will insert a single known element so that we can find it later.
         */

        Resistor resistor = resistorBuilder.buildComponent();
        String document = converter.toJson(resistor);

        client.prepareIndex(
            resistor.getCategory(),
            resistor.getType(),
            String.valueOf(resistor.getId())).setSource(document).execute().actionGet();

        GetResponse response = client.prepareGet(
            PassiveDevice.CATEGORY, Resistor.TYPE, resistor.getId().toString()).execute().actionGet();
        assertNotNull(response);
        log.info(response.getSourceAsString());
    }

    @Test
    public void testFindByQuery() {

    }

}
