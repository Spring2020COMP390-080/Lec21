package lec21;

public class SimpleTest {

	public static void main(String[] args) {
		Vertex[] vertices = new Vertex[11];
		DirectedGraph g = new DirectedGraphImpl();
		for (int i=0; i<vertices.length; i++) {
			vertices[i] = new LabeledVertex(Integer.toString(i));
			g.addVertex(vertices[i]);
		}
		
		g.addEdge(vertices[0], vertices[2]);
		g.addEdge(vertices[0], vertices[6]);
		g.addEdge(vertices[1], vertices[2]);
		g.addEdge(vertices[1], vertices[5]);
		g.addEdge(vertices[2], vertices[7]);
		g.addEdge(vertices[2], vertices[10]);
		g.addEdge(vertices[3], vertices[9]);
		g.addEdge(vertices[3], vertices[4]);
		g.addEdge(vertices[4], vertices[10]);
		g.addEdge(vertices[5], vertices[7]);
		g.addEdge(vertices[5], vertices[8]);
		g.addEdge(vertices[6], vertices[3]);
		g.addEdge(vertices[6], vertices[4]);
		g.addEdge(vertices[7], vertices[3]);
		g.addEdge(vertices[7], vertices[6]);
		g.addEdge(vertices[7], vertices[9]);
		g.addEdge(vertices[8], vertices[4]);
		g.addEdge(vertices[8], vertices[6]);
		
		System.out.println(TopologicalSort.sortFast(g));
	}
}
