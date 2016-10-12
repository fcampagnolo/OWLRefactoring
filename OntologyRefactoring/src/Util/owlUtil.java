package Util;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.log4j.BasicConfigurator;

public class owlUtil {
	// create the base model
//	HarryPotter
//	String SOURCE = "http://www.owl-ontologies.com/Ontology1320757455.owl";
//	String inputFileName = "src/Ontology/HarryPotter.owl";
	
	String SOURCE = "http://www.owl-ontologies.com/Ontology1448126814.owl";
	String NS = SOURCE + "#";
	String inputFileName = "src/Ontology/Refactoring_v1.1.owl";
	OntModel model;

	public owlUtil(){
		BasicConfigurator.configure();
		readOwl();
	}
	
	public void readOwl() {
		this.model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		// use the FileManager to find the input file
		try {
			File file = new File(inputFileName);
			FileReader reader = new FileReader(file);
			this.model.read(reader, NS);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR READ OWL");
		}
	}

	public Collection<String> getAllClassLabel(String name) {
		Collection<String> list = new ArrayList<>();
		
		// Get All Class.
		OntClass paper = this.model.getOntClass( NS + name );

		// Print each of its instances.
		for (ExtendedIterator<? extends OntResource> entitys = paper.listInstances(); entitys.hasNext();) {
//			list.add(entitys.next().getLocalName());
			list.add(entitys.next().getLabel(null));
		}
		
		return list;
	}

	public ResultSet sparqlQuery(String init_query) {
		// write it to standard out
//		this.model.write(System.out);

		String sparql = "PREFIX  my:<"+NS+">"
				+ "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
				+ init_query;

		Query query = QueryFactory.create(sparql);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		//
//		System.out.println("---------------------------------Class UTIL");
//		ResultSetFormatter.out(System.out, results, query);
		
		return results;
		// Busca todas as classes da ontologia
		/*
		 * Iterator classIter = model.listClasses(); while(classIter.hasNext()){
		 * OntClass ontClass = (OntClass) classIter.next(); String uri =
		 * ontClass.getURI(); if(uri != null){ System.out.println(uri); } }
		 */
	}
	
	public void owlSYSO(){
		// write it to standard out
		this.model.write(System.out);
	}

}
