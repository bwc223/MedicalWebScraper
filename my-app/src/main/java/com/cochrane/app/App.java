package com.cochrane.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//These libraries can help with a more secure connection to cover worst case scenarios with the webcrawler (webscraper).
import org.apache.hc.client5.http.*;
import org.apache.hc.core5.http.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Brendan Conway
 *
 * Below is a link to the Cochrane results page prior to the url changing after the search selection is made.
 * 
 * https://www.cochranelibrary.com/en/search?p_p_id=scolarissearchresultsportlet_WAR_scolarissearchresults&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_scolarissearchresultsportlet_WAR_scolarissearchresults_displayText=Health+professional+education&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchText=Health+professional+education&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchType=basic&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetQueryField=topic_id&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchBy=13&_scolarissearchresultsportlet_WAR_scolarissearchresults_orderBy=displayDate-true&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetDisplayName=Health+professional+education&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetQueryTerm=z1702221407285233729667009826083&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetCategory=Topics
 */
public class App 
{

    public static void main( String[] args )
    {
        //starting link (Health Professional Education)
        final String url = "https://www.cochranelibrary.com/en/search?p_p_id=scolarissearchresultsportlet_WAR_scolarissearchresults&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_scolarissearchresultsportlet_WAR_scolarissearchresults_displayText=Health+professional+education&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchText=Health+professional+education&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchType=basic&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetQueryField=topic_id&_scolarissearchresultsportlet_WAR_scolarissearchresults_searchBy=13&_scolarissearchresultsportlet_WAR_scolarissearchresults_orderBy=displayDate-true&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetDisplayName=Health+professional+education&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetQueryTerm=z1702221407285233729667009826083&_scolarissearchresultsportlet_WAR_scolarissearchresults_facetCategory=Topics";

        ArrayList<String> results = new ArrayList<String>(); //where the results will be stored.

        FileCreator(); //creates the file to be written to.

        try
        {
            final Document document = Jsoup.connect(url).get();

            for(Element row : document.select("div.search-results-section-body div.search-results-item"))
            {
                Element link = row.select("a").first();
                final String urlItem = link.attr("abs:href");
                final String topic = "Health Professional Education";
                final String title = row.select("h3.result-title").text();
                final String authors = row.select("div.search-result-authors").text();
                final String date = row.select("div.search-result-date").text();

                final String result = urlItem + "|" + topic + "|" + title + "|" + authors + "|" + date;
                results.add(result);
            }

        } 
        catch (Exception e) 
        {
            // TODO: handle exception
            e.printStackTrace();
        }

        FileWriter(results); //writes the completed results to the file.

    }

    public static void FileCreator()
    {
        try 
         {
             File outputFile = new File("cochrane_reviews.txt");
             if(outputFile.createNewFile())
             {
                 System.out.println("File has been created successfully.");
             }
             else
             {
                 System.out.println("File already exists.");
             }
         } 
         catch (IOException exception) 
         {
             System.out.println("Error: Unable to create file.");
         }
    }

    public static void FileWriter(ArrayList<String> result)
    {
        //Writing to the file output
        try 
        {
            FileWriter outputWriter = new FileWriter("cochrane_reviews.txt");
            for(int i = 0; i < result.size(); i++)
            {
                outputWriter.write(result.get(i) + "\n");
            }
            outputWriter.close();
            System.out.println("File has been successfully written.");
        }
        catch (Exception ex) 
        {
            System.out.println("Error: Unable to write to file.");
        }
    }
}
