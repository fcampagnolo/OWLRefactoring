package TDDJena;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ext.com.google.common.reflect.ClassPath.ClassInfo;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.QuerySolutionMap;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.ResourceImpl;
import org.apache.jena.sparql.core.VarExprList;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.VCARD;
import org.apache.log4j.BasicConfigurator;

import DAO.DAO_Refactoring;
import Util.owlUtil;

public class TestingJena {

	// public static String sparqlQuery = "SELEC *";

	public static void main(String[] args) {
		// rdf();
//		AnotherJenaSparqlExample();

		owlUtil owl = new owlUtil();

		owl.readOwl();

//		owl.sparqlQuery("SELECT ?r ?a ?w"
//				+ " WHERE { "
//					+ "?r a my:Refactoring. "
//					+ "?r my:createBy ?a. "
//					+ "?r my:hasWork ?w "
//					+ "} "
//					+ "ORDER BY ?a");
		
		
		DAO_Refactoring dao = new DAO_Refactoring();		
		System.out.println(dao.getExamplesRefacotringString("Self_Encaps"));
		
//		"Convert_Functional"
		
//		for (String s : owl.getAllClassLabel("Vantagens")) {
//			System.out.println(s);
//		}
//		owl.owlSYSO();
		
		System.out.println("---------------------FIM");
	}

	public static void AnotherJenaSparqlExample() {
		BasicConfigurator.configure();

		System.out.println("Working Directory = " + System.getProperty("user.dir"));

		String inputFileName = "src/HarryPotter.owl";
		// String inputFileName = "ontologyTest.xml";
		// Model model = ModelFactory.createDefaultModel();
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		// use the FileManager to find the input file
		try {
			File file = new File(inputFileName);
			FileReader reader = new FileReader(file);
			model.read(reader, "http://www.owl-ontologies.com/Ontology1320757455.owl#");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("------------------- TESTE");

		// write it to standard out
		model.write(System.out);

		String sparql = "PREFIX  my:<http://www.owl-ontologies.com/Ontology1320757455.owl#>"
				+ "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ " SELECT ?pessoa WHERE { ?pessoa a my:Pessoa}";

		Query query = QueryFactory.create(sparql);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		List vars = results.getResultVars();

		// while(results.hasNext()){
		// QuerySolution qs = results.nextSolution();
		// System.out.println("--------- Solution ---------");
		// for(int i = 0; i < vars.size(); i++){
		// String var = vars.get(i).toString();
		// RDFNode node = qs.get(var);
		// System.out.println(var + "\t" + node.toString());
		// System.out.println(var + "\t" + node.is);
		// }
		// }

		// for (int i = 0; i < vars.size(); i++) {
		// System.out.println(vars.get(i).toString());
		// }

		// ResultSetFormatter.out(System.out, results, query);

		// Busca todas as classes da ontologia

		Iterator classIter = model.listClasses();
		while (classIter.hasNext()) {
			OntClass ontClass = (OntClass) classIter.next();
			String uri = ontClass.getURI();
			if (uri != null) {
				System.out.println(uri);
			}
		}

		// Resource r = model.getResource(
		// "http://www.owl-ontologies.com/Ontology1320757455.owl#Pessoa");
		// OntClass paper = r.as( OntClass.class );
		// System.out.println(paper.getNameSpace());
		// System.out.println(paper.getURI());
		// System.out.println(paper.getLocalName());

		// Get All Class.
		OntClass paper = model.getOntClass("http://www.owl-ontologies.com/Ontology1320757455.owl#Animal");

		// Print each of its instances.
		for (ExtendedIterator<? extends OntResource> entitys = paper.listInstances(); entitys.hasNext();) {
			// System.out.println("---------------");
			System.out.println(entitys.next().getLocalName());
		}

		// Individual p1 =
		// model.getIndividual("http://www.owl-ontologies.com/Ontology1320757455.owl#Student");
		// System.out.println(p1.toString());
		// System.out.println(p1.getURI());
		// for (Iterator<Resource> i = p1.listRDFTypes(true); i.hasNext(); ) {
		// System.out.println( p1.getURI() + " is inferred to be in class " +
		// i.next() );
		// }

	}

	public static void rdf() {
		// Model m = ModelFactory.createDefaultModel();
		//
		// String NS = "http://example.com/test/";
		// Resource r = m.createResource( NS + "r");
		// Property p = m.createProperty( NS + "p");
		//
		// r.addProperty(p, "Hello World", XSDDatatype.XSDstring);
		//
		// m.write(System.out, "Turtle");
		//

		// create an empty model
		Model model = ModelFactory.createDefaultModel();
		String inputFileName = "ontologyTest.xml";
		// use the FileManager to find the input file
		InputStream in = FileManager.get().open(inputFileName);
		if (in == null) {
			throw new IllegalArgumentException("File: " + inputFileName + " not found");
		}

		// read the RDF/XML file
		model.read(in, null);

		// write it to standard out
		model.write(System.out);

		// select all the resources with a VCARD.FN property
		ResIterator iter = model.listSubjectsWithProperty(VCARD.FN);
		if (iter.hasNext()) {
			System.out.println("The database contains vcards for:");
			while (iter.hasNext()) {
				System.out.println("  " + iter.nextResource().getProperty(VCARD.FN).getString());
			}
		} else {
			System.out.println("No vcards were found in the database");
		}

		// String sparqlQuery = "SELECT ?r WHERE { ?r a :Family }";
		//
		// Query query = QueryFactory.create(sparqlQuery);
		// QueryExecution qe = QueryExecutionFactory.create(query);
		// ResultSet results = qe.execSelect();
		// System.out.println(results.toString());

	}
}
