import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class partation {

public List<clique> cliquePartition(Graph graph) throws CloneNotSupportedException{
	
	Graph graph_clone= new Graph();
    graph_clone = graph.clone();
    List<clique> CL = new ArrayList();
    int j=0;
    Graph g = new Graph();
    List<clique> Clique_list = new ArrayList();
    
    while(graph_clone.NumberofVertex()>0){       

     if(graph_clone.NumberofVertex()==1){
    	clique c1= new clique();
    	c1.vertex.add(graph_clone.FindIsoVertex());
    	graph_clone.removeVertex(graph_clone.FindIsoVertex().getLabel());
    	j++;
    	Clique_list.add(c1);
      } 
    
    if(graph_clone.NumberofEdge()==0&&graph_clone.NumberofVertex()!=0){
    	String s=null;
    	int i=0;
    	while(s==null){
    		if(graph_clone.getVertex(""+i)!=null){
    		s=graph_clone.getVertex(""+i).getLabel();
    		}
    		i++;
    	}
    	
    	clique c1= new clique();
    	c1.addVertex(graph.getVertex(s));
    	graph_clone.removeVertex(s);
    	Clique_list.add(c1);
    	j++;
    }
    
    g = graph_clone.clone();  
    clique c= new clique();
    
    //find clique 
    
    while(g.NumberofVertex()>0&&g.NumberofEdge()>0){
    	
	    if(g.NumberofVertex()==1){    	     	 
	         c.addVertex(g.FindIsoVertex());	
	         g.removeVertex(g.FindIsoVertex().getLabel());        
	       }
	    
	    else{
	    		if(g.findPQ()!=null&&g.findPQ().getOne()!=null&&g.findPQ().getTwo()!=null){
	    			Edge e = new Edge(g.findPQ().getOne(),g.findPQ().getTwo());	    			
	    			g=g.createCommonNrighborGraph(e.getOne(),e.getTwo());
	    			c.addVertex(graph.getVertex(e.getOne().getLabel()));
	    			c.addVertex(graph.getVertex(e.getTwo().getLabel()));     	
	    			g.removeVertex(g.findPQ().getOne().getLabel());  	
	    			g.removeVertex(e.getTwo().getLabel());   
	    		}    		
	    		else if(g.NumberofEdge()==0){
	    			c.addVertex(graph.getVertex(g.FindIsoVertex().getLabel()));
	    			while(g.NumberofVertex()>0){
	    				g.removeVertex(graph.getVertex(g.FindIsoVertex().getLabel()).getLabel());
	    			}
	    		}   		
	    	}
	    }
    
   //find clique 
    
	    if(c.vertex.size()>0){
	    	Clique_list.add(c);
	    }    
	    j++;
	    
	    for(int i=0; i<c.vertex.size();i++){
	     graph_clone.removeVertex(c.vertex.get(i).getLabel());   
	     //System.out.println("clique have vertex "+c.vertex.get(i));   
	    }
	    
    }
		return Clique_list; 	
    }

}
