
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


public class BuildGraph {

	public List<Point2D> vertex = new ArrayList();
	public List<int[]> edge = new ArrayList();
	public int  numberofVertex;
	public double Bounding, r;
	
	public void setR(double r){
		this.r = r;
	}
	public void setNumberofVertex(int n){
		this.numberofVertex = n;
	}
	public void setBounding(double b){
		this.Bounding = b;
	}
	
	
	public List<Point2D> DeployPoint(){
		for(int i=0; i<numberofVertex;i++){
			Point2D p = new Point2D.Double();
			p.setLocation(Math.random()*Bounding,Math.random()*Bounding);
			//System.out.println(p.getX()+","+p.getY());
			this.vertex.add(p);
		}	
		return vertex;
	}
	
	public List<int[]> setEdge(){
		for(int i=0; i<vertex.size();i++){
			for(int j=i+1; j<vertex.size();j++){
				if(this.vertex.get(i).distance(this.vertex.get(j))<r){
					int e[]={i,j};
					this.edge.add(e);
				}
			}
		}
		return edge;
	}
	
	public BuildGraph setGraph(double r, int n, double b){
		this.setBounding(b);
		this.setNumberofVertex(n);
		this.setR(r);
		this.DeployPoint();
		this.setEdge();
		return this;
	}
	
	
}
