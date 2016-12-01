package de.frickelbude.es.sandbox.controller;

import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.get.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import de.frickelbude.es.sandbox.dao.admin.*;
import de.frickelbude.es.sandbox.service.*;

@RestController
@RequestMapping("/sample-data")
public class SampleDataController {

    @Autowired
    private SampleDataService sampleDataService;

    @Autowired
    private IndexAdminDao indexAdminDao;

    @RequestMapping("/fill")
    public BulkResponse fillSampleData(@RequestParam("num_samples") final Integer numSamples) {
        return sampleDataService.fillSampleData(numSamples);
    }

    @RequestMapping("/clear")
    public ResponseEntity<String> ndropIndices() {
        indexAdminDao.dropIndices();
        return ResponseEntity.ok("All indices dropped!");
    }

    @RequestMapping("/find/{index}/{id}")
    public GetResponse find(@PathVariable("index") final String indexName, @PathVariable("id") final Long id) {

        return sampleDataService.getById(indexName, id);
    }

    @RequestMapping("/find/{index}/{type}/{id}")
    public GetResponse find(@PathVariable("index") final String indexName, @PathVariable("type") final String type,
        @PathVariable("id") final Long id) {

        return sampleDataService.getById(indexName, type, id);
    }

}
