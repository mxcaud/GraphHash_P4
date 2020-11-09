import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;

import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.graph.util.EdgeType;

import org.apache.commons.collections15.Factory;

import java.util.ArrayList;
import java.util.Collection;

//You may use your hash map and hash set if you'd like
//or you may import the java.util versions.
//The interface  is the same for both, so the code you
//write here (in ThreeTenGraph) should be the same for
//either one!

//Uncomment the following lines if you want to use the java.util version
//import java.util.HashMap; //or use ThreeTenHashMap!
//import java.util.HashSet; //or use ThreeTenHashSet!

/**
 * Primary class ThreeTen.
 * @param <V> object v.
 * @param <E> object edge.
 */
class ThreeTenGraph<V extends ThreeTenGraphComponent, E extends ThreeTenGraphComponent>
	implements Graph<V, E>, UndirectedGraph<V, E> {
	
	//********************************************************************************
	//   YOUR CODE GOES IN THIS SECTION
	//********************************************************************************
	
	//**************** IMPORTANT WARNING ****************
	//Due to Java complexities with bounded generics that it would be difficult to explain here,
	//if you want an array of V[] or E[], the following format ___SHOULD NOT___ be used:
	//         V[] items = (V[]) new Object[10];
	//instead, use this format:
	//         V[] items = (V[]) new ThreeTenGraphComponent[10];
	/**
	 * Array list of vertices.
	 */
	private ArrayList<V> verti;

	/**
	 * Array list that will contain edge nodes.
	 */
	private ArrayList<ArrayList<E>> edge;

	/**
	 * Array list that will contain chord across from nodes A to B.
	 */
	private ArrayList<Integer[]> edgeCord;

	/**
	 * Edges of cord at destination node.
	 */
	private ArrayList<E> edgeC;

	/**
	 * Begin vertex count at 0.
	 */
	private int vertCount = 0;

	/**
	 * Create a graph that will have size over zero.
	 */
	private int sizeOfGraph = 1; //can assume graphs will be >1


	/**
	 * Creates a new graph. Initializing all appropriate instance variables.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenGraph() {
		verti = new ArrayList<>();
		edge = new ArrayList<>();
		edgeC = new ArrayList<>();

		for(int i = 0; i < sizeOfGraph; i++){
			ArrayList<E> graph;
			graph = new ArrayList<>();
			int count = 0;
			while (count < sizeOfGraph) {
				graph.add(null);
				count++;
			}
			edge.add(graph);
		}
		edgeCord = new ArrayList<>();
	}
	
	/**
	 * Returns a view of all edges in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees 
	 * about the ordering of the vertices within the set.
	 * @return a Collection view of all edges in this graph
	 */
	public Collection<E> getEdges() {
		ArrayList<E> returnEdges = new ArrayList<>();

		for (E edges : edgeC) {
			returnEdges.add(edges);
		}
		return returnEdges;
	}
	
	/**
	 * Returns a view of all vertices in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees 
	 * about the ordering of the vertices within the set.
	 * @return a Collection view of all vertices in this graph
	 */
	public Collection<V> getVertices() {
		return verti;
	}
	
	/**
	 * Returns the number of edges in this graph.
	 * @return the number of edges in this graph
	 */
	public int getEdgeCount() {
		int count  = 0;
		for(int i = 0; i < vertCount; i++){
			int j = 0;
			while (j < vertCount) {
				if(edge.get(i).get(j) != null) count++;
				j++;
			}
		}
		return count;
	}
	
	/**
	 * Returns the number of vertices in this graph.
	 * @return the number of vertices in this graph
	 */
	public int getVertexCount() {
		return verti.size();
	}
	
	/**
	 * Returns the collection of vertices in this graph which are connected to edge.
	 * Note that for some graph types there are guarantees about the size of this collection
	 * (i.e., some graphs contain edges that have exactly two endpoints, which may or may 
	 * not be distinct).  Implementations for those graph types may provide alternate methods 
	 * that provide more convenient access to the vertices
	 * @param edge the edge whose incident vertices are to be returned
	 * @return  the collection of vertices which are connected to edge,
	 */
	public Collection<V> getIncidentVertices(E edge) {
		if (edge != null && edgeC.contains(edge)) {
			ArrayList<V> vertCol = new ArrayList<>();

			for (Integer[] integers : edgeCord) {
				int a = integers[0];
				int b = integers[1];
				if (this.edge.get(a).get(b) != null) {
					if (this.edge.get(a).get(b).equals(edge)) {
						vertCol.add(verti.get(a));
						vertCol.add(verti.get(b));
					}
				}
			}
			return vertCol;
		} else {
			return null;
		}
	}

	/**
	 * Returns the collection of vertices which are connected to vertex
	 * via any edges in this graph.
	 * If vertex is connected to itself with a self-loop, then 
	 * it will be included in the collection returned.
	 * @param vertex the vertex whose neighbors are to be returned
	 * @return  the collection of vertices which are connected to vertex,
	 */
	public Collection<V> getNeighbors(V vertex) {
		if (vertex != null) {
			int index = verti.indexOf(vertex);
			if (index == -1) {
				return null;
			}
			ArrayList<V> colVert = new ArrayList<>();
			int i = 0;
			while (i < vertCount) {
				if (edge.get(index).get(i) != null) {
					colVert.add(verti.get(i));
					i++;
					continue;
				}
				if (edge.get(i).get(index) != null) {
					colVert.add(verti.get(i));
				}
				i++;
			}
			return colVert;
		} else {
			return null;
		}


	}
	
	/**
	 * Returns the collection of edges in this graph which are connected to vertex.
	 * @param vertex the vertex whose incident edges are to be returned
	 * @return  the collection of edges which are connected to vertex,
	 */
	public Collection<E> getIncidentEdges(V vertex) {
		if(vertex == null) return null;
		int position = verti.indexOf(vertex);

		if(position == -1){
			return null;
		}
		ArrayList<E> colVert = new ArrayList<>();
		int i = 0;
		while (i < vertCount) {
			if(edge.get(position).get(i) != null){
				colVert.add(edge.get(position).get(i));
			}
			if(i == position){
				i++;
				continue;
			}
			if(edge.get(i).get(position) != null){
				colVert.add(edge.get(i).get(position));
			}
			i++;
		}
		return colVert;
	}

	/**
	 * Find the edge.
	 * @param v1 object.
	 * @param v2 object.
	 * @return edges of nodes.
	 */
	public E findEdge(V v1, V v2) {
		int getSource = verti.indexOf(v1);
		int getDest = verti.indexOf(v2);
		if(getSource == -1 || getDest == -1){
			return null;
		}
		if(edge.get(getSource).get(getDest) != null) return edge.get(getSource).get(getDest);

		return null;
	}

	/**
	 * Adds edge e to this graph such that it connects 
	 * vertex v1 to v2. 
	 * If this graph does not contain v1, v2, 
	 * or both, implementations may choose to either silently add 
	 * the vertices to the graph or throw an IllegalArgumentException.
	 * If this graph assigns edge types to its edges, the edge type of
	 * e will be the default for this graph.
	 * See Hypergraph.addEdge() for a listing of possible reasons
	 * for failure. In addition, this should fail if the vertices or edge
	 * violates any given restrictions (such as invalid IDs).
	 * @param e the edge to be added
	 * @param v1 the first vertex to be connected
	 * @param v2 the second vertex to be connected
	 * @return true if the add is successful, false otherwise
	 */
	public boolean addEdge(E e, V v1, V v2) {
		//return false;
		assert e != null && v1 != null && v2 != null && !edgeC.contains(e) : "Edge is null or Edge already exist in the Graph";
		int vertFirst = verti.indexOf(v1);
		int vertSec = verti.indexOf(v2);

		if (vertFirst == -1 || vertSec == -1) {
			return false;
		}
		if(edge.get(vertFirst).get(vertSec) != null){
			return false;
		}
		edge.get(vertFirst).set(vertSec,e);
		edgeCord.add(new Integer[]{vertFirst,vertSec});
		edgeC.add(e);
		return true;

	}
	
	/**
	 * Adds vertex to this graph.
	 * Fails if vertex is null or already in the graph.
	 * Also fails if the vertex violates and constraints given in
	 * the project (such as ID restrictions).
	 * @param vertex	the vertex to add
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if vertex is null
	 */
	public boolean addVertex(V vertex) {
		if(vertex == null){
			throw new IllegalArgumentException("Illegal operation, null vertex was added to graph");
		}
		if (!verti.contains(vertex)) {
			expandGraph();
			vertCount++;
			verti.add(vertex);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Expand graph if number of vertices has exceeded initial sizeOfGraph.
	 * See hashMap for more detail.
	 */
	private void expandGraph(){
		int add;
		if (sizeOfGraph != vertCount) {
			return;
		}
		int doubleSize = sizeOfGraph * 2;
		int i = 0;
		while (i < sizeOfGraph) {
			add = 0;
			while(add < sizeOfGraph){
				add++;
				edge.get(i).add(null);
			}
			i++;
		}
		add = 0;
		if (add < sizeOfGraph) {
			do {
				add++;
				int count = 0;
				ArrayList<E> verts = new ArrayList<>();
				while (count < doubleSize) {
					verts.add(null);
					count++;
				}
				edge.add(verts);
			} while (add < sizeOfGraph);
		}
		sizeOfGraph = doubleSize;

	}

	/**
	 * Removes edge from this graph.
	 * Fails if edge is null, or is otherwise not an element of this graph.
	 * @param edge the edge to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeEdge(E edge) {
		if(edge == null) return false;

		int position = edgeC.indexOf(edge);

		if (position == -1) {
			return false;
		}
		int a = edgeCord.get(position)[0];
		int b = edgeCord.get(position)[1];

		this.edge.get(a).set(b,null);
		edgeC.remove(position);
		edgeCord.remove(position);
		return true;
	}
	
	/**
	 * Removes vertex from this graph.
	 * As a side effect, removes any edges e incident to vertex if the 
	 * removal of vertex would cause e to be incident to an illegal
	 * number of vertices.  (Thus, for example, incident hyperedges are not removed, but 
	 * incident edges--which must be connected to a vertex at both endpoints--are removed.)
	 * Fails under the following circumstances:
	 * vertex is not an element of this graph
	 * vertex is null
	 * @param vertex the vertex to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeVertex(V vertex) {
		if (vertex != null) {
			int index = verti.indexOf(vertex);
			if (index != -1) {
				verti.remove(vertex);
				vertCount--;
				int originalSize = edgeCord.size();
				int vertCount = 0;

				while (vertCount < originalSize) {
					int a;
					int b;
					a = edgeCord.get(vertCount)[0];
					b = edgeCord.get(vertCount)[1];
					int alphaChange = edgeCord.get(vertCount)[0];
					int betaChange = edgeCord.get(vertCount)[1];
					if (a < index && b < index) {
						vertCount++;
						continue;
					}
					if (a != index && b != index) {
						if (a > index) {
							alphaChange--;
							edgeCord.get(vertCount)[0] = alphaChange;
						}
						if (b > index) {
							betaChange--;
							edgeCord.get(vertCount)[1] = betaChange;
						}
						if (edge.get(a).get(b).equals(edgeC.get(vertCount))) {
							edge.get(a).set(b, null);
						}
						edge.get(alphaChange).set(betaChange, edgeC.get(vertCount));
						vertCount++;
					} else {
						if (edge.get(a).get(b).equals(edgeC.get(vertCount))) {
							edge.get(a).set(b, null);
						}
						edgeCord.remove(vertCount);
						edgeC.remove(vertCount);
						originalSize--;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------

	/**
	 * Convert to a string.
	 * @return move to string.
	 */
	public String toString() {
		//you may edit this to make string representations of your
		//graph for testing
		return super.toString();
	}

	/**
	 * Tester.
	 * @param args test.
	 */
	public static void main(String[] args) {
		//Some example testing code...
		/**
		 * Class.
		 */
		class Person extends ThreeTenGraphComponent {
			/**
			 * print.
			 * @param id print.
			 */
			public Person(int id) { super(id); }
		}

		/**
		 * Cat.
		 */
		class Cat extends ThreeTenGraphComponent {
			/**
			 * Print the cat.
			 * @param id cat.
			 */
			public Cat(int id) { super(id); }
		}

		/**
		 * ThreeTen.
		 */
		class IntComponent extends ThreeTenGraphComponent {
			/**
			 * IntComp what.
			 * @param id name.
			 */
			public IntComponent(int id) { super(id); }
		}
		
		//constructs a graph
		
		ThreeTenGraph<Person,Cat> graph1 = new ThreeTenGraph<>();
		for(int i = 0; i < 3; i++) {
			graph1.addVertex(new Person(i));
		}
		for(Person p : graph1.getVertices()) {
			graph1.addEdge(new Cat((int)(Math.random()*100)), p, p);
		}
		
		if(graph1.getVertexCount() == 3 && graph1.getEdgeCount() == 3) {
			System.out.println("Yay 1");
		}
		
		//create a set of nodes and edges to test with
		IntComponent[] nodes = {
			new IntComponent(1), 
			new IntComponent(26), 
			new IntComponent(2), 
			new IntComponent(25), 
			new IntComponent(3), 
			new IntComponent(24), 
			new IntComponent(4), 
			new IntComponent(23), 
			new IntComponent(5), 
			new IntComponent(22)
		};
		
		IntComponent[] edges = {
			new IntComponent(10000000), 
			new IntComponent(4), 
			new IntComponent(Integer.MAX_VALUE),
			new IntComponent(Integer.MIN_VALUE), 
			new IntComponent(500), 
			new IntComponent(600000)
		};
		
		//constructs a graph
		ThreeTenGraph<IntComponent,IntComponent> graph2 = new ThreeTenGraph<>();
		for(IntComponent n : nodes) {
			graph2.addVertex(n);
		}
		graph2.addEdge(edges[0],nodes[0],nodes[1]);
		graph2.addEdge(edges[1],nodes[2],nodes[2]);
		graph2.addEdge(edges[2],nodes[3],nodes[4]);
		graph2.addEdge(edges[3],nodes[6],nodes[7]);
		graph2.addEdge(edges[4],nodes[8],nodes[9]);
		graph2.addEdge(edges[5],nodes[9],nodes[0]);
		
		
		if(graph2.containsVertex(new IntComponent(1)) && graph2.containsEdge(new IntComponent(10000000))) {
			System.out.println("Yay 2");
		}
		
		//lot more testing here...
	}
	
	//********************************************************************************
	//   YOU MAY, BUT DON'T NEED TO EDIT THINGS IN THIS SECTION
	// This is a good place to look for "optimizing" your code to be faster.
	//********************************************************************************
	
	/**
	 * Returns true if this graph's vertex collection contains vertex.
	 * Equivalent to getVertices().contains(vertex).
	 * @param vertex the vertex whose presence is being queried
	 * @return true iff this graph contains a vertex vertex
	 */
	public boolean containsVertex(V vertex) {
		return getVertices().contains(vertex);
	}
	
	/**
	 * Returns true if this graph's edge collection contains edge.
	 * Equivalent to getEdges().contains(edge).
	 * @param edge the edge whose presence is being queried
	 * @return true iff this graph contains an edge edge
	 */
	public boolean containsEdge(E edge) {
		return getEdges().contains(edge);
	}

	/**
	 * Returns true if vertex and edge 
	 * are incident to each other.
	 * Equivalent to getIncidentEdges(vertex).contains(edge) and to
	 * getIncidentVertices(edge).contains(vertex).
	 * @param vertex vertex.
	 * @param edge edge.
	 * @return true if vertex and edge
	 */
	public boolean isIncident(V vertex, E edge) {
		return getIncidentEdges(vertex).contains(edge);
	}

	/**
	 * Returns true if v1 and v2 share an incident edge.
	 * Equivalent to getNeighbors(v1).contains(v2).
	 * @param v1 the first vertex to test
	 * @param v2 the second vertex to test
	 * @return true if v1 and v2 share an incident edge
	 */
	public boolean isNeighbor(V v1, V v2) {
		return getNeighbors(v1).contains(v2);
	}
	
	/**
	 * Returns true if v1 is a predecessor of v2 in this graph.
	 * Equivalent to v1.getPredecessors().contains(v2).
	 * @param v1 the first vertex to be queried
	 * @param v2 the second vertex to be queried
	 * @return true if v1 is a predecessor of v2, and false otherwise.
	 */
	public boolean isPredecessor(V v1, V v2) {
		return getPredecessors(v1).contains(v2);
	}
	
	/**
	 * Returns true if v1 is a successor of v2 in this graph.
	 * Equivalent to v1.getSuccessors().contains(v2).
	 * @param v1 the first vertex to be queried
	 * @param v2 the second vertex to be queried
	 * @return true if v1 is a successor of v2, and false otherwise.
	 */
	public boolean isSuccessor(V v1, V v2) {
		return getSuccessors(v1).contains(v2);
	}
	
	/**
	 * Returns the number of edges incident to vertex.  
	 * Special cases of interest:
	 * Incident self-loops are counted once.
	 * If there is only one edge that connects this vertex to
	 * each of its neighbors (and vice versa), then the value returned 
	 * will also be equal to the number of neighbors that this vertex has
	 * (that is, the output of getNeighborCount).
	 * If the graph is directed, then the value returned will be
	 * the sum of this vertex's indegree (the number of edges whose 
	 * destination is this vertex) and its outdegree (the number
	 * of edges whose source is this vertex), minus the number of
	 * incident self-loops (to avoid double-counting).
	 * @param vertex the vertex whose degree is to be returned
	 * @return the degree of this node
	 */
	public int degree(V vertex) {
		return getIncidentEdges(vertex).size();
	}

	/**
	 * Returns the number of vertices that are adjacent to vertex
	 * (that is, the number of vertices that are incident to edges in vertex's
	 * incident edge set).
	 * @param vertex the vertex whose neighbor count is to be returned
	 * @return the number of neighboring vertices
	 */
	public int getNeighborCount(V vertex) {
		return getNeighbors(vertex).size();
	}
	
	/**
	 * Returns the number of incoming edges incident to vertex.
	 * Equivalent to getInEdges(vertex).size().
	 * @param vertex	the vertex whose indegree is to be calculated
	 * @return  the number of incoming edges incident to vertex
	 */
	public int inDegree(V vertex) {
		return getInEdges(vertex).size();
	}
	
	/**
	 * Returns the number of outgoing edges incident to vertex.
	 * Equivalent to getOutEdges(vertex).size().
	 * @param vertex the vertex whose outdegree is to be calculated
	 * @return  the number of outgoing edges incident to vertex
	 */
	public int outDegree(V vertex) {
		return getOutEdges(vertex).size();
	}

	/**
	 * Returns the number of predecessors that vertex has in this graph.
	 * Equivalent to vertex.getPredecessors().size().
	 * @param vertex the vertex whose predecessor count is to be returned
	 * @return  the number of predecessors that vertex has in this graph
	 */
	public int getPredecessorCount(V vertex) {
		return getPredecessors(vertex).size();
	}
	
	/**
	 * Returns the number of successors that vertex has in this graph.
	 * Equivalent to vertex.getSuccessors().size().
	 * @param vertex the vertex whose successor count is to be returned
	 * @return  the number of successors that vertex has in this graph
	 */
	public int getSuccessorCount(V vertex) {
		return getSuccessors(vertex).size();
	}
	
	/**
	 * Returns the vertex at the other end of edge from vertex.
	 * (That is, returns the vertex incident to edge which is not vertex.)
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return the vertex at the other end of edge from vertex
	 */
	public V getOpposite(V vertex, E edge) {
		Pair<V> p = getEndpoints(edge);
		if(p.getFirst().equals(vertex)) {
			return p.getSecond();
		}
		else {
			return p.getFirst();
		}
	}

	/**
	 * Find edge set.
	 * @param v1 yes.
	 * @param v2 test.
	 * @return edge set pair.
	 */
	public Collection<E> findEdgeSet(V v1, V v2) {
		E edge = findEdge(v1, v2);
		if(edge == null) {
			return null;
		}
		
		ArrayList<E> ret = new ArrayList<>();
		ret.add(edge);
		return ret;
		
	}
	
	/**
	 * Returns true if vertex is the source of edge.
	 * Equivalent to getSource(edge).equals(vertex).
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return true iff vertex is the source of edge
	 */
	public boolean isSource(V vertex, E edge) {
		return getSource(edge).equals(vertex);
	}
	
	/**
	 * Returns true if vertex is the destination of edge.
	 * Equivalent to getDest(edge).equals(vertex).
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return true iff vertex is the destination of edge
	 */
	public boolean isDest(V vertex, E edge) {
		return getDest(edge).equals(vertex);
	}

	/**
	 * Control vector space.
	 * @param e type.
	 * @param v1 object.
	 * @param v2 object.
	 * @param edgeType type.
	 * @return all edges.
	 */
	public boolean addEdge(E e, V v1, V v2, EdgeType edgeType) {
		//NOTE: Only undirected edges allowed
		
		if(edgeType == EdgeType.DIRECTED) {
			throw new IllegalArgumentException();
		}
		
		return addEdge(e, v1, v2);
	}

	/**
	 * Adds edge to this graph.
	 * Fails under the following circumstances:
	 * edge is already an element of the graph
	 * either edge or vertices is null
	 * vertices has the wrong number of vertices for the graph type
	 * vertices are already connected by another edge in this graph,
	 * and this graph does not accept parallel edges
	 * @param edge edge.
	 * @param vertices vertices.
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null,
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(E edge, Collection<? extends V> vertices) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}
		
		V[] vs = (V[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1]);
	}

	/**
	 * Adds edge to this graph with type edge type.
	 * Fails under the following circumstances:
	 * edge is already an element of the graph
	 * either edge or vertices is null
	 * vertices has the wrong number of vertices for the graph type
	 * vertices are already connected by another edge in this graph,
	 * and this graph does not accept parallel edges
	 * edge type is not legal for this graph
	 * @param edge edge.
	 * @param vertices vertices.
	 * @param edgeType whatever.
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null,
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(E edge, Collection<? extends V> vertices, EdgeType edgeType) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}
		
		V[] vs = (V[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1], edgeType);
	}
	
	/**
	 * Returns the number of edges of type edgeType in this graph.
	 * @param edgeType the type of edge for which the count is to be returned
	 * @return the number of edges of type edgeType in this graph
	 */
	public int getEdgeCount(EdgeType edgeType) {
		if(edgeType == EdgeType.UNDIRECTED) {
			return getEdgeCount();
		}
		return 0;
	}
	
	/**
	 * Returns the collection of edges in this graph which are of type edgeType.
	 * @param edgeType the type of edges to be returned
	 * @return the collection of edges which are of type edgeType, or
	 */
	public Collection<E> getEdges(EdgeType edgeType) {
		if(edgeType == EdgeType.UNDIRECTED) {
			return getEdges();
		}
		return null;
	}
	
	/**
	 * Returns the number of vertices that are incident to edge.
	 * For hyperedges, this can be any nonnegative integer; for edges this
	 * must be 2 (or 1 if self-loops are permitted).
	 * @param edge the edge whose incident vertex count is to be returned
	 * @return the number of vertices that are incident to edge.
	 */
	public int getIncidentCount(E edge) {
		return getIncidentVertices(edge).size();
	}
	
	/**
	 * If directedEdge is a directed edge in this graph, returns the source;
	 * otherwise returns null. 
	 * The source of a directed edge d is defined to be the vertex for which  
	 * d is an outgoing edge.
	 * directedEdge is guaranteed to be a directed edge if
	 * its EdgeType is DIRECTED. 
	 * @param directedEdge direct.
	 * @return  the source of directedEdge if it is a directed edge in this graph, or null otherwise
	 */
	public V getSource(E directedEdge) {
		return null;
	}

	/**
	 * If directedEdge is a directed edge in this graph, returns the destination;
	 * otherwise returns null. 
	 * The destination of a directed edge d is defined to be the vertex 
	 * incident to d for which  
	 * d is an incoming edge.
	 * directedEdge is guaranteed to be a directed edge if
	 * its EdgeType is DIRECTED. 
	 * @param directedEdge edge.
	 * @return  the destination of directedEdge if it is a directed edge in this graph, or null otherwise
	 */
	public V getDest(E directedEdge) {
		return null;
	}

	/**
	 * Returns a Collection view of the predecessors of vertex 
	 * in this graph.  A predecessor of vertex is defined as a vertex v 
	 * which is connected to 
	 * vertex by an edge e, where e is an outgoing edge of 
	 * v and an incoming edge of vertex.
	 * @param vertex	the vertex whose predecessors are to be returned
	 * @return  a Collection view of the predecessors of
	 */
	public Collection<V> getPredecessors(V vertex) {
		return getNeighbors(vertex);
	}
	
	/**
	 * Returns a Collection view of the successors of vertex 
	 * in this graph. A successor of vertex is defined as a vertex v
	 * which is connected to 
	 * vertex by an edge e, where e is an incoming edge of 
	 * v and an outgoing edge of vertex.
	 * @param vertex	the vertex whose predecessors are to be returned
	 * @return  a Collection view of the successors of
	 */
	public Collection<V> getSuccessors(V vertex) {
		return getNeighbors(vertex);
	}
	
	/**
	 * Returns a Collection view of the incoming edges incident to vertex
	 * in this graph.
	 * @param vertex the vertex whose incoming edges are to be returned
	 * @return a Collection view of the incoming edges incident
	 */
	public Collection<E> getInEdges(V vertex) {
		return getIncidentEdges(vertex);
	}
	
	/**
	 * Returns a Collection view of the outgoing edges incident to vertex
	 * in this graph.
	 * @param vertex	the vertex whose outgoing edges are to be returned
	 * @return  a Collection view of the outgoing edges incident
	 */
	public Collection<E> getOutEdges(V vertex) {
		return getIncidentEdges(vertex);
	}

	/**
	 * Returns the endpoints of edge as a Pair.
	 * @param edge the edge whose endpoints are to be returned.
	 * @return the endpoints (incident vertices) of edge.
	 */
	@SuppressWarnings("unchecked")
	public Pair<V> getEndpoints(E edge) {
		Object[] ends = getIncidentVertices(edge).toArray();
		return new Pair<>((V)ends[0],(V)ends[1]);
	}
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to edit/fix the JavaDocs)
	//********************************************************************************
	
	/**
	 * Returns a {@code Factory} that creates an instance of this graph type.
	 * @param <V> the vertex type for the graph factory
	 * @param <E> the edge type for the graph factory
	 * @return code factory.
	 */
	public static <V extends ThreeTenGraphComponent, E extends ThreeTenGraphComponent> Factory<Graph<V,E>> getFactory() { 
		return new Factory<Graph<V,E>> () {
			public Graph<V,E> create() {
				return new ThreeTenGraph<>();
			}
		};
	}

	/**
	 * Ok code controller.
	 * @param <V> object.
	 * @param <E> edge.
	 * @return code factory control three then.
	 */
	public static <V extends ThreeTenGraphComponent, E extends ThreeTenGraphComponent> Factory<UndirectedGraph<V,E>> getUndirectedFactory() { 
		return new Factory<UndirectedGraph<V,E>> () {
			public UndirectedGraph<V,E> create() {
				return new ThreeTenGraph<>();
			}
		};
	}
	
	/**
	 * Returns the edge type of edge in this graph.
	 * @param edge edges.
	 * @return the EdgeType of edge, or null if edge has no defined type
	 */
	public EdgeType getEdgeType(E edge) {
		return EdgeType.UNDIRECTED;
	}
	
	/**
	 * Returns the default edge type for this graph.
	 * @return the default edge type for this graph
	 */
	public EdgeType getDefaultEdgeType() {
		return EdgeType.UNDIRECTED;
	}
}
