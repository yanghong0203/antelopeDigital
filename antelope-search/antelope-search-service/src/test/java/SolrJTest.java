import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

public class SolrJTest {

/*   @Test
    public void addField(){
        SolrServer solrServer = new HttpSolrServer("http://115.159.68.198:8180/");
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","123");
        document.addField("item_title","yang");
        try {
            solrServer.add(document);
            solrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void deleteField() throws Exception{
        SolrServer solrServer = new HttpSolrServer("http://115.159.68.198:8180/");
        solrServer.deleteById("123");
        solrServer.commit();
    }

    @Test
    public void searchField() throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://127.0.0.1:8180/solr/");
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        QueryResponse queryResponse = solrServer.query(solrQuery);
        SolrDocumentList documentList = queryResponse.getResults();
        for(SolrDocument document : documentList){
            System.out.println(document.get("id"));
        }
    }*/
}
