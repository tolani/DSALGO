package com.tolani.Graphs_WIth_DP;

import com.tolani.Graphs.Graph3;
import com.tolani.Graphs.GraphType;
import com.tolani.Graphs.IsGraphWeighted;

import java.util.Arrays;

public class p4_SSSP_Bellman_Ford {

        public static final int INF = 99999;

        public static void main(String[] args)
        {
            Graph3 g = new Graph3(5,GraphType.DIRECTED, IsGraphWeighted.WEIGHTED);

            g.addWeightedEdge(0,1,3);
            g.addWeightedEdge(0,3,6);
            g.addWeightedEdge(1,3,2);
            g.addWeightedEdge(1,2,9);
            g.addWeightedEdge(3,4,4);
            g.addWeightedEdge(4,1,2);
            g.addWeightedEdge(4,2,1);

            g.displayEdgeList();

            System.out.println();
            SSSP_BellmanFordAlgo(g,0);
        }

        public static void SSSP_BellmanFordAlgo(Graph3 g, int src)
        {
            int n = g.getN();

            // Taking a O(n) space array D : which will contain distances frm src to all othr vertices
            int[] D = new int[n];

            // pi function : to print the shortest path : pi fun actually sets the immediate predecessor value
            int[] pi = new int[n];
            Arrays.fill(pi,-1);

            // intialization as : if thr exist a dirct edge then it is w(src,v) else INFINITE
            for(int i=0 ; i < n ; i++)
            {
                Graph3.Edge e = g.getEdge(src,i);     // this will essentially search in the edgelist : by iterating thru edge list

                if(e != null)
                {
                    D[i] = e.getEdgeWeight();

                    // u update the pi value here
                    pi[i] = src;   // bcz to reach 'i' u r goint thru source vertex
                }
                else       // BCZ we are returning null if thr is no such edge present in the edgelist
                {
                    D[i] = INF;
                }
            }

            // now u ve to apply RELAXATION procedure for more V-2 times or (n-2) times bcz in total u apply it (n-1) times
            // abv intialization is already applied it one time : so n-1 -1  = n-2 times left

            for(int k = 2; k <= n ; k++)
            {
                // we culd hv taken every vertex 'v' and then look at all incmng edges to 'v' and apply relaxation : but during this process
                // we came to knw tht every edge is considered exactly once so instead we cn just traverst thru all the edges frm the edgelist
                // exactly once : thts it O(E) time
                // travers thru the edge list and pick up every edge and apply relaxation procedure

                for(Graph3.Edge e : g.allEdges)
                {
                    // consider an edge (u,v) lets say
                    int u = e.getU();
                    int v = e.getV();

                    if(D[u] + e.getEdgeWeight() < D[v])         // here e.getEdgeWeight is essentially a w(u,v)
                    {
                        D[v] = D[u] + e.getEdgeWeight();

                        // set the pi value : as u came inside means u r setting immediate predecsr value for 'v'
                        pi[v] = u;
                    }
                }

            }

            // printing the shortest paths to all othr vertices frm the src node : means printing the array D
            for(int i : D)
            {
                System.out.print(i +  " ");
            }

            // System.out.println(Arrays.toString(pi));          : just to verify pi array

            System.out.println();

            printShortestPath(pi,2);         // printing the shortest path from src vertex to vertex 2
        }

        // recursive function to print the shortest path

        public static void printShortestPath(int[] pi, int v)
        {
            int k = pi[v];     // to reach 'v' , we go thru 'k'

            if(k != -1)        // termination condition for recursion
            {
                System.out.print(k + "<- ");
                printShortestPath(pi,k);
            }
        }
}
