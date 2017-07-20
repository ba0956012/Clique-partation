import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.geom.Point2D;

/**
 *
 * @author Michael Levet
 * @date June 09, 2015
 */
public class DemoGraph {
    
    public static void main(String[] args) throws CloneNotSupportedException, IOException{
        int round = 100;
        int str=100; //number of nodes	
        int end=1000; //number of nodes
    	double bound= 25.0;
        double edge = 5.4*0.866;
        String vertexfile;
        
        for(str=str; str<= end;str=str+100){
        	
        double AvgClique=0;
    	for(int r=1;r<=round;r++){  
    		
    	vertexfile = "c:\\r=2\\region 25,25\\points\\"+str+"\\"+r+".txt";
    		
    	/*
    	//create graph data
    	BuildGraph BG = new BuildGraph();
    	BG.setGraph(edge, str, bound, vertexfile);	
    	*/
    	
    	FileReader fr = new FileReader(vertexfile);
		BufferedReader br = new BufferedReader(fr);
		String s;
		List<Point2D> pl = new ArrayList<>();
    	while (br.ready()){
			s = br.readLine();			
			if(s.contains(",")==true){
			String[] a = s.split(",");
			Point2D p= new Point2D.Double(Double.parseDouble(a[0]),Double.parseDouble(a[1]));
			pl.add(p);
			}
    	}
    	
    	Graph graph = new Graph();    	
    	for(int i = 0; i < pl.size(); i++){
    		Vertex vertices = new Vertex("" + i);
    		vertices.p= pl.get(i);
    		//System.out.println(pl.get(i));
            graph.addVertex(vertices, true, pl.get(i));
        }
    	
    	for(int i = 0; i < pl.size(); i++){
    		for(int j = i+1; j < pl.size(); j++){
        		if(graph.getVertex(""+i).p.distance(graph.getVertex(""+j).p)<=edge){
        			graph.addEdge(graph.getVertex(""+i),graph.getVertex(""+j));
        		}
        	}
    	}
    	
    	
    	for(int i = 0; i < pl.size(); i++){
            // System.out.println(vertices[i]);
             
             for(int j = 0; j < graph.getVertex(""+i).getNeighborCount(); j++){
            //     System.out.println(vertices[i].getNeighbor(j));
             }             
        //     System.out.println();
         }
    	   	
        //initialize some vertices and add them to the graph
    	/*
        Vertex[] vertices = new Vertex[7];
        for(int i = 0; i < vertices.length; i++){
            vertices[i] = new Vertex("" + i);
            graph.addVertex(vertices[i], true);
        }
        */
        //illustrate the fact that duplicate edges aren't added
        /*
        for(int i = 0; i < vertices.length - 1; i++){
            for(int j = i + 1; j < vertices.length; j++){
               graph.addEdge(vertices[i], vertices[j]);
               graph.addEdge(vertices[i], vertices[j]);
               graph.addEdge(vertices[j], vertices[i]);
            }
        }
        */
    	/*
        graph.addEdge(vertices[0], vertices[1]);
        graph.addEdge(vertices[0], vertices[2]);
        graph.addEdge(vertices[0], vertices[6]);
        graph.addEdge(vertices[1], vertices[2]);
        graph.addEdge(vertices[1], vertices[6]);
        graph.addEdge(vertices[2], vertices[3]);
        graph.addEdge(vertices[2], vertices[4]);
        graph.addEdge(vertices[2], vertices[5]);
        graph.addEdge(vertices[3], vertices[4]);
        graph.addEdge(vertices[3], vertices[5]);
        graph.addEdge(vertices[4], vertices[5]);
        //graph.addEdge(vertices[4], vertices[6]);
        graph.addEdge(vertices[5], vertices[6]);
        */
        
        //display the initial setup- all vertices adjacent to each other
        /*
    	for(int i = 0; i < vertices.length; i++){
           // System.out.println(vertices[i]);
            
            for(int j = 0; j < vertices[i].getNeighborCount(); j++){
        //        System.out.println(vertices[i].getNeighbor(j));
            }
            
       //     System.out.println();
        }
        */
    
        //System.out.println("Graph Contains {3, 4} " + graph.containsEdge(new Edge(graph.getVertex("3"), graph.getVertex("4"))));
    /*
        List<Vertex> L = new ArrayList();
        L = new Edge(graph.getVertex("5"), graph.getVertex("4")).getCommonNeighbors();
        System.out.println("Common neighbor:");
        for(int i=0;i<L.size();i++){
        	System.out.println(L.get(i).getLabel());
        }
      */  

    	partation p = new partation();
    	List<clique> Clique_list = new ArrayList();
    	Clique_list = p.cliquePartition(graph);
    	
    	
    	/*//partation
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
        	//System.out.println("clique test 2 "+j);
			//System.out.println("clique have vertex "+c1.vertex.get(0));
          } 
        
        if(graph_clone.NumberofEdge()==0&&graph_clone.NumberofVertex()!=0){
        	//System.out.println(graph_clone.getVertex(""+0));
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
        	 //System.out.println("test ");
        	 //g.ShowGraph();        	 
        	 
             c.addVertex(g.FindIsoVertex());	
             g.removeVertex(g.FindIsoVertex().getLabel());        
           }
        
        else{
        	//System.out.println("test ");
        	//g.ShowGraph();
        	
        		if(g.findPQ()!=null&&g.findPQ().getOne()!=null&&g.findPQ().getTwo()!=null){
        			Edge e = new Edge(g.findPQ().getOne(),g.findPQ().getTwo());	
        			
        			g=g.createCommonNrighborGraph(e.getOne(),e.getTwo());
        			
        			//g.ShowGraph();
        			c.addVertex(graph.getVertex(e.getOne().getLabel()));
        			c.addVertex(graph.getVertex(e.getTwo().getLabel()));     	
        			g.removeVertex(g.findPQ().getOne().getLabel());  	
        			g.removeVertex(e.getTwo().getLabel());   
        		}
        		
        		
        		else if(g.NumberofEdge()==0){
        			//System.out.println("ISO "+g.FindIsoVertex());
        			c.addVertex(graph.getVertex(g.FindIsoVertex().getLabel()));
        			while(g.NumberofVertex()>0){
        				//System.out.println("ISO "+g.NumberofVertex());
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
        
        
        //System.out.println("clique id : "+j);
        for(int i=0; i<c.vertex.size();i++){
         graph_clone.removeVertex(c.vertex.get(i).getLabel());   
         //System.out.println("clique have vertex "+c.vertex.get(i));   
        }
        
        //graph_clone.ShowGraph();      
        
        }
        */ //partation
        
        /*  //check clique
    	for(int tt=0;tt<Clique_list.size();tt++){
        	for(int k=0;k<Clique_list.get(tt).vertex.size();k++){
        		for(int kk=k+1;kk<Clique_list.get(tt).vertex.size();kk++){
        			if(graph.containsEdge(new Edge(Clique_list.get(tt).vertex.get(k),Clique_list.get(tt).vertex.get(kk)))!=true){
        			System.out.println("clique false"); 			
        			}
        			
        			if(Clique_list.get(tt).vertex.get(k).p.distance(Clique_list.get(tt).vertex.get(kk).p)>edge){
        				System.out.println("d false"); 
        			}
        			
        			if(BG.vertex.get(Integer.valueOf(Clique_list.get(tt).vertex.get(k).getLabel())).distance(BG.vertex.get(Integer.valueOf(Clique_list.get(tt).vertex.get(kk).getLabel())))>edge)
        			{
        				System.out.println("distance false"); 
        			}
        		}
        	}
        }
        *///check clique
        
        FileWriter fw = new FileWriter("c:\\"+str+"\\"+r+".txt");
        fw.write(Clique_list.size()+"\r\n");
        for(int t=0;t<Clique_list.size();t++){
        	fw.write(Clique_list.get(t).vertex.size()+"\r\n");
        	for(int k=0;k<Clique_list.get(t).vertex.size();k++){
        	//System.out.println("Clique List "+t+" "+Clique_list.get(t).vertex.get(k));
        		
        		graph.getVertex(Clique_list.get(t).vertex.get(k).getLabel()).p.getX();
        		fw.write(Clique_list.get(t).vertex.get(k).p.getX()+","+Clique_list.get(t).vertex.get(k).p.getY());
        		/*
        		fw.write(BG.vertex.get(Integer.valueOf(Clique_list.get(t).vertex.get(k).getLabel())).getX()+","+
        		BG.vertex.get(Integer.valueOf(Clique_list.get(t).vertex.get(k).getLabel())).getY()+"\r\n");
        		*/
        		//fw.write(Clique_list.get(t).vertex.get(k).x+","+Clique_list.get(t).vertex.get(k).y+"\r\n");
        	}
        }
        fw.close();
        //System.out.println("number of clique:" + Clique_list.size());
        AvgClique=AvgClique+ Clique_list.size();
        //graph.ShowGraph();
    }
    	System.out.println("AVG number of clique," + (AvgClique/round));
    }
    }
}
