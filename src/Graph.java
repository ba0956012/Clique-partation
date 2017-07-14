import java.awt.geom.Point2D;
import java.util.*;

/**
 * This class models a simple, undirected graph using an 
 * incidence list representation. Vertices are identified 
 * uniquely by their labels, and only unique vertices are allowed.
 * At most one Edge per vertex pair is allowed in this Graph.
 * 
 * Note that the Graph is designed to manage the Edges. You
 * should not attempt to manually add Edges yourself.
 * 
 * @author Michael Levet
 * @date June 09, 2015
 */
public class Graph {
    
    private HashMap<String, Vertex> vertices;
    private HashMap<Integer, Edge> edges;
    
    public Graph(){
        this.vertices = new HashMap<String, Vertex>();
        this.edges = new HashMap<Integer, Edge>();
    }
    
    /**
     * This constructor accepts an ArrayList<Vertex> and populates
     * this.vertices. If multiple Vertex objects have the same label,
     * then the last Vertex with the given label is used. 
     * 
     * @param vertices The initial Vertices to populate this Graph
     */
    public Graph(ArrayList<Vertex> vertices){
        this.vertices = new HashMap<String, Vertex>();
        this.edges = new HashMap<Integer, Edge>();
        
        for(Vertex v: vertices){
            this.vertices.put(v.getLabel(), v);
        }
        
    }
    
    /**
     * This method adds am edge between Vertices one and two
     * of weight 1, if no Edge between these Vertices already
     * exists in the Graph.
     * 
     * @param one The first vertex to add
     * @param two The second vertex to add
     * @return true iff no Edge relating one and two exists in the Graph
     */
    public boolean addEdge(Vertex one, Vertex two){
        return addEdge(one, two, 1);
    }
    
    
    /**
     * Accepts two vertices and a weight, and adds the edge 
     * ({one, two}, weight) iff no Edge relating one and two 
     * exists in the Graph.
     * 
     * @param one The first Vertex of the Edge
     * @param two The second Vertex of the Edge
     * @param weight The weight of the Edge
     * @return true iff no Edge already exists in the Graph
     */
    public boolean addEdge(Vertex one, Vertex two, int weight){
        if(one.equals(two)){
            return false;   
        }
       
        //ensures the Edge is not in the Graph
        Edge e = new Edge(one, two, weight);
        if(edges.containsKey(e.hashCode())){
            return false;
        }
       
        //and that the Edge isn't already incident to one of the vertices
        else if(one.containsNeighbor(e) || two.containsNeighbor(e)){
            return false;
        }
            
        edges.put(e.hashCode(), e);
        one.addNeighbor(e);
        two.addNeighbor(e);
        return true;
    }
    
    /**
     * 
     * @param e The Edge to look up
     * @return true iff this Graph contains the Edge e
     */
    public boolean containsEdge(Edge e){
        
    	for(Map.Entry entry : this.edges.entrySet()){
    		if(this.edges.get(entry.getKey()).equals(e)){
    			return true;
    		}
    	}
    	
    	return false;
    	/*
    	if(e.getOne() == null || e.getTwo() == null){
            return false;
        }*/
        
        //return this.edges.containsKey(e.hashCode());
    
    }
    
    
    /**
     * This method removes the specified Edge from the Graph,
     * including as each vertex's incidence neighborhood.
     * 
     * @param e The Edge to remove from the Graph
     * @return Edge The Edge removed from the Graph
     */
    public Edge removeEdge(Edge e){
       e.getOne().removeNeighbor(e);
       e.getTwo().removeNeighbor(e);
       return this.edges.remove(e.hashCode());
       
    }
    
    /**
     * 
     * @param vertex The Vertex to look up
     * @return true iff this Graph contains vertex
     */
    public boolean containsVertex(Vertex vertex){
        return this.vertices.get(vertex.getLabel()) != null;
    }
    
    /**
     * 
     * @param label The specified Vertex label
     * @return Vertex The Vertex with the specified label
     */
    public Vertex getVertex(String label){
        return vertices.get(label);
    }
    
    /**
     * This method adds a Vertex to the graph. If a Vertex with the same label
     * as the parameter exists in the Graph, the existing Vertex is overwritten
     * only if overwriteExisting is true. If the existing Vertex is overwritten,
     * the Edges incident to it are all removed from the Graph.
     * 
     * @param vertex
     * @param overwriteExisting
     * @return true iff vertex was added to the Graph
     */
    public boolean addVertex(Vertex vertex, boolean overwriteExisting, Point2D p){
        Vertex current = this.vertices.get(vertex.getLabel());
        if(current != null){
            if(!overwriteExisting){
                return false;
            }
            
            while(current.getNeighborCount() > 0){
                this.removeEdge(current.getNeighbor(0));
            }
        }
        
        
        vertices.put(vertex.getLabel(), vertex);
        return true;
    }
    
    /**
     * 
     * @param label The label of the Vertex to remove
     * @return Vertex The removed Vertex object
     */
    public Vertex removeVertex(String label){      
    	//Vertex v = this.getVertex(label);
       // while(v.getNeighborCount() > 0){
       //     this.removeEdge(v.getNeighbor((0)));
       // }
        List<Edge> L = new ArrayList();
        for(Map.Entry entry : this.edges.entrySet()){
        	if(label.equals(this.edges.get(entry.getKey()).getOne().getLabel())||
        	   label.equals(this.edges.get(entry.getKey()).getTwo().getLabel())){
        		L.add(this.edges.get(entry.getKey()));
        	}    		
    	}
        for(int i=0;i<L.size();i++){
        	this.edges.remove(L.get(i).hashCode());
        	this.getVertex(L.get(i).getOne().getLabel()).removeNeighbor(L.get(i));
        	this.getVertex(L.get(i).getTwo().getLabel()).removeNeighbor(L.get(i));
        }    
        L.clear();
        Vertex v = this.vertices.remove(label);
        return v;
    }
    
    /**
     * 
     * @return Set<String> The unique labels of the Graph's Vertex objects
     */
    public Set<String> vertexKeys(){
        return this.vertices.keySet();
    }
    
    /**
     * 
     * @return Set<Edge> The Edges of this graph
     */
    public Set<Edge> getEdges(){
        return new HashSet<Edge>(this.edges.values());
    }
    
    public Edge findPQ(){
    	int NumberofMaxCN = 0;
    	Edge  e = null;
	
    	for (Map.Entry entry : this.edges.entrySet()){
    	//	for (Map.Entry entry1 : this.vertices.entrySet()){
    	//		System.out.println("size: "+this.edges.get(entry.getKey()).getCommonNeighbors().size());
    	
    			if(this.edges.get(entry.getKey()).getCommonNeighbors().size()>=NumberofMaxCN){
    				{
    					
    					if(e==null){
    						NumberofMaxCN = this.edges.get(entry.getKey()).getCommonNeighbors().size();
    						e = this.edges.get(entry.getKey());
    					}
    						
    					else if((e.getOne().getNeighborCount()+e.getTwo().getNeighborCount()<
        				this.edges.get(entry.getKey()).getOne().getNeighborCount()+this.edges.get(entry.getKey()).getTwo().getNeighborCount()))
    					{	
    						NumberofMaxCN = this.edges.get(entry.getKey()).getCommonNeighbors().size();
    						e = this.edges.get(entry.getKey());
    					}
    				}
    			}	
    	//	}
    	}
    	//System.out.println("test1 :"+e.getOne()+","+e.getTwo());
    	//System.out.println(e.getCommonNeighbors().size());
    	return e; 
    }
    
    public Graph createCommonNrighborGraph(Vertex v1, Vertex v2) throws CloneNotSupportedException {
		Graph sg= new Graph();
		sg = this.clone();
		//sg.findPQ().getOne();
		//sg.findPQ().getTwo();
		List<String> s = new ArrayList();
        
		for(Map.Entry entry : sg.vertices.entrySet()){
			 if(sg.containsEdge(new Edge(v1,sg.vertices.get(entry.getKey())))==false
				||sg.containsEdge(new Edge(v2,sg.vertices.get(entry.getKey())))==false){
				 
				 //System.out.println("test3: "+ sg.vertices.get(entry.getKey()).getLabel());
				 
				 if(v1.getLabel().equals(sg.vertices.get(entry.getKey()).getLabel())==false
					&&v2.getLabel().equals(sg.vertices.get(entry.getKey()).getLabel())==false){
					 s.add(sg.vertices.get(entry.getKey()).getLabel());
					// System.out.println("test4: "+ sg.vertices.get(entry.getKey()).getLabel());
					 //sg.removeVertex(s);
				 }
			 }
			}
		
		/*
		for(Map.Entry entry : sg.vertices.entrySet()){
		 if(sg.containsEdge(new Edge(sg.findPQ().getOne(),sg.vertices.get(entry.getKey())))==false
			||sg.containsEdge(new Edge(sg.findPQ().getTwo(),sg.vertices.get(entry.getKey())))==false){
			 
			 //System.out.println("test3: "+ sg.vertices.get(entry.getKey()).getLabel());
			 
			 if(sg.findPQ().getOne().equals(sg.vertices.get(entry.getKey()))==false
				&&sg.findPQ().getTwo().equals(sg.vertices.get(entry.getKey()))==false){
				 s.add(sg.vertices.get(entry.getKey()).getLabel());
				// System.out.println("test4: "+ sg.vertices.get(entry.getKey()).getLabel());
				 //sg.removeVertex(s);
			 }
		 }
		}			
		*/
		for(int i=0; i<s.size();i++)
			sg.removeVertex(s.get(i));
    	return sg;
    	
    }
    
    public int NumberofVertex(){
    	int n=0;
    	for(Map.Entry entry : this.vertices.entrySet())
    	n++;
		return n;
    }
    public int NumberofEdge(){
    	int n=0;
    	for(Map.Entry entry : this.edges.entrySet())
    	n++;
		return n;
    }
    
    public Graph clone(){
    	Graph g = new Graph();
    	//g.vertices.putAll(this.vertices);
    	g.vertices = (HashMap)this.vertices.clone();
    	g.edges = (HashMap)this.edges.clone();
    	//g.edges.putAll(this.edges);
    	return g;

    }
    
    public void ShowGraph(){
    	for(Map.Entry entry : this.vertices.entrySet()){
    		System.out.println(this.vertices.get(entry.getKey()));
    	}
    	
    	for(Map.Entry entry : this.edges.entrySet()){
    		System.out.println(this.edges.get(entry.getKey()));
    	}
    }
    
    
    public Vertex FindIsoVertex(){
    	
    	if(this.NumberofVertex()==1){
    		for(Map.Entry entry : this.vertices.entrySet()){
    			return	this.getVertex(this.vertices.get(entry.getKey()).getLabel());
    		//return this.vertices.get(this.vertices.get(entry.getKey()));
    		}
    	}
    	else{
    	for(Map.Entry entry : this.vertices.entrySet()){
    		if(this.getVertex(this.vertices.get(entry.getKey()).getLabel()).getNeighbors().size()==1
    			||this.getVertex(this.vertices.get(entry.getKey()).getLabel()).getNeighbors().size()==0){
    			return this.getVertex(this.vertices.get(entry.getKey()).getLabel());
    		}
    		else
    			return null;
    	}
    	}
		return null;
    }
    
}