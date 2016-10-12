package DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JCheckBox;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.RDFNode;

import Util.owlUtil;

public class DAO_Refactoring {

	private owlUtil owl = new owlUtil();

	public Collection<Refactoring> getRefactoringFilterAdvantegeDomain(List<String> lDomains, List<String> lAdvantages, Boolean hasReverse) {
		String query = "";
		String selects = "";
		Collection<Refactoring> lRefactoring = new ArrayList();

		int count = 1;
		query = " ?r a my:Refactoring. "
			  + " ?r my:createBy ?a. "
			  + " ?a my:name ?name_author. "
			  + " ?r my:hasWork ?w. "
			  + " ?w my:title ?work_title.  ";
		
		for (String domain : lDomains) {
			query += "?r my:hasDomain ?d" + count + ". " + "?d" + count + " rdfs:label ?lb_dom" + count
					+ " FILTER REGEX(?lb_dom" + count + ", \"" + domain + "\", \"i\"). ";
			count++;
		}

		for (String advantage : lAdvantages) {
			query += "?r my:hasAdvantage ?v" + count + ". " + "?v" + count + " rdfs:label ?lb_adv" + count
					+ " FILTER REGEX(?lb_adv" + count + ", \"" + advantage + "\", \"i\"). ";
			count++;
		}

		if(hasReverse){
			query +=  "OPTIONAL {?r my:hasReverse ?reverse. }";
			selects += "?reverse ";
		}
		
		query = "SELECT ?r ?name_author ?work_title ?reverse WHERE { " + query + " } ORDER BY ?a";

//		 System.out.println(query);

		ResultSet results = owl.sparqlQuery(query);
		
		while (results.hasNext()) {
			QuerySolution row = results.next();
			Refactoring r = new Refactoring();
//			System.out.println(row.getResource("r").getLocalName());
			r.setName(row.getResource("r").getLocalName());
//			r.setAuthor(row.getResource("a").getLocalName());
			r.setAuthor(row.getLiteral("name_author").getString());
			r.setWorkTitle(row.getLiteral("work_title").getString());
			r.setReverse( row.getResource("reverse") == null ? "" : row.getResource("reverse").getLocalName() );
			lRefactoring.add(r);
		}

		return lRefactoring;
	}
	
	
	public ResultSet  getExamplesRefacotring(String refactoring) {		
		String query = "";
		String selects = "";
		
		query = " SELECT ?r ?e ?before ?after "
		      + " WHERE { "
			      + " ?r a my:Refactoring. "
				  + " ?r my:hasExample ?e. "
			      + " ?e my:beforeRefactoring ?before. "
			      + " ?e my:afterRefactoring ?after. "
				  + " FILTER regex(str(?r), \""+refactoring+"\", \"i\") "
			  + " }";

		System.out.println(query);
		
		return owl.sparqlQuery(query);
		
	}
	
	public String getExamplesRefacotringString(String refactoring){
		String result = "";
		int i = 1;
		
		ResultSet results = getExamplesRefacotring(refactoring);
		
		while (results.hasNext()) {
			QuerySolution row = results.next();
			result += " - - - - - EXAMPLE "+ i + " - - - - - \n";
			result += "Before:\n" + row.getLiteral("before").getString() + "\n" ;
			result += "After:\n" + row.getLiteral("after").getString() + "\n" ;
		}
	
		return result;
		
	}

}
