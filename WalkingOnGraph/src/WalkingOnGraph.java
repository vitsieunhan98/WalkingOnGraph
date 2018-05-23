import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

class Node implements Comparable<Node> {
	public int V;
	public int D;
	
	public Node (int V, int D) {
		this.V = V;
		this.D = D;
	}

	@Override
	public int compareTo(Node arg0) {
		if (this.D > arg0.D) {
            return 1;
        } else if (this.D < arg0.D) {
            return -1;
        } else {
            return 0;
        }
	}
}

public class WalkingOnGraph {
	static List<List<Integer>> Edges = new ArrayList<List<Integer>>();
	static List<Integer> Distance = new ArrayList<Integer>();
	static int n; //số đỉnh
	static int m; //số cung
	static int a, b, c, d;
	//(a,b) => (c,d)
	static int r; //r>0
	static Scanner sc = new Scanner(System.in);
	
	public static void Input() {
		n = sc.nextInt();
		m = sc.nextInt();
		for(int i=1; i<=m; i++) {
			int g = sc.nextInt(); //đỉnh
			int h = sc.nextInt(); //đỉnh
			int w = sc.nextInt(); //trọng số
			List<Integer> t = new ArrayList<Integer>();
			t.add(g);
			t.add(h);
			Edges.add(t);
			Distance.add(w);
		}
		a = sc.nextInt();
		b = sc.nextInt();
		c = sc.nextInt();
		d = sc.nextInt();
		r = sc.nextInt();
	}
	
	public static int Dijkstra(int s, int des) {
		int[] dist = new int[100];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[s] = 0;
		
		Queue<Node> H = new PriorityQueue<>();
		for(int i=0; i<n; i++) {
			Node node = new Node(s, dist[i]);
			H.add(node);
		}
		
		while(!H.isEmpty()) {
			int u = H.poll().V;
			for(int i=0; i<Edges.size(); i++) {
				if(Edges.get(i).contains(u)) {
					int v = (Edges.get(i).get(0) == u) ? Edges.get(i).get(1) : Edges.get(i).get(0);
					if(dist[v] > dist[u] + Distance.get(i)) {
						dist[v] = dist[u] + Distance.get(i);
						Node newN = new Node(v, dist[v]);
						H.add(newN);
					}
				}
			}
		}
		
		return dist[des];
	}
	
	public static void Walking() {
		int length = 0;
		
		int[] Prev = new int[100000];
		Arrays.fill(Prev, 0);
		
		List<List<Integer>> S = new ArrayList<>();
		
		Queue<List<Integer>> queue = new LinkedList<List<Integer>>();
		boolean p = false;
		boolean[][] check = new boolean[1000][1000];
		
		
		for(int i=1; i<=n; i++) {
			for(int j=1; j<=n; j++) {
				check[i][j] = false;
			}
		}
		
		List<Integer> list = new ArrayList<>();
		list.add(a);
		list.add(b);
		list.add(length++);
		queue.add(list);
		S.add(list);
		check[a][b] = true;
		
		while(!queue.isEmpty()) {
			List<Integer> t = queue.poll();
			int v1 = t.get(0);
			int v2 = t.get(1);
			int parent = t.get(2);
			
			List<Integer> x = new ArrayList<>(); //chứa các đỉnh kề với v1 bao gồm chính nó (robot1)
			List<Integer> y = new ArrayList<>(); //chứa các đỉnh kề với v2 bao gồm chính nó (robot2)
			x.add(v1);
			y.add(v2);
			
			for(int i=0; i<Edges.size(); i++) {
				if(Edges.get(i).contains(v1)) {
					int k = (Edges.get(i).get(0) == v1) ? Edges.get(i).get(1) : Edges.get(i).get(0);
					x.add(k);
				}
				
				if(Edges.get(i).contains(v2)) {
					int k = (Edges.get(i).get(0) == v2) ? Edges.get(i).get(1) : Edges.get(i).get(0);
					y.add(k);
				}
			}
			
			for(int i=0; i<x.size(); i++) {
				for(int j=0; j<y.size(); j++) {
					int V1 = x.get(i);
					int V2 = y.get(j);
					if(Dijkstra(V1, V2) > r && check[V1][V2] == false) {
						List<Integer> child = new ArrayList<>();
						child.add(V1);
						child.add(V2);
						child.add(length);
						
						check[V1][V2] = true;
						
						queue.add(child);
						S.add(child);
						Prev[length++] = parent;
						
						if(V1==c && V2==d) {
							p = true;
							break;
						}
					}
				}
				if(p==true) break;
			}
			if(p==true) break;
		}
		
		if(p == false) {
			System.out.println("Không thể !");
		}
		else {
			List<List<Integer>> Path = new ArrayList<>();
			Path.add(S.get(length-1));
			List<Integer> t = new ArrayList<>(S.get(length-1));
			
			while(!t.equals(S.get(0))) {
				for(List<Integer> i : S) {
					if(Prev[t.get(2)] == i.get(2)) {
						t = new ArrayList<>(i);
						Path.add(i);
						break;
					}
				}
			}
			
			Collections.reverse(Path);
			for(List<Integer> j : Path) {
				System.out.println(j.get(0) + " " + j.get(1));
			}
		}
		
	}
	
	public static void main(String[] args) {
		Input();
		Walking();
	}

}
