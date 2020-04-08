package lec21;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class DirectedGraphImpl implements DirectedGraph {

	private Map<Vertex, List<DirectedEdge>> _adj_lists;
	private Map<Vertex, Integer> _in_degrees;
	
	public DirectedGraphImpl() {
		_adj_lists = new HashMap<Vertex, List<DirectedEdge>>();
		_in_degrees = new HashMap<Vertex, Integer>();
	}

	@Override
	public void addVertex(Vertex v) {
		if (hasVertex(v)) {
			// Already in the graph.
			return;
		}
		
		_adj_lists.put(v, new ArrayList<DirectedEdge>());
		_in_degrees.put(v, 0);
	}

	@Override
	public void removeVertex(Vertex v) {
		
		List<DirectedEdge> outbound_edges = _adj_lists.get(v);
		for (DirectedEdge e : outbound_edges) {
			Vertex dest = e.getDestination();
			int dest_in_degree = _in_degrees.get(dest);
			_in_degrees.put(dest, dest_in_degree-1);
		}
		

		if (getInDegreeOfVertex(v) != 0) {
			for (Vertex other : _adj_lists.keySet()) {
				removeEdge(other, v);
			}
		}
		
		_adj_lists.remove(v);
		_in_degrees.remove(v);		
	}

	@Override
	public boolean hasVertex(Vertex v) {
		return _adj_lists.containsKey(v);
	}

	@Override
	public void addEdge(Vertex from, Vertex to) {
		
		if (!hasVertex(from) || !hasVertex(to)) {
			throw new IllegalArgumentException("Either from or to is not in graph");
		}
		
		if (hasEdge(from, to)) {
			return;
		}
		
		_adj_lists.get(from).add(new DirectedEdgeImpl(from, to));
		_in_degrees.put(to, _in_degrees.get(to)+1);
	}

	@Override
	public void removeEdge(Vertex from, Vertex to) {
		DirectedEdge edge = findEdge(from, to);
		
		if (edge != null) {
			_adj_lists.get(from).remove(edge);
			_in_degrees.put(to, _in_degrees.get(to)-1);
		}
	}

	@Override
	public boolean hasEdge(Vertex from, Vertex to) {
		return findEdge(from, to) != null;
	}

	@Override
	public DirectedEdge findEdge(Vertex from, Vertex to) {
		if (!hasVertex(from) || !hasVertex(to)) {
			return null;
		}

		List<DirectedEdge> edge_list = _adj_lists.get(from);
		
		for(DirectedEdge e : edge_list) {
			if (e.getDestination() == to) {
				return e;
			}
		}
		return null;
	}
	
	
	@Override
	public Set<Vertex> getVertices() {
		return _adj_lists.keySet();
	}

	@Override
	public Set<DirectedEdge> getEdges() {
		
		Set<DirectedEdge> result = new HashSet<DirectedEdge>();
		for (List<DirectedEdge> edge_list : _adj_lists.values()) {
			for (DirectedEdge e : edge_list) {
				result.add(e);
			}
		}
		return result;
	}
	
	@Override
	public DirectedGraph clone() {
		DirectedGraph clone = new DirectedGraphImpl();
		for (Vertex v : getVertices()) {
			clone.addVertex(v);
		}
		for (DirectedEdge e : getEdges()) {
			clone.addEdge(e.getSource(), e.getDestination());
		}
		return clone;
	}

	@Override
	public int getInDegreeOfVertex(Vertex v) {
		
		if (!hasVertex(v)) {
			throw new IllegalArgumentException("v not in graph");
		}
		return _in_degrees.get(v);
	}

	@Override
	public Vertex[] getAdjacent(Vertex v) {
		List<DirectedEdge> outbound_edges = _adj_lists.get(v);
		
		Vertex[] adjacent = new Vertex[outbound_edges.size()];
		for (int i=0; i<outbound_edges.size(); i++) {
			adjacent[i] = outbound_edges.get(i).getDestination();
		}
		return adjacent;
	}
}
