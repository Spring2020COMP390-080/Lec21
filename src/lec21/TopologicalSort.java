package lec21;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;

public class TopologicalSort {

	public static List<Vertex> sortFast(DirectedGraph g) {
		List<Vertex> ts = new ArrayList<Vertex>();

		DirectedGraph working_copy = g.clone();

		Queue<Vertex> vq = new LinkedList<Vertex>();

		/* The following 3 lines were causing the bug at the end
		 * of lecture 21.
		 */
		
//		Vertex init = findZeroInDegreeVertex(working_copy);
//		if (init != null) {
//			vq.add(init);
//		}
		
		for (Vertex v : g.getVertices()) {
			if (g.getInDegreeOfVertex(v) == 0) {
				vq.add(v);
			}
		}
		
		while(vq.size() > 0) {
			Vertex next_vertex = vq.remove();
			ts.add(next_vertex);
			Vertex[] adjacent = working_copy.getAdjacent(next_vertex);
			working_copy.removeVertex(next_vertex);
			for (Vertex v : adjacent) {
				if (working_copy.getInDegreeOfVertex(v) == 0) {
					vq.add(v);
				}
			}
		}
		
		if (working_copy.getVertices().size() != 0) {
			throw new RuntimeException("No topological sort");
		}
		
		return ts;
	}
				
	public static List<Vertex> sortSlow(DirectedGraph g) {
		List<Vertex> ts = new ArrayList<Vertex>();

		DirectedGraph working_copy = g.clone();

		Vertex next_vertex = findZeroInDegreeVertex(working_copy);

		while(next_vertex != null) {
			ts.add(next_vertex);
			working_copy.removeVertex(next_vertex);
			next_vertex = findZeroInDegreeVertex(working_copy);
		}

		if (working_copy.getVertices().size() != 0) {
			throw new RuntimeException("No topological sort");
		}
		
		return ts;
	}

	public static Vertex findZeroInDegreeVertex(DirectedGraph g) {
		for (Vertex v : g.getVertices()) {
			if (g.getInDegreeOfVertex(v) == 0) {
				return v;
			}
		}
		return null;
	}
}
