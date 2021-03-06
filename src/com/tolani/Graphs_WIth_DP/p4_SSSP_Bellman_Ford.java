/*
*   dont consider code for NSP : no of shortest path which are of same length in below code :
*
*   SOlutin u thought is : no of shortest path : will be an array NSP[] lets say
*
*   now u will intialize NSP by 0 if thr is no dirct edge frm src to the 'v' , else 1 if thr exist a dircet edge frm src to 'v'
*
*   now thr cn be 3 cases :
*
*   1. the path u discoverd is < older path : then u update NSP[v] = NSP[u]    : S ----- u -> v   : path u discovered is going thru 'u'
*
*           here u considerd already computed #of shortest paths to 'u'
*
*   2. the path u discvrd is == older shortest path : then u add : NSP[v] = NSP[v] + NSP[u]   :    so u do additon
*
*   3.                       >                      : then thr is no need to do anything
*
*   problem with this soln is :
*   when u enter into 2nd conditin : now the way in which u written ur bellman ford algo is taversing the same edge list again and again
*   so u will enter for the same parent 'u' into loop again and again : so u need to keep trck tht u shuld nt enter fr the same parent
*   for which u already added the #of shortest paths
*
*   how to solve this problem ? (space efficiently : ask sir)
*
*   so solutin u thought fr this is the p5 program : which is stroing "multiple parent list" for each vertex , so in this case pi will be changed
*                                                   to list of parent vertices for each vertex , this is working !
*
* */

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
            g.addWeightedEdge(0,3,4);   // changed to 4 to chck at least 'l'
            g.addWeightedEdge(1,3,2);
            g.addWeightedEdge(1,2,9);
            g.addWeightedEdge(3,4,4);
            g.addWeightedEdge(4,1,2);
            g.addWeightedEdge(4,2,1);

            System.out.print("edge list : ");
            g.displayEdgeList();
            System.out.println();

            System.out.println();
            SSSP_BellmanFordAlgo(g,0);
        }

        public static void SSSP_BellmanFordAlgo(Graph3 g, int src)
        {
            int n = g.getN();

            // Taking a O(n) space array D : which will contain distances frm src to all othr vertices
            int[] D = new int[n];

            Arrays.fill(D,INF);
            D[0] =0; // src to be 0 to get started :imp

            // pi function : to print the shortest path : pi fun actually sets the immediate predecessor value
            int[] pi = new int[n];
            Arrays.fill(pi,-1);

            /*
            // counting total #of shortest paths
            int[] NSP = new int[n];
            */

            // intialization as : if thr exist a dirct edge then it is w(src,v) else INFINITE
            for(int i=0 ; i < n ; i++)
            {
                Graph3.Edge e = g.getEdge(src,i);     // this will essentially search in the edgelist : by iterating thru edge list

                if(e != null)
                {
                    D[i] = e.getEdgeWeight();

                    // code for pi
                    // u update the pi value here
                    pi[i] = src;   // bcz to reach 'i' u r goint thru source vertex

                    // code for counting NSP
                    // NSP[i] = 1;
                }
                else       // BCZ we are returning null if thr is no such edge present in the edgelist
                {
                    D[i] = INF;

                    // code for counting NSP : if thr is no edge then
                    //NSP[i] = 0;
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

                        // code for NSP : if u discovered a new smaller path thru the vertex 'u' then u update #of shortest paths to be NSP[u}
                        //NSP[v] = NSP[u];
                    }

                    /*
                    // code for counting NSP : else this conditin is nt needed for original prblm
                    else if((D[u] + e.getEdgeWeight()) == D[v]  && pi[v] != u)
                    {
                        // as u r discovring the shortest path of same length then u add the #of shortest paths to the already #of Shortst Path
                        // BUT one imp thing here is to chck tht if it is cmng again thru same immediate predecsr then u shuld not add
                        // thts why we ve put a conditin " pi[v]!= u "  in above else if statement.
                        NSP[v] = NSP[v] + NSP[u];

                       // ? : this wont work bcz edges are going to consider again nd again pi[v] = u;

                        // do we need to store all the parents  : multiple parents and then chck by tht ?
                    }
                    */
                }

            }

            // printing the shortest paths to all othr vertices frm the src node : means printing the array D
            System.out.println("dist array : " + Arrays.toString(D));

            System.out.println("pi array" + Arrays.toString(pi));       //   : just to verify pi array

            System.out.println();

            printShortestPath(pi,2);         // printing the shortest path from src vertex to vertex 2


            // code for NSP
            //System.out.println();
            //System.out.println("no of shortest paths are : " + Arrays.toString(NSP));

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
