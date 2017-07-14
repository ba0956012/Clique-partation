import java.util.ArrayList;
import java.util.List;


public class clique {
	List<Vertex> vertex = new ArrayList(); 
	
	public void addVertex(Vertex v){
	  int j=0;
	for(int i=0; i<vertex.size();i++){
		if(v.getLabel().toString().equals(vertex.get(i).getLabel().toString()))
		j=1;
	 }
	 if(j==0)
	  vertex.add(v);
	}
	
	
	
}
