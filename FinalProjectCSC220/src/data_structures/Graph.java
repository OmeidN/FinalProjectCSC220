package data_structures;

import java.util.*;

public class Graph<T> {
    private Map<T, List<T>> adjacencyList; // Adjacency list to store the graph

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    // Adds a vertex to the graph
    public void addVertex(T vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    // Removes a vertex and all its edges from the graph
    public void removeVertex(T vertex) {
        adjacencyList.remove(vertex); // Remove the vertex
        for (List<T> neighbors : adjacencyList.values()) {
            neighbors.remove(vertex); // Remove edges pointing to this vertex
        }
    }

    // Adds a directed edge from source to destination
    public void addEdge(T source, T destination) {
        adjacencyList.putIfAbsent(source, new ArrayList<>());
        adjacencyList.putIfAbsent(destination, new ArrayList<>());
        adjacencyList.get(source).add(destination);
    }

    // Removes a directed edge from source to destination
    public void removeEdge(T source, T destination) {
        List<T> neighbors = adjacencyList.get(source);
        if (neighbors != null) {
            neighbors.remove(destination);
        }
    }

    // Retrieves all vertices in the graph
    public Set<T> getVertices() {
        return adjacencyList.keySet();
    }

    // Retrieves neighbors of a given vertex
    public List<T> getNeighbors(T vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    // Checks if the graph contains a vertex
    public boolean containsVertex(T vertex) {
        return adjacencyList.containsKey(vertex);
    }

    // Checks if the graph contains an edge from source to destination
    public boolean containsEdge(T source, T destination) {
        List<T> neighbors = adjacencyList.get(source);
        return neighbors != null && neighbors.contains(destination);
    }

    // Detects if the graph contains a cycle using DFS
    public boolean hasCycle() {
        Set<T> visited = new HashSet<>();
        Set<T> recursionStack = new HashSet<>();

        for (T vertex : adjacencyList.keySet()) {
            if (dfs(vertex, visited, recursionStack)) {
                return true; // Cycle detected
            }
        }
        return false; // No cycles
    }

    // Helper method for cycle detection
    private boolean dfs(T vertex, Set<T> visited, Set<T> recursionStack) {
        if (recursionStack.contains(vertex)) {
            return true; // Cycle found
        }
        if (visited.contains(vertex)) {
            return false; // Already processed
        }

        visited.add(vertex);
        recursionStack.add(vertex);

        for (T neighbor : adjacencyList.getOrDefault(vertex, new ArrayList<>())) {
            if (dfs(neighbor, visited, recursionStack)) {
                return true;
            }
        }

        recursionStack.remove(vertex);
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Graph:\n");
        for (Map.Entry<T, List<T>> entry : adjacencyList.entrySet()) {
            sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
